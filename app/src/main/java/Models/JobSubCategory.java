package Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rakeshkoplod on 09/12/15.
 */
public class JobSubCategory implements Parcelable {
    private String tagId;
    private String tagName;
    private String tagImage;

    public String getTagImage() {
        return tagImage;
    }

    public void setTagImage(String tagImage) {
        this.tagImage = tagImage;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public JobSubCategory(String tagId, String tagName,String tagImage) {

        this.tagId = tagId;
        this.tagName = tagName;
        this.tagImage = tagImage;
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
        dest.writeString(this.tagId);
        dest.writeString(this.tagName);
        dest.writeString(this.tagImage);
    }

    /**
     * Retrieving Student data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     **/
    private JobSubCategory(Parcel in){
        this.tagId = in.readString();
        this.tagName = in.readString();
        this.tagImage = in.readString();
    }
    public static final Parcelable.Creator<JobSubCategory> CREATOR = new Parcelable.Creator<JobSubCategory>() {

        @Override
        public JobSubCategory createFromParcel(Parcel source) {
            return new JobSubCategory(source);
        }

        @Override
        public JobSubCategory[] newArray(int size) {
            return new JobSubCategory[size];
        }
    };

}
