package Network;

/**
 * Created by rakeshkoplod on 16/10/15.
 */
public interface ResponseParserInterface {
    public <T> T parseObject(String data, Class<T> cls);
}
