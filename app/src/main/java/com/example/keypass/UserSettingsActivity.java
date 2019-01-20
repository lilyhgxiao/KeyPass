package com.example.keypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserSettingsActivity extends AppCompatActivity {
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(MainActivity.USERID);

        //get username from userId from server
        userName = "Jawn Smeeth";

        TextView usernameView;
        usernameView = findViewById(R.id.username);
        usernameView.setText(userName);

        //get username and car lists from userid

    }
}
