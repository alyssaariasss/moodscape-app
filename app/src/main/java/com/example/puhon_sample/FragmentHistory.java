package com.example.puhon_sample;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class FragmentHistory extends Fragment {

    RecyclerView goalsRecyclerView, goalsRecyclerView1;

    ArrayList<UserGoals> GoalsList, GoalsList2;
    historyGoalsAdapter HistoryGoalsAdapter;
    historyGoalsAdapter1 HistoryGoalsAdapter1;

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id, date;
    UserGoals userGoals;

    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Button dateButton;
    TextView completedHeader, missedHeader, emptyData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        goalsRecyclerView = (RecyclerView) view.findViewById(R.id.goalsRecyclerGroup);
        goalsRecyclerView1 = (RecyclerView) view.findViewById(R.id.goalsRecyclerGroup1);

        completedHeader = view.findViewById(R.id.completedHeader);
        missedHeader = view.findViewById(R.id.missedHeader);
        emptyData = view.findViewById(R.id.emptyData);

        completedHeader.setVisibility(View.GONE);
        missedHeader.setVisibility(View.GONE);

        dateButton = (Button) view.findViewById(R.id.historyDatePicker);
        dateButton.setText(getTodaysDate());

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        dateButton.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        dateSetListener = (datePicker, year1, month1, day1) -> {
            month1 = month1 + 1;
            date = makeDateString(day1, month1, year1);
            dateButton.setText(date);

            // retrieves goal data based on selected mood
            RetrieveData();
        };

        userGoals = new UserGoals();

        //database
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();

        reference = database.getReference().child("users").child(id).child("UserGoals");

        return view;
    }

    private void RetrieveData() {
        completedHeader.setVisibility(View.VISIBLE);
        missedHeader.setVisibility(View.VISIBLE);

        reference.orderByChild("date").equalTo(date).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GoalsList.clear();
                GoalsList2.clear();
                if (snapshot.exists()) {
                    emptyData.setVisibility(View.GONE);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        userGoals = dataSnapshot.getValue(UserGoals.class);
                        String status = Objects.requireNonNull(dataSnapshot.child("status").getValue()).toString();
                        if (status.equals("1")) {
                            GoalsList.add(userGoals);
                        }
                        else {
                            GoalsList2.add(userGoals);
                        }
                    }
                    HistoryGoalsAdapter.notifyDataSetChanged();
                    HistoryGoalsAdapter1.notifyDataSetChanged();
                } else {
                    completedHeader.setVisibility(View.GONE);
                    missedHeader.setVisibility(View.GONE);
                    emptyData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        goalsRecyclerView1.setHasFixedSize(true);
        goalsRecyclerView1.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        goalsRecyclerView.setHasFixedSize(true);
        goalsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        GoalsList = new ArrayList<>();
        GoalsList2 = new ArrayList<>();

        HistoryGoalsAdapter = new historyGoalsAdapter(this, GoalsList);
        HistoryGoalsAdapter1 = new historyGoalsAdapter1(this, GoalsList2);

        goalsRecyclerView1.setAdapter(HistoryGoalsAdapter);
        goalsRecyclerView.setAdapter(HistoryGoalsAdapter1);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year){
        return String.format("%02d", day) + "/" + getMonthFormat(month) + "/" + year;
    }

    private String getMonthFormat(int month){
        if (month == 1)
            return "Jan";
        if (month == 2)
            return "Feb";
        if (month == 3)
            return "Mar";
        if (month == 4)
            return "Apr";
        if (month == 5)
            return "May";
        if (month == 6)
            return "Jun";
        if (month == 7)
            return "Jul";
        if (month == 8)
            return "Aug";
        if (month == 9)
            return "Sep";
        if (month == 10)
            return "Oct";
        if (month == 11)
            return "Nov";
        if (month == 12)
            return "Dec";

        //default
        return "Jan";
    }
}