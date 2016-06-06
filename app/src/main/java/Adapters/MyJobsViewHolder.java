package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.holler.hollerapp.R;


/**
 * Created by rakeshkoplod on 06/01/16.
 */
public class MyJobsViewHolder extends RecyclerView.ViewHolder {

    public TextView txtTitle;
    public TextView txtCategory;
    public TextView txtSalary;
    public TextView txtTime;
    public ImageView jobImage;

    public MyJobsViewHolder(View itemLayoutView) {
        super(itemLayoutView);
        txtTitle = (TextView) itemLayoutView.findViewById(R.id.job_title);
        txtCategory = (TextView) itemLayoutView.findViewById(R.id.job_category);
        txtSalary = (TextView) itemLayoutView.findViewById(R.id.job_salary);
        txtTime = (TextView) itemLayoutView.findViewById(R.id.time_posted);
        jobImage = (ImageView) itemLayoutView
                .findViewById(R.id.job_image);
    }
}
