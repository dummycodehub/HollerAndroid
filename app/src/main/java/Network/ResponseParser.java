package Network;

import android.text.TextUtils;
import android.util.Log;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by rakeshkoplod on 16/10/15.
 */
public class ResponseParser implements ResponseParserInterface {

    @Override
    public <T> T parseObject(String data, Class<T> cls) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (!TextUtils.isEmpty(data)) {
                return mapper.readValue(data, cls);
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.e("", e.getLocalizedMessage());
        }
        return null;
    }
}
