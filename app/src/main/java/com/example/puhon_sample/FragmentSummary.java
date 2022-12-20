package com.example.puhon_sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
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

    UserMoods userMoods;

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

        FetchMoodData();

        userMoods = new UserMoods();

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // Sample Data for Bar Chart
        for (int i=1; i<7; i++) {
            float value = (float) (i*10.0);
            BarEntry barEntry = new BarEntry(i, value);
            barEntries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Mood");
        InitMoodChart();
        barDataSet.setColors(new int[] {R.color.dark_pink, R.color.light_pink, R.color.mud, R.color.canary, R.color.dark_gray, R.color.brown}, getActivity());
        barDataSet.setDrawValues(false);
        moodChart.setData(new BarData(barDataSet));
    }

    private void FetchMoodData() {
        ShowDate();
        reference = database.getReference().child("users").child(id).child("UserMoods");

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

    // Customize bar chart
    private void InitMoodChart() {
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("", "Happy", "Angry", "Fearful", "Disgusted", "Sad", "Surprised"));
        // Lines and grids
        moodChart.setDrawGridBackground(false);
        moodChart.setDrawBarShadow(false);
        moodChart.setDrawBorders(false);

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
        YAxis rightAxis = moodChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);

        Legend legend = moodChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
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