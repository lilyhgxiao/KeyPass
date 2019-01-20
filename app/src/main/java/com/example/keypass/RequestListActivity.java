package com.example.keypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RequestListActivity extends AppCompatActivity {
    private ArrayList<String> availRequests;
    private ArrayList<String[]> requestData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(MainActivity.USERID);

        //get list of available requested cars from server
        availRequests = new ArrayList<String>();
        availRequests.add("Breetany");
        availRequests.add("Caysey");

        //get car and user data from server based on ids
        int count = 0;
        for (String car: availRequests) {
            count++;
            //0: carid, 1:user's name, 2:car's name
            String[] arr = {car, car, car + "'s Car"};
            requestData.add(arr);
        }

        Button request1 = findViewById(R.id.request_button1);
        TextView user_name1 = findViewById(R.id.user_name1);
        TextView car_name1 = findViewById(R.id.car_name1);
        user_name1.setText((requestData.get(0)[1]));
        car_name1.setText((requestData.get(0)[2]));

        TextView user_name2 = findViewById(R.id.user_name2);
        TextView car_name2 = findViewById(R.id.car_name2);
        user_name2.setText((requestData.get(1)[1]));
        car_name2.setText((requestData.get(1)[2]));
    }
}
