package Network;

/**
 * Created by rakeshkoplod on 16/10/15.
 */
public interface BaseServiceInterface {

    public <T> void execute(HTTPRequestClass request, CallBack<String> callback);
}
