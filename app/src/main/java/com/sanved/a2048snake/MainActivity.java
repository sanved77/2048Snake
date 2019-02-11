package com.sanved.a2048snake;

import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    GridView grid;
    GridAdapter gAdapt;
    FloatingActionButton up, down, left, right;
    Handler handler;
    Random rand;
    Random rand2;
    TextView tvGameOver;

    boolean gameOver = false;
    int posi = 3;
    ArrayList<SnakeBod> snake;
    private static int DIRECTION = 3;
    private String[] alphabets = new String[26];
    private int nums[] = new int[48];
    private static int SNAKE_SPEED = 500;

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

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable, SNAKE_SPEED);

        makeFood();
        makeFood();
        makeFood();

    }

    Runnable runnable = new Runnable() {

        public void run() {
            paintCanvas();

            //repeat every SNAKE_SPEED miliseconds
            if(!gameOver)
                handler.postDelayed(this, SNAKE_SPEED);

        }
    };



    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.fabUp:
                DIRECTION = 1;
                break;

            case R.id.fabDown:
                DIRECTION = 3;
                break;

            case R.id.fabLeft:
                DIRECTION = 4;
                break;

            case R.id.fabRight:
                DIRECTION = 2;
                break;
        }

    }

    public void paintCanvas(){
        /*
        1 - Up
        2 - Right
        3 - Down
        4 - Left
        */

        int tempPosi, tempNum, newPosi, i;
        switch(DIRECTION){
            case 1: // Up

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
                            if(i == snake.size() - 1){
                                nums[snake.get(i).getOldPosi()] = 1;
                            }
                        }

                    }
                }
                break;
            case 3: // Down

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
                            if(i == snake.size() - 1){
                                nums[snake.get(i).getOldPosi()] = 1;
                            }
                        }

                    }
                }
                break;
            case 4: // Left

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
                            if(i == snake.size() - 1){
                                nums[snake.get(i).getOldPosi()] = 1;
                            }
                        }

                    }
                }
                break;
            case 2: // Right
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
                            if(i == snake.size() - 1){
                                nums[snake.get(i).getOldPosi()] = 1;
                            }
                        }

                    }
                }
        }
        if(!gameOver) {
            paintSnake();
            gAdapt.updateDat(nums);
        }else{
            // Game Over
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

        rand = new Random();
        rand2 = new Random();
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
        for(int i = 0; i < snake.size(); i++){
            nums[snake.get(i).getPosi()] = snake.get(i).getNum();
        }
    }

    public void makeFood(){

        while(true){
            int n = rand.nextInt(48);

            if(nums[n] == 1){
                int rnd = rand2.nextInt(3);
                switch (rnd){
                    case 0:
                        nums[n] = 2;
                        break;

                    case 1:
                        nums[n] = 4;
                        break;

                    case 2:
                        nums[n] = 8;
                        break;
                }
                break;
            }

        }
        gAdapt.updateDat(nums);

    }


}
