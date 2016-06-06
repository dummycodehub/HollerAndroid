package com.holler.hollerapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kpbird.chipsedittextlibrary.ChipsAdapter;
import com.kpbird.chipsedittextlibrary.ChipsItem;
import com.kpbird.chipsedittextlibrary.ChipsMultiAutoCompleteTextview;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.JobsListAdapter;
import Interfaces.CategorySelectedInterface;
import Models.Job;
import Models.JobCategory;
import Models.JobSubCategory;
import Models.TagItem;
import Models.User;
import Network.CallBack;
import Network.ObjectFactory;
import Utilities.Constants;
import Utilities.DBController;

public class JobsList extends AppCompatActivity implements CategorySelectedInterface,TokenCompleteTextView.TokenListener<TagItem> {

    private Toolbar toolbarJobsList;
    private ArrayList<Job> jobsList;
    private RecyclerView jobsRcyclerView;
    private JobsListAdapter jobsListAdapter;

    private ProgressDialog progressDialog;
    private ArrayList<Integer> tagIds = new ArrayList<Integer>();

    ContactsCompletionView completionView;
    TagItem[] people;
    ArrayAdapter<TagItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list2);

        this.progressDialog = new ProgressDialog(JobsList.this);

        toolbarJobsList = (Toolbar) findViewById(R.id.tool_bar_jobs_list); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbarJobsList);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Search tags code
        final DBController controller = new DBController(this);
        ArrayList<TagItem> tagItems = new ArrayList<TagItem>();
        tagItems = controller.getAllTags();
        people = new TagItem[tagItems.size()];
        for (int j = 0; j < tagItems.size(); j++) {
            people[j] = tagItems.get(j);
        }

        adapter = new FilteredArrayAdapter<TagItem>(JobsList.this, android.R.layout.simple_list_item_1, people) {
            @Override
            protected boolean keepObject(TagItem obj, String mask) {
                mask = mask.toLowerCase();
                return obj.getTagName().toLowerCase().startsWith(mask);
            }
        };
        completionView = (ContactsCompletionView)findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);

        completionView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tagIds.clear();
                    for (int i = 0; i < completionView.getObjects().size(); i++) {
                        TagItem item = completionView.getObjects().get(i);
                        tagIds.add(Integer.parseInt(item.getTagID()));

                    }
                    if (tagIds.size() > 0) {
                        getJobsForSearchedTags();
                    }
                }
                return false;
            }
        });

        jobsList = new ArrayList<Job>();

       /* for (int i = 0 ; i < 10 ; i++)
        {
            jobsList.add(new Job("Photography","Wedding Photography","$300","2 minutes ago"));
        }*/




        Intent intent = getIntent();

        if (intent.getBooleanExtra("isForJobSearch",false) == true)
        {
           jobsList = intent.getParcelableArrayListExtra("jobsList");
            Log.d("Jobs list received",""+jobsList.size());
            jobsRcyclerView = (RecyclerView) findViewById(R.id.recyclerViewJobsList);
            jobsRcyclerView.setLayoutManager(new LinearLayoutManager(this));
            jobsListAdapter = new JobsListAdapter(this,jobsList);
            jobsRcyclerView.setAdapter(jobsListAdapter);
            jobsListAdapter.setCategoryClickListner(this);
            jobsListAdapter.notifyDataSetChanged();
        }
        else
        {
            jobsRcyclerView = (RecyclerView) findViewById(R.id.recyclerViewJobsList);
            jobsRcyclerView.setLayoutManager(new LinearLayoutManager(this));
            jobsListAdapter = new JobsListAdapter(this,jobsList);
            jobsRcyclerView.setAdapter(jobsListAdapter);
            jobsListAdapter.setCategoryClickListner(this);
            getJobsListbyTagName(intent.getStringExtra("tagName"));
        }


    }

    private void getJobsListbyTagName(final String tagName)
    {
        this.progressDialog.setMessage("Fetching Jobs");
        this.progressDialog.show();
        SharedPreferences mPrefs = getSharedPreferences(Constants.kSharedPreferenceConstant, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.kUserObjectPreference, "");

        User userObject = gson.fromJson(json, User.class);
        ObjectFactory.getInstance().getTagsServiceInstance().getJobsListByTagName(userObject.getEmail(), userObject.getToken(), tagName, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                progressDialog.hide();
                progressDialog.dismiss();
                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(response);

                    if (mainObject.getString("status").equalsIgnoreCase(Constants.kFailure))
                    {
                        Toast.makeText(JobsList.this, mainObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        JSONArray jobsListResponse = mainObject.getJSONArray("result");
                        for (int i = 0 ; i <= jobsListResponse.length() ; i++) {
                            JSONObject parentTag = (JSONObject) jobsListResponse.get(i);
                            Job job = new Job(tagName, parentTag.getString("title"),parentTag.getString("compensation"),"2 minutes ago",parentTag.getString("jobId"));
                            jobsList.add(job);
                        }
                        jobsListAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // progressDialog.hide();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception exception) {
                progressDialog.hide();
                progressDialog.dismiss();
            }
        });
    }

    public void getJobsForSearchedTags()
    {
        SharedPreferences mPrefs = this.getSharedPreferences(Constants.kSharedPreferenceConstant, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.kUserObjectPreference, "");
        User userObject = gson.fromJson(json, User.class);
        this.progressDialog.setMessage("Fetching jobs");
        this.progressDialog.show();
        ObjectFactory.getInstance().getTagsServiceInstance().getSearchedtagsJobsList(userObject.getEmail(), userObject.getToken(), userObject.getUserID(), tagIds, new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                progressDialog.dismiss();
                progressDialog.hide();
                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(response);

                    if (mainObject.getString("status").equalsIgnoreCase(Constants.kFailure))
                    {
                        Toast.makeText(JobsList.this, mainObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        JSONArray jobsListResponse = mainObject.getJSONArray("result");
                        jobsList.clear();
                        for (int i = 0 ; i < jobsListResponse.length() ; i++) {
                            JSONObject parentTag = (JSONObject) jobsListResponse.get(i);
                            Job job = new Job("Photography", parentTag.getString("title"),parentTag.getString("compensation"),"2 minutes ago",parentTag.getString("jobId"));
                            jobsList.add(job);
                        }
                        jobsListAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // progressDialog.hide();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception exception) {
                progressDialog.dismiss();
                progressDialog.hide();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jobs_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void categoryClickedAtPosition(int position) {
        Intent intent = new Intent(this,JobDescriptionActivity.class);
        intent.putExtra("jobID", jobsList.get(position).getJobId());
        startActivity(intent);
    }

    @Override
    public void onTokenAdded(TagItem tagItem) {

    }

    @Override
    public void onTokenRemoved(TagItem tagItem) {

    }
}
