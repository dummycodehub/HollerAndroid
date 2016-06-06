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
import Models.JobCategory;
import Models.JobSubCategory;

/**
 * Created by rakeshkoplod on 09/12/15.
 */
public class SubCategoryAdapter extends  RecyclerView.Adapter<SubCategoryViewHolder> {

    private  ArrayList<JobSubCategory> subCategoryItems = new ArrayList<JobSubCategory>();
    private CategorySelectedInterface categoryClickListner;
    private Context context;

    public  SubCategoryAdapter(Context context, ArrayList<JobSubCategory> subCategoryItems)
    {
        this.subCategoryItems = subCategoryItems;
        this.context = context;
    }

    public void setCategoryClickListner(CategorySelectedInterface click) {
        this.categoryClickListner = click;
    }

    @Override
    public SubCategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        //return null;
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sub_category_list_item, null);

        SubCategoryViewHolder viewHolder = new SubCategoryViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubCategoryViewHolder subCategoryViewHolder,final int position) {
        subCategoryViewHolder.txtSubCategotyTitle.setText(this.subCategoryItems.get(position).getTagName());
        ((Activity) this.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Code for the UiThread
                Picasso.with(context)
                        .load("http://icons.iconarchive.com/icons/hopstarter/soft-scraps/256/Address-Book-icon.png")
                        .resize(60, 60)
                        .into(subCategoryViewHolder.tagImage);
            }
        });
        subCategoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickListner.categoryClickedAtPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.subCategoryItems.size();
    }
}
