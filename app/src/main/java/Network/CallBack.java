package Network;

/**
 * Created by rakeshkoplod on 16/10/15.
 */
public interface CallBack<String> {

    public void onSuccess(String response);

    public void onFailure(Exception exception);
}
