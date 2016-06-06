package Network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rakeshkoplod on 16/10/15.
 */
public class StringRequestClass extends StringRequest {

    protected HashMap<String, String> headerParams = null;
    protected String postBody = null;
    protected String postBodyContentType = HTTPRequestClass.BODY_CONTENT_TYPE_APP_JSON;

    public StringRequestClass(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public StringRequestClass(int method, String url,
                           HashMap<String, String> headerParams,
                           String postBody, String postBodyContentType,
                           Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.headerParams = headerParams;
        this.postBody = postBody;
        this.postBodyContentType = postBodyContentType;
    }

    @Override
    public String getBodyContentType() {
        return postBodyContentType == null ? super.getBodyContentType() : postBodyContentType;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return postBody == null ? super.getBody() : postBody.getBytes();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headerParams == null ? super.getHeaders() : headerParams;
    }
}
