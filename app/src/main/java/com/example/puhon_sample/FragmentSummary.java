package com.example.puhon_sample;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FragmentSummary extends Fragment {
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id, dateToday, mood, date;
    int happyCount, angryCount, fearfulCount, disgustedCount, sadCount, surprisedCount;

    TextView moodTxt, taskTxt, sleepTxt;

    BarChart moodChart, goalsChart;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moodTxt = view.findViewById(R.id.mood_txt);
        taskTxt = view.findViewById(R.id.task_txt);
        sleepTxt = view.findViewById(R.id.sleep_txt);

        moodChart = view.findViewById(R.id.moodChart);
        goalsChart = view.findViewById(R.id.goalsChart);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();

        reference = database.getReference().child("users").child(id).child("UserMoods");

        FetchMoodData();
        CountMoods();
        CountGoals();
    }

    private void FetchMoodData() {
        ShowDate();

        reference.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                date = snapshot.child("date").getValue(String.class);

                if (Objects.equals(dateToday, date)) {
                    mood = String.valueOf(snapshot.child("mood").getValue());
                } else {
                    moodTxt.setText(R.string.mood_null);
                }

                CheckMoodInput();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    private void CheckMoodInput() {
        if (Objects.equals(mood, null)) {
            moodTxt.setText(R.string.mood_null);
        } else if (Objects.equals(mood, "Happy")) {
            moodTxt.setText(R.string.mood_great);
        } else {
            moodTxt.setText(R.string.mood_others);
        }
    }

    // Retrieves all moods and initializes mood count chart
    private void CountMoods() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (Objects.requireNonNull(dataSnapshot.child("mood").getValue(String.class))) {
                        case "Happy":
                            ++happyCount;
                            break;
                        case "Angry":
                            ++angryCount;
                            break;
                        case "Fearful":
                            ++fearfulCount;
                            break;
                        case "Disgusted":
                            ++disgustedCount;
                            break;
                        case "Sad":
                            ++sadCount;
                            break;
                        case "Surprised":
                            ++surprisedCount;
                            break;
                    }
                }
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                int i = 1;

                barEntries.add(new BarEntry(i, happyCount));
                barEntries.add(new BarEntry(i+1, angryCount));
                barEntries.add(new BarEntry(i+2, fearfulCount));
                barEntries.add(new BarEntry(i+3, disgustedCount));
                barEntries.add(new BarEntry(i+4, sadCount));
                barEntries.add(new BarEntry(i+5, surprisedCount));

                BarDataSet barDataSet = new BarDataSet(barEntries,"Mood");
                InitMoodChart();
                barDataSet.setColors(new int[] {R.color.dark_pink, R.color.light_pink, R.color.mud, R.color.canary, R.color.dark_gray, R.color.brown}, getActivity());
                barDataSet.setDrawValues(false);
                moodChart.setData(new BarData(barDataSet));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    // Dummy data for goals
    private void CountGoals() {
        ArrayList<BarEntry> goalEntries = new ArrayList<>();

        // Sample Data for Bar Chart
        for (int i=1; i<8; i++) {
            float value = (float) (i*10.0);
            BarEntry goalEntry = new BarEntry(i, value);
            goalEntries.add(goalEntry);
        }

        BarDataSet goalDataSet = new BarDataSet(goalEntries,"Dates");
        InitGoalChart();
        goalDataSet.setColor(ContextCompat.getColor(requireContext(), R.color.dark_pink));
        goalDataSet.setDrawValues(false);
        goalsChart.setData(new BarData(goalDataSet));
    }

    // Customize mood chart
    private void InitMoodChart() {
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("", "Happy", "Angry", "Fearful", "Disgust", "Sad", "Surprised"));

        // General settings
        moodChart.setDrawGridBackground(false);
        moodChart.setDrawBarShadow(false);
        moodChart.setDrawBorders(false);
        moodChart.setExtraOffsets(5f,5f,5f,15f);

        // Remove description label
        Description description = new Description();
        description.setEnabled(false);
        moodChart.setDescription(description);

        // Bar animation
        moodChart.animateY(1500);
        moodChart.animateX(1500);

        // X axis settings
        XAxis xAxis = moodChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        // Y axis settings
        YAxis leftAxis = moodChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setAxisMinimum(0.0f);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setGranularity(1.0f);

        YAxis rightAxis = moodChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setAxisMinimum(0.0f);
        rightAxis.setGranularityEnabled(true);
        rightAxis.setGranularity(1.0f);


        // Touch and Scale settings
        moodChart.setTouchEnabled(false);
        moodChart.setDragEnabled(false);
        moodChart.setScaleEnabled(false);
        moodChart.setScaleXEnabled(false);
        moodChart.setPinchZoom(false);
    }

    // Customize goal chart
    private void InitGoalChart() {
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("", "12/19", "12/20", "12/21", "12/22", "12/23", "12/24", "12/25"));

        // General settings
        goalsChart.setDrawGridBackground(false);
        goalsChart.setDrawBarShadow(false);
        goalsChart.setDrawBorders(false);
        goalsChart.setExtraOffsets(5f,5f,5f,15f);

        // Remove description label
        Description description = new Description();
        description.setEnabled(false);
        goalsChart.setDescription(description);

        // Bar animation
        goalsChart.animateY(1500);
        goalsChart.animateX(1500);

        // X axis settings
        XAxis xAxis = goalsChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        // Y axis settings
        YAxis leftAxis = goalsChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        YAxis rightAxis = goalsChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);

        // Touch and Scale settings
        goalsChart.setTouchEnabled(false);
        goalsChart.setDragEnabled(false);
        goalsChart.setScaleEnabled(false);
        goalsChart.setScaleXEnabled(false);
        goalsChart.setPinchZoom(false);
    }

    // Gets current date
    private void ShowDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
        dateToday = dateFormat.format(today);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }
}