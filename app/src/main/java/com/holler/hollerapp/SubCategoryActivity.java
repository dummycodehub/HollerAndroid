package com.holler.hollerapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import Adapters.SubCategoryAdapter;
import Interfaces.CategorySelectedInterface;
import Models.Job;
import Models.JobSubCategory;
import Models.TagItem;
import Models.User;
import Network.CallBack;
import Network.ObjectFactory;
import Utilities.Constants;
import Utilities.DBController;

/**
 * Created by rakeshkoplod on 09/12/15.
 */
public class SubCategoryActivity extends AppCompatActivity implements CategorySelectedInterface,TokenCompleteTextView.TokenListener<TagItem>{

    Toolbar toolbarSubCategory;
    private RecyclerView subCategoryRcyclerView;
    private SubCategoryAdapter subCategoryRecyclerViewAdapter;
    private ArrayList<JobSubCategory> jobSubCategories;

    ContactsCompletionView completionView;
    TagItem[] people;
    ArrayAdapter<TagItem> adapter;

    private ProgressDialog progressDialog;
    private ArrayList<Integer> tagIds = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        toolbarSubCategory = (Toolbar) findViewById(R.id.tool_bar_sub_category); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbarSubCategory);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.progressDialog = new ProgressDialog(SubCategoryActivity.this);

        // Search tags code

        final DBController controller = new DBController(this);
        ArrayList<TagItem> tagItems = new ArrayList<TagItem>();
        tagItems = controller.getAllTags();
        Log.d("Tags from DB in SubCategory ------------------>",""+tagItems.size());
        people = new TagItem[tagItems.size()];
        for (int j = 0; j < tagItems.size(); j++) {
            people[j] = tagItems.get(j);
        }

        adapter = new FilteredArrayAdapter<TagItem>(SubCategoryActivity.this, android.R.layout.simple_list_item_1, people) {
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
                    for (int i = 0; i < completionView.getObjects().size() ; i++)
                    {
                        TagItem item = completionView.getObjects().get(i);
                        tagIds.add(Integer.parseInt(item.getTagID()));

                    }
                    if (tagIds.size() > 0)
                    {
                        getJobsForSearchedTags();
                    }
                }
                return false;
            }
        });

        jobSubCategories = new ArrayList<JobSubCategory>();
        Intent intent = getIntent();
        jobSubCategories = intent.getParcelableArrayListExtra("childTags");
        subCategoryRcyclerView = (RecyclerView) findViewById(R.id.recyclerViewSubCategory);
        subCategoryRcyclerView.setLayoutManager(new LinearLayoutManager(this));
        subCategoryRecyclerViewAdapter = new SubCategoryAdapter(this,jobSubCategories);
        subCategoryRcyclerView.setAdapter(subCategoryRecyclerViewAdapter);
        subCategoryRecyclerViewAdapter.setCategoryClickListner(this);
        subCategoryRcyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    @Override
    public void categoryClickedAtPosition(int position) {
        Intent intent = new Intent(this, JobsList.class);
        intent.putExtra("tagName",jobSubCategories.get(position).getTagName());
        intent.putExtra("isForJobSearch", false);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_category, menu);
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
                        Toast.makeText(SubCategoryActivity.this, mainObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        JSONArray jobsListResponse = mainObject.getJSONArray("result");
                        ArrayList<Job> jobsList = new ArrayList<Job>();
                        jobsList.clear();
                        for (int i = 0 ; i < jobsListResponse.length() ; i++) {
                            JSONObject parentTag = (JSONObject) jobsListResponse.get(i);
                            Job job = new Job("Photography", parentTag.getString("title"),parentTag.getString("compensation"),"2 minutes ago",parentTag.getString("jobId"));
                            jobsList.add(job);
                        }
                        Intent intent = new Intent(SubCategoryActivity.this, JobsList.class);
                        intent.putExtra("isForJobSearch",true);
                        intent.putParcelableArrayListExtra("jobsList", jobsList);
                        startActivity(intent);
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
    public void onTokenAdded(TagItem tagItem) {

    }

    @Override
    public void onTokenRemoved(TagItem tagItem) {

    }
}
