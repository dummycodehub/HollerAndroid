package Interfaces;

import java.util.ArrayList;

import Network.CallBack;

/**
 * Created by rakeshkoplod on 04/01/16.
 */
public interface TagsInterface {

    public void getParentTags(String email,String token,CallBack<String> callback);
    public void getAllChildTags(String email,String token,CallBack<String> callback);
    public void getJobsListByTagName(String email,String token,String tagName,CallBack<String> callback);
    public void getSearchedtagsJobsList(String email,String token,String userID,ArrayList<Integer> tagIds,CallBack<String> callback);
    public void postAJob(String email,String token,String userID,String jobTitle,String jobDescription,ArrayList<Integer> tagIds,String compensation,String gender,String jobTime,String specialRequirement,String jobDate,String latitide,String longitude,CallBack<String> callback);
    public void getJobDetails(String email,String token,String jobID,CallBack<String> callback);
    public void getPostedJobs(String email,String token,String userID,CallBack<String> callback);
    public void getAcceptedJobs(String email,String token,String userID,CallBack<String> callback);
    public void acceptJob(String email,String token,String userID,String jobID,String status,CallBack<String> callback);
}
