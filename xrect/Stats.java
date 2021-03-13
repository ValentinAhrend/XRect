package com.function.xrect;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by Jim-Linus Valentin Ahrend on 3/7/21.
 * Located in com.function.xrect in AndroidAppExample
 **/
public class Stats {

    private long duration;

    public void start(){
        duration = System.currentTimeMillis();
    }

    public void stop(){
        duration = System.currentTimeMillis() - duration;

        for (HashSet<Point[]> points : task0) {
            HashSet<Rect> rects = new HashSet<>();
            for (Point[] points1 : points){

                if(points1!=null && points1.length>0){

                    rects.add(new Rect(points1));

                }

            }
            add(rects);
        }

        /*for (Task t : task) {
            HashSet<Rect> rect = t.generate();
            add(rect);
        }*/

        best();


    }

    private ArrayList<Rect> rect;
    private void best(){
        int min = Collections.min(integers);
        for (int iii: integers){
            Log.i("88","i"+iii);
        }
        int index = 0;
        for (int m:integers){
            if(m == min){
                rect = new ArrayList<>(hash_rect.get(index));
                break;
            }
            index++;
        }
    }

    public ArrayList<Rect> getRect() {
        return rect;
    }

    public long getDuration() {
        return duration;
    }

    private ArrayList<ArrayList<Rect>> hash_rect = new ArrayList<>();
    private ArrayList<Integer> integers = new ArrayList<>();

    public void add(HashSet<Rect> rect){
        System.err.println("log size:"+integers.size()+"/" + hash_rect.size());
        integers.add(rect.size());
        hash_rect.add(new ArrayList<>(rect));
    }
    private HashSet<HashSet<Point[]>> task0 = new HashSet<>();
    void add$(HashSet<Point[]> points){
        task0.add(points);
    }
    private ArrayList<Task> task = new ArrayList<>();
    void addTask(Task t){
        task.add(t);
    }

    static class Task {

        private HashSet<Point[]> based = null;
        private ArrayList<RectHelper.Process2.EditRect> task = null;

        public void setBased(HashSet<Point[]> based) {
            this.based = based;
        }

        void addTask(ArrayList<RectHelper.Process2.EditRect> editRects) {

            task = editRects;
        }

        public HashSet<Rect> generate() {
            ArrayList<Rect> rects = new ArrayList<>();
            if (based != null) {
                for (Point[] points1 : based) {

                    if (points1 != null && points1.length > 0) {

                        rects.add(new Rect(points1));

                    }

                }
            }

            if (task == null) throw new IllegalArgumentException("Call addTask before");

            for (RectHelper.Process2.EditRect editRect : task) {
                Rect rect = new Rect(editRect.rows, editRect.points);
                for (int i = 0; i < editRect.rows; i++) {
                    for (int j = 0; j < editRect.points; j++) {
                        rect.addPoint(new Point(j, i));
                    }
                }
                rect.setStartingPoint(editRect.mesh, editRect.first_vertex);
                rects.add(rect);
            }

return new HashSet<>(rects);

        }
    }
}
