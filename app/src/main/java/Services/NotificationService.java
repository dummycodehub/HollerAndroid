package Services;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import Interfaces.NotificationServiceInterface;
import Models.Notification;
import Network.BaseService;
import Network.CallBack;
import Network.HTTPRequestClass;
import Utilities.Constants;

/**
 * Created by pravina on 21/06/16.
 */
public class NotificationService extends BaseService implements NotificationServiceInterface {

    @Override
    public void getUserUnreadNotificationCount(String email, String token, Integer userId,final CallBack<String> callback) throws JSONException {
        HashMap requestMap = new HashMap();
        requestMap.put("email", email);
        requestMap.put("token", token);
        requestMap.put("userId", userId);
        requestMap.put("Content-Type", "application/json");

        HTTPRequestClass getkUserUnreadNotificationCount = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL + Constants.kUserUnreadNotificationCount, null, null, requestMap, null, Notification.class);
        super.execute(getkUserUnreadNotificationCount, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("User UnreadNotification", response);
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
    public void getUserNotificationTemplates(String email, String token, Integer userId,final CallBack<String> callback) throws JSONException, IOException {

        HashMap requestMap = new HashMap();
        requestMap.put("email", email);
        requestMap.put("token", token);
        requestMap.put("userId", userId);
        requestMap.put("Content-Type", "application/json");

        HTTPRequestClass getUserSettingsRequest = new HTTPRequestClass(HTTPRequestClass.Method.POST, Constants.kBaseURL + Constants.kFetchUserNotificationTemplates, null, null, requestMap, null, Notification.class);
        super.execute(getUserSettingsRequest, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Notification template", response);
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
