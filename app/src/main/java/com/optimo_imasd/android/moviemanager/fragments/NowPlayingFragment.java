package com.optimo_imasd.android.moviemanager.fragments;


import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.optimo_imasd.android.moviemanager.R;
import com.optimo_imasd.android.moviemanager.adapters.MovieRecyclerViewAdapter;
import com.optimo_imasd.android.moviemanager.database.DataBaseDAO;
import com.optimo_imasd.android.moviemanager.models.Movie;
import com.optimo_imasd.android.moviemanager.models.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements View.OnClickListener{


    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;
    Unbinder unbinder;
    public List<Question> movies;
   //public ArrayList<Question> contactArrayList
    public MovieRecyclerViewAdapter adapter;

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);//fragment_now_playing.xml
       //View view2 = inflater.inflate(R.layout.nav_header_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeData();

        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rvMovies.setHasFixedSize(true);//rvMovies is in fragment_now_playing.xml layout
        rvMovies.setLayoutManager(llm);

        adapter = new MovieRecyclerViewAdapter(this.getContext(), movies);
        rvMovies.setAdapter(adapter);
        //unbinder = ButterKnife.bind(this, view);
     /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Movie saved as favourite", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        FloatingActionButton upButton = (FloatingActionButton) view.findViewById(R.id.fab);
        upButton.setOnClickListener(this);

       // FloatingActionButton notifications = (FloatingActionButton) view.findViewById(R.id.fabnotifications);
        // notifications.setOnClickListener(this);



        return view;
    }
    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.fab:
                //Snackbar.make(v, "Movie saved as favourite", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
               // msgbox("hello", "Hello");
                QandA qa=new QandA("","", movies, adapter);
                CustomDialogClass cdd=new CustomDialogClass(this.getActivity(),qa);
                cdd.show();
                break;
           /* case R.id.fabnotifications:
                NotificationsDialogClass n = new NotificationsDialogClass(this.getActivity());
                n.show();
                break;
*/
           /* case R.id.nav_now_playing:
               // Toast.makeText(getContext(), "Notification clicked.", Toast.LENGTH_SHORT).show();
                NotificationsDialogClass n=new NotificationsDialogClass (this.getActivity());
                n.show();
                break;*/
        }
    }



    private void initializeData() {
        //Question q=new Question();
        //q.setAnswer();

        ArrayList<Question> questions = new ArrayList<Question>();
        //Question question= new Question();
        DataBaseDAO dao= new DataBaseDAO(getContext());
        dao.open();
        questions=dao.getAllQuestions();
        movies = new ArrayList<>();
        try {
            if (questions.size() > 0) {
                ArrayList<Question> myMovies = getSeed();
                for (int j=0;j < myMovies.size();j++) {
                    movies.add(new Question(myMovies.get(j).getQuestion(), myMovies.get(j).getAnswer()));//, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));

                }
                for (int i = 0; i < questions.size(); i++) {
                    movies.add(new Question(questions.get(i).getQuestion(), questions.get(i).getAnswer()));//, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));
                }
                dao.close();
            }
            else {

                ArrayList<Question> myMovies = getSeed();
                for (int i=0;i < myMovies.size();i++) {
                    movies.add(new Question(myMovies.get(i).getQuestion(), myMovies.get(i).getAnswer()));//, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));

                }
            }
           /* String json_return_by_the_function=loadJSONFromAsset();

            JSONObject obj = new JSONObject(json_return_by_the_function);
            JSONArray m_jArry = obj.getJSONArray("questions_and_answers");
            //ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            //HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                //Log.d("Details-->", jo_inside.getString("formule"));
                String formula_value = jo_inside.getString("question");
                String url_value = jo_inside.getString("answer");
                //movies.add(new Movie(String.valueOf(i), formula_value, url_value, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));
                movies.add(new Question(formula_value, url_value));//, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));

            }
         }
*/
        }
        catch (Exception j)
        {
        }


       /* movies.add(new Movie("277834", "Moana", "In Ancient Polynesia, when a terrible curse incurred by Maui reaches an impetuous Chieftain's daughter's island, she answers the Ocean's call to seek out the demigod to set things right.", 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));

        movies.add(new Movie("121856", "Passengers", "A spacecraft traveling to a distant colony planet and transporting thousands of people has a malfunction in its sleep chambers. As a result, two passengers are awakened 90 years early.", 6.2f, 745, "/5gJkVIVU7FDp7AfRAbPSvvdbre2.jpg", "/5EW4TR3fWEqpKsWysNcBMtz9Sgp.jpg"));

        movies.add(new Movie("330459", "Assassin's Creed", "Lynch discovers he is a descendant of the secret Assassins society through unlocked genetic memories that allow him to relive the adventures of his ancestor, Aguilar, in 15th Century Spain. After gaining incredible knowledge and skills he’s poised to take on the oppressive Knights Templar in the present day.", 5.3f, 691, "/tIKFBxBZhSXpIITiiB5Ws8VGXjt.jpg", "/5EW4TR3fWEqpKsWysNcBMtz9Sgp.jpg"));

        movies.add(new Movie("283366", "Rogue One: A Star Wars Story", "A rogue band of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy.", 7.2f, 1802, "/qjiskwlV1qQzRCjpV0cL9pEMF9a.jpg", "/tZjVVIYXACV4IIIhXeIM59ytqwS.jpg"));

        movies.add(new Movie("313369", "La La Land", "Mia, an aspiring actress, serves lattes to movie stars in between auditions and Sebastian, a jazz musician, scrapes by playing cocktail party gigs in dingy bars, but as success mounts they are faced with decisions that begin to fray the fragile fabric of their love affair, and the dreams they worked so hard to maintain in each other threaten to rip them apart.", 8, 396, "/ylXCdC106IKiarftHkcacasaAcb.jpg", "/nadTlnTE6DdgmYsN4iWc2a2wiaI.jpg"));
*/

    }
public ArrayList<Question> getSeed() {
    ArrayList<Question> movies_temp = new ArrayList<>();
    String json_return_by_the_function = loadJSONFromAsset();
    try {
        JSONObject obj = new JSONObject(json_return_by_the_function);
        JSONArray m_jArry = obj.getJSONArray("questions_and_answers");
        //ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
        //HashMap<String, String> m_li;

        for (int i = 0; i < m_jArry.length(); i++) {
            JSONObject jo_inside = m_jArry.getJSONObject(i);
            //Log.d("Details-->", jo_inside.getString("formule"));
            String formula_value = jo_inside.getString("question");
            String url_value = jo_inside.getString("answer");
            //movies.add(new Movie(String.valueOf(i), formula_value, url_value, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));
            movies_temp.add(new Question(formula_value, url_value));//, 6.5f, 854, "/z4x0Bp48ar3Mda8KiPD1vwSY3D8.jpg", "/1qGzqGUd1pa05aqYXGSbLkiBlLB.jpg"));
        }
    } catch (JSONException j) {
    }
    return movies_temp;
}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("qanda_final.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
