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

    String id, dateToday, wStartDate, wEndDate, mStartDate, mEndDate, sMonth;
    String mood = "none";
    int sleep = 0;
    List<String> days;
    // For mood count
    int happyCount, angryCount, fearfulCount, disgustedCount, sadCount, surprisedCount;
    // For goal count
    int checkedCount, uncheckedCount, checkedCount1, uncheckedCount1, checkedCount2, uncheckedCount2,
            checkedCount3, uncheckedCount3, checkedCount4, uncheckedCount4, checkedCount5, uncheckedCount5,
            checkedCount6, uncheckedCount6, checked, totalGoals;

    TextView moodTxt, taskTxt, sleepTxt;

    BarChart moodChart, goalsChart;
    BarData data;

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
        FetchGoalData();
        FetchSleepData();

        InitSpinners();

        return view;
    }

    // Initializes and checks value of spinners for data filtering
    private void InitSpinners() {
        // Mood Spinner
        String [] filter = getResources().getStringArray(R.array.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, filter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moodSpinner.setAdapter(adapter);

        // Goals Spinner
        String [] filter1 = getResources().getStringArray(R.array.spinner1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, filter1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalsSpinner.setAdapter(adapter1);

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

        goalsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String valueFromSpinner = adapterView.getItemAtPosition(i).toString();
                if (valueFromSpinner.equals("By Week")) {
                    GetFirstDayWeek();
                    CountGoals();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                GetFirstDayWeek();
                CountGoals();
            }
        });
    }

    // Gets latest input of mood
    private void FetchMoodData() {
        ShowDate();
        CheckMoodInput();

        reference.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String date = snapshot.child("date").getValue(String.class);

                if (Objects.equals(dateToday, date)) {
                    mood = String.valueOf(snapshot.child("mood").getValue());
                }
                CheckMoodInput();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

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

    // Gets total count of completed goals
    private void FetchGoalData() {
        ShowDate();

        DatabaseReference newRef = database.getReference().child("users").child(id).child("UserGoals");

        newRef.orderByChild("date").equalTo(dateToday).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ++totalGoals;
                    if (Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString().equals("1")) {
                        ++checked;
                    }
                }
                taskTxt.setText(String.format("%s out of %s tasks done.", checked, totalGoals));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    // Gets latest sleep data
    private void FetchSleepData() {
        ShowDate();
        CheckSleepInput();

        DatabaseReference newRef = database.getReference().child("users").child(id).child("UserQuestions");

        newRef.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String date = snapshot.child("date").getValue(String.class);

                if (Objects.equals(dateToday, date)) {
                    sleep = ((Long) Objects.requireNonNull(snapshot.child("userQuestion5").getValue())).intValue();
                }
                CheckSleepInput();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    // Displays text depending on sleep input
    private void CheckSleepInput() {
        if (sleep == 0) {
            sleepTxt.setText(R.string.sleep_null);
        } else if (sleep >= 6) {
            sleepTxt.setText(R.string.sleep_great);
        } else {
            sleepTxt.setText(R.string.sleep_others);
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
        DatabaseReference newRef = database.getReference().child("users").child(id).child("UserGoals");

        newRef.orderByChild("date").startAt(wStartDate).endAt(wEndDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String date = Objects.requireNonNull(dataSnapshot.child("date").getValue()).toString();
                    date = date.substring(0,2);

                    if (Objects.equals(date, days.get(0).substring(3,5))) {
                        switch (Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString()) {
                            case "1":
                                ++checkedCount;
                                break;
                            case "0":
                                ++uncheckedCount;
                                break;
                        }
                    } else if (Objects.equals(date, days.get(1).substring(3,5))) {
                        switch (Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString()) {
                            case "1":
                                ++checkedCount1;
                                break;
                            case "0":
                                ++uncheckedCount1;
                                break;
                        }
                    } else if (Objects.equals(date, days.get(2).substring(3,5))) {
                        switch (Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString()) {
                            case "1":
                                ++checkedCount2;
                                break;
                            case "0":
                                ++uncheckedCount2;
                                break;
                        }
                    } else if (Objects.equals(date, days.get(3).substring(3,5))) {
                        switch (Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString()) {
                            case "1":
                                ++checkedCount3;
                                break;
                            case "0":
                                ++uncheckedCount3;
                                break;
                        }
                    } else if (Objects.equals(date, days.get(4).substring(3,5))) {
                        switch (Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString()) {
                            case "1":
                                ++checkedCount4;
                                break;
                            case "0":
                                ++uncheckedCount4;
                                break;
                        }
                    } else if (Objects.equals(date, days.get(5).substring(3,5))) {
                        switch (Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString()) {
                            case "1":
                                ++checkedCount5;
                                break;
                            case "0":
                                ++uncheckedCount5;
                                break;
                        }
                    } else if (Objects.equals(date, days.get(6).substring(3,5))) {
                        switch (Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString()) {
                            case "1":
                                ++checkedCount6;
                                break;
                            case "0":
                                ++uncheckedCount6;
                                break;
                        }
                    }
                }
                SetGoalData();
                // Sets all count to 0 to avoid overlapping of values from weekly and monthly chart
                checkedCount = 0; uncheckedCount = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
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
        GetFirstDayWeek();
        List<String> xAxisValues = new ArrayList<>(days);

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
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMinimum(0);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        // Y axis settings
        YAxis leftAxis = goalsChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setAxisMinimum(0.0f);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setGranularity(1.0f);

        YAxis rightAxis = goalsChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setAxisMinimum(0.0f);
        rightAxis.setGranularityEnabled(true);
        rightAxis.setGranularity(1.0f);

        // Group bar data settings
        float barSpace = 0.1f;
        float groupSpace = 0.02f;
        data.setBarWidth(0.40f);

        goalsChart.groupBars(0, groupSpace, barSpace);
        goalsChart.invalidate();

        // Touch settings
        goalsChart.setDragEnabled(true);
        goalsChart.setVisibleXRangeMaximum(3);

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

    private void SetGoalData() {
        ArrayList<BarEntry> goalEntries = new ArrayList<>();
        ArrayList<BarEntry> goalEntries1 = new ArrayList<>();
        int i = 1;

        goalEntries.add(new BarEntry(i, checkedCount));
        goalEntries.add(new BarEntry(i+1, checkedCount1));
        goalEntries.add(new BarEntry(i+2, checkedCount2));
        goalEntries.add(new BarEntry(i+3, checkedCount3));
        goalEntries.add(new BarEntry(i+4, checkedCount4));
        goalEntries.add(new BarEntry(i+5, checkedCount5));
        goalEntries.add(new BarEntry(i+6, checkedCount6));

        goalEntries1.add(new BarEntry(i, uncheckedCount));
        goalEntries1.add(new BarEntry(i+1, uncheckedCount1));
        goalEntries1.add(new BarEntry(i+2, uncheckedCount2));
        goalEntries1.add(new BarEntry(i+3, uncheckedCount3));
        goalEntries1.add(new BarEntry(i+4, uncheckedCount4));
        goalEntries1.add(new BarEntry(i+5, uncheckedCount5));
        goalEntries1.add(new BarEntry(i+6, uncheckedCount6));

        BarDataSet goalDataSet = new BarDataSet(goalEntries,"Checked goals");
        goalDataSet.setColor(ContextCompat.getColor(requireContext(), R.color.dark_pink));
        BarDataSet goalDataSet1 = new BarDataSet(goalEntries1,"Unchecked goals");
        goalDataSet1.setColor(ContextCompat.getColor(requireContext(), R.color.light_pink));

        data = new BarData(goalDataSet, goalDataSet1);
        goalsChart.setData(data);
        InitGoalChart();
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
            sMonth = new SimpleDateFormat("MM/", Locale.US).format(sWeek);
            assert eWeek != null;
            wEndDate = new SimpleDateFormat("dd/MMM/yyyy", Locale.US).format(eWeek);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Gets all days of the week for goals chart x axis values
        int sDay = Integer.parseInt(wStartDate.substring(0,2));
        days = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            if (sDay < 10) {
                days.add(sMonth + "0" + sDay++);
            } else {
                days.add(sMonth + sDay++);
            }
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