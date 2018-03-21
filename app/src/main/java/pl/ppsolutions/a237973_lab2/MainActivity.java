package pl.ppsolutions.a237973_lab2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.util.List;

import pl.ppsolutions.a237973_lab2.utils.SamplePeopleDataProvider;

public class MainActivity extends AppCompatActivity
{
    private ListView peopleListView;
    private ProgressBar pbLoadingPeopleProgress;
    PeopleListAdapter peopleListAdapter;
    private List<PersonData> peopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        peopleListView = findViewById(R.id.lv_people_list);
        pbLoadingPeopleProgress = findViewById(R.id.pb_loading_people_progress);
        new LoadPeopleTask().execute();
        logAllFiles();  // debug method
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        switch(itemId)
        {
            case R.id.add_person:
            {
                Intent addPersonIntent = new Intent(this, AddPersonActivity.class);
                startActivity(addPersonIntent);
                return true;
            }
            case R.id.load_sample_data:
            {
                loadSampleData();
                return true;
            }
            case R.id.clear_data:
            {
                clearData();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logAllFiles()
    {
        File directory = getDir("people_photos", MODE_PRIVATE);
        String [] fileNamesdirectory = directory.list();
        for(int i =0; i<fileNamesdirectory.length; i++)
        {
            Log.d("MainActivity", "File "+(i+1)+" : "+fileNamesdirectory[i]);
        }
    }

    private class LoadPeopleTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            showProgressBar();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            PeopleDataStorage peopleData = new PeopleDataStorage(MainActivity.this);
            peopleList = peopleData.readPeopleData();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            peopleListAdapter= new PeopleListAdapter(MainActivity.this, R.layout.my_list_item, peopleList);
            peopleListView.setAdapter(peopleListAdapter);
            hideProgressBar();
        }
    }

    private void showProgressBar()
    {
        peopleListView.setVisibility(View.INVISIBLE);
        pbLoadingPeopleProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar()
    {
        pbLoadingPeopleProgress.setVisibility(View.GONE);
        peopleListView.setVisibility(View.VISIBLE);
    }

    private void loadSampleData()
    {
        new AddSampleDataTask().execute();
    }

    private class AddSampleDataTask extends AsyncTask<Void, Void, List<PersonData>>
    {

        @Override
        protected void onPreExecute()
        {
            showProgressBar();
        }

        @Override
        protected List<PersonData> doInBackground(Void... voids)
        {
            List<PersonData> people = SamplePeopleDataProvider.getInstance(MainActivity.this).getPeopleSampleData();

            PeopleDataStorage dataStorage = new PeopleDataStorage(MainActivity.this);
            dataStorage.storePeopleData(people);

            return people;
        }

        @Override
        protected void onPostExecute(List<PersonData> people)
        {
            peopleListAdapter.addAll(people);
            hideProgressBar();
        }
    }

    private void clearData()
    {
        new ClearDataTask().execute();
    }

    private class ClearDataTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            PeopleDataStorage peopleData = new PeopleDataStorage(MainActivity.this);
            peopleData.clearAllData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            peopleListAdapter.clear();
        }
    }
}
