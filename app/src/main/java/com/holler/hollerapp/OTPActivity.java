package com.holler.hollerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Models.CircleProgressBar;
import Network.CallBack;
import Network.ObjectFactory;
import Utilities.Constants;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView timerTextView;
    private CircleProgressBar circularProgress;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        this.progressDialog = new ProgressDialog(OTPActivity.this);

        timerTextView = (TextView)findViewById(R.id.timerLabel);
        circularProgress = (CircleProgressBar)findViewById(R.id.custom_progressBar);

        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("00:" + millisUntilFinished / 1000);
                circularProgress.setProgress(millisUntilFinished/1000);
            }
            public void onFinish() {
                finish();
            }
        }.start();
        getOTP();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ot, menu);
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

    // API Methods
    private void getOTP() {

        this.progressDialog.setMessage("Generating OTP");
        this.progressDialog.show();

        ObjectFactory.getInstance().getUserMgmtInstance().getOTP(new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                progressDialog.dismiss();
                Log.d("Get OTP response", response);
                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(response);
                    if (mainObject.getString("status").equalsIgnoreCase(Constants.kFailure))
                    {
                        Toast.makeText(OTPActivity.this, mainObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        String userOTP = mainObject.getString("otp");
                        //Toast.makeText(OTPActivity.this, "Your OTP is : " + userOTP, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.putExtra("otp",userOTP);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    //  String uniURL = uniObject.getJSONObject("url");
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
