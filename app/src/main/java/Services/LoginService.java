package Services;

import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Interfaces.LoginServiceInterface;
import Models.User;
import Network.BaseService;
import Network.CallBack;
import Network.HTTPRequestClass;
import Utilities.Constants;

/**
 * Created by rakeshkoplod on 16/10/15.
 */
public class LoginService extends BaseService implements LoginServiceInterface {
    @Override
    public void login(String otp,String email,String mobileNumber,final CallBack<String> callback) throws JSONException {
            //GtHttpRequest signInRequest = new GtHttpRequest(GtHttpRequest.Method.POST, mWebService.LOGIN_USER, null, postBody, null, null, GtPerson.class);
        //GtHttpManagerFactory.getHttpRequestManager(null).execute(signInRequest, callback);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email",email);
        jsonBody.put("phoneNumber", mobileNumber);
        jsonBody.put("otp", otp);
        final String mRequestBody = jsonBody.toString();

        HashMap headerMap = new HashMap();
        headerMap.put("Content-Type","application/json");

        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST,Constants.kBaseURL+Constants.kLoginURL,null,null,headerMap,mRequestBody, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Success in Service",response);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d("Failure","response in service");
                callback.onFailure(exception);
            }
        });
    }

    @Override
    public void getOTP(final CallBack<String> callback) {

        HashMap requestMap = new HashMap();
        requestMap.put("phoneNumber","9930297373");
        requestMap.put("Content-Type","application/json");
        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL+Constants.kGetOTP,null,null,requestMap,null, User.class);

        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("OTP response in service",response);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d("Failure","response in service");
                callback.onFailure(exception);
            }
        });

    }

    @Override
    public void signUp(String otp,String fullName,String email,String mobileNumber,final CallBack<String> callback) throws JSONException {

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email",email);
        jsonBody.put("name", fullName);
        jsonBody.put("phoneNumber", mobileNumber);
        jsonBody.put("otp", otp);
        final String mRequestBody = jsonBody.toString();

        HashMap headerMap = new HashMap();
        headerMap.put("Content-Type","application/json");
        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST,Constants.kBaseURL+Constants.kSignUpURL,null,null,headerMap,mRequestBody, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Sign Up Success",response);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d("Failure","response in service");
                callback.onFailure(exception);
            }
        });

    }

    /*
    let loginURLString = getStringURLFromDict(["grant_type":"password","username":username,"password":password])!
        let requestURL = "https://api.usergrid.com/avijeet/smartevaluations/token"
        let loginURL = NSURL(string: requestURL)
        let request = NSMutableURLRequest(URL: loginURL!)
        request.HTTPMethod = "POST"
        request.HTTPBody = loginURLString.dataUsingEncoding(NSUTF8StringEncoding)
     */


}
