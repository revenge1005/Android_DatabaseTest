package com.example.choi.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    DBHelper db;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        ArrayList arrayList = db.getAllList();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, arrayList);

        listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

                String item = (String) ((ListView)parent).getItemAtPosition(position);
                String[] strArray = item.split(" ");

                int id = Integer.parseInt(strArray[0]);

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id);
                Intent intent = new Intent(getApplicationContext(), Display.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.clear();
        adapter.addAll(db.getAllList());
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view){

        Bundle bundle = new Bundle();

        bundle.putInt("id", 0);
        Intent intent = new Intent(getApplicationContext(), Display.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
