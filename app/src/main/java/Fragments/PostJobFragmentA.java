package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.holler.hollerapp.ContactsCompletionView;
import com.holler.hollerapp.PostJobActivity;
import com.holler.hollerapp.R;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;

import Models.TagItem;
import Utilities.DBController;

/**
 * Created by rakeshkoplod on 16/01/16.
 */
public class PostJobFragmentA extends android.support.v4.app.Fragment implements View.OnClickListener, TokenCompleteTextView.TokenListener<TagItem> {

    private Button buttonNext;
    private EditText jobTitleEditText;
    private EditText jobDescriptionEditText;

    ContactsCompletionView completionView;
    TagItem[] people ;
    ArrayAdapter<TagItem> adapter;
    private ArrayList<Integer> tagIds = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_job_a, container, false);

        buttonNext = (Button) rootView.findViewById(R.id.button_next);
        buttonNext.setOnClickListener(this);

        jobTitleEditText = (EditText) rootView.findViewById(R.id.jobTitleEditText);
        jobDescriptionEditText = (EditText) rootView.findViewById(R.id.jobDescriptionEditText);

        // Search tags code
        final DBController controller = new DBController(this.getActivity());
        ArrayList<TagItem> tagItems = new ArrayList<TagItem>();
        tagItems = controller.getAllTags();
        people = new TagItem[tagItems.size()];
        for (int j = 0; j < tagItems.size(); j++) {
            people[j] = tagItems.get(j);
        }

        adapter = new FilteredArrayAdapter<TagItem>(this.getActivity(), android.R.layout.simple_list_item_1, people) {
            @Override
            protected boolean keepObject(TagItem obj, String mask) {
                mask = mask.toLowerCase();
                return obj.getTagName().toLowerCase().startsWith(mask);
            }
        };
        completionView = (ContactsCompletionView)rootView.findViewById(R.id.tagsSearchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);

        completionView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    for (int i = 0; i < completionView.getObjects().size(); i++) {
                        TagItem item = completionView.getObjects().get(i);
                    }
                }
                return false;
            }
        });


        return rootView;
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this.getActivity(), "Next Clicked", Toast.LENGTH_LONG).show();

        for (int i = 0; i < completionView.getObjects().size() ; i++)
        {
            TagItem item = completionView.getObjects().get(i);
            tagIds.add(Integer.parseInt(item.getTagID()));

        }

        Intent intent = new Intent(getActivity(), PostJobActivity.class);
        intent.putIntegerArrayListExtra("tags",tagIds);
        intent.putExtra("title", jobTitleEditText.getText().toString());
        intent.putExtra("description",jobDescriptionEditText.getText().toString());
        startActivity(intent);

    }

    @Override
    public void onTokenAdded(TagItem tagItem) {

    }

    @Override
    public void onTokenRemoved(TagItem tagItem) {

    }
}
