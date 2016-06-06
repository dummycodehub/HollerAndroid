package Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.DividerItemDecoration;
import Adapters.MyJobsAdapter;
import Interfaces.CategorySelectedInterface;
import Models.Job;
import Models.User;
import Network.CallBack;
import Network.ObjectFactory;
import Utilities.Constants;

import com.google.gson.Gson;
import com.holler.hollerapp.JobDescriptionActivity;
import com.holler.hollerapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab2 extends Fragment implements CategorySelectedInterface{

    private ArrayList<Job> jobsList;
    private RecyclerView acceptedJobsRcyclerView;
    private MyJobsAdapter acceptedJobsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.tab_2,container,false);

        jobsList = new ArrayList<Job>();


        /*for (int i = 0 ; i < 10 ; i++)
        {
            jobsList.add(new Job("Photography","Wedding Photography","$300","2 minutes ago","4"));
        }*/

        acceptedJobsRcyclerView = (RecyclerView) rootView.findViewById(R.id.acceptedJobsRecyclerView);
        acceptedJobsRcyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        acceptedJobsAdapter = new MyJobsAdapter(getContext(),jobsList);
        acceptedJobsAdapter.setCategoryClickListner(this);
        acceptedJobsRcyclerView.setAdapter(acceptedJobsAdapter);
        acceptedJobsRcyclerView.setItemAnimator(new DefaultItemAnimator());
        acceptedJobsRcyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getActivity().getBaseContext(),DividerItemDecoration.VERTICAL_LIST);
        acceptedJobsRcyclerView.addItemDecoration(itemDecoration);

        getAcceptedJobs();

        return rootView;
    }

    private void getAcceptedJobs()
    {
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences(Constants.kSharedPreferenceConstant, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.kUserObjectPreference, "");
        User userObject = gson.fromJson(json, User.class);

        ObjectFactory.getInstance().getTagsServiceInstance().getAcceptedJobs(userObject.getEmail(), userObject.getToken(), userObject.getUserID(), new CallBack<String>() {
            @Override
            public void onSuccess(String response) {

                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(response);

                    if (mainObject.getString("status").equalsIgnoreCase(Constants.kFailure)) {
                        Toast.makeText(getActivity(), mainObject.getString("message"), Toast.LENGTH_LONG).show();
                    } else {
                        JSONArray jobsListResponse = mainObject.getJSONArray("result");
                        jobsList.clear();
                        for (int i = 0; i < jobsListResponse.length(); i++) {
                            JSONObject parentTag = (JSONObject) jobsListResponse.get(i);
                            Job job = new Job("Photography", parentTag.getString("title"), "Rs." + parentTag.getString("compensation"), "2 minutes ago", parentTag.getString("jobId"));
                            jobsList.add(job);
                        }
                        acceptedJobsAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // progressDialog.hide();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }

    @Override
    public void categoryClickedAtPosition(int position) {
        Intent intent = new Intent(this.getContext(),JobDescriptionActivity.class);
        intent.putExtra("jobID", jobsList.get(position).getJobId());
        startActivity(intent);
    }
}