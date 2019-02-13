package com.sanved.a2048snake;

public class SnakeBod {

    int posi;
    int num;
    int oldPosi;

    boolean isHead, isTail;

    SnakeBod(int posi, int oldPosi, int num){
        this.posi = posi;
        this.num = num;
        this.oldPosi = oldPosi;
    }

    public int getNum() {
        return num;
    }

    public int getOldPosi() {
        return oldPosi;
    }

    public int getPosi() {
        return posi;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setOldPosi(int oldPosi) {
        this.oldPosi = oldPosi;
    }

    public void setPosi(int posi) {
        this.posi = posi;
    }
}
