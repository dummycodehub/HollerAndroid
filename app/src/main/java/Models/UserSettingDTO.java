package Models;

/**
 * Created by pravina on 21/06/16.
 */
public class UserSettingDTO {

    private Integer userId;
    private Integer compensationRange;
    private Integer pushNotification;
    private Integer jobDiscoveryLimit;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompensationRange() {
        return compensationRange;
    }

    public void setCompensationRange(Integer compensationRange) {
        this.compensationRange = compensationRange;
    }

    public Integer getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(Integer pushNotification) {
        this.pushNotification = pushNotification;
    }

    public Integer getJobDiscoveryLimit() {
        return jobDiscoveryLimit;
    }

    public void setJobDiscoveryLimit(Integer jobDiscoveryLimit) {
        this.jobDiscoveryLimit = jobDiscoveryLimit;
    }


}
