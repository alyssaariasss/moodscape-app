package com.example.puhon_sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class FragmentOverallReport extends Fragment {
    CustomCalendar customCalendar;
    CardView selectedDateCard;
    ImageView moodView;
    TextView selectedDateText;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customCalendar = view.findViewById(R.id.customCalendar);
        selectedDateCard = view.findViewById(R.id.selectedDateCard);
        moodView = view.findViewById(R.id.moodView);
        selectedDateText = view.findViewById(R.id.selectedDateText);

        selectedDateCard.setVisibility(View.INVISIBLE);
        selectedDateText.setVisibility(View.INVISIBLE);
        moodView.setVisibility(View.INVISIBLE);

        // Sample Data for Calendar View
        InitCalendarView();
        HashMap<Integer, Object> mapDateToDesc = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        mapDateToDesc.put(2, "happy");
        mapDateToDesc.put(3, "happy");
        mapDateToDesc.put(4, "surprised");
        mapDateToDesc.put(6, "sad");
        mapDateToDesc.put(7, "sad");
        mapDateToDesc.put(9, "surprised");
        mapDateToDesc.put(11, "angry");
        mapDateToDesc.put(12, "angry");
        mapDateToDesc.put(15, "fearful");
        mapDateToDesc.put(17, "fearful");
        mapDateToDesc.put(18, "disgusted");
        mapDateToDesc.put(22, "disgusted");
        customCalendar.setDate(calendar, mapDateToDesc);

        customCalendar.setOnDateSelectedListener((view1, selectedDate, desc) -> {
            selectedDateCard.setVisibility(View.VISIBLE);
            selectedDateText.setVisibility(View.VISIBLE);
            moodView.setVisibility(View.VISIBLE);

            // Get date as string
            String sDate = (selectedDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH))
                    + " " + selectedDate.get(Calendar.DAY_OF_MONTH);
            // Display Date
            selectedDateText.setText(sDate);

            // Set Image
            if (desc == "happy") {
                moodView.setImageResource(R.drawable.happy_icon);
            } else if (desc == "angry") {
                moodView.setImageResource(R.drawable.angry_icon);
            } else if (desc == "fearful") {
                moodView.setImageResource(R.drawable.fearful_icon);
            } else if (desc == "sad") {
                moodView.setImageResource(R.drawable.sad_icon);
            } else if (desc == "disgusted") {
                moodView.setImageResource(R.drawable.disgusted_icon);
            } else if (desc == "surprised") {
                moodView.setImageResource(R.drawable.surprised_icon);
            }
        });
    }

    // Customize calendar bg based on mood
    private void InitCalendarView() {
        HashMap<Object, Property> mapDescToProp = new HashMap<>();

        // Default View
        Property propDefault = new Property();
        propDefault.layoutResource = R.layout.default_view;
        propDefault.dateTextViewResource = R.id.defaultDateTextView;
        mapDescToProp.put("default", propDefault);

        // Happy View
        Property propHappy = new Property();
        propHappy.layoutResource = R.layout.mood_happy_view;
        propHappy.dateTextViewResource = R.id.happyDateTextView;
        mapDescToProp.put("happy", propHappy);

        // Angry View
        Property propAngry = new Property();
        propAngry.layoutResource = R.layout.mood_angry_view;
        propAngry.dateTextViewResource = R.id.angryDateTextView;
        mapDescToProp.put("angry", propAngry);

        // Fearful View
        Property propFearful = new Property();
        propFearful.layoutResource = R.layout.mood_fearful_view;
        propFearful.dateTextViewResource = R.id.fearfulDateTextView;
        mapDescToProp.put("fearful", propFearful);

        // Disgusted View
        Property propDisgusted = new Property();
        propDisgusted.layoutResource = R.layout.mood_disgusted_view;
        propDisgusted.dateTextViewResource = R.id.disgustedDateTextView;
        mapDescToProp.put("disgusted", propDisgusted);

        // Sad View
        Property propSad = new Property();
        propSad.layoutResource = R.layout.mood_sad_view;
        propSad.dateTextViewResource = R.id.sadDateTextView;
        mapDescToProp.put("sad", propSad);

        // Surprised View
        Property propSurprised = new Property();
        propSurprised.layoutResource = R.layout.mood_surprised_view;
        propSurprised.dateTextViewResource = R.id.surprisedDateTextView;
        mapDescToProp.put("surprised", propSurprised);

        customCalendar.setMapDescToProp(mapDescToProp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overall_report, container, false);
    }
}