package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.holler.hollerapp.R;

/**
 * Created by rakeshkoplod on 12/05/16.
 */
public class FAQViewHolder extends RecyclerView.ViewHolder {

    public TextView txtTitle;


    public FAQViewHolder(View itemLayoutView) {
        super(itemLayoutView);
        txtTitle = (TextView) itemLayoutView.findViewById(R.id.faq_item);

    }
}
