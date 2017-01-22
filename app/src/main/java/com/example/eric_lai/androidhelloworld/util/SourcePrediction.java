package com.example.eric_lai.androidhelloworld.util;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Prediction;

import java.io.File;
import java.util.List;

/**
 * Created by m.ding on 2017-01-21.
 */
public class SourcePrediction {


    public static String[] getStyleAndColor(String path) {

        String[] result = new String[2];

        String appID = "HznAAm_v6uLy6_C98ix3f5ofuTsen6wtqTGg83i2";
        String appSecret = "k9F_x__shus5N7tVMUWSy3nhXuJCWOtO-JsMqt_X";
        final ClarifaiClient client =
                new ClarifaiBuilder(appID, appSecret).buildSync();

        // predict with my clothes model
        final List<ClarifaiOutput<Prediction>> clothesPredictionResults =
                client.predict("clothes")
                        .withInputs(
                                ClarifaiInput.forImage(ClarifaiImage.of(new File(path)))
                        )
                        .executeSync()
                        .get();

        // predict color using public color model
        final List<ClarifaiOutput<Color>> colorPredictionResults =
                client.getDefaultModels().colorModel().predict()
                        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(new File(path))))
                        .executeSync()
                        .get();

        // extract clothes style
        List<Prediction> predictions = clothesPredictionResults.get(0).data();
        float max4Style = 0.0F;
        String style = "";
        for (Prediction e : predictions) {
            String name = e.asConcept().name();
            float value = e.asConcept().value();
            if (value > max4Style) {
                max4Style = value;
                style = name;
            }
        }
        result[0] = style;

        // extract color code
        List<Color> colors = colorPredictionResults.get(0).data();
        float max4Color = 0.0F;
        String colorCode = "";
        for (Color icolor : colors) {
            if (icolor.value() > max4Color) {
                max4Color = icolor.value();
                colorCode = icolor.webSafeHex();
            }
        }
        result[1] = colorCode;

        return result;
    }

    // test
    public static void main(String[] args) {

        String path = "/Users/m.ding/Desktop/testPic.jpg";
        String[] result = getStyleAndColor(path);

        System.out.println(result[0]);
        System.out.println(result[1]);

        System.exit(0);

    }


}
