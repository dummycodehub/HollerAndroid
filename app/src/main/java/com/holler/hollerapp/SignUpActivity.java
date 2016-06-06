package com.holler.hollerapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Models.User;
import Network.CallBack;
import Network.ObjectFactory;
import Utilities.Constants;
import Utilities.Utility;


/**
 * Created by rakeshkoplod on 11/12/15.
 */


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    private Button buttonRegister;
    private Button buttonLogin;
    private Toolbar toolbarRegister;

    private CallbackManager callbackManager;
    private LoginButton fbLoginButton;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;


    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;

    private boolean signedInUser;

    private ConnectionResult mConnectionResult;

    private SignInButton signinButton;


    private static final int PICK_MEDIA_REQUEST_CODE = 8;
    private static final int SHARE_MEDIA_REQUEST_CODE = 9;
    private static final int SIGN_IN_REQUEST_CODE = 10;
    private static final int ERROR_DIALOG_REQUEST_CODE = 11;

    private boolean mSignInClicked;

    private EditText fullNameEditText;
    private EditText emailEditText;
    private EditText mobileNumberTextField;

    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        setContentView(R.layout.activity_sing_up);

        this.progressDialog = new ProgressDialog(SignUpActivity.this);

        toolbarRegister = (Toolbar) findViewById(R.id.tool_bar_sing_up); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbarRegister);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        buttonRegister = (Button) findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(this);

        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(this);

        fullNameEditText = (EditText)findViewById(R.id.input_full_name);
        emailEditText = (EditText)findViewById(R.id.input_email);
        mobileNumberTextField = (EditText)findViewById(R.id.input_mobile_number);


        //You need this method to be used only once to configure
        //your key hash in your App Console at
        // developers.facebook.com/apps

        getFbKeyHash("com.holler.hollerapp");

        fbLoginButton = (LoginButton)findViewById(R.id.fb_login_button);

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("Facebook Login Successful!");
                System.out.println("Logged in user Details : ");
                System.out.println("--------------------------");
                System.out.println("User ID  : " + loginResult.getAccessToken().getUserId());
                System.out.println("Authentication Token : " + loginResult.getAccessToken().getToken());
                Toast.makeText(SignUpActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignUpActivity.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(SignUpActivity.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();
                System.out.println("Facebook Login failed!!");
            }
        });

        //Login with Gmail

        signinButton = (SignInButton) findViewById(R.id.signin);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSignIn();
            }
        });

        mGoogleApiClient = buildGoogleAPIClient();

    }

    private void processSignIn() {

        if (!mGoogleApiClient.isConnecting()) {
            processSignInError();
            mSignInClicked = true;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        // make sure to initiate connection
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // disconnect api if it is connected
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    private GoogleApiClient buildGoogleAPIClient() {
        return new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    private void processSignInError() {
        if (mConnectionResult != null && mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this,
                        SIGN_IN_REQUEST_CODE);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    ERROR_DIALOG_REQUEST_CODE).show();
            return;
        }
        if (!mIntentInProgress) {
            mConnectionResult = result;

            if (mSignInClicked) {
                processSignInError();
            }
        }

    }

    /**
     * Callback for GoogleApiClient connection success
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        mSignInClicked = false;
      //  Toast.makeText(getApplicationContext(), "Signed In Successfully",
        //        Toast.LENGTH_LONG).show();

        processUserInfoAndUpdateUI();

        //processUIUpdate(true);

    }

    /**
     * Callback for suspension of current connection
     */
    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();

    }


    /**
     * API to update signed in user information
     */
    private void processUserInfoAndUpdateUI() {
        Person signedInUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (signedInUser != null) {

            if (signedInUser.hasDisplayName()) {
                String userName = signedInUser.getDisplayName();
                //this.userName.setText("Name: " + userName);
                Toast.makeText(getApplicationContext(), "Signed In as "+userName,
                        Toast.LENGTH_SHORT).show();
            }

            if (signedInUser.hasTagline()) {
                String tagLine = signedInUser.getTagline();
                //this.userTagLine.setText("TagLine: " + tagLine);
                //this.userTagLine.setVisibility(View.VISIBLE);
             //   Toast.makeText(getApplicationContext(), "Tag line is "+tagLine,
               //         Toast.LENGTH_SHORT).show();
            }

            if (signedInUser.hasAboutMe()) {
                String aboutMe = signedInUser.getAboutMe();
                // this.userAboutMe.setText("About Me: " + aboutMe);
                //this.userAboutMe.setVisibility(View.VISIBLE);
             //   Toast.makeText(getApplicationContext(), "About me "+aboutMe,
               //         Toast.LENGTH_SHORT).show();
            }

            if (signedInUser.hasBirthday()) {
                String birthday = signedInUser.getBirthday();
                //this.userBirthday.setText("DOB " + birthday);
                //this.userBirthday.setVisibility(View.VISIBLE);
               // Toast.makeText(getApplicationContext(), "Birthday "+birthday,
                 //       Toast.LENGTH_SHORT).show();
            }

            if (signedInUser.hasCurrentLocation()) {
                String userLocation = signedInUser.getCurrentLocation();
                // this.userLocation.setText("Location: " + userLocation);
                // this.userLocation.setVisibility(View.VISIBLE);
            }



            //String userEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
            //this.userEmail.setText("Email: " + userEmail);
            //   Toast.makeText(getApplicationContext(), "Signed Email is "+userEmail,
            //         Toast.LENGTH_SHORT).show();

            /*if (signedInUser.hasImage()) {
                String userProfilePicUrl = signedInUser.getImage().getUrl();
                // default size is 50x50 in pixels.changes it to desired size
                int profilePicRequestSize = 250;

                userProfilePicUrl = userProfilePicUrl.substring(0,
                        userProfilePicUrl.length() - 2) + profilePicRequestSize;
                new UpdateProfilePicTask(userProfilePic)
                        .execute(userProfilePicUrl);
            }*/

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        } else if (requestCode == PICK_MEDIA_REQUEST_CODE) {
            // If picking media is success, create share post using
            // PlusShare.Builder
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                ContentResolver cr = this.getContentResolver();
                String mime = cr.getType(selectedImage);

                PlusShare.Builder share = new PlusShare.Builder(this);
                share.setText("Hello from AndroidSRC.net");
                share.addStream(selectedImage);
                share.setType(mime);
                startActivityForResult(share.getIntent(),
                        SHARE_MEDIA_REQUEST_CODE);
            }
        }
        else if (requestCode == 100)
        {
            if(resultCode == RESULT_OK){
                String userOTP = data.getStringExtra("otp");
               // Toast.makeText(SignUpActivity.this, "OTP : " + userOTP + "Email : "+emailEditText.getText()+" Mobile Number : "+mobileNumberTextField.getText()+" Full Name :"+fullNameEditText.getText(), Toast.LENGTH_LONG).show();
                signUpUser(userOTP);
            }
        }
    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            //displayMessage(profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    @Override
    public void onClick(View v) {

        if (v == buttonLogin)
        {
            finish();
        }

        else
        {
             if (emailEditText.getText().toString().isEmpty() || mobileNumberTextField.getText().toString().isEmpty() || fullNameEditText.getText().toString().isEmpty())
            {
                Toast.makeText(SignUpActivity.this,"Fields should not be empty",Toast.LENGTH_SHORT).show();
            }
            else if (!Utility.isValidEmail(emailEditText.getText().toString()))
            {
                Toast.makeText(SignUpActivity.this,"Please enter valid Email",Toast.LENGTH_SHORT).show();
            }
            else if (mobileNumberTextField.getText().toString().length() > 10 || mobileNumberTextField.getText().toString().length() < 10)
            {
                Toast.makeText(SignUpActivity.this,"Mobile number should be of 10 digits",Toast.LENGTH_SHORT).show();
            }
            else
             {
                 Intent intent = new Intent(this,OTPActivity.class);
                 startActivityForResult(intent, 100);
             }
        }
    }




    public void getFbKeyHash(String packageName) {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
               // Log.d("/rn0m6TJIR79gIT+Hb/ZVR1V3+c= :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("/rn0m6TJIR79gIT+Hb/ZVR1V3+c=: "+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    //This function will option signing intent
    private void signIn() {
        //Creating an intent
        Log.d("signIn","===========================> intent");
      //  Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
      //  startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    // API Methods

    private void signUpUser(String otp)
    {
        this.progressDialog.setMessage("Creating account");
        this.progressDialog.show();
        try {
            ObjectFactory.getInstance().getUserMgmtInstance().signUp(otp, fullNameEditText.getText().toString(), emailEditText.getText().toString(), mobileNumberTextField.getText().toString(), new CallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    progressDialog.hide();
                    JSONObject mainObject = null;
                    try {
                        mainObject = new JSONObject(response);

                        if (mainObject.getString("status").equalsIgnoreCase(Constants.kFailure))
                        {
                            Toast.makeText(SignUpActivity.this,mainObject.getString("message"),Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            JSONObject userObject = mainObject.getJSONObject("result");
                            User user = new User(userObject.getString("email"), userObject.getString("fullName"), userObject.getString("phoneNumber"), userObject.getString("token"), userObject.getString("userID"));
                            SharedPreferences mPrefs = getSharedPreferences(Constants.kSharedPreferenceConstant, MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = mPrefs.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(user);
                            prefsEditor.putString(Constants.kUserObjectPreference, json);
                            prefsEditor.commit();
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        progressDialog.hide();
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Exception exception) {
                    progressDialog.hide();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    /*
     get user's information name, email, profile pic,Date of birth,tag line and about me
     */

   /* private void getProfileInfo() {

        try {

            if (Plus.PeopleApi.getCurrentPerson(google_api_client) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(google_api_client);
                setPersonalInfo(currentPerson);

            } else {
                Toast.makeText(getApplicationContext(),
                        "No Personal info mention", Toast.LENGTH_LONG).show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /*
     set the User information into the views defined in the layout
     */

   /* private void setPersonalInfo(Person currentPerson){

        String personName = currentPerson.getDisplayName();
        String personPhotoUrl = currentPerson.getImage().getUrl();
        String email = Plus.AccountApi.getAccountName(google_api_client);

        progress_dialog.dismiss();
        Toast.makeText(this, "Person information is shown! email = "+email, Toast.LENGTH_LONG).show();
    }*/
}
