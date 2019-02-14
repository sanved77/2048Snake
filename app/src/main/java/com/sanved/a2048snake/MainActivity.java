package com.sanved.a2048snake;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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

    private static int SNAKE_SPEED = 800;
    // More the speed, slower the snake
    int foodArr[] = new int[48];
    private int FOOD_STACK[] = new int[15];
    // If 1, no food. If 2^n, then food there at 2^n levels
    private ArrayList<Food> foodDigest;
    private ArrayList<Integer> foodDeleteQueue;
    boolean gameOver = false;
    int posi = 3;
    ArrayList<SnakeBod> snake;
    private int stack_ptr = -1;
    private String[] alphabets = new String[26];
    private int nums[] = new int[48];
    private int DIRECTION = 3;

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

        gAdapt = new GridAdapter(this, nums, foodDigest);

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
                if (DIRECTION == 3) break;
                else DIRECTION = 1;
                break;

            case R.id.fabDown:
                if (DIRECTION == 1) break;
                DIRECTION = 3;
                break;

            case R.id.fabLeft:
                if (DIRECTION == 2) break;
                DIRECTION = 4;
                break;

            case R.id.fabRight:
                if (DIRECTION == 4) break;
                DIRECTION = 2;
                break;
        }

        paintCanvas();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, SNAKE_SPEED);

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
                } else {
                    for (i = 0; i < snake.size(); i++) {

                        if (i == 0) {
                            tempPosi = snake.get(0).getPosi();
                            tempNum = snake.get(0).getNum();
                            SnakeBod sn = new SnakeBod(tempPosi - 6, tempPosi, tempNum);
                            snake.set(0, sn);
                            // Check if landed on a number
                            if (nums[snake.get(0).getPosi()] != 1) {

                                // Check if food or snake body

                                if (foodArr[snake.get(0).getPosi()] != 1) {
                                    // If food
                                    foodDigest.add(new Food(snake.get(0).getPosi(), foodArr[snake.get(0).getPosi()]));
                                    FOOD_STACK[++stack_ptr] = stack_ptr;
                                    foodArr[snake.get(0).getPosi()] = 1; // Food taken away
                                    makeFood();
                                } else {
                                    // if body
                                    int posi = snake.get(0).getPosi();
                                    int hit = 0;
                                    for (int j = 2; j < snake.size(); j++) {
                                        if (snake.get(j).getPosi() == posi) {
                                            // the part where snake hit
                                            hit = j;
                                            break;
                                        }
                                    }
                                    if (stack_ptr != -1) {
                                        // check if digesting food exists
                                        for (int k = 0; k < foodDigest.size(); k++) {
                                            // for every digesting food, checks if posi lies on the dead snake
                                            for (int j = hit; j < snake.size(); j++) {
                                                // Will check if k.posi matches dead snake posi
                                                if (foodDigest.get(k).getPosi() == snake.get(j).getPosi()) {
                                                    stack_ptr--;
                                                    foodDeleteQueue.add(k);
                                                }
                                            }
                                        }
                                        for (int m = 0; m < foodDeleteQueue.size(); m++) {
                                            int temp = foodDeleteQueue.get(m);
                                            foodDigest.remove(temp);
                                        }
                                        foodDeleteQueue.clear();
                                    }
                                    SnakeBod sn2 = snake.get(hit);
                                    foodDigest.add(new Food(snake.get(0).getPosi(), sn2.getNum()));
                                    FOOD_STACK[++stack_ptr] = stack_ptr;
                                    // remove snake body
                                    for (int l = hit; l < snake.size(); l++) {
                                        nums[snake.get(l).getPosi()] = 1;
                                    }

                                    snake.subList(hit, snake.size()).clear();

                                }
                            }

                        } else {
                            tempNum = snake.get(i).getNum();
                            tempPosi = snake.get(i).getPosi();
                            newPosi = snake.get(i - 1).getOldPosi();
                            SnakeBod sn = new SnakeBod(newPosi, tempPosi, tempNum);
                            snake.set(i, sn);
                            if(i == snake.size() - 1){
                                if (stack_ptr != -1) {
                                    if (foodDigest.get(0).getPosi() == snake.get(i).getOldPosi()) {
                                        Food f = foodDigest.get(0);
                                        SnakeBod sn2 = new SnakeBod(f.getPosi(), f.getPosi(), f.getWeight());
                                        snake.add(sn2);
                                        foodDigest.remove(0);
                                        stack_ptr--;
                                    } else
                                        nums[snake.get(i).getOldPosi()] = 1;
                                } else {
                                    // Place a 1 to indicate an empty mat
                                    nums[snake.get(i).getOldPosi()] = 1;
                                }
                            }
                        }

                    }
                }
                break;
            case 3: // Down

                if(snake.get(0).getPosi() >= 42 && snake.get(0).getPosi() <= 47){
                    gameOver = true;

                }else {

                    for (i = 0; i < snake.size(); i++) {

                        if (i == 0) {
                            tempPosi = snake.get(0).getPosi();
                            tempNum = snake.get(0).getNum();
                            SnakeBod sn = new SnakeBod(tempPosi + 6, tempPosi, tempNum);
                            snake.set(0, sn);
                            /*if (foodArr[snake.get(0).getPosi()] != 1) {
                                foodDigest.add(new Food(snake.get(0).getPosi(), foodArr[snake.get(0).getPosi()]));
                                FOOD_STACK[++stack_ptr] = stack_ptr;
                                foodArr[snake.get(0).getPosi()] = 1; // Food taken away
                                makeFood();
                            }*/
                            // Check if landed on a number
                            if (nums[snake.get(0).getPosi()] != 1) {

                                // Check if food or snake body

                                if (foodArr[snake.get(0).getPosi()] != 1) {
                                    // If food
                                    foodDigest.add(new Food(snake.get(0).getPosi(), foodArr[snake.get(0).getPosi()]));
                                    FOOD_STACK[++stack_ptr] = stack_ptr;
                                    foodArr[snake.get(0).getPosi()] = 1; // Food taken away
                                    makeFood();
                                } else {
                                    // if body
                                    int posi = snake.get(0).getPosi();
                                    int hit = 0;
                                    for (int j = 2; j < snake.size(); j++) {
                                        if (snake.get(j).getPosi() == posi) {
                                            // the part where snake hit
                                            hit = j;
                                            break;
                                        }
                                    }

                                    if (stack_ptr != -1) {
                                        // check if digesting food exists
                                        for (int k = 0; k < foodDigest.size(); k++) {
                                            // for every digesting food, checks if posi lies on the dead snake
                                            for (int j = hit; j < snake.size(); j++) {
                                                // Will check if k.posi matches dead snake posi
                                                if (foodDigest.get(k).getPosi() == snake.get(j).getPosi()) {
                                                    stack_ptr--;
                                                    foodDeleteQueue.add(k);
                                                }
                                            }
                                        }
                                        for (int m = 0; m < foodDeleteQueue.size(); m++) {
                                            int temp = foodDeleteQueue.get(m);
                                            foodDigest.remove(temp);
                                        }
                                        foodDeleteQueue.clear();
                                    }
                                    SnakeBod sn2 = snake.get(hit);
                                    foodDigest.add(new Food(snake.get(0).getPosi(), sn2.getNum()));
                                    FOOD_STACK[++stack_ptr] = stack_ptr;
                                    // remove snake body
                                    for (int l = hit; l < snake.size(); l++) {
                                        nums[snake.get(l).getPosi()] = 1;
                                    }

                                    snake.subList(hit, snake.size()).clear();

                                }
                            }
                        } else {
                            tempNum = snake.get(i).getNum();
                            tempPosi = snake.get(i).getPosi();
                            newPosi = snake.get(i - 1).getOldPosi();
                            SnakeBod sn = new SnakeBod(newPosi, tempPosi, tempNum);
                            snake.set(i, sn);
                            if(i == snake.size() - 1){
                                if (stack_ptr != -1) {
                                    if (foodDigest.get(0).getPosi() == snake.get(i).getOldPosi()) {
                                        Food f = foodDigest.get(0);
                                        SnakeBod sn2 = new SnakeBod(f.getPosi(), f.getPosi(), f.getWeight());
                                        snake.add(sn2);
                                        foodDigest.remove(0);
                                        stack_ptr--;
                                    } else
                                        nums[snake.get(i).getOldPosi()] = 1;
                                } else {
                                    // Place a 1 to indicate an empty mat
                                    nums[snake.get(i).getOldPosi()] = 1;
                                }
                            }
                        }

                    }
                }
                break;
            case 4: // Left

                if(snake.get(0).getPosi() % 6 == 0){
                    gameOver = true;

                }
                else {
                    for (i = 0; i < snake.size(); i++) {

                        if (i == 0) {
                            tempPosi = snake.get(0).getPosi();
                            tempNum = snake.get(0).getNum();
                            SnakeBod sn = new SnakeBod(tempPosi - 1, tempPosi, tempNum);
                            snake.set(0, sn);
                            // Check if landed on a number
                            if (nums[snake.get(0).getPosi()] != 1) {

                                // Check if food or snake body

                                if (foodArr[snake.get(0).getPosi()] != 1) {
                                    // If food
                                    foodDigest.add(new Food(snake.get(0).getPosi(), foodArr[snake.get(0).getPosi()]));
                                    FOOD_STACK[++stack_ptr] = stack_ptr;
                                    foodArr[snake.get(0).getPosi()] = 1; // Food taken away
                                    makeFood();
                                } else {
                                    // if body
                                    int posi = snake.get(0).getPosi();
                                    int hit = 0;
                                    for (int j = 2; j < snake.size(); j++) {
                                        if (snake.get(j).getPosi() == posi) {
                                            // the part where snake hit
                                            hit = j;
                                            break;
                                        }
                                    }

                                    if (stack_ptr != -1) {
                                        // check if digesting food exists
                                        for (int k = 0; k < foodDigest.size(); k++) {
                                            // for every digesting food, checks if posi lies on the dead snake
                                            for (int j = hit; j < snake.size(); j++) {
                                                // Will check if k.posi matches dead snake posi
                                                if (foodDigest.get(k).getPosi() == snake.get(j).getPosi()) {
                                                    stack_ptr--;
                                                    foodDeleteQueue.add(k);
                                                }
                                            }
                                        }
                                        for (int m = 0; m < foodDeleteQueue.size(); m++) {
                                            int temp = foodDeleteQueue.get(m);
                                            foodDigest.remove(temp);
                                        }
                                        foodDeleteQueue.clear();
                                    }
                                    SnakeBod sn2 = snake.get(hit);
                                    foodDigest.add(new Food(snake.get(0).getPosi(), sn2.getNum()));
                                    FOOD_STACK[++stack_ptr] = stack_ptr;
                                    // remove snake body
                                    for (int l = hit; l < snake.size(); l++) {
                                        nums[snake.get(l).getPosi()] = 1;
                                    }

                                    snake.subList(hit, snake.size()).clear();

                                }
                            }
                        } else {
                            tempNum = snake.get(i).getNum();
                            tempPosi = snake.get(i).getPosi();
                            newPosi = snake.get(i - 1).getOldPosi();
                            SnakeBod sn = new SnakeBod(newPosi, tempPosi, tempNum);
                            snake.set(i, sn);
                            if(i == snake.size() - 1){
                                if (stack_ptr != -1) {
                                    if (foodDigest.get(0).getPosi() == snake.get(i).getOldPosi()) {
                                        Food f = foodDigest.get(0);
                                        SnakeBod sn2 = new SnakeBod(f.getPosi(), f.getPosi(), f.getWeight());
                                        snake.add(sn2);
                                        foodDigest.remove(0);
                                        stack_ptr--;
                                    } else
                                        nums[snake.get(i).getOldPosi()] = 1;
                                } else {
                                    // Place a 1 to indicate an empty mat
                                    nums[snake.get(i).getOldPosi()] = 1;
                                }
                            }
                        }

                    }
                }
                break;
            case 2: // Right
                if((snake.get(0).getPosi() + 1) % 6 == 0){
                    gameOver = true;

                }
                else {
                    for (i = 0; i < snake.size(); i++) {

                        if (i == 0) {
                            tempPosi = snake.get(0).getPosi();
                            tempNum = snake.get(0).getNum();
                            SnakeBod sn = new SnakeBod(tempPosi + 1, tempPosi, tempNum);
                            snake.set(0, sn);
                            // Check if landed on a number
                            if (nums[snake.get(0).getPosi()] != 1) {

                                // Check if food or snake body

                                if (foodArr[snake.get(0).getPosi()] != 1) {
                                    // If food
                                    foodDigest.add(new Food(snake.get(0).getPosi(), foodArr[snake.get(0).getPosi()]));
                                    FOOD_STACK[++stack_ptr] = stack_ptr;
                                    foodArr[snake.get(0).getPosi()] = 1; // Food taken away
                                    makeFood();
                                } else {
                                    // if body
                                    int posi = snake.get(0).getPosi();
                                    int hit = 0;
                                    for (int j = 2; j < snake.size(); j++) {
                                        if (snake.get(j).getPosi() == posi) {
                                            // the part where snake hit
                                            hit = j;
                                            break;
                                        }
                                    }

                                    if (stack_ptr != -1) {
                                        // check if digesting food exists
                                        for (int k = 0; k < foodDigest.size(); k++) {
                                            // for every digesting food, checks if posi lies on the dead snake
                                            for (int j = hit; j < snake.size(); j++) {
                                                // Will check if k.posi matches dead snake posi
                                                if (foodDigest.get(k).getPosi() == snake.get(j).getPosi()) {
                                                    stack_ptr--;
                                                    foodDeleteQueue.add(k);
                                                }
                                            }
                                        }
                                        for (int m = 0; m < foodDeleteQueue.size(); m++) {
                                            int temp = foodDeleteQueue.get(m);
                                            foodDigest.remove(temp);
                                        }
                                        foodDeleteQueue.clear();
                                    }
                                    SnakeBod sn2 = snake.get(hit);
                                    foodDigest.add(new Food(snake.get(0).getPosi(), sn2.getNum()));
                                    FOOD_STACK[++stack_ptr] = stack_ptr;
                                    // remove snake body
                                    for (int l = hit; l < snake.size(); l++) {
                                        nums[snake.get(l).getPosi()] = 1;
                                    }

                                    snake.subList(hit, snake.size()).clear();

                                }
                            }
                        } else {
                            tempNum = snake.get(i).getNum();
                            tempPosi = snake.get(i).getPosi();
                            newPosi = snake.get(i - 1).getOldPosi();
                            SnakeBod sn = new SnakeBod(newPosi, tempPosi, tempNum);
                            snake.set(i, sn);
                            if(i == snake.size() - 1){
                                if (stack_ptr != -1) {
                                    if (foodDigest.get(0).getPosi() == snake.get(i).getOldPosi()) {
                                        Food f = foodDigest.get(0);
                                        SnakeBod sn2 = new SnakeBod(f.getPosi(), f.getPosi(), f.getWeight());
                                        snake.add(sn2);
                                        foodDigest.remove(0);
                                        stack_ptr--;
                                    } else
                                        nums[snake.get(i).getOldPosi()] = 1;
                                } else {
                                    // Place a 1 to indicate an empty mat
                                    nums[snake.get(i).getOldPosi()] = 1;
                                }
                            }
                        }

                    }
                }
        }
        if(!gameOver) {
            paintSnake();
            gAdapt.updateDat(nums, foodDigest);

        }else{
            // Game Over
            tvGameOver.setVisibility(View.VISIBLE);
            up.hide();
            down.hide();
            right.hide();
            left.hide();
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(500);
            }
            DIRECTION = 3;
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

        tvGameOver = findViewById(R.id.tvGameOver);

        Arrays.fill(foodArr, 1); // Empty mat with no food

        foodDigest = new ArrayList<>();

        foodDeleteQueue = new ArrayList<>();

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
        snake.add(new SnakeBod(3, 3, 8));
        snake.add(new SnakeBod(9,9,16));
        snake.add(new SnakeBod(15, 15, 32));
        snake.add(new SnakeBod(14, 15, 64));
        snake.add(new SnakeBod(13, 15, 128));
        snake.add(new SnakeBod(12, 15, 256));
        snake.add(new SnakeBod(18, 15, 512));
        snake.add(new SnakeBod(24, 15, 1024));


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
                        foodArr[n] = 2;
                        break;

                    case 1:
                        nums[n] = 4;
                        foodArr[n] = 4;
                        break;

                    case 2:
                        nums[n] = 8;
                        foodArr[n] = 8;
                        break;
                }
                break;

            }

        }
        gAdapt.updateDat(nums, foodDigest);

    }


}
