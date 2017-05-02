package com.optimo_imasd.android.moviemanager.fragments;

import android.app.Activity;
import android.app.Dialog;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.optimo_imasd.android.moviemanager.R;
import com.optimo_imasd.android.moviemanager.adapters.MovieRecyclerViewAdapter;
import com.optimo_imasd.android.moviemanager.models.Preferences;

import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jose Maria on 27/04/2017.
 */

public class NotificationsDialogClass extends android.support.v4.app.Fragment implements View.OnClickListener{

    public NotificationsDialogClass() {
        // TODO Auto-generated constructor stub

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.notifications, container, false);//fragment_now_playing.xml
        //View view2 = inflater.inflate(R.layout.nav_header_main, container, false);

        //unbinder = ButterKnife.bind(this, view);
      Button cancel= (Button) view.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(this);
        Button save = (Button) view.findViewById(R.id.btnSave);
        save.setOnClickListener(this);
        CheckBox enableNotifications = (CheckBox) view.findViewById(R.id.chkNotifications);
        EditText frequency=(EditText) view.findViewById(R.id.txtFrequency);
Preferences o = new Preferences();
        GetPreferences(o);
        enableNotifications.setChecked(o.enablenotifications);
        frequency.setText(o.frequency.toString());
        // FloatingActionButton upButton = (FloatingActionButton) view.findViewById(R.id.fab);
       // upButton.setOnClickListener(this);

        // FloatingActionButton notifications = (FloatingActionButton) view.findViewById(R.id.fabnotifications);
        // notifications.setOnClickListener(this);



        return view;
    }

    private void GetPreferences(Preferences o) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        o.enablenotifications = sharedPref.getBoolean("enable_notifications2",false);
        o.frequency = sharedPref.getInt("frequency2", 3000);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                //c.finish();
                //Toast.makeText(getContext(), "Cancel clicked.", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                //Log.i("MainActivity", "popping backstack");
                fm.popBackStack();}
                break;
            case R.id.btnSave:
                CheckBox enableNotifications = (CheckBox) getView().findViewById(R.id.chkNotifications);
                EditText frequency=(EditText) getView().findViewById(R.id.txtFrequency);
                Integer f = Integer.parseInt(frequency.getText().toString());
                SavePreferences(enableNotifications.isChecked(),f);
                Toast.makeText(getContext(), "Changes saved sucessfully.", Toast.LENGTH_SHORT).show();
                if (!enableNotifications.isChecked()){
                    //cancel all jobs
                    FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getContext()));
                    dispatcher.cancelAll();

                }else
                {
                    GoJobSchedule(f,getContext());
                }
                FragmentManager fmr = getFragmentManager();
                if (fmr.getBackStackEntryCount() > 0) {
                    //Log.i("MainActivity", "popping backstack");
                    fmr.popBackStack();}
                break;
            default:
                break;
        }

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
    private void SavePreferences(boolean checked, Integer frequency) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("enable_notifications2", checked);
        editor.putInt("frequency2",frequency);
        editor.commit();
       /* SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("name", "Elena");
        editor.putInt("idName", 12);
        editor.commit();*/
    }
}
