package com.example.keypass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FamilyRecyclerViewAdapter extends RecyclerView.Adapter<FamilyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> familyNames;
    private ArrayList<String> carNames;
    private Context mContext;

    public FamilyRecyclerViewAdapter(ArrayList<String> familyNames, ArrayList<String> carNames, Context context) {
        this.familyNames = familyNames;
        this.carNames = carNames;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.familyName.setText(familyNames.get(i));
        viewHolder.carName.setText(carNames.get(i));
        viewHolder.requestButton.setTag(i);
    }

    @Override
    public int getItemCount() {
        return familyNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView familyName;
        TextView carName;
        RelativeLayout parentLayout;
        Button requestButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            familyName = itemView.findViewById(R.id.family_name);
            carName = itemView.findViewById(R.id.car_name);
            requestButton = itemView.findViewById(R.id.requestButton);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
