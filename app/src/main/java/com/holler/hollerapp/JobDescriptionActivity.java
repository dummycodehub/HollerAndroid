package com.holler.hollerapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Models.Job;
import Models.JobDescription;
import Models.User;
import Network.CallBack;
import Network.ObjectFactory;
import Utilities.Constants;
import de.hdodenhof.circleimageview.CircleImageView;


public class JobDescriptionActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout addressLayout;
    private RelativeLayout specialNotesLayout;
    private RelativeLayout tagsLayout;
    private Toolbar jobDescriptionToolBar;
    private Button showMoreButton,connectButton,acceptButton;
    private CircleImageView userProfileImage;

    private TextView jobTitleTextview;
    private TextView jobDescriptionTextView;
    private TextView jobAddress;
    private TextView jobTime;
    private TextView jobDate;
    private TextView gender;
    private TextView specialNotes;
    private Button salaryButton;

    private ProgressDialog progressDialog;

    JobDescription descriptionObject;

    String isForEdit;

    String jobID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);

        jobDescriptionToolBar = (Toolbar) findViewById(R.id.tool_bar_job_description); // Attaching the layout to the toolbar object
        setSupportActionBar(jobDescriptionToolBar);

        isForEdit = getIntent().getStringExtra("isForEdit");


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.progressDialog = new ProgressDialog(JobDescriptionActivity.this);

        jobTitleTextview = (TextView)findViewById(R.id.job_description_title);
        salaryButton = (Button)findViewById(R.id.button_salary);
        jobDescriptionTextView = (TextView)findViewById(R.id.job_description_content);
        jobAddress = (TextView)findViewById(R.id.job_address);
        jobDate = (TextView)findViewById(R.id.job_date);
        jobTime = (TextView)findViewById(R.id.job_time);
        gender = (TextView)findViewById(R.id.job_gender);
        specialNotes = (TextView)findViewById(R.id.job_special_notes);
        acceptButton = (Button)findViewById(R.id.button_accept);

        userProfileImage = (CircleImageView)findViewById(R.id.job_description_profile_image);
        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobDescriptionActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });


        addressLayout = (RelativeLayout)findViewById(R.id.addressLayout);
        specialNotesLayout = (RelativeLayout)findViewById(R.id.specialNotesLayout);
        tagsLayout = (RelativeLayout)findViewById(R.id.tagsLayout);
        showMoreButton = (Button)findViewById(R.id.button_showMore);
        connectButton = (Button)findViewById(R.id.buttonConnect);

        showMoreButton.setOnClickListener(this);
        connectButton.setOnClickListener(this);
        acceptButton.setOnClickListener(this);

        addressLayout.setVisibility(View.GONE);
        specialNotesLayout.setVisibility(View.GONE);
        tagsLayout.setVisibility(View.GONE);

        getJobDetails();
    }

    private void getJobDetails()
    {
        this.progressDialog.setMessage("Fetching Job Details");
        this.progressDialog.show();
        SharedPreferences mPrefs = this.getSharedPreferences(Constants.kSharedPreferenceConstant, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.kUserObjectPreference, "");
        User userObject = gson.fromJson(json, User.class);

        Intent intentReceived = getIntent();

        ObjectFactory.getInstance().getTagsServiceInstance().getJobDetails(userObject.getEmail(), userObject.getToken(), intentReceived.getStringExtra("jobID"), new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                progressDialog.hide();
                progressDialog.dismiss();
                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(response);

                    if (mainObject.getString("status").equalsIgnoreCase(Constants.kFailure))
                    {
                        Toast.makeText(JobDescriptionActivity.this, mainObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        JSONObject descriptionResponse = mainObject.getJSONObject("result");
                        String requiredGender = "";
                        if (descriptionResponse.getInt("genderRequirement") == 1)
                        {
                            requiredGender = "Male";
                        }
                        else
                        {
                            requiredGender = "Female";
                        }
                        descriptionObject = new JobDescription(descriptionResponse.getString("jobAddress"),getDate(descriptionResponse.getLong("jobdate"), "dd/MM/yyyy"),descriptionResponse.getString("jobDescription"),requiredGender,"Rs."+descriptionResponse.getString("compensation"),descriptionResponse.getString("specialrequirement"),descriptionResponse.getString("jobTimeStamp"),descriptionResponse.getString("title"),descriptionResponse.getString("jobId"));
                        jobTitleTextview.setText(descriptionObject.getTitle());
                        salaryButton.setText(descriptionObject.getSalary());
                        jobDescriptionTextView.setText(descriptionObject.getDescription());
                        jobAddress.setText(descriptionObject.getAddress());
                        jobDate.setText(descriptionObject.getDate());
                        jobTime.setText(descriptionObject.getTime());
                        gender.setText(descriptionObject.getGender());
                        specialNotes.setText(descriptionObject.getSpecialNotes());
                    }
                } catch (JSONException e) {
                    // progressDialog.hide();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception exception) {
                progressDialog.hide();
                progressDialog.dismiss();
            }
        });
    }

    private void acceptJob()
    {
        this.progressDialog.setMessage("Accepting job");
        this.progressDialog.show();
        SharedPreferences mPrefs = this.getSharedPreferences(Constants.kSharedPreferenceConstant, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.kUserObjectPreference, "");
        User userObject = gson.fromJson(json, User.class);

        ObjectFactory.getInstance().getTagsServiceInstance().acceptJob(userObject.getEmail(), userObject.getToken(), userObject.getUserID(), descriptionObject.getJobID(), Constants.kAccepted, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                progressDialog.hide();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Exception exception) {
                progressDialog.hide();
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (isForEdit != null && isForEdit.equalsIgnoreCase("YES"))
        {
            getMenuInflater().inflate(R.menu.menu_job_description, menu);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(JobDescriptionActivity.this, EditPostedJobsActivity.class);
            intent.putExtra("description",descriptionObject);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == showMoreButton)
        {
            if (addressLayout.getVisibility() == View.VISIBLE)
            {
                showMoreButton.setText("Show More");
                addressLayout.setVisibility(View.GONE);
                specialNotesLayout.setVisibility(View.GONE);
                tagsLayout.setVisibility(View.GONE);
            }
            else {
                showMoreButton.setText("Show Less");
                addressLayout.setVisibility(View.VISIBLE);
                specialNotesLayout.setVisibility(View.VISIBLE);
                tagsLayout.setVisibility(View.VISIBLE);
            }

        }
        else if (v == connectButton)
        {
            Intent textShareIntent = new Intent(Intent.ACTION_SEND);
            textShareIntent.putExtra(Intent.EXTRA_TEXT, "Google play store link or jobs link will be shared");
            textShareIntent.setType("text/plain");
            startActivity(textShareIntent);
        }
        else if (v == acceptButton)
        {
            acceptJob();
        }
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
