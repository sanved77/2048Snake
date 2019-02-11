package com.sanved.a2048snake;

import android.animation.FloatArrayEvaluator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    GridView grid;
    GridAdapter gAdapt;
    FloatingActionButton up, down, left, right;
    int posi = 3;
    ArrayList<SnakeBod> snake;

    private String[] alphabets = new String[26];
    private int nums[] = new int[48];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating and Initiating the gridView

        grid = findViewById(R.id.gridView);

        initVals();
        fillNumArr();
        testData();
        putSnake();

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

    @Override
    public void onClick(View v) {
        int temp, temp2, i, flicka = 1;
        switch(v.getId()){
            /*
            Single sprite
            case R.id.fabUp:
                temp = nums[posi];
                nums[posi] = 1;
                posi -= 6;
                nums[posi] = temp;
                break;
            case R.id.fabDown:
                temp = nums[posi];
                nums[posi] = 1;
                posi += 6;
                nums[posi] = temp;
                break;
            case R.id.fabLeft:
                temp = nums[posi];
                nums[posi] = 1;
                posi -= 1;
                nums[posi] = temp;
                break;
            case R.id.fabRight:
                temp = nums[posi];
                nums[posi] = 1;
                posi += 1;
                nums[posi] = temp;
                break;*/

            /*case R.id.fabUp:
                for(i = 0 ; i < snake.size(); i++){

                }
                break;
            case R.id.fabDown:
                for(i = 0 ; i < snake.size(); i++){

                    if(i == 0){
                        temp = 0;
                        snake.add(0,snake.get(0) + 6);
                    }else{
                        if(flicka == 1){
                             temp2 = i;
                             snake.add(temp, )
                        }else{

                        }
                    }

                }
                break;
            case R.id.fabLeft:
                temp = nums[posi];
                nums[posi] = 1;
                posi -= 1;
                nums[posi] = temp;
                break;
            case R.id.fabRight:
                temp = nums[posi];
                nums[posi] = 1;
                posi += 1;
                nums[posi] = temp;
                break;*/
        }
        gAdapt.updateDat(nums);
    }

    public void initVals(){
        up = findViewById(R.id.fabUp);
        down = findViewById(R.id.fabDown);
        left = findViewById(R.id.fabLeft);
        right = findViewById(R.id.fabRight);

        up.setOnClickListener(this);
        down.setOnClickListener(this);
        right.setOnClickListener(this);
        left.setOnClickListener(this);

        snake = new ArrayList<>();
    }

    public void fillNumArr(){
        for(int i = 0; i < 48; i++){
            nums[i] = 1;
        }
    }

    public void testData(){
        nums[3] = 2;
        /*nums[4] = 4;
        nums[5] = 8;
        nums[10] = 16;
        nums[15] = 256;*/
    }

    public void putSnake(){

        snake.add(new SnakeBod(5,2));
        snake.add(new SnakeBod(4,4));
        snake.add(new SnakeBod(3,64));
        snake.add(new SnakeBod(9,16));
        snake.add(new SnakeBod(15,128));

        paintSnake();

    }

    public void paintSnake(){
        for(int i = 0; i < snake.size(); i++){
            nums[snake.get(i).getPosi()] = snake.get(i).getNum();
        }
    }


}
