package Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holler.hollerapp.R;

import java.util.ArrayList;

import Fragments.HomeFragment;
import Interfaces.CategorySelectedInterface;
import Models.JobCategory;

public class  LandingScreenAdapter extends  RecyclerView.Adapter<LandingScreenViewHolder>
{
    private ArrayList<JobCategory> itemsData;

    public void setCategoryClickListner(HomeFragment click) {
        this.categoryClickListner = click;
    }

    private CategorySelectedInterface categoryClickListner;



    public  LandingScreenAdapter(ArrayList<JobCategory> itemsData)
    {
        this.itemsData = itemsData;
    }

    @Override
    public LandingScreenViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.landing_page_list_item, null);

        LandingScreenViewHolder viewHolder = new LandingScreenViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LandingScreenViewHolder viewHolder, final int position) {

        viewHolder.itemView.setBackgroundResource(R.drawable.cell_background_one);
        viewHolder.itemIcon.setImageResource(R.drawable.repairing_maintenance_icon);

       /* if (position == 0)
        {
            viewHolder.itemIcon.setImageResource(R.drawable.repairing_maintenance_icon);
        }
        else if (position == 1)
        {
            viewHolder.itemIcon.setImageResource(R.drawable.electrical_services_icon);
        }
        else if (position == 2)
        {
            viewHolder.itemIcon.setImageResource(R.drawable.beauty_saloon_service_icon);
        }*/

        viewHolder.txtCategotyTitle.setText(itemsData.get(position).getTagName());
        viewHolder.txtServices.setText(itemsData.get(position).childTags.size()+" Services");
        viewHolder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickListner.categoryClickedAtPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

}