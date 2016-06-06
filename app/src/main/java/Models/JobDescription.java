package Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rakeshkoplod on 22/05/16.
 */
public class JobDescription implements Parcelable {

    private String title;
    private String salary;
    private String description;
    private String address;
    private String date;
    private String time;
    private String gender;
    private String specialNotes;
    private String jobID;

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public JobDescription(String address, String date, String description, String gender, String salary, String specialNotes, String time, String title,String jobID) {
        this.address = address;
        this.date = date;
        this.description = description;
        this.gender = gender;
        this.salary = salary;
        this.specialNotes = specialNotes;
        this.time = time;
        this.title = title;
        this.jobID = jobID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        dest.writeString(this.address);
        dest.writeString(this.date);
        dest.writeString(this.description);
        dest.writeString(this.gender);
        dest.writeString(this.salary);
        dest.writeString(this.specialNotes);
        dest.writeString(this.time);
        dest.writeString(this.title);
        dest.writeString(this.jobID);
    }

    private JobDescription(Parcel in){
        this.address = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.gender = in.readString();
        this.salary = in.readString();
        this.specialNotes = in.readString();
        this.time = in.readString();
        this.title = in.readString();
        this.jobID = in.readString();
    }
    public static final Parcelable.Creator<JobDescription> CREATOR = new Parcelable.Creator<JobDescription>() {

        @Override
        public JobDescription createFromParcel(Parcel source) {
            return new JobDescription(source);
        }

        @Override
        public JobDescription[] newArray(int size) {
            return new JobDescription[size];
        }
    };

}
