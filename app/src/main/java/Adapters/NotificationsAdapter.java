package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holler.hollerapp.R;

import java.util.ArrayList;

import Models.JobSubCategory;
import Models.Notification;

/**
 * Created by rakeshkoplod on 16/03/16.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsViewHolder> {

    private ArrayList<Notification> notificatios = new ArrayList<Notification>();


    public  NotificationsAdapter(ArrayList<Notification> notifications)
    {
        this.notificatios = notifications;
    }

    @Override
    public NotificationsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notifications_list_item, null);

        NotificationsViewHolder viewHolder = new NotificationsViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationsViewHolder notificationsViewHolder, int position) {
        notificationsViewHolder.notificationText.setText(this.notificatios.get(position).getNotificationTitle());
        notificationsViewHolder.notificationTime.setText(this.notificatios.get(position).getNotificationTime());
    }

    @Override
    public int getItemCount() {
        return this.notificatios.size();
    }
}
