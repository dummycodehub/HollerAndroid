package Network;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rakeshkoplod on 16/10/15.
 */
public class HTTPRequestClass {

    public static final String BODY_CONTENT_TYPE_APP_JSON = "application/json";

    private String contentType = BODY_CONTENT_TYPE_APP_JSON;
    private Method method = Method.GET;
    private String url;
    private String endPoint;
    private HashMap<String, String> requestParams;
    private HashMap<String, String> headerParams = null;
    private String postBody;
    private Class<?> responseClassType;



    public HTTPRequestClass(Method method, String url, String endPoint,
                         HashMap<String, String> requestParams,
                         HashMap<String, String> headerParams, String postBody,
                         Class<?> responseClassType) {
        Log.d("CURRENT URL ======>",url);
        setMethod(method);
        setUrl(url);
        setEndPoint(endPoint);
        setRequestParams(requestParams);
        setHeaderParams(headerParams);
        setPostBody(postBody);
        setResponseClassType(responseClassType);
    }

    public String getRequestUrl() {
        Uri.Builder uriBuilder = Uri.parse(getUrl() + getEndPoint())
                .buildUpon();
        if (null != getRequestParams() && !getRequestParams().isEmpty()) {
            for (Map.Entry<String, String> requestParam : getRequestParams()
                    .entrySet()) {
                uriBuilder.appendQueryParameter(requestParam.getKey(),
                        requestParam.getValue());
            }
        }
        return uriBuilder.build().toString();
    }

    public String getEndPoint() {
        return endPoint == null ? "" : endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUrl() {
        if (TextUtils.isEmpty(url)) {
//            url = ClFileUtils.getInstance().getString(
//                    ClPhoneApplication.getWebService().ZUMBA_PORTAL_API, null);
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(HashMap<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    public HashMap<String, String> getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(HashMap<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public Class<?> getResponseClassType() {
        return responseClassType;
    }

    public void setResponseClassType(Class<?> responseClassType) {
        this.responseClassType = responseClassType;
    }

    public enum Method {
        GET, POST, PUT, DELETE
    }

}
