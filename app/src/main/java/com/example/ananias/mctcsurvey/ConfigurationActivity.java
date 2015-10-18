package com.example.ananias.mctcsurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfigurationActivity extends AppCompatActivity {

    //declare Save Button
    private Button mSaveButton;

    //set keys for question and answers
    private static final String EXTRA_SURVEY_QUESTION = "com.example.ananias.mctcsurvey.survey_question";
    private static final String EXTRA_FIRST_ANSWER_TEXT = "com.example.ananias.mctcsurvey.first_answer_text";
    private static final String EXTRA_SECOND_ANSWER_TEXT = "com.example.ananias.mctcsurvey.second_answer_text";

    public static String getExtraSurveyQuestion(Intent result) {
        return result.getStringExtra(EXTRA_SURVEY_QUESTION);
    }

    public static String getExtraFirstAnswerText(Intent result) {
        return result.getStringExtra(EXTRA_FIRST_ANSWER_TEXT);
    }

    public static String getExtraSecondAnswerText(Intent result)  {
        return result.getStringExtra(EXTRA_SECOND_ANSWER_TEXT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText questionField = (EditText) findViewById(R.id.question_field);
                EditText firstAnswerField = (EditText) findViewById(R.id.answer_1_field);
                EditText secondAnswerField = (EditText) findViewById(R.id.answer_2_field);
                saveSurveyQuestionAndAnswers(questionField.getText().toString(), firstAnswerField.getText().toString(), secondAnswerField.getText().toString());
            }
        });
    }

    private void saveSurveyQuestionAndAnswers(String question, String firstAnswer, String secondAnswer) {
        Intent data = new Intent();
        data.putExtra(EXTRA_SURVEY_QUESTION, question);
        data.putExtra(EXTRA_FIRST_ANSWER_TEXT, firstAnswer);
        data.putExtra(EXTRA_SECOND_ANSWER_TEXT, secondAnswer);
        setResult(RESULT_OK, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
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
