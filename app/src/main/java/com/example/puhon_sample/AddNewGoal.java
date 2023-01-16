package com.example.puhon_sample;


import static android.content.Context.ALARM_SERVICE;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddNewGoal extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewGoal";

    EditText goalEdit;
    Button addGoal, setTime;
    int hour, minute, currentHour, goalId;

    UserGoals userGoals;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id, dateToday, goal, time;

    boolean isUpdate, isSaved;

    int i = 0;

    public static AddNewGoal newInstance(){

        return new AddNewGoal();
    }

    @Override
    public void onCreate(Bundle savedInstanceStae) {
        super.onCreate(savedInstanceStae);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_goal , container , false);
        Objects.requireNonNull(getDialog()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTime = requireView().findViewById(R.id.goalTime);
        goalEdit = requireView().findViewById(R.id.editxtNGoal);
        addGoal = requireView().findViewById(R.id.buttonAddGoal);

        CreateNotificationChannel();
        InitializeUpdate();

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();

        reference = database.getReference().child("users").child(id).child("UserGoals");

        // Displays pop up time picker
        setTime.setOnClickListener(view1 -> {
            ShowDate();

            // Gets current hour
            Instant instant = Instant.now();
            ZoneId z = ZoneId.of("Asia/Manila");
            ZonedDateTime zdt = instant.atZone(z);
            currentHour = zdt.getHour();

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    (timePicker, selectedHour, selectedMinute) -> {
                        hour = selectedHour;
                        minute = selectedMinute;

                        Calendar calendar = Calendar.getInstance();

                        // Checks if selected hour has passed the current hour
                        if (hour < currentHour) {
                            Toast.makeText(getContext(), "Set the deadline at a later date.", Toast.LENGTH_SHORT).show();
                        } else {
                            calendar.set(0,0,0, hour, minute);
                            setTime.setText(DateFormat.format("hh:mm aa", calendar));
                        }
                    }, 12, 0, false);

            timePickerDialog.updateTime(hour, minute);
            timePickerDialog.show();
        });

        addGoal.setOnClickListener(view2 -> {
            boolean finalIsUpdate = isUpdate;

            ShowDate();

            goal = goalEdit.getText().toString().trim();
            time = setTime.getText().toString();

            // Check if dialog is in update state
            if (finalIsUpdate) {
                // Retrieves goalId of goal to be edited
                Query query = reference.orderByChild("goalId").equalTo(goalId);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            dataSnapshot.getRef().child("goal").setValue(goal);
                            dataSnapshot.getRef().child("deadline").setValue(time);
                        }
                        Toast.makeText(getContext(), "Changes have been saved.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, error.getMessage());
                    }
                });

            } else {
                // Validates input from user
                if (TextUtils.isEmpty(goal)) {
                    goalEdit.setError("Goal title is required.");
                    return;
                }

                if (time.equals("Set Time")) {
                    setTime.setError("Deadline is required.");
                    return;
                }

                // Gets children count of UserGoals
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            i = (int) snapshot.getChildrenCount();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, error.getMessage());
                    }
                });

                // Create new instance of UserGoals and stores new goal and deadline
                userGoals = new UserGoals();

                reference.orderByChild("goal").equalTo(goal).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String date = Objects.requireNonNull(dataSnapshot.child("date").getValue()).toString();

                                if (date.equals(dateToday)) {
                                    isSaved = false;
                                    DisplayToast();
                                } else {
                                    SaveNewGoal();
                                }
                            }
                        } else {
                            SaveNewGoal();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, error.getMessage());
                    }
                });
            }
        });
    }

    // Saves new goal to userGoals and pushes it to database
    private void SaveNewGoal() {
        userGoals.setGoalId(i+1);
        userGoals.setDate(dateToday);
        userGoals.setGoal(goal);
        userGoals.setDeadline(time);
        userGoals.setStatus(0);
        reference.child(String.valueOf(i+1)).setValue(userGoals);

        isSaved = true;
        DisplayToast();

        // Set up alarm system for goals
        Intent intent = new Intent(getContext(), GoalsNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(ALARM_SERVICE);

        // Add selected hour and minute to calendar
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);

        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
    }

    // Display toast message after saving data
    private void DisplayToast() {
        if (isSaved) {
            Toast.makeText(getContext(), "A new goal has been added.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Goal already exists. Please enter a different goal title.", Toast.LENGTH_SHORT).show();
        }
    }

    // Initialize GoalsNotification channel
    private void CreateNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "GoalsReminderChannel";
            String description = "Channel for Goals";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("GoalNotification", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = requireActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Retrieves value from FragmentToday
    private void InitializeUpdate() {
        isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String goal = bundle.getString("goal");
            String time = bundle.getString("time");
            goalId = bundle.getInt("goalId");
            goalEdit.setText(goal);
            setTime.setText(time);

            if (goal.length() > 0) {
                addGoal.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            }
        }

        goalEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    addGoal.setEnabled(false);
                    addGoal.setTextColor(Color.WHITE);
                } else {
                    addGoal.setEnabled(true);
                    addGoal.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialogInterface) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener)activity).handleDialogClose(dialogInterface);
        }
    }

    // Gets current date
    private void ShowDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
        dateToday = dateFormat.format(today);
    }
}
