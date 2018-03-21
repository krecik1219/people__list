package pl.ppsolutions.a237973_lab2;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import pl.ppsolutions.a237973_lab2.utils.DateUtils;
import pl.ppsolutions.a237973_lab2.utils.NameSurnameTextUtils;

public class AddPersonActivity extends AppCompatActivity
{
    private static final String ERROR_NAME_SURNAME_DIGITS = "Name and surname can not contain any digit";
    private static final String ERROR_EMPTY_INPUT_FIELD = "Field can not be empty";
    private static final String ERROR_EMPTY_BIRTH_DATE = "Please select person birth date";
    private static final String ERROR_FUTURE_BIRTH_DATE = "It's impossible, this person is not born yet";

    private TextView tvName;
    private TextView tvSurname;
    private TextView tvBirthDate;
    private EditText etProvideName;
    private EditText etProvideSurname;
    private EditText etSelectBirthDate;
    private Date selectedDate = null;
    private ProgressBar pbAddingProgress;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth, 0, 0, 0);
            selectedDate = calendar.getTime();
            updateBirthDateEditField();
        }
    };;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        etProvideName = findViewById(R.id.et_name);
        etProvideSurname = findViewById(R.id.et_surname);
        pbAddingProgress = findViewById(R.id.pb_adding_person_progress);
        tvName = findViewById(R.id.tv_name_label);
        tvSurname = findViewById(R.id.tv_surname_label);
        tvBirthDate = findViewById(R.id.tv_birth_date);
        etSelectBirthDate = findViewById(R.id.et_birth_date);
        etSelectBirthDate.setInputType(InputType.TYPE_NULL);
        etSelectBirthDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                displayDataPickerDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_person_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.confirm_editing)
        {
            addPerson();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addPerson()
    {
        if(allInputsCorrect())
        {
            String name = etProvideName.getText().toString();
            String surname = etProvideSurname.getText().toString();
            Date birthDate = selectedDate;
            //for now dummy photo bitmap
            //for now every person has its own copy of bitmap image
            Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.user_default_portrait);
            PersonData personData = new PersonData(name, surname, birthDate, photo);
            new AddPersonTask().execute(personData);
        }
    }

    private boolean allInputsCorrect()
    {
        boolean nameCorrect = validateNameOrSurname(etProvideName);
        boolean surnameCorrect = validateNameOrSurname(etProvideSurname);
        boolean birthDateCorrect = validateBirthDate();

        return nameCorrect && surnameCorrect && birthDateCorrect;
    }

    private boolean validateBirthDate()
    {
        if(selectedDate!= null)
        {
            if(DateUtils.notAfterToday(selectedDate))
                return true;
            else
            {
                Toast.makeText(this, ERROR_FUTURE_BIRTH_DATE, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else
        {
            Toast.makeText(this, ERROR_EMPTY_BIRTH_DATE, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateNameOrSurname(@NonNull EditText inputField)
    {
        String inputText = inputField.getText().toString();
        if(!inputText.isEmpty())
        {
            String skippedWhiteSpaces = NameSurnameTextUtils.skipLeadingAndTrailingWhitespaces(inputText);
            if(NameSurnameTextUtils.isDigitsFree(skippedWhiteSpaces))
            {
                String capitalized = NameSurnameTextUtils.capitalizeFirstLetter(skippedWhiteSpaces);
                inputField.setText(capitalized);
                return true;
            }
            else
            {
                inputField.setError(ERROR_NAME_SURNAME_DIGITS);
                return false;
            }
        }
        else
        {
            inputField.setError(ERROR_EMPTY_INPUT_FIELD);
            return false;
        }
    }

    private class AddPersonTask extends AsyncTask<PersonData, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            showProgressBar();
        }

        @Override
        protected Void doInBackground(PersonData... peopleData)
        {
            PersonData personData = peopleData[0];
            PeopleDataStorage dataStorage = new PeopleDataStorage(AddPersonActivity.this);
            dataStorage.storePersonData(personData);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            NavUtils.navigateUpFromSameTask(AddPersonActivity.this);
        }
    }

    private void showProgressBar()
    {
        tvName.setVisibility(View.INVISIBLE);
        tvSurname.setVisibility(View.INVISIBLE);
        tvBirthDate.setVisibility(View.INVISIBLE);
        etProvideName.setVisibility(View.INVISIBLE);
        etProvideSurname.setVisibility(View.INVISIBLE);
        etSelectBirthDate.setVisibility(View.INVISIBLE);
        findViewById(R.id.confirm_editing).setVisibility(View.INVISIBLE);  // menu item hide
        ActionBar bar = getSupportActionBar();
        if(bar!=null)
            bar.setDisplayHomeAsUpEnabled(false);
        pbAddingProgress.setVisibility(View.VISIBLE);
    }

    private void displayDataPickerDialog()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, R.style.Theme_AppCompat_Light_Dialog, mOnDateSetListener, year, month, day);
        dialog.show();
    }

    private void updateBirthDateEditField()
    {
        String dateString = DateFormat.format("dd/MM/yyyy", selectedDate).toString();
        etSelectBirthDate.setText(dateString);
    }
}
