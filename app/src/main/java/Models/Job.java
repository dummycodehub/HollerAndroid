package Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rakeshkoplod on 10/12/15.
 */
public class Job implements Parcelable {

    private String jobTitle;
    private String category;
    private String salary;
    private String time;
    private String jobId;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Job(String category, String jobTitle, String salary, String time, String jobId) {
        this.category = category;
        this.jobTitle = jobTitle;
        this.jobId = jobId;
        this.salary = salary;
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Storing the Student data to Parcel object
     **/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jobTitle);
        dest.writeString(this.category);
        dest.writeString(this.salary);
        dest.writeString(this.time);
        dest.writeString(this.jobId);
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * Retrieving Student data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private Job(Parcel in){
        this.jobTitle = in.readString();
        this.category = in.readString();
        this.salary = in.readString();
        this.time = in.readString();
        this.jobId = in.readString();
    }
    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {

        @Override
        public Job createFromParcel(Parcel source) {
            return new Job(source);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };


}
