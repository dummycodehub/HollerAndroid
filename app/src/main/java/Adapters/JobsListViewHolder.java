package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.holler.hollerapp.R;


/**
 * Created by rakeshkoplod on 10/12/15.
 */
public class JobsListViewHolder extends RecyclerView.ViewHolder {

    public TextView txtCategotyTitle;
    public ImageView jobImage;

    public JobsListViewHolder(View itemLayoutView) {
        super(itemLayoutView);
        txtCategotyTitle = (TextView) itemLayoutView.findViewById(R.id.job_title);
        jobImage = (ImageView)itemLayoutView.findViewById(R.id.jobsList_image);
    }

}
