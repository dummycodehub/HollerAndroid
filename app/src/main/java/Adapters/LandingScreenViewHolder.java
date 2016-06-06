package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.holler.hollerapp.R;


public  class LandingScreenViewHolder extends RecyclerView.ViewHolder {
        
        public TextView txtCategotyTitle;
        public TextView txtServices;
        public Button buttonDetails;
        public ImageView itemIcon;

        public LandingScreenViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtCategotyTitle = (TextView) itemLayoutView.findViewById(R.id.category);
            txtServices = (TextView) itemLayoutView.findViewById(R.id.services);
            buttonDetails = (Button) itemLayoutView.findViewById(R.id.button_details);
            itemIcon = (ImageView)itemLayoutView.findViewById(R.id.item_icon);
        }
    }