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
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    GridView grid;
    GridAdapter gAdapt;
    FloatingActionButton up, down, left, right;
    boolean gameOver = false;
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
        int tempPosi, tempNum, newPosi, temp2, i, flicka = 1;
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

            case R.id.fabUp:

                if(snake.get(0).getPosi() >= 0 && snake.get(0).getPosi() <= 5){
                    gameOver = true;
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (i = 0; i < snake.size(); i++) {

                        if (i == 0) {
                            tempPosi = snake.get(0).getPosi();
                            tempNum = snake.get(0).getNum();
                            SnakeBod sn = new SnakeBod(tempPosi - 6, tempPosi, tempNum);
                            snake.set(0, sn);
                        } else {
                            tempNum = snake.get(i).getNum();
                            tempPosi = snake.get(i).getPosi();
                            newPosi = snake.get(i - 1).getOldPosi();
                            SnakeBod sn = new SnakeBod(newPosi, tempPosi, tempNum);
                            snake.set(i, sn);
                        }

                    }
                }
                break;
            case R.id.fabDown:

                if(snake.get(0).getPosi() >= 42 && snake.get(0).getPosi() <= 47){
                    gameOver = true;
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
                }else {

                    for (i = 0; i < snake.size(); i++) {

                        if (i == 0) {
                            tempPosi = snake.get(0).getPosi();
                            tempNum = snake.get(0).getNum();
                            SnakeBod sn = new SnakeBod(tempPosi + 6, tempPosi, tempNum);
                            snake.set(0, sn);
                        } else {
                            tempNum = snake.get(i).getNum();
                            tempPosi = snake.get(i).getPosi();
                            newPosi = snake.get(i - 1).getOldPosi();
                            SnakeBod sn = new SnakeBod(newPosi, tempPosi, tempNum);
                            snake.set(i, sn);
                        }

                    }
                }
                break;
            case R.id.fabLeft:

                if(snake.get(0).getPosi() % 6 == 0){
                    gameOver = true;
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (i = 0; i < snake.size(); i++) {

                        if (i == 0) {
                            tempPosi = snake.get(0).getPosi();
                            tempNum = snake.get(0).getNum();
                            SnakeBod sn = new SnakeBod(tempPosi - 1, tempPosi, tempNum);
                            snake.set(0, sn);
                        } else {
                            tempNum = snake.get(i).getNum();
                            tempPosi = snake.get(i).getPosi();
                            newPosi = snake.get(i - 1).getOldPosi();
                            SnakeBod sn = new SnakeBod(newPosi, tempPosi, tempNum);
                            snake.set(i, sn);
                        }

                    }
                }
                break;
            case R.id.fabRight:
                if((snake.get(0).getPosi() + 1) % 6 == 0){
                    gameOver = true;
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (i = 0; i < snake.size(); i++) {

                        if (i == 0) {
                            tempPosi = snake.get(0).getPosi();
                            tempNum = snake.get(0).getNum();
                            SnakeBod sn = new SnakeBod(tempPosi + 1, tempPosi, tempNum);
                            snake.set(0, sn);
                        } else {
                            tempNum = snake.get(i).getNum();
                            tempPosi = snake.get(i).getPosi();
                            newPosi = snake.get(i - 1).getOldPosi();
                            SnakeBod sn = new SnakeBod(newPosi, tempPosi, tempNum);
                            snake.set(i, sn);
                        }

                    }
                }
        }
        if(!gameOver) {
            paintSnake();
            gAdapt.updateDat(nums);
        }
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

        snake.add(new SnakeBod(5,5,2));
        snake.add(new SnakeBod(4,4,4));
        snake.add(new SnakeBod(3,3,64));
        snake.add(new SnakeBod(9,9,16));
        snake.add(new SnakeBod(15,15,128));

        paintSnake();

    }

    public void paintSnake(){
        Arrays.fill(nums, 1);
        for(int i = 0; i < snake.size(); i++){
            nums[snake.get(i).getPosi()] = snake.get(i).getNum();
        }
    }


}
