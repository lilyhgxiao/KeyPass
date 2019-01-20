package com.example.keypass;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FamilyListActivity extends AppCompatActivity {

    private ArrayList<String> familyNames = new ArrayList<>();
    private ArrayList<String> familyIds = new ArrayList<>();
    private ArrayList<String> carNames = new ArrayList<>();
    private ArrayList<String> carIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_list);
        initFamilyNames();
    }

    private void initFamilyNames() {
        String familyName = "Happy Family";
        String familyId = "d38f8b57-2224-44c0-a714-d03f9ab061be";

        try {
            getCarsOfFamily(familyName, familyId);
        }
        catch (JSONException je) {
            je.printStackTrace();
        }
    }

    public void getCarsOfFamily(final String familyName, final String familyId) throws JSONException {
        HttpRestClient.get("/family/" + familyId, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println(response.getString("id"));
                    System.out.println(response.toString());
                    JSONArray cars = response.getJSONArray("cars");
                    System.out.println("ssssssssssssssssss");
                    System.out.println(cars.toString());
                    System.out.println(cars.length());
                    for (int i = 0; i < cars.length(); i++) {
                        System.out.println(cars.getJSONObject(i).toString());
                        familyNames.add(familyName);
                        familyIds.add(familyId);
                        carNames.add("Car " + i);
                        carIds.add(cars.getJSONObject(i).get("id").toString());
                    }
                    System.out.println(carIds.toString());
                }
                catch (Exception je) {
                    System.out.println("e");
                    je.printStackTrace();
                }
                initRecyclerView();
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FamilyRecyclerViewAdapter adapter = new FamilyRecyclerViewAdapter(familyNames, carNames,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void requestKeyFromFamily(View view) {
        int index = Integer.parseInt(view.getTag().toString());

        MainActivity.setCurrFamilyId(familyIds.get(index));
        MainActivity.setCurrCarId(carIds.get(index));

        System.out.println("DEBUGLOG: switched family id (" + familyIds.get(index) + ") and car id (" + carIds.get(index) + ")");
    }

    public void unlockThisCar(String familyId, String carId) throws JSONException {
        HttpRestClient.post("/family/" + familyId + "/unlock?car_id=" + carId + "&lat=123&lon=456", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println(response.getString("status"));
                }
                catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        });
    }
}
