package com.example.tfarthing.izagradio;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jtwheadon on 11/29/17.
 */

public class ScheduleFragment extends Fragment {

    private LinearLayout linearLayout;
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.schedule_layout, container, false);

        // Set Title
        getActivity().setTitle("");
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.schedule_layout);

        // Create the list of days
        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");

        // Create the array adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, days) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView1 = (TextView)view.findViewById(android.R.id.text1);
                textView1.setTextColor(getResources().getColor(R.color.white));

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                view.setLayoutParams(layoutParams);
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };

        // Create Spinner and Adapter
        final Spinner scheduleSpinner = (Spinner)myView.findViewById(R.id.schedule_spinner);
        scheduleSpinner.setAdapter(arrayAdapter);

        // ImageView to hold the schedule of that day (for now)
        final ImageView scheduleImage = myView.findViewById(R.id.schedule_image);

        scheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView1 = (TextView)view.findViewById(android.R.id.text1);
                switch(textView1.getText().toString()) {
                    case "Monday":
                        scheduleImage.setImageResource(R.drawable.monday);
                        break;
                    case "Tuesday":
                        scheduleImage.setImageResource(R.drawable.tuesday);
                        break;
                    case "Wednesday":
                        scheduleImage.setImageResource(R.drawable.wednesday);
                        break;
                    case "Thursday":
                        scheduleImage.setImageResource(R.drawable.thursday);
                        break;
                    case "Friday":
                        scheduleImage.setImageResource(R.drawable.friday);
                        break;
                    case "Saturday":
                        scheduleImage.setImageResource(R.drawable.saturday);
                        break;
                    case "Sunday":
                        scheduleImage.setImageResource(R.drawable.sunday);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:
                scheduleSpinner.setSelection(0);
                break;

            case Calendar.TUESDAY:
                scheduleSpinner.setSelection(1);
                break;

            case Calendar.WEDNESDAY:
                scheduleSpinner.setSelection(2);
                break;

            case Calendar.THURSDAY:
                scheduleSpinner.setSelection(3);
                break;

            case Calendar.FRIDAY:
                scheduleSpinner.setSelection(4);
                break;

            case Calendar.SATURDAY:
                scheduleSpinner.setSelection(5);
                break;

            case Calendar.SUNDAY:
                scheduleSpinner.setSelection(6);
                break;

            default:
                scheduleSpinner.setSelection(0);
        }
    }
}
