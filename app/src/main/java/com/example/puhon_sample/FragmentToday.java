package com.example.puhon_sample;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FragmentToday extends Fragment implements DialogCloseListener {
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id, dateToday;

    RecyclerView recyclerView;
    Button buttonAddNewGoals;
    TextView emptyData;

    ArrayList<UserGoals> UserGoals;
    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        recyclerView = view.findViewById(R.id.goalsRecyclerView);
        buttonAddNewGoals = view.findViewById(R.id.addnewGoals);
        emptyData = view.findViewById(R.id.emptyData);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();

        reference = database.getReference().child("users").child(id).child("UserGoals");

        UserGoals = new ArrayList<>();

        InitializeRecyclerView();
        RetrieveGoals();

        buttonAddNewGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddNewGoal.newInstance().show(getParentFragmentManager() , AddNewGoal.TAG);
            }
        });

        return view;
    }

    private void RetrieveGoals() {
        ShowDate();

        reference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserGoals.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("goal").exists()) {
                        UserGoals userGoals = dataSnapshot.getValue(UserGoals.class);
                        assert userGoals != null;
                        userGoals.setGoalId(Integer.parseInt(Objects.requireNonNull(dataSnapshot.getKey())));
                        UserGoals.add(userGoals);
                        key = dataSnapshot.getKey();
                    }

                }
                InitializeRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    // Displays goal to recyclerview
    private void InitializeRecyclerView() {
        if (UserGoals.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyData.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyData.setVisibility(View.GONE);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
            GoalsAdapter goalsAdapter = new GoalsAdapter(this, UserGoals);
            recyclerView.setAdapter(goalsAdapter);

            HandleSwipe();
        }
    }

    // Adds edit and delete swipe to recycler view
    private void HandleSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBackMethod);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Initializes swipe settings
    ItemTouchHelper.SimpleCallback callBackMethod = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            UserGoals userGoals = UserGoals.get(position);

            switch (direction) {
                // Swipe to remove
                case (ItemTouchHelper.LEFT):
                    reference.child(String.valueOf(userGoals.getGoalId())).removeValue();

                    Objects.requireNonNull(recyclerView.getAdapter()).notifyItemRemoved(position);
                    Toast.makeText(getContext(), "Goal has been removed.", Toast.LENGTH_SHORT).show();
                    break;
                // Swipe to edit
                case (ItemTouchHelper.RIGHT):
                    GoalsAdapter.editItem(position);
                    break;
            }
        }

        // Initializes swipe layout
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftLabel("Delete")
                    .setSwipeLeftLabelColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .setSwipeLeftActionIconTint(ContextCompat.getColor(requireContext(), R.color.white))
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
                    .addSwipeRightLabel("Edit")
                    .setSwipeRightLabelColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                    .setSwipeRightActionIconTint(ContextCompat.getColor(requireContext(), R.color.white))
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_pink))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    // Gets current date
    private void ShowDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
        dateToday = dateFormat.format(today);
    }

    @Override
    public void handleDialogClose(DialogInterface dialogInterface) {
        RetrieveGoals();
    }
}