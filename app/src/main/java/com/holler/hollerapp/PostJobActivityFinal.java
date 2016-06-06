package com.holler.hollerapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import Models.User;
import Network.CallBack;
import Network.ObjectFactory;
import Utilities.Constants;


public class PostJobActivityFinal extends AppCompatActivity {

    private Toolbar postJobFianlToolBar;
    private Spinner spinnerRupee;
    private EditText specialNotesEditText;
    private Button postJobButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_activity_final);

        postJobFianlToolBar = (Toolbar) findViewById(R.id.tool_bar_post_job_final); // Attaching the layout to the toolbar object
        setSupportActionBar(postJobFianlToolBar);

        specialNotesEditText = (EditText)findViewById(R.id.specialNotesEditText);
        postJobButton = (Button)findViewById(R.id.postJobButton);

        this.progressDialog = new ProgressDialog(PostJobActivityFinal.this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerRupee = (Spinner) findViewById(R.id.rupee_spinner);
        String[] genderItems = new String[] { "100", "200","300","400","500","600","700","800","900" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, genderItems);

        spinnerRupee.setAdapter(adapter);

        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postAJob();
            }
        });

        Intent intentReceived = getIntent();
        Log.d("Tags =====>", "" + intentReceived.getIntegerArrayListExtra("tags"));
        Log.d("Job Title =====>", intentReceived.getStringExtra("title"));
        Log.d("Job Description =====>", intentReceived.getStringExtra("description"));
        Log.d("Job Date =====>", String.valueOf(intentReceived.getLongExtra("date", 0000000000)));
        Log.d("Job Time =====>", String.valueOf(intentReceived.getLongExtra("time", 0000000000)));
        Log.d("Job Lat =====>", String.valueOf(intentReceived.getDoubleExtra("lat", 0000000000)));
        Log.d("Job Lng =====>", String.valueOf(intentReceived.getDoubleExtra("lng", 0000000000)));
        Log.d("Gender =====>", intentReceived.getStringExtra("gender"));

    }

    private void postAJob()
    {
        Intent intentReceived = getIntent();
        Log.d("Tags =====>", "" + intentReceived.getIntegerArrayListExtra("tags"));
        Log.d("Job Title =====>",intentReceived.getStringExtra("title"));
        Log.d("Job Description =====>", intentReceived.getStringExtra("description"));
        Log.d("Job Date =====>", String.valueOf(intentReceived.getLongExtra("date", 0000000000)));
        Log.d("Job Time =====>", String.valueOf(intentReceived.getLongExtra("time", 0000000000)));
        Log.d("Gender =====>", intentReceived.getStringExtra("gender"));

        SharedPreferences mPrefs = this.getSharedPreferences(Constants.kSharedPreferenceConstant, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.kUserObjectPreference, "");
        User userObject = gson.fromJson(json, User.class);

        this.progressDialog.setMessage("Fetching jobs");
        this.progressDialog.show();

        ObjectFactory.getInstance().getTagsServiceInstance().postAJob(userObject.getEmail(),userObject.getToken(),userObject.getUserID(),intentReceived.getStringExtra("title"),intentReceived.getStringExtra("description"), intentReceived.getIntegerArrayListExtra("tags"), "100", intentReceived.getStringExtra("gender"), String.valueOf(intentReceived.getLongExtra("time", 0000000000)),specialNotesEditText.getText().toString(), String.valueOf(intentReceived.getLongExtra("date", 0000000000)), String.valueOf(intentReceived.getDoubleExtra("lat", 0000000000)),String.valueOf(intentReceived.getDoubleExtra("lng", 0000000000)), new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                progressDialog.dismiss();
                progressDialog.hide();
                Toast.makeText(PostJobActivityFinal.this,"Job Posted Successfully",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Exception exception) {
                progressDialog.dismiss();
                progressDialog.hide();
                Toast.makeText(PostJobActivityFinal.this,"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_job_activity_final, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Toast.makeText(this, "Back Clicked", Toast.LENGTH_SHORT).show();
                finish();
            default:
                break;
        }
        return  true;
    }
}
