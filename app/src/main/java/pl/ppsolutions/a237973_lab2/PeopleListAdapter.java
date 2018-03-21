package pl.ppsolutions.a237973_lab2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.ppsolutions.a237973_lab2.utils.DateUtils;

/**
 * Created by Kretek on 19/03/2018.
 */

public class PeopleListAdapter extends ArrayAdapter<PersonData>
{
    private static final String TAG = PeopleListAdapter.class.getSimpleName();
    private int rowLayoutResourceId;

    public PeopleListAdapter(@NonNull Context context, int resource, List<PersonData> peopleData)
    {
        super(context, resource, peopleData);
        rowLayoutResourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ViewHolder holder = null;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(rowLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.ivPhoto = convertView.findViewById(R.id.iv_person_photo);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvSurname = convertView.findViewById(R.id.tv_surname);
            holder.tvAge = convertView.findViewById(R.id.tv_display_age);
            holder.ibRemoveButton = convertView.findViewById(R.id.ib_remove_person);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        PersonData person = getItem(position);
        if(person!=null)
        {
            holder.ivPhoto.setImageBitmap(person.getPhoto());
            holder.tvName.setText(person.getName());
            holder.tvSurname.setText(person.getSurname());
            holder.tvAge.setText(String.valueOf(DateUtils.getDiffInYearsByToday(person.getBirthDate())));
            Log.d(TAG, "Person attached to view holder: id: "+person.getId()+" ; name: "+person.getName());
            holder.ibRemoveButton.setTag(person);
            holder.ibRemoveButton.setOnClickListener(removeButtonOnClickListener);
        }

        return convertView;
    }

    private static class ViewHolder
    {
        private ImageView ivPhoto;
        private TextView tvName;
        private TextView tvSurname;
        private TextView tvAge;
        private ImageButton ibRemoveButton;
    }

    private View.OnClickListener removeButtonOnClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            PersonData person = (PersonData) v.getTag();
            remove(person);
            new RemovePersonTask().execute(person);
        }
    };

    private class RemovePersonTask extends AsyncTask<PersonData, Void, Void>
    {

        @Override
        protected Void doInBackground(PersonData... personData)
        {
            PersonData person = personData[0];
            PeopleDataStorage peopleData = new PeopleDataStorage(getContext());
            peopleData.removePerson(person);
            return null;
        }
    }
}
