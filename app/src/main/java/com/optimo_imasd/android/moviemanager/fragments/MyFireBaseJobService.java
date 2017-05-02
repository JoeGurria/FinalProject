package com.optimo_imasd.android.moviemanager.fragments;

/**
 * Created by Jose Maria on 28/04/2017.
 */
import android.content.Context;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;


    public class MyFireBaseJobService extends JobService {
     /*   public android.content.Context _context;
        public MyFireBaseJobService(Context context) {
            _context = context;
        }

*/
     private Context context = this;
        @Override
        public boolean onStartJob(JobParameters job) {
            // Do some work here
            Toast.makeText(this, "Job started", Toast.LENGTH_SHORT).show();
            MyNotification notification=new MyNotification(context );
            notification.GoNotification();
            return false;
           // return false; // Answers the question: "Is there still work going on?"
        }

        @Override
        public boolean onStopJob(JobParameters job) {
            return false; // Answers the question: "Should this job be retried?"
        }
    }

