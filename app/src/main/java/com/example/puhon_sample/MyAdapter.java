package com.example.puhon_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    FragmentOverallReport context1;
    ArrayList<UserAnswers> list1;


    public MyAdapter(FragmentOverallReport context1, ArrayList<UserAnswers> list1) {
        this.context1 = context1;
        this.list1 = list1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context1.getActivity()).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserAnswers userAnswers = list1.get(position);
        holder.AnswerList1.setText(userAnswers.getUserQuestion1());
        holder.AnswerList2_1.setText(userAnswers.getUserQuestion2_1());
        holder.AnswerList2_2.setText(userAnswers.getUserQuestion2_2());
        holder.AnswerList3.setText(userAnswers.getUserQuestion3());
        holder.AnswerList4.setText(String.valueOf(userAnswers.getUserQuestion4()));
        holder.AnswerList5.setText(String.valueOf(userAnswers.getUserQuestion5()));

    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView AnswerList1, AnswerList2_1, AnswerList2_2, AnswerList3,
                AnswerList4, AnswerList5;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            AnswerList1 = itemView.findViewById(R.id.answerList1);
            AnswerList2_1 = itemView.findViewById(R.id.answerList2_1);
            AnswerList2_2 = itemView.findViewById(R.id.answerList2_2);
            AnswerList3 = itemView.findViewById(R.id.answerList3);
            AnswerList4 = itemView.findViewById(R.id.answerList4);
            AnswerList5 = itemView.findViewById(R.id.answerList5);
        }
    }

}