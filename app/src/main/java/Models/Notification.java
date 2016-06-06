package Models;

/**
 * Created by rakeshkoplod on 16/03/16.
 */
public class Notification {

    private String notificationTitle;
    private String notificationTime;

    public Notification(String notificationTime, String notificationTitle) {
        this.notificationTime = notificationTime;
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }
}
