package com.example.ananias.mctcsurvey;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_1_COUNT = "com.example.ananias.mctcsurvey.answer_1_count";
    private static final String EXTRA_ANSWER_2_COUNT = "com.example.ananias.mctcsurvey.answer_2_count";
    private static final String EXTRA_ANSWER_1_TEXT = "com.example.ananias.mctcsurvey.answer_1_text";
    private static final String EXTRA_ANSWER_2_TEXT = "com.example.ananias.mctcsurvey.answer_2_text";

    private static final String EXTRA_RESET_COUNT = "com.example.ananias.mctcsurvey.reset_count";
    private static final String EXTRA_CONTINUE_COUNT = "com.example.ananias.mctcsurvey.continue_count";

    private TextView mAnswer_1_TextView;
    private TextView mAnswer_2_TextView;

    private String mAnswer_1_Text;
    private String mAnswer_2_Text;

    private int mAnswer_1_Count;
    private int mAnswer_2_Count;

    private Button mResetButton;
    private Button mContinueButton;

    private boolean mResetCount;
    private boolean mContinueCount;

    public static Intent newIntent(Context packageContext, int answer_1_Count, int answer_2_Count, String answer_1_text, String answer_2_text) {
        Intent i = new Intent(packageContext, ResultsActivity.class);
        i.putExtra(EXTRA_ANSWER_1_COUNT, answer_1_Count);
        i.putExtra(EXTRA_ANSWER_2_COUNT, answer_2_Count);
        i.putExtra(EXTRA_ANSWER_1_TEXT, answer_1_text);
        i.putExtra(EXTRA_ANSWER_2_TEXT, answer_2_text);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //show how many people answered yes
        mAnswer_1_TextView = (TextView) findViewById(R.id.answer_1_results_text_view);
        mAnswer_1_Text = getIntent().getStringExtra(EXTRA_ANSWER_1_TEXT);
        mAnswer_1_Count = getIntent().getIntExtra(EXTRA_ANSWER_1_COUNT, 0);
        mAnswer_1_TextView.setText(mAnswer_1_Text + ": " + mAnswer_1_Count);

        //show how many people answered no
        mAnswer_2_TextView = (TextView) findViewById(R.id.answer_2_results_text_view);
        mAnswer_2_Text = getIntent().getStringExtra(EXTRA_ANSWER_2_TEXT);
        mAnswer_2_Count = getIntent().getIntExtra(EXTRA_ANSWER_2_COUNT, 0);
        mAnswer_2_TextView.setText(mAnswer_2_Text + ": " + mAnswer_2_Count);

        mResetButton = (Button) findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResetCount = true;
                mAnswer_1_Count = 0;
                mAnswer_1_TextView.setText(mAnswer_1_Text + ": " + mAnswer_1_Count);

                mAnswer_2_Count = 0;
                mAnswer_2_TextView.setText(mAnswer_2_Text + ": " + mAnswer_2_Count);
                setCountResult(mResetCount, mContinueCount);
            }
        });

        mContinueButton = (Button) findViewById(R.id.continue_button);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContinueCount = true;
                setCountResult(mResetCount, mContinueCount);
                finish();
            }
        });
    }

    private void setCountResult(boolean resetCount, boolean continueCount) {
        Intent data = new Intent();
        data.putExtra(EXTRA_RESET_COUNT, resetCount);
        data.putExtra(EXTRA_CONTINUE_COUNT, continueCount);
        setResult(RESULT_OK, data);
    }

    public static boolean wasCountReset (Intent result) {
        return result.getBooleanExtra(EXTRA_RESET_COUNT, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
