package com.example.puhon_sample;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
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

    String id, dateToday, date, wStartDate, wEndDate, mStartDate, mEndDate;
    String mood = "none";
    int happyCount, angryCount, fearfulCount, disgustedCount, sadCount, surprisedCount;

    TextView moodTxt, taskTxt, sleepTxt;

    BarChart moodChart, goalsChart;

    Spinner moodSpinner, goalsSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        moodTxt = view.findViewById(R.id.mood_txt);
        taskTxt = view.findViewById(R.id.task_txt);
        sleepTxt = view.findViewById(R.id.sleep_txt);

        moodChart = view.findViewById(R.id.moodChart);
        goalsChart = view.findViewById(R.id.goalsChart);

        moodSpinner = view.findViewById(R.id.moodSpinner);
        goalsSpinner = view.findViewById(R.id.goalsSpinner);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();

        reference = database.getReference().child("users").child(id).child("UserMoods");

        FetchMoodData();
        InitSpinners();
        CountGoals();

        return view;
    }

    // Initializes and checks value of spinners for data filtering
    private void InitSpinners() {
        String [] filter = getResources().getStringArray(R.array.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, filter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(adapter);
        goalsSpinner.setAdapter(adapter);

        // Checks value of spinner and displays mood data
        moodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String valueFromSpinner = adapterView.getItemAtPosition(i).toString();
                if (valueFromSpinner.equals("By Week")) {
                    GetFirstDayWeek();
                    CountWeeklyMood();
                } else {
                    GetFirstDayMonth();
                    CountMonthlyMood();
                }
            }

            // Default weekly data
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                GetFirstDayWeek();
                CountWeeklyMood();
            }
        });

        // goalsSpinner start onItemSelected


        // goalsSpinner end onItemSelected
    }

    // Gets latest input of mood
    private void FetchMoodData() {
        ShowDate();

        reference.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                date = snapshot.child("date").getValue(String.class);

                if (Objects.equals(dateToday, date)) {
                    mood = String.valueOf(snapshot.child("mood").getValue());
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

    // Displays text depending on mood input
    private void CheckMoodInput() {
        if (Objects.equals(mood, "none")) {
            moodTxt.setText(R.string.mood_null);
        } else if (Objects.equals(mood, "Happy")) {
            moodTxt.setText(R.string.mood_great);
        } else {
            moodTxt.setText(R.string.mood_others);
        }
    }

    // Retrieves weekly moods and initializes mood count chart
    private void CountWeeklyMood() {
        reference.orderByChild("date").startAt(wStartDate).endAt(wEndDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wStartDate = wStartDate.substring(3,6);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String date = Objects.requireNonNull(dataSnapshot.child("date").getValue()).toString();
                    date = date.substring(3,6);

                    if (Objects.equals(date, wStartDate)) {
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
                }
                SetMoodData();
                // Sets all count to 0 to avoid overlapping of values from weekly and monthly chart
                happyCount = 0; angryCount = 0; fearfulCount = 0; disgustedCount = 0; sadCount = 0; surprisedCount = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    // Retrieves monthly moods and initializes mood count chart
    private void CountMonthlyMood() {
        reference.orderByChild("date").startAt(mStartDate).endAt(mEndDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mStartDate = mStartDate.substring(3,6);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String date = Objects.requireNonNull(dataSnapshot.child("date").getValue()).toString();
                    date = date.substring(3,6);

                    if (Objects.equals(date, mStartDate)) {
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
                }
                SetMoodData();
                // Sets all count to 0 to avoid overlapping of values from weekly and monthly chart
                happyCount = 0; angryCount = 0; fearfulCount = 0; disgustedCount = 0; sadCount = 0; surprisedCount = 0;
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
        // Sample date
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

    // Adds count to bar entries and sets data for chart
    private void SetMoodData() {
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

    // Gets current date
    private void ShowDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
        dateToday = dateFormat.format(today);
    }

    // Gets first day and end day of the week
    private void GetFirstDayWeek() {
        final LocalizedWeek week = new LocalizedWeek(Locale.US);
        wStartDate = String.valueOf(week.getFirstDay());
        wEndDate = String.valueOf(week.getLastDay());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            Date sWeek = dateFormat.parse(wStartDate);
            Date eWeek = dateFormat.parse(wEndDate);

            assert sWeek != null;
            wStartDate = new SimpleDateFormat("dd/MMM/yyyy", Locale.US).format(sWeek);
            assert eWeek != null;
            wEndDate = new SimpleDateFormat("dd/MMM/yyyy", Locale.US).format(eWeek);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Gets first day and end day of the month
    private void GetFirstDayMonth() {
        ZoneId TZ = ZoneId.of("Asia/Manila");
        LocalDate today = LocalDate.now(TZ);
        mStartDate = String.valueOf(today.withDayOfMonth(1));
        mEndDate = String.valueOf(today.with(TemporalAdjusters.lastDayOfMonth()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            Date sMonth = dateFormat.parse(mStartDate);
            Date eMonth = dateFormat.parse(mEndDate);

            assert sMonth != null;
            mStartDate = new SimpleDateFormat("dd/MMM/yyyy", Locale.US).format(sMonth);
            assert eMonth != null;
            mEndDate = new SimpleDateFormat("dd/MMM/yyyy", Locale.US).format(eMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}