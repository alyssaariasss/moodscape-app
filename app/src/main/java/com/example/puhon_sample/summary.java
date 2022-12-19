package com.example.puhon_sample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class summary extends AppCompatActivity {

    ListView mUserMoodData;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    UserMoods userMoods;

    AppCompatRadioButton summaryRb, overallRb;
    TextView inputTitle, overallReportTitle, chartTitle, selectedDateText;
    CardView inputCard, overallReportCard, progressCard, selectedDateCard;
    ImageView moodView;

    LinearLayout linearLayout;

    BarChart barChart;

    CustomCalendar customCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        summaryRb = findViewById(R.id.summaryRb);
        overallRb = findViewById(R.id.overallRb);

        inputCard = findViewById(R.id.inputCard);
        inputTitle = findViewById(R.id.inputTitle);
        overallReportCard = findViewById(R.id.overallReportCard);
        progressCard = findViewById(R.id.progressCard);
        chartTitle = findViewById(R.id.chartTitle);
        overallReportTitle = findViewById(R.id.overallReportTitle);
        selectedDateCard = findViewById(R.id.selectedDateCard);
        selectedDateText = findViewById(R.id.selectedDateText);
        moodView = findViewById(R.id.moodView);

        linearLayout = findViewById(R.id.linearLayout);

        barChart = findViewById(R.id.barChart);

        customCalendar = findViewById(R.id.customCalendar);

        userMoods = new UserMoods();

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        // Sample Data for Bar Chart
        for (int i=1; i<7; i++) {
            float value = (float) (i*10.0);
            BarEntry barEntry = new BarEntry(i, value);
            barEntries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Mood");
        initBarChart();
        barDataSet.setColors(new int[] {R.color.dark_pink, R.color.light_pink, R.color.mud, R.color.canary, R.color.dark_gray, R.color.brown}, this);
        barDataSet.setDrawValues(false);
        barChart.setData(new BarData(barDataSet));

        // Sample Data for Calendar View
        initCalendarView();
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

        customCalendar.setOnDateSelectedListener((view, selectedDate, desc) -> {
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

//        fAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = fAuth.getCurrentUser();
//        id = user.getUid();
//        reference = database.getInstance().getReference().child("users").child(id).child("UserMoods");
//        list = new ArrayList<>();
//        adapter = new ArrayAdapter<String>(this, R.layout.list,R.id.usermoodlist,list);
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//
//                for (DataSnapshot ds : datasnapshot.getChildren()) {
//
//                    userMoods = ds.getValue(UserMoods.class);
//                    list.add(userMoods.getMood());
//
//                }
//                mUserMoodData.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);

        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        ImageButton btn_info = findViewById(R.id.nav_about_mood);

        btn_info.setOnClickListener(v -> {

            Intent intent = new Intent(this, BreakScreen1.class);
            startActivity(intent);
        });

        ImageButton btn_progress = findViewById(R.id.nav_progress);

        btn_progress.setOnClickListener(v -> {

            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        ImageButton btn_settings = findViewById(R.id.nav_settings);

        btn_settings.setOnClickListener(v -> {

            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        });

    }

    // Toggle switch button for Summary and Overall Report
    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean isSelected = ((AppCompatRadioButton)view).isChecked();
        switch (view.getId()) {
            case R.id.summaryRb:
                if(isSelected) {
                    // Radio Button State
                    summaryRb.setTextColor(ContextCompat.getColor(this, R.color.white));
                    summaryRb.setBackground(ContextCompat.getDrawable(this, R.drawable.radio_btn_pressed));
                    summaryRb.setElevation(5);
                    summaryRb.setStateListAnimator(null);

                    overallRb.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));
                    overallRb.setBackground(ContextCompat.getDrawable(this, R.drawable.radio_btn_default));
                    overallRb.setElevation(0);

                    // Card State
                    inputCard.setVisibility(View.VISIBLE);
                    inputTitle.setVisibility(View.VISIBLE);
                    progressCard.setVisibility(View.VISIBLE);
                    chartTitle.setVisibility(View.VISIBLE);
                    barChart.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    overallReportCard.setVisibility(View.GONE);
                    customCalendar.setVisibility(View.GONE);
                    overallReportTitle.setVisibility(View.GONE);
                    selectedDateCard.setVisibility(View.GONE);
                    selectedDateText.setVisibility(View.GONE);
                    moodView.setVisibility(View.GONE);
                }
                break;
            case R.id.overallRb:
                if(isSelected) {
                    // Radio Button State
                    summaryRb.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));
                    summaryRb.setBackground(ContextCompat.getDrawable(this, R.drawable.radio_btn_default));
                    summaryRb.setElevation(0);

                    overallRb.setTextColor(ContextCompat.getColor(this, R.color.white));
                    overallRb.setBackground(ContextCompat.getDrawable(this, R.drawable.radio_btn_pressed));
                    overallRb.setElevation(5);
                    overallRb.setStateListAnimator(null);

                    // Card State
                    inputCard.setVisibility(View.GONE);
                    inputTitle.setVisibility(View.GONE);
                    progressCard.setVisibility(View.GONE);
                    chartTitle.setVisibility(View.GONE);
                    barChart.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    overallReportCard.setVisibility(View.VISIBLE);
                    customCalendar.setVisibility(View.VISIBLE);
                    overallReportTitle.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    // Customize bar chart
    private void initBarChart() {
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("", "Happy", "Angry", "Fearful", "Disgusted", "Sad", "Surprised"));
        // Lines and grids
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawBorders(false);

        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        // Bar animation
        barChart.animateY(1500);
        barChart.animateX(1500);

        // X axis settings
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        // Y axis settings
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);

        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
    }

    // Customize calendar bg based on mood
    private void initCalendarView() {
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
}