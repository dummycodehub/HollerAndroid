package Interfaces;


import org.json.JSONException;

import Network.CallBack;

/**
 * Created by rakeshkoplod on 16/10/15.
 */
public interface LoginServiceInterface {

    public void login(String otp,String email,String mobileNumber,CallBack<String> callback) throws JSONException;
    public void getOTP(CallBack<String> callback);
    public void signUp(String otp,String fullName,String email,String mobileNumber,CallBack<String> callback) throws JSONException;

}
