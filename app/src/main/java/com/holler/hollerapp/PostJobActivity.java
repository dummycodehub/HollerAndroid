package com.holler.hollerapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class PostJobActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar postJobToolBar;
    private Button buttonNext;
    private Button buttonEnterAddress;
    private Button buttonRequestedDate;
    private Button buttonRequestedTime;
    private Spinner spinnerGender;

    private long timeInMiliSeconds;
    private long dateInMiliSeconds;

    double latitide;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        postJobToolBar = (Toolbar) findViewById(R.id.tool_bar_post_job_b); // Attaching the layout to the toolbar object
        setSupportActionBar(postJobToolBar);

        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setOnClickListener(this);

        buttonEnterAddress = (Button) findViewById(R.id.button_enter_location);
        buttonEnterAddress.setOnClickListener(this);

        buttonRequestedDate = (Button) findViewById(R.id.button_date);
        buttonRequestedDate.setOnClickListener(this);

        buttonRequestedTime = (Button) findViewById(R.id.button_time);
        buttonRequestedTime.setOnClickListener(this);

        spinnerGender = (Spinner) findViewById(R.id.gender_spinner);
        String[] genderItems = new String[] { "Male", "Female" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, genderItems);

        spinnerGender.setAdapter(adapter);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_job, menu);
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

    @Override
    public void onClick(View v) {
        if (v == buttonEnterAddress)
        {
            //Toast.makeText(this,"Address",Toast.LENGTH_SHORT).show();
            Intent intentReceived = getIntent();

            Intent intent = new Intent(this, LocationActivity.class);
            /*intent.putExtra("date", buttonRequestedDate.getText().toString());
            intent.putExtra("time",buttonRequestedTime.getText().toString());
            intent.putIntegerArrayListExtra("tags",intentReceived.getIntegerArrayListExtra("tags"));
            intent.putExtra("title", intentReceived.getStringExtra("title"));
            intent.putExtra("description",intentReceived.getStringExtra("description"));*/
            startActivityForResult(intent, 200);
        }
        else if (v == buttonRequestedDate)
        {
            showDatePicker();
        }
        else if (v == buttonRequestedTime)
        {
            showTimePicker();
        }
        else
        {
            Intent intent = new Intent(this, PostJobActivityFinal.class);
            Intent intentReceived = getIntent();
            intent.putExtra("date", dateInMiliSeconds);
            intent.putExtra("time",timeInMiliSeconds);
            if (spinnerGender.getSelectedItem().toString().equalsIgnoreCase("Male"))
            {
                intent.putExtra("gender","1");
            }
            else
            {
                intent.putExtra("gender","0");
            }
            intent.putIntegerArrayListExtra("tags",intentReceived.getIntegerArrayListExtra("tags"));
            intent.putExtra("title", intentReceived.getStringExtra("title"));
            intent.putExtra("description",intentReceived.getStringExtra("description"));
            intent.putExtra("lat",this.latitide);
            intent.putExtra("lng",this.longitude);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if(resultCode == RESULT_OK){
             this.latitide = getIntent().getDoubleExtra("lat",00000);
             this.longitude = getIntent().getDoubleExtra("lng",00000);
         }
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
                        buttonRequestedDate.setText(dayOfMonth + "/"
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
                buttonRequestedTime.setText(selectedHour + ":" + selectedMinute);
                timeInMiliSeconds = mcurrentTime.getTimeInMillis();
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
}
