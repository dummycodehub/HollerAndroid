package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.holler.hollerapp.R;


/**
 * Created by rakeshkoplod on 09/12/15.
 */
public class SubCategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView txtSubCategotyTitle;
    public ImageView tagImage;

    public SubCategoryViewHolder(View itemLayoutView) {
        super(itemLayoutView);
        txtSubCategotyTitle = (TextView) itemLayoutView.findViewById(R.id.sub_category_title);
        tagImage = (ImageView)itemLayoutView.findViewById(R.id.sub_category_image);
    }

}
