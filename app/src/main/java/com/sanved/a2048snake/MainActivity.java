package com.sanved.a2048snake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GridView grid;
    GridAdapter gAdapt;

    private String[] alphabets = new String[26];
    private int nums[] = new int[60];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating and Initiating the gridView

        grid = findViewById(R.id.gridView);

        fillNumArr();
        testData();

        gAdapt = new GridAdapter(this, nums);

        grid.setAdapter(gAdapt);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Starts the Madiin Activity displaying all the word but also sends the selected alphabet
                // so that only the words starting with that letter appear.

                Toast.makeText(MainActivity.this, "Nagdi bai - " + i, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void fillNumArr(){
        for(int i = 0; i < 60; i++){
            nums[i] = 1;
        }
    }

    public void testData(){
        nums[3] = 2;
        nums[4] = 4;
        nums[5] = 8;
        nums[10] = 16;
        nums[15] = 256;
    }
}
