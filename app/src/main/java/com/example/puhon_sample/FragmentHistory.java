package com.example.puhon_sample;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventListener;

public class FragmentHistory extends Fragment {

    RecyclerView goalsRecyclerView, goalsRecyclerView1;
    ArrayList<String> arrayListGroup;
    ArrayList<String> arrayListMissed;
    ArrayList<UserGoals> GoalsList, GoalsList2;
    LinearLayoutManager linearLayoutManager;
    GroupAdapter groupAdapter;
    historyGoalsAdapter HistoryGoalsAdapter;
    historyGoalsAdapter1 HistoryGoalsAdapter1;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    UserGoals userGoals;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        initDatePicker();

        dateButton = (Button) view.findViewById(R.id.historyDatePicker);
        dateButton.setText(getTodaysDate());

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        goalsRecyclerView = (RecyclerView) view.findViewById(R.id.goalsRecyclerGroup);
        goalsRecyclerView1 = (RecyclerView) view.findViewById(R.id.goalsRecyclerGroup1);

        userGoals = new UserGoals();

        //database
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();

        reference = database.getReference().child("users").child(id).child("UserGoals");
        //HistoryGoalsAdapter = new historyGoalsAdapter(this, GoalsList);
        //HistoryGoalsAdapter1 = new historyGoalsAdapter1(this, GoalsList2);

        //String dateHistory =


        //Completed Goals
     //ValueEventListener valueEventListener = new ValueEventListener()

        reference.child("date").equalTo(date).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GoalsList.clear();
                GoalsList2.clear();
                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        userGoals = dataSnapshot.getValue(UserGoals.class);
                        String status = dataSnapshot.child("status").getValue().toString();
                        if (status.equals("1")) {
                            GoalsList.add(userGoals);
                        }
                        else {
                            GoalsList2.add(userGoals);
                        }

                    }
                    HistoryGoalsAdapter.notifyDataSetChanged();
                    HistoryGoalsAdapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //reference.addListenerForSingleValueEvent(valueEventListener);
       // Query query = reference.orderByChild("date").equalTo(date);
        //System.out.println(date);
        //query.addListenerForSingleValueEvent(valueEventListener);
        //reference.addListenerForSingleValueEvent(valueEventListener);
        //Query query = reference.orderByChild("status").equalTo(1);
        //query.addListenerForSingleValueEvent(valueEventListener);
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


        // Missed Goals
        //ValueEventListener valueEventListener1 = new ValueEventListener() {
            //@Override
           // public void onDataChange(@NonNull DataSnapshot snapshot) {
               // GoalsList2.clear();
               // if (snapshot.exists()) {
                   // for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                      //  userGoals = dataSnapshot.getValue(UserGoals.class);
                      //  GoalsList2.add(userGoals);
                  //  }
                   // HistoryGoalsAdapter1.notifyDataSetChanged();
               // }
           // }

           // @Override
           // public void onCancelled(@NonNull DatabaseError error) {

          //  }
       // };
       // reference.addListenerForSingleValueEvent(valueEventListener1);
        //Query query1 = reference.orderByChild("status").equalTo(0);
      //  query1.addListenerForSingleValueEvent(valueEventListener1);
       // goalsRecyclerView.setHasFixedSize(true);
       // goalsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
      //  GoalsList2 = new ArrayList<>();
       // HistoryGoalsAdapter1 = new historyGoalsAdapter1(this, GoalsList);
       // goalsRecyclerView.setAdapter(HistoryGoalsAdapter1);




        //arrayListGroup = new ArrayList<>();

        // Date
       // for (int i=1; i<=10; i++) {
        //    arrayListGroup.add("04/Jan/2023");
       // }

       // arrayListMissed = new ArrayList<>();
       // for (int i=1; i <= 4; i++) {
       //     arrayListMissed.add("Missed Goals " + i);
       // }

       // groupAdapter = new GroupAdapter(FragmentHistory.this, arrayListGroup, arrayListMissed);
       // linearLayoutManager = new LinearLayoutManager(getContext());
       // goalsRecyclerView.setLayoutManager(linearLayoutManager);
       // goalsRecyclerView.setAdapter(groupAdapter);

       return view;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = makeDateString(day,month,year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
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