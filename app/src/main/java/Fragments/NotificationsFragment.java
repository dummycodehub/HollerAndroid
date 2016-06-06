package Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holler.hollerapp.R;
import com.kpbird.chipsedittextlibrary.ChipsItem;

import java.util.ArrayList;

import Adapters.NotificationsAdapter;
import Adapters.SubCategoryAdapter;
import Models.JobSubCategory;
import Models.Notification;

/**
 * Created by rakeshkoplod on 05/01/16.
 */
public class NotificationsFragment extends android.support.v4.app.Fragment {

    private RecyclerView notificationsRcyclerView;
    private NotificationsAdapter notificationsRecyclerViewAdapter;
    private ArrayList<Notification> notifications;

    public NotificationsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        notifications = new ArrayList<Notification>();

        for(int i = 0 ; i < 20 ; i++){
            notifications.add(new Notification("5 min ago","Not sure who to award? John Doe, currently best for this work"));
        }

        notificationsRcyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewNotifications);
        notificationsRcyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationsRecyclerViewAdapter = new NotificationsAdapter(notifications);
        notificationsRcyclerView.setAdapter(notificationsRecyclerViewAdapter);
        //subCategoryRecyclerViewAdapter.setCategoryClickListner(this);
        notificationsRcyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }
}
