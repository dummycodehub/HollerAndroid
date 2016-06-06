package Fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holler.hollerapp.R;

import Adapters.ViewPagerAdapter;
import Utilities.SlidingTabLayout;

/**
 * Created by rakeshkoplod on 02/01/16.
 */
public class MyJobsFragment extends android.support.v4.app.Fragment {

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Posted","Accepted"};
    int Numboftabs = 2;

    public MyJobsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_jobs, container, false);

        adapter =  new ViewPagerAdapter(getFragmentManager(),Titles,Numboftabs);



        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) rootView.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        /*ViewPagerA adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.accent_material_light)), "CAT");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.ripple_material_light)), "DOG");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.button_material_dark)), "MOUSE");
        viewPager.setAdapter(adapter);*/
    }
}
