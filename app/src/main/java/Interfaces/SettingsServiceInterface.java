package Interfaces;


import org.json.JSONException;

import java.io.IOException;

import Models.UserSettingDTO;
import Network.CallBack;

/**
 * Created by pravina on 21/06/16.
 */
public interface SettingsServiceInterface {

    public void getUserSettings(String email, String token, Integer userId, CallBack<String> callback) throws JSONException;

    public void updateUserSettings(String email, String token, Integer userId, UserSettingDTO userSettingDTO, CallBack<String> callback) throws JSONException, IOException;

}
