package Network;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.holler.hollerapp.VolleyApplication;

/**
 * Created by rakeshkoplod on 16/10/15.
 */
public class BaseService implements BaseServiceInterface {

    private static RequestQueue queue;
    private final int SOCKET_TIMEOUT_MS = 15 * 1000;// Socket timeout of 15secs.
    private final int MAX_RETRIES = 3;// Maximum 3 retries for the API.

    public BaseService() {
    }

    private synchronized RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(VolleyApplication.getContext());
        }
        return queue;
    }

    @Override
    public <T> void execute(final HTTPRequestClass request, final CallBack<String> callback) {

        Log.d("", "URL HEADER PARAMETERS: " + request.getHeaderParams());
        if (!request.getRequestUrl().contains("null")) {
            StringRequestClass stringRequest = new StringRequestClass(request
                    .getMethod().ordinal(), request.getRequestUrl(),
                    request.getHeaderParams(), request.getPostBody(),
                    request.getContentType(), new Response.Listener<String>() {
                @SuppressWarnings("unchecked")
                @Override
                public void onResponse(String response) {
                    /*if (null != response) {
                        //TODO
                        Log.d("Response is: ",response);
                        //callback.onSuccess(new ResponseParser().parseObject(response,request.getResponseClassType()));
                    }*/
                    if (null != response) {

                        callback.onSuccess(response);
                    }
                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (null != error && error.networkResponse != null
                            && error.networkResponse.data != null)
                    {
                        Log.e("", "Failure Response: " + error);
                        callback.onFailure(new Exception());
                    } else {
                        //ClUtils.hideProgressDialog();
                        callback.onFailure(new Exception());
                    }
                }
            });
            getRequestQueue().add(stringRequest).setRetryPolicy(
                    new DefaultRetryPolicy(SOCKET_TIMEOUT_MS, MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } else {
            //ClUtils.hideProgressDialog();
            //ClUtils.showToast("Please configure server through settings.");
        }
    }
}
