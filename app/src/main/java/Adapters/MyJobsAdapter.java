package Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.holler.hollerapp.R;
import com.holler.hollerapp.VolleyApplication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Interfaces.CategorySelectedInterface;
import Models.Job;

/**
 * Created by rakeshkoplod on 06/01/16.
 */
public class MyJobsAdapter extends RecyclerView.Adapter<MyJobsViewHolder> {

    private ArrayList<Job> jobsList = new ArrayList<Job>();

    private Context context;

    public  MyJobsAdapter(Context context, ArrayList<Job> itemsData)
    {
        this.context = context;
        this.jobsList = itemsData;
    }

    private CategorySelectedInterface categoryClickListner;

    ImageLoader imageLoader = VolleyApplication.getInstance().getImageLoader();

    public void setCategoryClickListner(CategorySelectedInterface click) {
        this.categoryClickListner = click;
    }

    @Override
    public MyJobsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_jobs_list_item, null);
        MyJobsViewHolder viewHolder = new MyJobsViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyJobsViewHolder myJobsViewHolder,final int position) {
        myJobsViewHolder.txtTitle.setText(jobsList.get(position).getJobTitle());
        myJobsViewHolder.txtCategory.setText(jobsList.get(position).getCategory());
        myJobsViewHolder.txtSalary.setText(jobsList.get(position).getSalary());
        myJobsViewHolder.txtTime.setText(jobsList.get(position).getTime());
       // myJobsViewHolder.jobImage.setImageUrl("http://api.androidhive.info/json/movies/1.jpg", imageLoader);

        //http://icons.iconarchive.com/icons/hopstarter/soft-scraps/256/Address-Book-icon.png

       /* imageLoader = VolleyApplication.getInstance()
                .getImageLoader();
        imageLoader.get("http://icons.iconarchive.com/icons/hopstarter/soft-scraps/256/Address-Book-icon.png", ImageLoader.getImageListener(myJobsViewHolder.jobImage,
                R.drawable.google_button, android.R.drawable
                        .ic_dialog_alert));
        myJobsViewHolder.jobImage.setImageUrl("http://icons.iconarchive.com/icons/hopstarter/soft-scraps/256/Address-Book-icon.png", imageLoader);*/

        ((Activity) this.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Code for the UiThread
                Picasso.with(context)
                        .load("http://icons.iconarchive.com/icons/hopstarter/soft-scraps/256/Address-Book-icon.png")
                        .resize(60, 60)
                        .into(myJobsViewHolder.jobImage);
            }
        });



        myJobsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickListner.categoryClickedAtPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }
}
