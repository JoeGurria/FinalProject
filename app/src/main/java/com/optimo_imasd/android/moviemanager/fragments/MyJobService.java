package com.optimo_imasd.android.moviemanager.fragments;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.widget.Toast;

import static java.security.AccessController.getContext;

/**
 * Created by Byron on 3/12/2017.
 */
@TargetApi(21)
public class MyJobService extends JobService {
    public android.content.Context _context;

    public MyJobService(Context context) {
        _context = context;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
       // Toast.makeText(this, "Job started", Toast.LENGTH_SHORT).show();
        MyNotification notification=new MyNotification(_context);
        notification.GoNotification();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, "Job finished", Toast.LENGTH_SHORT).show();
        return false;
    }
}
