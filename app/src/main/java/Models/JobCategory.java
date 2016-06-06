package Models;

import java.util.ArrayList;

public class JobCategory {

    private String tagName;
    private String tagId;
    public ArrayList<JobSubCategory> childTags = new ArrayList<JobSubCategory>();

    public ArrayList<JobSubCategory> getChildTags() {
        return childTags;
    }

    public void setChildTags(ArrayList<JobSubCategory> childTags) {
        this.childTags = childTags;
    }

    public JobCategory(String tagId, String title) {
        this.tagId = tagId;
        this.tagName = title;
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
}
