package com.function.xrect;

import android.icu.text.MessagePattern;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import javax.annotation.Resource;
import javax.sql.PooledConnection;

/**
 * Created by Jim-Linus Valentin Ahrend on 2/27/21.
 * Located in com.function.xrect in AndroidAppExample
 **/
public class Rect {

    private static final String TAG = "Rect";

    private int width;
    private int height;
    private boolean[][] matrix;
    private HashSet<Point> points = new HashSet<>();
    private int current_size;

    public HashSet<Point> getPoints() {
        return points;
    }

    Rect(int height, int width){
        this.width  = width;
        this.height = height;
        this.matrix = new boolean[height][width];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    void addPoint(@NonNull Point p){
        if(width<=p.getX() || height<= p.getY() || 0>p.getX() || 0>p.getY()){
            System.err.println(p);
            System.err.println(this);
            throw new IllegalArgumentException("w:"+width+"/h:"+height);
        }
        points.add(p);
        this.current_size++;
        if(matrix[p.getY()][p.getX()])Log.i(TAG, "The rect already contains the point ["+p.getX()+"]["+p.getY()+"]");
        else matrix[p.getY()][p.getX()] = true;
    }

    boolean removePoint(@NonNull Point p){
        if(width<=p.getX() || height<= p.getY() || 0>p.getX() || 0>p.getY()){
            throw new IllegalArgumentException(p.toString());
        }

        System.err.println(points.remove(p));

        this.current_size--;
        if(!matrix[p.getY()][p.getX()])return false;
        else matrix[p.getY()][p.getX()] = false;
        return true;
    }

    boolean hasPoint(Point p){
        if(width<=p.getX() || height<= p.getY() || 0>p.getX() || 0>p.getY()){
            throw new IllegalArgumentException(p +"//"+width+"&"+height);
        }
        return matrix[p.getY()][p.getX()];
    }

    boolean hasPoint(int x, int y){
        return matrix[y][x];
    }

    public boolean test(Point p){
        return width > p.getX() && height > p.getY() && 0 <= p.getX() && 0 <= p.getY();
    }

    public void clear(){
        this.matrix = new boolean[height][width];
        this.points = new HashSet<>();
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                Point p = new Point(j,i);
                sb.append(hasPoint(p)?"■":"□");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Rect copy(){
        Rect rect = new Rect(height,width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Point p = new Point(j,i);
                if(hasPoint(p)){
                    rect.addPoint(p);
                }
            }
        }
        return rect;
    }

    Rect(Point[] points){

        //for (Point p:points)System.err.println("x:"+p);

        if(points!=null && points.length>0) {

            //getWidth


            this.width = points[points.length-1].getX()-points[0].getX()+1;
            this.height = points[points.length-1].getY()-points[0].getY()+1;
            this.matrix = new boolean[height][width];

            this.setStartingPoint(points[0].getX(),points[0].getY());

            for (Point point : points) {
                this.addPoint(new Point(point.getX()-points[0].getX(), point.getY()-points[0].getY()));
            }

        }

    }

    Rect(String s){

        while (s.contains("0")||s.contains("1")){
            s = s.replace("0","□");
            s = s.replace("1","■");
        }

        String[] rows = s.split("\n");
        HashSet<Point> points = new HashSet<>();
        int var0 = 0;
        for (String row : rows) {
            int var1 = 0;
            for (int i = 0; i < row.length(); i++) {
                if(row.charAt(i) == '■')points.add(new Point(var1,var0));
                var1++;
            }
            var0++;
        }
        int w = rows[0].toCharArray().length;
        int h = rows.length;

        this.width = w;
        this.height = h;
        this.matrix = new boolean[h][w];
        this.points = points;
        for (Point point : points) {
            System.err.println("?"+point);
            matrix[point.getY()][point.getX()] = true;
            if(matrix[point.getY()][point.getX()])System.err.println("true");
            current_size++;
        }


        System.err.println("hash:"+this);

    }



    public boolean[][] getMatrix() {
        return matrix;
    }

    public int getCurrentSize() {
        return current_size;
    }

    public String getRow(int i){
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < getWidth(); j++) {
        if(hasPoint(j,i))sb.append("■");
        }
        return sb.toString();
    }
    public String highlightPoint(Point x){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(i == x.getX() && j == x.getY())sb.append("*");
                else {
                    if (matrix[i][j]) sb.append("■");
                    else sb.append("□");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    //secure
    private int starting_x;
    private int starting_y;

    void setStartingPoint(int x, int y){
        this.starting_x = x;
        this.starting_y = y;
    }

    public Point getStarting() {
        return new Point(starting_x,starting_y);
    }

    Point[] getStartingPoints(){

        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if(hasPoint(j,i)){
                    points.add(new Point(starting_x+j, starting_y+i));
                }
            }
        }
        return points.toArray(new Point[0]);
    }

    Point getFirstYPoint(){
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if(hasPoint(j,i))return new Point(j,i);
            }
        }
        return null;
    }
    Point getFirstXPoint(){
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++){
                if(hasPoint(i,j))return new Point(i,j);
            }
        }
        return null;
    }
    Point getLastYPoint(){
        for (int i = getHeight()-1; i >= 0; i--) {
            for (int j = getWidth()-1; j >= 0; j--) {
                if(hasPoint(j,i))return new Point(j,i);
            }
        }
        return null;
    }
    Point getLastXPoint(){
        for (int i = getWidth()-1; i >= 0; i--) {
            for (int j = getHeight()-1; j >= 0; j--) {
                if(hasPoint(i,j))return new Point(i,j);
            }
        }
        return null;
    }

    int sq(){
        return getWidth() * getHeight();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Rect) {
            Rect r = (Rect) obj;
            return (
                    getMatrix() == r.getMatrix() &&
                            getHeight() == r.getHeight() &&
                            getWidth() == r.getWidth() &&
                            getFirstYPoint().equals(r.getFirstYPoint()) &&
                            getFirstXPoint().equals(r.getFirstXPoint()) &&
                            toString().equals(r.toString())
            );
        }else return this==obj;
    }

    Point partnerPoint(Point p){
        /*
        0000
        XXX0
        XYX0
        XXX0

        Y is p

        partner Point = 0 next to YX

        the nearest point
         */

        final int x1 = p.getX();
        final int y1 = p.getY();

        //each direction

        int x = 0;//left
        int x_ = 0;//right
        int y = 0;//up
        int y_ = 0;//down

        for (x = 0; x < width-p.getX(); x++) {
            Point test = new Point(x+1+x1,y1);
            if(!test(test) || !hasPoint(test))break;
        }
        for (x_ = x1; x_ > 0; x_--) {
            Point test = new Point(x_-1,y1);
            if(!test(test) || !hasPoint(test))break;
        }
        for (y = 0; y < height-p.getY(); y++) {
            Point test = new Point(x1,y1+1+y);
            if(!test(test) || !hasPoint(test))break;
        }
        for (y_ = y1; y_ > 0; y_--) {
            Point test = new Point(x1,y_-1);
            if(!test(test) || !hasPoint(test))break;
        }


        Random r = new Random();
        int i = r.nextInt(4);
        if(i==0)return new Point(x1+x+1,y1);
        if(i==1)return new Point(x_-1,y1);
        if(i==2)return new Point(x1,y+y1+1);
        if(i==3)return new Point(x1,y_-1);
        return p;
    }
}
