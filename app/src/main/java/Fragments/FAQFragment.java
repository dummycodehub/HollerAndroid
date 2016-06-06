package Fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.holler.hollerapp.R;

import Adapters.FAQAdapter;
import Adapters.JobsListAdapter;

/**
 * Created by rakeshkoplod on 12/05/16.
 */
public class FAQFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private RecyclerView helpTopicsRcyclerView;
    private RecyclerView topQueriesRcyclerView;
    private FAQAdapter faqAdapter;
    private Button buttonHelpTopics;

    private Boolean showHelpTopics;

    public FAQFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);

        buttonHelpTopics = (Button) rootView.findViewById(R.id.button_help_topics);
        buttonHelpTopics.setOnClickListener(this);

        showHelpTopics = false;

        helpTopicsRcyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerHelpTopics);
        helpTopicsRcyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        faqAdapter = new FAQAdapter();
        helpTopicsRcyclerView.setAdapter(faqAdapter);

        topQueriesRcyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerTopQueries);
        topQueriesRcyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        faqAdapter = new FAQAdapter();
        topQueriesRcyclerView.setAdapter(faqAdapter);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (showHelpTopics)
        {
            showHelpTopics = false;
            topQueriesRcyclerView.setVisibility(View.VISIBLE);
        }
        else
        {
            showHelpTopics = true;
            topQueriesRcyclerView.setVisibility(View.GONE);
        }
    }
}
