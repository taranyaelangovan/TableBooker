package com.example.tablebooker;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantListActivity extends AppCompatActivity {

    ListView myListView;
    String[] names;
    String[] rating;
    String[] descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantlist);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.myListView);
        names = res.getStringArray(R.array.names);
        rating = res.getStringArray(R.array.rating);
        descriptions = res.getStringArray(R.array.descriptions);

        ItemAdapter itemAdapter = new ItemAdapter(this, names, rating, descriptions);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RestaurantDescActivity.class);
                intent.putExtra("index",position);
                startActivity(intent);
            }
        });

    }
}