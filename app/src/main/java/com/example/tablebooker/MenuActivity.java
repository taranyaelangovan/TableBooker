package com.example.tablebooker;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    ListView myListView;
    String[] items;
    String[] prices;
    String[] descriptions;
    int idx=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.myListView);

        Intent intent=getIntent();
        idx=intent.getIntExtra("idx",0);

        if(idx==0) {
            items = res.getStringArray(R.array.items_o);
            prices = res.getStringArray(R.array.prices_o);
            descriptions = res.getStringArray(R.array.descriptions_o);
        }

        if(idx==1) {
            items = res.getStringArray(R.array.items_s);
            prices = res.getStringArray(R.array.prices_s);
            descriptions = res.getStringArray(R.array.descriptions_s);
        }

        if(idx==2) {
            items = res.getStringArray(R.array.items_b);
            prices = res.getStringArray(R.array.prices_b);
            descriptions = res.getStringArray(R.array.descriptions_b);
        }

        ItemAdapterforMenu itemAdapterforMenu = new ItemAdapterforMenu(this, items, prices, descriptions);
        myListView.setAdapter(itemAdapterforMenu);
    }
}