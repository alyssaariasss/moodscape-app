package com.example.puhon_sample;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnNavigationButtonClickedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class FragmentOverallReport extends Fragment implements OnNavigationButtonClickedListener {
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference ref;
    String id, qDate;
    UserAnswers userAnswers;

    MyAdapter myAdapter;
    ArrayList<UserAnswers> list1;
    RecyclerView AnswerOne;

    CustomCalendar customCalendar;
    CardView selectedDateCard;
    ImageView moodView;
    TextView selectedDateText, overallReportTitle;

    HashMap<Integer, Object> mapDateToDesc;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customCalendar = view.findViewById(R.id.customCalendar);
        selectedDateCard = view.findViewById(R.id.selectedDateCard);
        moodView = view.findViewById(R.id.moodView);
        selectedDateText = view.findViewById(R.id.selectedDateText);
        overallReportTitle = view.findViewById(R.id.overallReportTitle);

        // For Questions
        AnswerOne = view.findViewById(R.id.answerOne);
        userAnswers = new UserAnswers();

        selectedDateCard.setVisibility(View.INVISIBLE);
        selectedDateText.setVisibility(View.INVISIBLE);
        moodView.setVisibility(View.INVISIBLE);

        AnswerOne.setVisibility(View.INVISIBLE);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();

        mapDateToDesc = new HashMap<>();


        reference = database.getReference().child("users").child(id).child("UserMoods");
        ref = database.getReference().child("users").child(id).child("UserQuestions");

        RetrieveMoods();



        // Previous and Next buttons on calendar
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.PREVIOUS, this);
        customCalendar.setOnNavigationButtonClickedListener(CustomCalendar.NEXT, this);

        // Displays date and mood when date is selected
        customCalendar.setOnDateSelectedListener((view1, selectedDate, desc) -> {
            selectedDateCard.setVisibility(View.VISIBLE);
            selectedDateText.setVisibility(View.VISIBLE);
            moodView.setVisibility(View.VISIBLE);
            AnswerOne.setVisibility(View.VISIBLE);


            // Gets date as string
            String sDate = (selectedDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH))
                    + " " + selectedDate.get(Calendar.DAY_OF_MONTH);
            // Displays selected date
            selectedDateText.setText(sDate);

            // Sets and displays image
            DisplayImage(desc);

            // display of answers from UserQuestions start
            qDate = (selectedDate.get(Calendar.DAY_OF_MONTH)) + "/" +
                    selectedDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                    + "/" + selectedDate.get(Calendar.YEAR);
            System.out.println(qDate);

            RetrieveQuestions();
            AnswerOne.setHasFixedSize(true);
            AnswerOne.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            list1 = new ArrayList<>();
            myAdapter = new MyAdapter(this, list1);

            // display of answers from UserQuestions end

        });
    }

    // Customizes calendar bg based on mood
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
        mapDescToProp.put("Happy", propHappy);

        // Angry View
        Property propAngry = new Property();
        propAngry.layoutResource = R.layout.mood_angry_view;
        propAngry.dateTextViewResource = R.id.angryDateTextView;
        mapDescToProp.put("Angry", propAngry);

        // Fearful View
        Property propFearful = new Property();
        propFearful.layoutResource = R.layout.mood_fearful_view;
        propFearful.dateTextViewResource = R.id.fearfulDateTextView;
        mapDescToProp.put("Fearful", propFearful);

        // Disgusted View
        Property propDisgusted = new Property();
        propDisgusted.layoutResource = R.layout.mood_disgusted_view;
        propDisgusted.dateTextViewResource = R.id.disgustedDateTextView;
        mapDescToProp.put("Disgusted", propDisgusted);

        // Sad View
        Property propSad = new Property();
        propSad.layoutResource = R.layout.mood_sad_view;
        propSad.dateTextViewResource = R.id.sadDateTextView;
        mapDescToProp.put("Sad", propSad);

        // Surprised View
        Property propSurprised = new Property();
        propSurprised.layoutResource = R.layout.mood_surprised_view;
        propSurprised.dateTextViewResource = R.id.surprisedDateTextView;
        mapDescToProp.put("Surprised", propSurprised);

        customCalendar.setMapDescToProp(mapDescToProp);
    }

    // Retrieves all moods and sends it to customCalendar
    private void RetrieveMoods() {
        Calendar calendar = Calendar.getInstance();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String date = Objects.requireNonNull(dataSnapshot.child("date").getValue()).toString();
                    String mood = Objects.requireNonNull(dataSnapshot.child("mood").getValue()).toString();
                    Integer day = Integer.parseInt(date.substring(0,2));

                    mapDateToDesc.put(day, mood);
                }
                customCalendar.setDate(calendar, mapDateToDesc);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    //Retrieve Questions
    private void RetrieveQuestions() {
        Calendar calendar = Calendar.getInstance();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    //UserAnswers answer = dataSnapshot.child("userQuestion1").getValue().toString;

                    UserAnswers userAnswers = dataSnapshot.getValue(UserAnswers.class);
                    String date1 = Objects.requireNonNull(dataSnapshot.child("date").getValue()).toString();
                    list1.add(userAnswers);
                    System.out.println(userAnswers);
                    System.out.println(date1);

                    if (qDate.equals(date1)) {
                        // For recyclerview
                        myAdapter.notifyDataSetChanged();
                        AnswerOne.setAdapter(myAdapter);
                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void DisplayImage(Object desc) {
        if (Objects.equals(desc, "Happy")) {
            moodView.setImageResource(R.drawable.happy_icon);
        } else if (Objects.equals(desc, "Angry")) {
            moodView.setImageResource(R.drawable.angry_icon);
        } else if (Objects.equals(desc, "Fearful")) {
            moodView.setImageResource(R.drawable.fearful_icon);
        } else if (Objects.equals(desc, "Sad")) {
            moodView.setImageResource(R.drawable.sad_icon);
        } else if (Objects.equals(desc, "Disgusted")) {
            moodView.setImageResource(R.drawable.disgusted_icon);
        } else if (Objects.equals(desc, "Surprised")) {
            moodView.setImageResource(R.drawable.surprised_icon);
        }
    }

    // Binds mood records to month -- still hardcoded
    @Override
    public Map<Integer, Object>[] onNavigationButtonClicked(int whichButton, Calendar newMonth) {
        Map<Integer, Object>[] arr = new Map[2];

        switch(newMonth.get(Calendar.MONTH)) {
            case (Calendar.DECEMBER):
                arr[0] = new HashMap<>();
                arr[0].put(19, "Angry");
                arr[0].put(20, "Happy");
                arr[0].put(22, "Angry");
                arr[0].put(27, "Sad");
                arr[1] = null;
                break;
            case (0):
                arr[0] = new HashMap<>();
                arr[0].put(1, "Angry");
                arr[0].put(12, "Happy");
                arr[0].put(21, "Angry");
                arr[0].put(29, "Sad");
                break;
            case (10):
                arr[0] = new HashMap<>();
                arr[0].put(5, "Disgusted");
                arr[0].put(10, "Fearful");
                arr[0].put(19, "Surprised");
                break;
        }
        return arr;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overall_report, container, false);
    }
}