package Network;


import Services.LoginService;
import Services.TagsService;

/**
 * Created by rakeshkoplod on 28/10/15.
 */
public class ObjectFactory {
    private static ObjectFactory mObjectFactory;
    private LoginService mUserMgmt;
    private TagsService tagsService;

    public static synchronized ObjectFactory getInstance() {
        if (null == mObjectFactory) {
            synchronized (ObjectFactory.class) {
                // Double checked singleton lazy initialization.
                mObjectFactory = new ObjectFactory();
            }
        }
        return mObjectFactory;
    }

    public synchronized LoginService getUserMgmtInstance() {
        if (null == this.mUserMgmt) {
            this.mUserMgmt = new LoginService();
        }

        return this.mUserMgmt;
    }

    public synchronized TagsService getTagsServiceInstance() {
        if (null == this.tagsService) {
            this.tagsService = new TagsService();
        }

        return this.tagsService;
    }
}
