package com.sanved.a2048snake;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private int nums[];
    private final String[] alphabets = new String[26];
    private int DIRECTION;
    private int head;
    private ArrayList<Food> foodDigest;

    //private static int count;


    public GridAdapter(Context context, int nums[], ArrayList<Food> foodDigest, int DIRECTION, int head) {
        this.context = context;
        this.nums = nums;
        this.foodDigest = foodDigest;
        this.DIRECTION = DIRECTION;
        this.head = head;
        fillAlphabets();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        /*if (convertView == null) {

        } else {
            gridView = (View) convertView;
        }*/

        gridView = new View(context);

        // get layout from grid_item.xml
        gridView = inflater.inflate(R.layout.list_item, null);
        CardView cv1 = gridView.findViewById(R.id.cv1);

        switch(nums[position]){
            case 1:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg0));
                break;
            case 2:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg2));
                break;
            case 4:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg4));
                break;
            case 8:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg8));
                break;
            case 16:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg16));
                break;
            case 32:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg32));
                break;
            case 64:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg64));
                break;
            case 128:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg128));
                break;
            case 256:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg256));
                break;
            case 512:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg512));
                break;
            case 1024:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg1024));
                break;
            case 2048:
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg2048));
                break;
        }

        TextView textView = gridView
                .findViewById(R.id.tvNum);

        TextView textView2 = gridView
                .findViewById(R.id.tvNum2);
        textView2.setText("");

        for (int i = 0; i < foodDigest.size(); i++) {
            if (position == foodDigest.get(i).getPosi()) {
                cv1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
                textView.setTextColor(ContextCompat.getColor(context, R.color.white));
                textView2.setText(""+foodDigest.get(i).getWeight());
            }
        }
        // set value into textview

        if(nums[position] > 1000){
            textView.setTextSize(18);
        }else if(nums[position] > 100){
            textView.setTextSize(24);
        }else {
            textView.setTextSize(25);
        }
        if(nums[position] != 1)
            textView.setText("" + nums[position]);

        ImageView snakeHead = gridView.findViewById(R.id.ivHead);

        if(position == head) {
            switch (DIRECTION) {

            /*
                1 - Up
                2 - Right
                3 - Down
                4 - Left
            */

                case 1:
                    snakeHead.setBackground(ContextCompat.getDrawable(context, R.drawable.head1));
                    break;
                case 2:
                    snakeHead.setBackground(ContextCompat.getDrawable(context, R.drawable.head2));
                    break;
                case 3:
                    snakeHead.setBackground(ContextCompat.getDrawable(context, R.drawable.head3));
                    break;
                case 4:
                    snakeHead.setBackground(ContextCompat.getDrawable(context, R.drawable.head4));
                    break;
            }
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return nums.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public void updateDat(int nums[], ArrayList<Food> foodDigest, int DIRECTION, int head) {
        this.nums = nums;
        this.foodDigest = foodDigest;
        this.DIRECTION = DIRECTION;
        this.head = head;
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void fillAlphabets() {
        int i = 0;
        char a = 'A';
        for (i = 0, a = 'A'; a <= 'Z'; a++, i++) {
            alphabets[i] = "" + a;
        }
    }

}