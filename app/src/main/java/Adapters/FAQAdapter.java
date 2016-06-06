package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holler.hollerapp.R;

import java.util.ArrayList;

import Interfaces.CategorySelectedInterface;
import Models.Job;

/**
 * Created by rakeshkoplod on 12/05/16.
 */
public class FAQAdapter extends RecyclerView.Adapter<FAQViewHolder> {

    @Override
    public FAQViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.faq_list_item, null);
        FAQViewHolder viewHolder = new FAQViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FAQViewHolder faqViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
