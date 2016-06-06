package com.holler.hollerapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import Models.JobDescription;
import Models.User;
import Network.CallBack;
import Network.ObjectFactory;
import Utilities.Constants;

public class EditPostedJobsActivity extends AppCompatActivity {

    private Toolbar editJobToolBar;

    private EditText jobTitleEditText;
    private EditText jobDescriptionEditText;
    private Spinner salarySpinner;
    private Button dateButton;
    private Button timeButton;
    private Button addressButton;
    private Spinner genderSpinner;
    private EditText specialNotesEditText;
    private EditText addTagsEditText;
    private Button updateJobButton;

    private long timeInMiliSeconds;
    private long dateInMiliSeconds;

    private ProgressDialog progressDialog;

    private ArrayList<Integer> tagIds = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_posted_jobs);

        editJobToolBar = (Toolbar) findViewById(R.id.tool_bar_edit_job); // Attaching the layout to the toolbar object
        setSupportActionBar(editJobToolBar);

        this.progressDialog = new ProgressDialog(EditPostedJobsActivity.this);

        jobTitleEditText = (EditText)findViewById(R.id.edit_job_title);
        jobDescriptionEditText = (EditText)findViewById(R.id.edit_job_description_EditText);
        salarySpinner = (Spinner)findViewById(R.id.edit_rupee_spinner);
        dateButton = (Button)findViewById(R.id.edit_date_buttod);
        timeButton = (Button)findViewById(R.id.edit_time_button);
        addressButton = (Button)findViewById(R.id.edit_button_location);
        genderSpinner = (Spinner)findViewById(R.id.edit_gender_spinner);
        specialNotesEditText = (EditText)findViewById(R.id.edit_special_notes_EditText);
        updateJobButton = (Button)findViewById(R.id.button_update);

        updateJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateJob();
            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        JobDescription description = getIntent().getParcelableExtra("description");
        jobTitleEditText.setText(description.getTitle());
        jobDescriptionEditText.setText(description.getDescription());
        dateButton.setText(description.getDate());
        timeButton.setText(description.getTime());
        addressButton.setText(description.getAddress());
        specialNotesEditText.setText(description.getSpecialNotes());

        String[] salaryItems = new String[] { "100", "200","300","400","500","600","700","800","900" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, salaryItems);

        salarySpinner.setAdapter(adapter);
        for (int i = 0 ; i < salaryItems.length ; i++)
        {
            if (salaryItems[i].equalsIgnoreCase(description.getSalary()))
            {
                salarySpinner.setSelection(i);
            }
        }

        String[] genderItems = new String[] { "Male", "Female"};

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, genderItems);

        genderSpinner.setAdapter(genderAdapter);
        if (description.getGender().equalsIgnoreCase("Male"))
        {
            genderSpinner.setSelection(0);
        }
        else
        {
            genderSpinner.setSelection(1);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void updateJob()
    {
        this.progressDialog.setMessage("Updating jobs");
        this.progressDialog.show();
        SharedPreferences mPrefs = this.getSharedPreferences(Constants.kSharedPreferenceConstant, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.kUserObjectPreference, "");
        User userObject = gson.fromJson(json, User.class);

        tagIds.add(4);
        tagIds.add(3);

        ObjectFactory.getInstance().getTagsServiceInstance().postAJob(userObject.getEmail(), userObject.getToken(), userObject.getUserID(), jobTitleEditText.getText().toString(), jobDescriptionEditText.getText().toString(), tagIds, salarySpinner.getSelectedItem().toString(), ""+genderSpinner.getSelectedItemPosition(), String.valueOf(timeInMiliSeconds), specialNotesEditText.getText().toString(), String.valueOf(dateInMiliSeconds), "18.3", "24.10", new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                progressDialog.dismiss();
                progressDialog.hide();
                Toast.makeText(EditPostedJobsActivity.this, "Job Updated Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Exception exception) {
                progressDialog.dismiss();
                progressDialog.hide();
                Toast.makeText(EditPostedJobsActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showDatePicker()
    {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(this,R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        dateButton.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                        dateInMiliSeconds = c.getTimeInMillis();
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void showTimePicker()
    {
        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeButton.setText(selectedHour + ":" + selectedMinute);
                timeInMiliSeconds = mcurrentTime.getTimeInMillis();
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
}
