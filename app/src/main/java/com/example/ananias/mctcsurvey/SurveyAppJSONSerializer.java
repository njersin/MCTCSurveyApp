package com.example.ananias.mctcsurvey;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by ananias on 10/17/2015.
 */
public class SurveyAppJSONSerializer {

    private Context mContext;
    private String mFilename;

    public SurveyAppJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public JSONObject loadSurvey() throws IOException, JSONException {
        BufferedReader reader = null;
        JSONObject jsonObject = null;

        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            jsonObject = (JSONObject) new JSONTokener(jsonString.toString()).nextValue();

        } catch (FileNotFoundException e) {

        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return jsonObject;
    }

    public void saveSurvey(String surveyQuestion, String answer_1, String answer_2, int results_1, int results_2)
            throws JSONException, IOException {

        //Build an array of crimes in JSON
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("surveyquestion", surveyQuestion);
        jsonObject.put("firstanswer", answer_1);
        jsonObject.put("secondanswer", answer_2);
        jsonObject.put("firstanswercount", results_1);
        jsonObject.put("secondanswercount", results_2);

        //Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jsonObject.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
