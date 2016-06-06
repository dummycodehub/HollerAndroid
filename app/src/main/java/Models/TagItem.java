package Models;

import java.io.Serializable;

public class TagItem implements Serializable {
    private String tagName;
    private String tagID;

    public TagItem(String tagID, String tagName) {
        this.tagID = tagID;
        this.tagName = tagName;
    }

    public String getTagID() {

        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public TagItem(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return tagName;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }



}