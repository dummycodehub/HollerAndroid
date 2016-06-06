package Services;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Interfaces.TagsInterface;
import Models.User;
import Network.BaseService;
import Network.CallBack;
import Network.HTTPRequestClass;
import Utilities.Constants;

/**
 * Created by rakeshkoplod on 04/01/16.
 */
public class TagsService extends BaseService implements TagsInterface {

    @Override
    public void getParentTags(String email,String token,final CallBack<String> callback) {

        HashMap requestMap = new HashMap();
        requestMap.put("email",email);
        requestMap.put("token", token);
        requestMap.put("Content-Type","application/json");
        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL+Constants.kGetParentTagsURL,null,null,requestMap,null, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Parent tags success", response);
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
    public void getAllChildTags(String email, String token, final CallBack<String> callback) {
        HashMap requestMap = new HashMap();
        requestMap.put("email",email);
        requestMap.put("token", token);
        requestMap.put("Content-Type","application/json");
        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL+Constants.kGetAllChildTags,null,null,requestMap,null, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Child tags success", response);
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
    public void getJobsListByTagName(String email, String token, String tagName,final CallBack<String> callback) {
        HashMap requestMap = new HashMap();
        requestMap.put("email",email);
        requestMap.put("token", token);
        requestMap.put("tag", tagName);
        requestMap.put("Content-Type","application/json");
        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL+Constants.kGetJobsListURL,null,null,requestMap,null, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Jobs list success", response);
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
    public void getSearchedtagsJobsList(String email,String token,String userID,ArrayList<Integer> tagIds,final CallBack<String> callback) {
        HashMap requestMap = new HashMap();
        requestMap.put("email",email);
        requestMap.put("token", token);
        requestMap.put("Content-Type","application/json");

        JSONObject jsonBody = new JSONObject();
        JSONArray x = new JSONArray();
        for(Integer tagId : tagIds){
            x.put(tagId);
        }
        try {
            jsonBody.put("userId",userID);
            jsonBody.put("tagIds", x);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = jsonBody.toString();
        Log.d("Request Body =======>",mRequestBody);

        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL+Constants.kGetJobsForSearchedTagsURL,null,null,requestMap,mRequestBody, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Jobs list success", response);
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
    public void postAJob(String email,String token,String userID, String jobTitle, String jobDescription, ArrayList<Integer> tagIds, String compensation, String gender, String jobTime, String specialRequirement, String jobDate, String latitide, String longitude,final CallBack<String> callback) {
        HashMap requestMap = new HashMap();
        requestMap.put("email",email);
        requestMap.put("token", token);
        requestMap.put("Content-Type","application/json");

        JSONObject jsonBody = new JSONObject();
        JSONArray x = new JSONArray();
        for(Integer tagId : tagIds){
            x.put(tagId);
        }
        try {
            jsonBody.put("userId",userID);
            jsonBody.put("title",jobTitle);
            jsonBody.put("jobDescription",jobDescription);
            jsonBody.put("compensation",compensation);
            jsonBody.put("tags", x);
            jsonBody.put("genderRequirement",gender);
            jsonBody.put("jobTimeStamp",jobTime);
            jsonBody.put("specialrequirement",specialRequirement);
            jsonBody.put("jobdate",jobDate);
            jsonBody.put("lat",latitide);
            jsonBody.put("lng",longitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = jsonBody.toString();
        Log.d("Request Body =======>",mRequestBody);

        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL+Constants.kPostAJobURL,null,null,requestMap,mRequestBody, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Post job success", response);
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
    public void getJobDetails(String email, String token, String jobID,final CallBack<String> callback) {
        HashMap requestMap = new HashMap();
        requestMap.put("email",email);
        requestMap.put("token", token);
        requestMap.put("jobId", jobID);
        requestMap.put("Content-Type","application/json");
        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL+Constants.kGetJobDetailsURL,null,null,requestMap,null, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Job details success", response);
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
    public void getAcceptedJobs(String email, String token, String userID,final CallBack<String> callback) {
        HashMap requestMap = new HashMap();
        requestMap.put("email",email);
        requestMap.put("token", token);
        requestMap.put("userId", userID);
        requestMap.put("Content-Type","application/json");
        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL+Constants.kGetAcceptedJobs,null,null,requestMap,null, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Accepted jobs success", response);
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
    public void getPostedJobs(String email, String token, String userID, final CallBack<String> callback) {
        HashMap requestMap = new HashMap();
        requestMap.put("email",email);
        requestMap.put("token", token);
        requestMap.put("userId", userID);
        requestMap.put("Content-Type","application/json");
        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL+Constants.kGetPostedJobs,null,null,requestMap,null, User.class);
        super.execute(signInRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Posted jobs success", response);
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
    public void acceptJob(String email, String token, String userID, String jobID, String status, final CallBack<String> callback) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("status", status);
            jsonBody.put("userId", userID);
            jsonBody.put("jobId", jobID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = jsonBody.toString();

        HashMap headerMap = new HashMap();
        headerMap.put("Content-Type","application/json");
        headerMap.put("email",email);
        headerMap.put("token", token);

        HTTPRequestClass signInRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST,Constants.kBaseURL+Constants.kAcceptJobURL,null,null,headerMap,mRequestBody, User.class);
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
}
