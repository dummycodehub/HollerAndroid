package Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holler.hollerapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Interfaces.CategorySelectedInterface;
import Models.Job;

/**
 * Created by rakeshkoplod on 10/12/15.
 */
public class JobsListAdapter extends  RecyclerView.Adapter<JobsListViewHolder> {

    private ArrayList<Job> jobsList = new ArrayList<Job>();
    private Context context;

    public  JobsListAdapter(Context context, ArrayList<Job> itemsData)
    {
        this.jobsList = itemsData;
        this.context = context;
    }

    private CategorySelectedInterface categoryClickListner;

    public void setCategoryClickListner(CategorySelectedInterface click) {
        this.categoryClickListner = click;
    }


    @Override
    public JobsListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.jobs_list_item, null);
        JobsListViewHolder viewHolder = new JobsListViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final JobsListViewHolder jobsListViewHolder,final int position) {
        jobsListViewHolder.txtCategotyTitle.setText(jobsList.get(position).getJobTitle());
        ((Activity) this.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Code for the UiThread
                Picasso.with(context)
                        .load("http://icons.iconarchive.com/icons/hopstarter/soft-scraps/256/Address-Book-icon.png")
                        .resize(60, 60)
                        .into(jobsListViewHolder.jobImage);
            }
        });
        jobsListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickListner.categoryClickedAtPosition(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return jobsList.size();
    }
}
