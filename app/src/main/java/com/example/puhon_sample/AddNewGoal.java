package com.example.puhon_sample;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewGoal extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewGoal";

    private TextView setTime;
    private EditText goalEdit;
    private Button addGoal;


    public static AddNewGoal newInstance(){

        return new AddNewGoal();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_goal , container , false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTime = view.findViewById(R.id.goalTime);
        goalEdit = view.findViewById(R.id.editxtNGoal);
        addGoal = view.findViewById(R.id.buttonAddGoal);

    }
}
