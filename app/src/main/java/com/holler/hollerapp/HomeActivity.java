package com.holler.hollerapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import Adapters.LandingScreenAdapter;
import Fragments.FAQFragment;
import Fragments.HomeFragment;
import Fragments.MyJobsFragment;
import Fragments.NotificationsFragment;
import Fragments.PostJobFragmentA;
import Fragments.RatingFragment;
import Fragments.SettingsFragment;
import Fragments.VerificationFragment;
import Models.JobCategory;
import Models.User;
import Utilities.Constants;
import Utilities.RoundImage;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rakeshkoplod on 27/11/15.
 */
public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private Toolbar toolbarHome;
    private ArrayList<JobCategory> jobCategories;
    private RecyclerView landingPageRcyclerView;
    private LandingScreenAdapter landingPageRecyclerViewAdapter;
    private DrawerLayout drawerLayout;
    private android.support.v4.app.FragmentManager fragmentManager;
    private RoundImage roundedImage;
    private NavigationView navigation;
    private ImageView profileImageView;

    private Button viewProfileButton;
    private CircleImageView userProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbarHome = (Toolbar) findViewById(R.id.tool_bar_home); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbarHome);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
           // actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        fragmentManager = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = new HomeFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.content, fragment).commit();

        NavigationView view = (NavigationView) findViewById(R.id.navigation);
        view.setNavigationItemSelectedListener(this);

        viewProfileButton = (Button)view.findViewById(R.id.editProfileButton);
        viewProfileButton.setOnClickListener(this);

        userProfileImage = (CircleImageView) view.findViewById(R.id.user_profile);
        userProfileImage.setOnClickListener(this);



        /*user_name = (TextView) header.findViewById(R.id.username);
        user_picture = (ImageView) header.findViewById(R.id.profile_pic);
        user_email = (TextView) header.findViewById(R.id.email);*/

        /*jobCategories = new ArrayList<JobCategory>();

        for (int i = 0 ; i < 10 ; i++)
        {
            jobCategories.add(new JobCategory("Category "+i,i+1));
        }
        landingPageRcyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        landingPageRcyclerView.setLayoutManager(new LinearLayoutManager(this));
        landingPageRecyclerViewAdapter = new LandingScreenAdapter(jobCategories);
        landingPageRcyclerView.setAdapter(landingPageRecyclerViewAdapter);
        landingPageRecyclerViewAdapter.setCategoryClickListner(this);
        landingPageRcyclerView.setItemAnimator(new DefaultItemAnimator());
       // RecyclerView.ItemDecoration itemDecoration = new
         //       Divideri(this, DividerItemDecoration.VERTICAL_LIST);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST);
        landingPageRcyclerView.addItemDecoration(itemDecoration);*/

    }

    public void setNavigationHeader(){

       /* navigation_view = (NavigationView) findViewById(R.id.nav_view);

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_home, null);
        navigation_view.addHeaderView(header);

        user_name = (TextView) header.findViewById(R.id.username);
        user_picture = (ImageView) header.findViewById(R.id.profile_pic);
        user_email = (TextView) header.findViewById(R.id.email);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
           // case android.R.id.home:

              //  return true;
        }
        item.setChecked(true);
        drawerLayout.openDrawer(GravityCompat.END);

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        //Toast.makeText(HomeActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();

        android.support.v4.app.Fragment fragment = null;

        /*android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ExampleFragments fragment = new ExampleFragments();
        fragmentTransaction.replace(R.id.frag, fragment);
        fragmentTransaction.commit();*/


        switch (menuItem.getItemId()) {
            case R.id.drawer_home:
                fragment = new HomeFragment();
                break;
            case R.id.drawer_my_job:
                fragment = new MyJobsFragment();
                break;
            case R.id.drawer_post_job:
                fragment = new PostJobFragmentA();
                break;
            case R.id.drawer_share_holler:
            {
                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.putExtra(Intent.EXTRA_TEXT, "Google play store link will be shared");
                textShareIntent.setType("text/plain");
                startActivity(textShareIntent);
            }
                break;
            case R.id.drawer_call_holler:
            {
                askUserPermissionForCall();
            }
                break;
            case R.id.drawer_notification:
                fragment = new NotificationsFragment();
                break;
            case R.id.drawer_about:
                fragment = new VerificationFragment();
                break;
            case R.id.drawer_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.drawer_faq_help:
                fragment = new FAQFragment();
                break;
            case R.id.drawer_sign_out:
                finish();
                break;

            default:
                break;
        }

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content, fragment).commit();
            drawerLayout.closeDrawers();
        }

        return true;
    }


    private void askUserPermissionForCall()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to call Holler");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+91-9930297373", null)));
            }
        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MyProfileActivity.class);
        startActivity(intent);
    }
}
