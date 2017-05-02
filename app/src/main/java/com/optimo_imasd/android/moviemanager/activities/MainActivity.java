package com.optimo_imasd.android.moviemanager.activities;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.optimo_imasd.android.moviemanager.R;
import com.optimo_imasd.android.moviemanager.database.ContactsProvider;
import com.optimo_imasd.android.moviemanager.fragments.CustomDialogClass;
import com.optimo_imasd.android.moviemanager.fragments.MyFireBaseJobService;
import com.optimo_imasd.android.moviemanager.fragments.MyJobService;
import com.optimo_imasd.android.moviemanager.fragments.MyNotification;
import com.optimo_imasd.android.moviemanager.fragments.NotificationsDialogClass;
import com.optimo_imasd.android.moviemanager.fragments.NowPlayingFragment;
import com.optimo_imasd.android.moviemanager.fragments.JobSchedulerService;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , LoaderManager.LoaderCallbacks<Cursor>{
    private static final int JOB_ID = 1;
    private static final int REFRESH_INTERVAL = 3;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//activity_main.xml layout?
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//app_bar_main.xml layout
        setSupportActionBar(toolbar);
//
//Job scheduler Lesson 3
    /*    JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        //Create a component passing the JobService that we want to use
        ComponentName jobService =  new ComponentName(getPackageName(), MyJobService.class.getName());

        //Create a JobInfo passing a unique JOB_ID and the jobService
        //also set the periodic time to repeat this job
        JobInfo jobInfo =  new JobInfo.Builder(JOB_ID, jobService)
                .setPeriodic(REFRESH_INTERVAL)
                .build();

        jobScheduler.schedule(jobInfo);*/
        //end Job scheduler Lesson 3
        //Job scheduler
       /*try {

            JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1,
                    new ComponentName(getPackageName(),
                            JobSchedulerService.class.getName()));


            builder.setPeriodic(3000);


            if (mJobScheduler.schedule(builder.build()) <= 0) {
                //If something goes wrong
                System.out.println("Error in Job Scheduler");
            }
        }
        catch(Exception e){
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();

        }*/
        //
        // end job scheduler
/////
// Launch Notification with Job scheduler
// ////
        context = MainActivity.this;
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        Boolean notifications_enabled= sharedPref.getBoolean("enable_notifications2",false);
        Integer frequency=sharedPref.getInt("frequency2", 3);
        if (notifications_enabled== true) {
            try {
                GoJobSchedule(frequency, context);
                //GoNotification();
            }catch (Exception e){
                Toast.makeText(this, "GoJobSchedule error", Toast.LENGTH_SHORT).show();
            }
        }else{
            //cancel job schedule
            StopJobSchedule(context);



        }
        ///
        // end launch notification with Job scheduler
        ///

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);//activity_main.xml
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);//activity_main.xml
        navigationView.setNavigationItemSelectedListener(this);
   /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Movie saved as favourite", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        showFragment(NowPlayingFragment.class);
    }

    private void StopJobSchedule(Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        dispatcher.cancelAll();
    }

    public void GoNotification(){
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v4.app.NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notifications)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
        // Intent resultIntent = new Intent(this, ResultActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        // TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        // stackBuilder.addParentStack(ResultActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        //stackBuilder.addNextIntent(resultIntent);
        //PendingIntent resultPendingIntent =
//stackBuilder.getPendingIntent(
//0,PendingIntent.FLAG_UPDATE_CURRENT
        // );
        //     mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }
public void GoJobSchedule(Integer frequency, Context context){

   /* JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    //Create a component passing the JobService that we want to use
    ComponentName jobService =  new ComponentName(getPackageName(), MyJobService.class.getName());
    //Create a JobInfo passing a unique JOB_ID and the jobService
    //also set the periodic time to repeat this job
    JobInfo jobInfo =  new JobInfo.Builder(JOB_ID, jobService)
            .setPeriodic(frequency)
            .build();

    jobScheduler.schedule(jobInfo);*/
   // with Firebase
    FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

    Job myJob = dispatcher.newJobBuilder()
            // the JobService that will be called
            .setService(MyFireBaseJobService.class)
            // uniquely identifies the job
            .setTag("my-unique-tag")
            // one-off job
            .setRecurring(true)
            // don't persist past a device reboot
            .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
            // start between 0 and 60 seconds from now
            .setTrigger(Trigger.executionWindow(0, frequency))
            // don't overwrite an existing job with the same tag
            .setReplaceCurrent(true)
            // retry with exponential backoff
            .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
            .setConstraints(Constraint.ON_ANY_NETWORK)
            // constraints that need to be satisfied for the job to run
            .build();

    dispatcher.mustSchedule(myJob);
}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);//activity_main.xml
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {//main.xml
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class fragment=null;

        if (id == R.id.nav_now_playing) {//activity_main_drawer.xml
           fragment= NotificationsDialogClass.class;
            showFragment(fragment);
            //NotificationsDialogClass cdd=new NotificationsDialogClass (this.getActivity());
            //cdd.show();

       // } else if (id == R.id.nav_upcoming) {//activity_main_drawer.xml
           // fragment= UpcomingFragment.class;
           // showFragment(fragment);

        }  else if (id == R.id.nav_logout) {//activity_main_drawer.xml
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);// activity_main.xml
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Class fragmentClass) {

        Fragment fragment=null;
        try {
            fragment=(Fragment) fragmentClass.newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//flContent is contained in content_main.xml
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent,fragment)
        .addToBackStack( "tag" )
                .commit();


//flContent is contained in content_main.xml
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, ContactsProvider.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
