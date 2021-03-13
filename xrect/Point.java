package com.function.xrect;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Jim-Linus Valentin Ahrend on 2/27/21.
 * Located in com.function.xrect in AndroidAppExample
 **/
public class Point {
    public static final String TAG = "Point";
    private int x, y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void print(){
        Log.i(TAG, "x = "+x+", y = "+y);
    }

    @Override
    public int hashCode() {
        return x >>> y;
    }

    @NonNull
    @Override
    public String toString() {
        return "Point ["+x+" "+y+"]";
    }

    private int maj = 0;
    public void setMaj(int i){
        this.maj = i;
    }

    public int getMaj() {
        return maj;
    }


    public boolean equals(@NonNull Point obj) {
        return x == obj.getX() && y == obj.getY();
    }
}
