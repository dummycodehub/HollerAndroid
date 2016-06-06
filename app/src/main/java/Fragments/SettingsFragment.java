package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.holler.hollerapp.R;

/**
 * Created by rakeshkoplod on 09/05/16.
 */
public class SettingsFragment extends android.support.v4.app.Fragment {

    public SettingsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        //RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        //Drawable drawable = ratingBar.getProgressDrawable();
        //drawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        //ratingBar.setRating(3);
        return rootView;
    }
}
