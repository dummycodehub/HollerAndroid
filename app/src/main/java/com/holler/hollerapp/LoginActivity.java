package com.holler.hollerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import Models.User;
import Network.CallBack;
import Network.ObjectFactory;
import Utilities.Constants;
import Utilities.Utility;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private Button buttonLogin;
    private Button buttonRegister;

    private ProgressDialog progressDialog;

    private EditText emailEditText;
    private EditText mobileNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.progressDialog = new ProgressDialog(LoginActivity.this);

        emailEditText = (EditText)findViewById(R.id.input_email);
        mobileNumberEditText = (EditText)findViewById(R.id.input_mobile_number);

        buttonLogin = (Button)findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(this);

        buttonRegister = (Button)findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(this);

        /*ObjectFactory.getInstance().getUserMgmtInstance().login(new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Success in Activity", response);
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d("Failure", "response in actvity");
                Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });*/



    }

    @Override
    public void onClick(View v) {

        if (v == buttonLogin)
        {
            if (emailEditText.getText().toString().isEmpty() || mobileNumberEditText.getText().toString().isEmpty())
            {
                Toast.makeText(LoginActivity.this,"Fields should not be empty",Toast.LENGTH_SHORT).show();
            }
            else if (!Utility.isValidEmail(emailEditText.getText().toString()))
            {
                Toast.makeText(LoginActivity.this,"Please enter valid Email",Toast.LENGTH_SHORT).show();
            }
            else if (mobileNumberEditText.getText().toString().length() > 10 || mobileNumberEditText.getText().toString().length() < 10)
            {
                Toast.makeText(LoginActivity.this,"Mobile number should be of 10 digits",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Intent intent = new Intent(this, HomeActivity.class);
                //startActivity(intent);
                Intent intent = new Intent(this,OTPActivity.class);
                startActivityForResult(intent, 101);
            }

        }
        else if (v == buttonRegister)
        {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101)
        {
            if(resultCode == RESULT_OK){
                String userOTP = data.getStringExtra("otp");
                // Toast.makeText(SignUpActivity.this, "OTP : " + userOTP + "Email : "+emailEditText.getText()+" Mobile Number : "+mobileNumberTextField.getText()+" Full Name :"+fullNameEditText.getText(), Toast.LENGTH_LONG).show();
                loginUser(userOTP);
            }
        }
    }

    private void loginUser(String otp)
    {
        this.progressDialog.setMessage("Logging in");
        this.progressDialog.show();
        try {
            ObjectFactory.getInstance().getUserMgmtInstance().login(otp, emailEditText.getText().toString(), mobileNumberEditText.getText().toString(), new CallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    progressDialog.hide();
                    JSONObject mainObject = null;
                    try {
                        mainObject = new JSONObject(response);

                        if (mainObject.getString("status").equalsIgnoreCase(Constants.kFailure))
                        {
                            Toast.makeText(LoginActivity.this,mainObject.getString("message"),Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            JSONObject userObject = mainObject.getJSONObject("result");
                            User user = new User(userObject.getString("email"), userObject.getString("fullName"), userObject.getString("phoneNumber"), userObject.getString("token"), userObject.getString("userID"));
                            SharedPreferences mPrefs = getSharedPreferences(Constants.kSharedPreferenceConstant,MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(user);
                            prefsEditor.putString(Constants.kUserObjectPreference, json);
                            prefsEditor.commit();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        progressDialog.hide();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
