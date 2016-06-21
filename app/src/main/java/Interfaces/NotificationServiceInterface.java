package Interfaces;


import org.json.JSONException;

import java.io.IOException;

import Models.UserSettingDTO;
import Network.CallBack;

/**
 * Created by pravina on 21/06/16.
 */
public interface NotificationServiceInterface {

    public void getUserUnreadNotificationCount(String email, String token, Integer userId, CallBack<String> callback) throws JSONException;

    public void getUserNotificationTemplates(String email, String token, Integer userId, CallBack<String> callback) throws JSONException, IOException;

}
