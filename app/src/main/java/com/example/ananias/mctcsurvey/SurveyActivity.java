package com.example.ananias.mctcsurvey;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SurveyActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_RESULTS = 0;
    private static final int REQUEST_CODE_CONFIGURE = 1;

    //declare survey question
    private TextView mSurveyQuestion;

    //declare answers, Show Results, and Configure buttons
    private Button mFirstAnswerButton;
    private Button mSecondAnswerButton;
    private Button mShowResultsButton;
    private Button mConfigureButton;

    //initialize variables that will keep track of the number of answers
    private int mFirstAnswerCount = 0;
    private int mSecondAnswerCount = 0;

    private SurveyAppJSONSerializer mSerializer;

    private static final String TAG = "SurveyActivity";
    private static final String FILENAME = "surveydata.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mSerializer = new SurveyAppJSONSerializer(this, FILENAME);
        JSONObject jsonObject = null;

        //set a default survey question
        mSurveyQuestion = (TextView) findViewById(R.id.survey_question_text_view);

        //set listener for the FirstAnswer button
        mFirstAnswerButton = (Button) findViewById(R.id.answer_1_button);
        mFirstAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirstAnswerCount += 1; //update answer count
            }
        });

        //set listener for the SecondAnswer button
        mSecondAnswerButton = (Button) findViewById(R.id.answer_2_button);
        mSecondAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSecondAnswerCount += 1; //update answer count
            }
        });

        try {
            jsonObject = mSerializer.loadSurvey();

            if (jsonObject != null) {
                mSurveyQuestion.setText(jsonObject.getString("surveyquestion"));
                mFirstAnswerButton.setText(jsonObject.getString("firstanswer"));
                mSecondAnswerButton.setText(jsonObject.getString("secondanswer"));
                mFirstAnswerCount = jsonObject.getInt("firstanswercount");
                mSecondAnswerCount = jsonObject.getInt("secondanswercount");
            } else {
                mSurveyQuestion.setText("Are the Vikings going to win the Superbowl?");
                mFirstAnswerButton.setText("Yes");
                mSecondAnswerButton.setText("No");
            }
        } catch (Exception e) {
            mSurveyQuestion.setText("Are the Vikings going to win the Superbowl?");
            mFirstAnswerButton.setText("Yes");
            mSecondAnswerButton.setText("No");
        }

        //set listener for Show Results button
        mShowResultsButton = (Button) findViewById(R.id.results_button);
        mShowResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start ResultsActivity
                String answer_1 = (String) mFirstAnswerButton.getText();
                String answer_2 = (String) mSecondAnswerButton.getText();
                Intent i = ResultsActivity.newIntent(SurveyActivity.this, mFirstAnswerCount, mSecondAnswerCount, answer_1, answer_2);
                startActivityForResult(i, REQUEST_CODE_RESULTS);
            }
        });

        //set listener for Configure button
        mConfigureButton = (Button) findViewById(R.id.configure_button);
        mConfigureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start ConfigurationActivity
                Intent i = new Intent(SurveyActivity.this, ConfigurationActivity.class);
                startActivityForResult(i, REQUEST_CODE_CONFIGURE);
            }
        });
    }


    public void saveSurveyData() {
        try {
            mSerializer.saveSurvey(mSurveyQuestion.getText().toString(),
                    mFirstAnswerButton.getText().toString(),
                    mSecondAnswerButton.getText().toString(),
                    mFirstAnswerCount,
                    mSecondAnswerCount);
            Log.d(TAG, "Survey data saved");
        } catch (Exception e) {
            Log.e(TAG, "Error saving survey data");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveSurveyData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CONFIGURE) {
            if (data == null) {
                return;
            }

            mSurveyQuestion.setText(ConfigurationActivity.getExtraSurveyQuestion(data));
            mFirstAnswerButton.setText(ConfigurationActivity.getExtraFirstAnswerText(data));
            mSecondAnswerButton.setText(ConfigurationActivity.getExtraSecondAnswerText(data));
            mFirstAnswerCount = 0;
            mSecondAnswerCount = 0;

        } else if (requestCode == REQUEST_CODE_RESULTS) {

            if (data == null) {
                return;
            }

            if (ResultsActivity.wasCountReset(data)) {
                mFirstAnswerCount = 0;
                mSecondAnswerCount = 0;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_survey, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

