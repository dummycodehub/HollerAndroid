package Fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.holler.hollerapp.R;


/**
 * Created by rakeshkoplod on 07/02/16.
 */
public class RatingFragment extends android.support.v4.app.Fragment {

    public RatingFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rating, container, false);
        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        //Drawable drawable = ratingBar.getProgressDrawable();
        //drawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        //ratingBar.setRating(3);
        return rootView;
    }

}
