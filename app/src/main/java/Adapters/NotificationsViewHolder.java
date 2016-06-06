package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.holler.hollerapp.R;


/**
 * Created by rakeshkoplod on 16/03/16.
 */
public class NotificationsViewHolder extends RecyclerView.ViewHolder {

    public TextView notificationText;
    public TextView notificationTime;

    public NotificationsViewHolder(View itemLayoutView) {
        super(itemLayoutView);
        notificationText = (TextView) itemLayoutView.findViewById(R.id.notification_text);
        notificationTime = (TextView) itemLayoutView.findViewById(R.id.notification_time);
    }
}
