package Services;

import android.util.Log;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import Interfaces.SettingsServiceInterface;
import Models.UserSettingDTO;
import Network.BaseService;
import Network.CallBack;
import Network.HTTPRequestClass;
import Utilities.Constants;

/**
 * Created by pravina on 21/06/16.
 */
public class SettingsService extends BaseService implements SettingsServiceInterface {

    @Override
    public void getUserSettings(String email, String token, Integer userId, final CallBack<String> callback) throws JSONException {
        HashMap requestMap = new HashMap();
        requestMap.put("email", email);
        requestMap.put("token", token);
        requestMap.put("userId", userId);
        requestMap.put("Content-Type", "application/json");

        HTTPRequestClass getUserSettingsRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL + Constants.kGetUserSettings, null, null, requestMap, null, UserSettingDTO.class);
        super.execute(getUserSettingsRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("User settings success", response);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d("Failure", "response in service");
                callback.onFailure(exception);
            }
        });
    }

    @Override
    public void updateUserSettings(String email, String token, Integer userId, UserSettingDTO userSettingDTO,final CallBack<String> callback) throws JSONException, IOException {
        HashMap headerMap = new HashMap();
        headerMap.put("Content-Type","application/json");
        headerMap.put("email",email);
        headerMap.put("token", token);

        JSONObject jsonBody = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();

        /*try {
            jsonBody.put("userId", userId);
            jsonBody.put("compensationRange", compensationRange);
            jsonBody.put("pushNotification", pushNotification);
            jsonBody.put("jobDiscoveryLimit", jobDiscoveryLimit);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        final String mRequestBody = mapper.writeValueAsString(userSettingDTO);

        HTTPRequestClass updateUserSettingsRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL + Constants.kUpdateUserSettings, null, null, headerMap, mRequestBody, UserSettingDTO.class);
        super.execute(updateUserSettingsRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("User settings success", response);
                callback.onSuccess(response);
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d("Failure", "response in service");
                callback.onFailure(exception);
            }
        });
    }


}
