package Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.holler.hollerapp.HomeActivity;
import com.holler.hollerapp.JobsList;
import com.holler.hollerapp.R;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import Adapters.DividerItemDecoration;
import Adapters.LandingScreenAdapter;
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

import com.holler.hollerapp.ContactsCompletionView;
import com.holler.hollerapp.PostJobActivityFirst;
import com.holler.hollerapp.SubCategoryActivity;

public class HomeFragment extends android.support.v4.app.Fragment implements CategorySelectedInterface, TokenCompleteTextView.TokenListener<TagItem> {

    private ArrayList<JobCategory> jobCategories;
    private RecyclerView landingPageRcyclerView;
    private LandingScreenAdapter landingPageRecyclerViewAdapter;
    private ObjectMapper mapper;
   private ImageButton postJobButton;


    ContactsCompletionView completionView;
    TagItem[] people ;
    ArrayAdapter<TagItem> adapter;

    private ProgressDialog progressDialog;
    private ArrayList<Integer> tagIds = new ArrayList<Integer>();

    public HomeFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        jobCategories = new ArrayList<JobCategory>();

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        this.progressDialog = new ProgressDialog(this.getActivity());

        postJobButton = (ImageButton)rootView.findViewById(R.id.fab_image_button);
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostJobActivityFirst.class);
                startActivity(intent);
            }
        });


        // Parent tags code
        landingPageRcyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        landingPageRcyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        landingPageRecyclerViewAdapter = new LandingScreenAdapter(jobCategories);
        landingPageRcyclerView.setAdapter(landingPageRecyclerViewAdapter);
        landingPageRecyclerViewAdapter.setCategoryClickListner(this);
        landingPageRcyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getActivity().getBaseContext(),DividerItemDecoration.VERTICAL_LIST);
        landingPageRcyclerView.addItemDecoration(itemDecoration);
        loadJSONFromAsset();



        // Search tags code
        completionView = (ContactsCompletionView)rootView.findViewById(R.id.searchView);
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


        getParentTags();
        getAllChildTags();

        return rootView;
    }


    private void getParentTags()
    {
        this.progressDialog.setMessage("Loading tags");
        this.progressDialog.show();
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences(Constants.kSharedPreferenceConstant, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.kUserObjectPreference, "");

        User userObject = gson.fromJson(json, User.class);

        ObjectFactory.getInstance().getTagsServiceInstance().getParentTags(userObject.getEmail(), userObject.getToken(), new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                progressDialog.hide();
                progressDialog.dismiss();
                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(response);

                    if (mainObject.getString("status").equalsIgnoreCase(Constants.kFailure))
                    {
                        Toast.makeText(getActivity(), mainObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        JSONArray parentTags = mainObject.getJSONArray("result");
                        for (int i = 0 ; i < parentTags.length() ; i++) {
                            JSONObject parentTag = (JSONObject) parentTags.get(i);
                            JobCategory jobsCategory = new JobCategory(parentTag.getString("id"), parentTag.getString("parentTagName"));
                            JSONArray childTags = parentTag.getJSONArray("childTags");
                            for (int j = 0; j < childTags.length(); j++) {
                                JSONObject childTag = (JSONObject) childTags.get(j);
                                jobsCategory.childTags.add(new JobSubCategory(childTag.getString("tagId"), childTag.getString("tagName"), childTag.getString("tagImage")));
                            }
                            jobCategories.add(jobsCategory);
                        }
                        landingPageRecyclerViewAdapter.notifyDataSetChanged();
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

    private void getAllChildTags()
    {
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences(Constants.kSharedPreferenceConstant, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.kUserObjectPreference, "");
        User userObject = gson.fromJson(json, User.class);

        final DBController controller = new DBController(getActivity());
        controller.deleteAllTags();

        ObjectFactory.getInstance().getTagsServiceInstance().getAllChildTags(userObject.getEmail(), userObject.getToken(), new CallBack<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d("Child tags response", "=====> " + response);
                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(response);

                    if (mainObject.getString("status").equalsIgnoreCase(Constants.kFailure)) {
                        Toast.makeText(getActivity(), mainObject.getString("message"), Toast.LENGTH_LONG).show();
                    } else {
                        JSONArray childTags = mainObject.getJSONArray("result");
                        people = new TagItem[childTags.length()];
                        for (int j = 0; j < childTags.length(); j++) {
                            JSONObject childTag = (JSONObject) childTags.get(j);
                            people[j] = new TagItem(childTag.getString("tagId"), childTag.getString("tagName"));
                            controller.insertTag(new TagItem(childTag.getString("tagId"), childTag.getString("tagName")));
                        }
                        Log.d("From DB ======>", controller.getAllTags().size() + "");
                        ArrayList<TagItem> tagItems = new ArrayList<TagItem>();
                        for (int j = 0; j < tagItems.size(); j++) {
                            people[j] = tagItems.get(j);
                        }
                        adapter = new FilteredArrayAdapter<TagItem>(getActivity(), android.R.layout.simple_list_item_1, people) {
                            @Override
                            protected boolean keepObject(TagItem obj, String mask) {
                                mask = mask.toLowerCase();
                                return obj.getTagName().toLowerCase().startsWith(mask);
                            }
                        };

                        completionView.setAdapter(adapter);
                        completionView.setTokenListener(HomeFragment.this);
                        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);

                        adapter.notifyDataSetChanged();

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

    public void getJobsForSearchedTags()
    {
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences(Constants.kSharedPreferenceConstant, Context.MODE_PRIVATE);
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
                        Toast.makeText(getActivity(), mainObject.getString("message"), Toast.LENGTH_LONG).show();
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
                        Log.d("Jobs list",""+jobsList.size());
                        Intent intent = new Intent(getActivity(), JobsList.class);
                        intent.putExtra("isForJobSearch",true);
                        intent.putParcelableArrayListExtra("jobsList",jobsList);
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

    public void loadJSONFromAsset() {
        /*AssetManager assetManager = this.getActivity().getAssets();
        //JsonParser jp = factory.createParser(reader);
        Log.d("JSON =========>", "Before");
        try {
            InputStream input = assetManager.open("parent.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            String text = new String(buffer);
            Log.d("JSON =========>", text);

            JSONObject jsonObject = new JSONObject(text);
            JSONArray jArray = jsonObject.getJSONArray("parentTags");

            for (int i = 0 ; i < jArray.length() ; i++)
            {
                JSONObject jsonObject1 = jArray.getJSONObject(i);
                jobCategories.add(new JobCategory(jsonObject1.getString("services"),jsonObject1.getString("tagId"),jsonObject1.getString("tagName")));
            }
            Log.d("NAME =========>", "" + jobCategories);
            landingPageRecyclerViewAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //return "";
    }


    @Override
    public void categoryClickedAtPosition(int position) {
        Intent intent = new Intent(getActivity(), SubCategoryActivity.class);
        //intent.putExtra("childTags",jobCategories.get(position).childTags);
        intent.putParcelableArrayListExtra("childTags",jobCategories.get(position).childTags);
        startActivity(intent);
    }

    @Override
    public void onTokenAdded(TagItem tagItem) {

    }

    @Override
    public void onTokenRemoved(TagItem tagItem) {
        //Toast.makeText(this.getActivity(),"Removed",Toast.LENGTH_SHORT).show();
    }
}