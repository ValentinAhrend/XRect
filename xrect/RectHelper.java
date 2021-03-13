package com.function.xrect;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.exsent.app.util.Modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jim-Linus Valentin Ahrend on 2/28/21.
 * Located in com.function.xrect in AndroidAppExample
 **/
public class RectHelper {

    private static boolean inRun = false;

    static void calculateRectInRect(XRect x,Rect rx, boolean fast){

        if(!inRun) {
            inRun = true;

            Progress progress = new Progress(x, rx, fast);
            progress.run();

        }
    }



    private static class Progress implements Runnable{

        private Stats stats;

        private static class FixedRect {
            private Point start;
            private Point end;
            FixedRect(@NonNull Point[] points){
                this.start = points[0];
                this.end = points[points.length-1];
            }
            boolean compare(int i, int j, Point off){
                return i >= start.getX() && j >= start.getY() && end.equals(off);
            }

            @NonNull
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("[{").append(start).append("}{").append(end).append("}");
                StringBuilder sb1 = new StringBuilder();
                for (int i = 0; i < end.getX()-start.getX(); i++) {
                    sb1.append("■");
                }
                for (int i = 0; i < end.getY()-start.getY(); i++) {
                    sb.append("\n");
                    sb.append(sb1.toString());
                }
                sb.append("\n");
                return sb.toString();
            }
        }

        private Rect base;
        private XRect x;
        private boolean fast;
        Progress(XRect x,Rect r, boolean fast){
            this.base = r;
            this.x = x;
            this.fast = fast;
            this.stats = new Stats();
            stats.start();
        }

        @Override
        public void run() {
            //finished
            try {

                if(fixedRects.size()!=0 && fast)throw new Exception("ignored");

                    int[] tx = new int[fixedRects.size()];
                    int var0 = 0;
                    for(FixedRect rect:fixedRects){

                        int s = rect.end.getX()*rect.end.getY()+rect.start.getY()*rect.start.getX();
                        for (int w:tx){
                            if(w == s)throw new Exception("ignored");
                        }
                        tx[var0] = s;
                        var0++;
                    }

                run(base.copy());


            }catch (Exception end){

                //count results

                Stats.Task clear = new Stats.Task();
                Process2 process2 = new Process2(x,base.copy(),stats,clear);
                results.add(process2.run());

                int min_out = Collections.min(results);

                result(min_out);

            }
        }

        private void result(int i){
            inRun = false;
            stats.stop();
            msg("There are "+i+" rectangles!");
            x.result(stats);
        }
        private void msg(String txt){
            x.result(txt);
        }

        private ArrayList<Integer> results = new ArrayList<>();
        private ArrayList<FixedRect> fixedRects = new ArrayList<>();

        private void run(Rect base){
            Thread thread = new Thread(() -> {


        AtomicInteger mom = new AtomicInteger(0);

        ArrayList<Integer> integers = new ArrayList<>();

        HashSet<Point[]> rects = new HashSet<>();

        Rect biggest = null;
        boolean double_null = false;


            while (true){


                Rect rect;
                if(mom.get()==0){
                    rect = biggestRect(base, fixedRects.toArray(new FixedRect[0]));
                    biggest = rect.copy();
                }else{
                    rect = biggestRect(base,null);
                }

                Point[] points = rect.getStartingPoints();

                for (Point xs:points){
                    base.removePoint(xs);
                }

                rects.add(points);


                Stats.Task task = new Stats.Task();
                task.setBased(rects);
                integers.add(new Process2(x,base,stats,task).run()+mom.get()+1);

                double_null = double_null && rect.getCurrentSize() == 0;

                mom.getAndIncrement();

                if (base.getCurrentSize() == 1) {
                    mom.getAndIncrement();
                    rects.add(new Point[]{base.getFirstXPoint()});
                    break;
                }
                if (base.getCurrentSize() == 0 || double_null) {
                    break;
                }

                double_null = rect.getCurrentSize() == 0;
            }

            fixedRects.add(new FixedRect(biggest.getStartingPoints()));

            integers.add(mom.get());

            stats.add$(rects);

            Collections.sort(integers);

            int out = integers.get(0);


            msg("Calculating, could be "+out);

            results.add(out);
                run();
            });
            thread.start();
        }

        private int biggest(ArrayList<Rect> points){
            if(points==null)return 0;
            int max = 0;
            for(Rect r: points){
                if(r.sq()>max)max = r.sq();
            }
            return max;
        }

        private Rect biggestRect(Rect r, FixedRect[] fixed){



            ArrayList<Rect> points = new ArrayList<>();

            for (int j = 0; j < (r.getHeight()); j++) {
                for (int i = 0; i < (r.getWidth()); i++) {



                    if(r.hasPoint(i, j)){

                        Rect points1;

                        //compute Point
                        //get left
                        //get right;

                        int left = 0;
                        int down = 0;

                        for (int k = 0; k < r.getWidth()-i; k++) {
                            //check points
                            if(r.hasPoint(i+k,j))left++;
                            else break;
                        }
                        for (int k = 0; k < r.getHeight()-j; k++) {
                            //check points
                            if(r.hasPoint(i,j+k))down++;
                            else break;
                        }

                        if(left*down<biggest(points)){
                            //already found
                            continue;
                        }




                        //generated triangle.
                        //try to resolve left * down

                        //load in rows
                        // load in points

                    /*
                    x l l
                    d e e
                    d e e
                     */






                        Point off_r = null;

                        for (int k = 0; k < down; k++) {
                            boolean b0 = false;
                            for (int l = 0; l < left  ; l++) {
                                if(r.hasPoint(l+i,k+j)){

                                    off_r = new Point(l+i,k+j);
                                }
                                else {b0 = true; break;}
                            }
                            if(b0)break;
                        }



                        Point off_l = null;

                        for (int k = 0; k < left  ; k++) {
                            boolean b0 = false;
                            for (int l = 0; l < down  ; l++) {
                                if(r.hasPoint(k+i ,l+j))off_l = new Point(k+i,l+j);
                                else {b0 = true; break;}
                            }
                            if(b0)break;
                        }





                        if (checkPrivilege(i, j, off_r, fixed) && checkPrivilege(i, j, off_l, fixed)) {
                            if (off_r != null) {
                                if (off_l == null || (off_r.getX() - i + 1) * (off_r.getY() - j + 1) > (off_l.getX() - i + 1) * (off_l.getY() - j + 1)) {

                                    points1 = new Rect((off_r.getY() - j + 1) , (off_r.getX() - i + 1));

                                    for (int l = 0; l < off_r.getY()-j+1; l++) {
                                        for (int k = 0; k < off_r.getX()-i+1; k++) {
                                            points1.addPoint(new Point(k,l));
                                        }
                                    }


                                } else {

                                    if ((off_r.getX() - i +1) * (off_r.getY() - j + 1) == (off_l.getX() - i + 1) * (off_l.getY() - j + 1)) {

                                        points1 = new Rect((off_r.getY() - j + 1) , (off_r.getX() - i + 1));

                                        for (int l = 0; l < off_r.getY()-j+1; l++) {
                                            for (int k = 0; k < off_r.getX()-i+1; k++) {
                                                points1.addPoint(new Point(k,l));
                                            }
                                        }



                                        Rect points2 = new Rect((off_l.getY() - j + 1) , (off_l.getX() - i + 1));

                                        for (int l = 0; l < off_l.getY()-j+1; l++) {
                                            for (int k = 0; k < off_l.getX()-i+1; k++) {
                                                points2.addPoint(new Point(k,l));
                                            }
                                        }

                                        points2.setStartingPoint(i,j);


                                        points.add(points2);

                                    } else {

                                        points1 = new Rect((off_l.getY() - j + 1) , (off_l.getX() - i + 1));

                                        for (int l = 0; l < off_l.getY()-j+1; l++) {
                                            for (int k = 0; k < off_l.getX()-i+1; k++) {
                                                points1.addPoint(new Point(k,l));
                                            }
                                        }

                                    }

                                }
                            } else {
                                if (off_l != null) {

                                    points1 = new Rect((off_l.getY() - j + 1) , (off_l.getX() - i + 1));

                                    for (int l = 0; l < off_l.getY()-j+1; l++) {
                                        for (int k = 0; k < off_l.getX()-i+1; k++) {
                                            points1.addPoint(new Point(k,l));
                                        }
                                    }

                                } else {

                                    points1 = new Rect(1,1);

                                }
                            }
                            points1.setStartingPoint(i,j);
                            points.add(points1);
                        }

                    }



                }
            }

            int[] sized = new int[points.size()];
            int var0 = 0;
            Rect biggest = points.get(0);
            for (Rect rxx: points) {
                if(Modules.maxInIntArray(sized) < rxx.getCurrentSize()){
                    biggest = rxx;
                }
                sized[var0] = rxx.getCurrentSize();
                var0++;
            }



            return biggest;

        }

        private boolean checkPrivilege(int i, int j, Point off, FixedRect[] fixedRect){
            if(fixedRect==null || fixedRect.length==0 || off==null)return true;
            for (FixedRect rect : fixedRect) {
                if(rect.compare(i,j,off))return false;
            }
            return true;
        }

    }
    private interface InTRun{
        int run();
    }
    static class Process2 implements InTRun{

        private Rect rect;
        private XRect xRect;
        private Stats stats;
        private Stats.Task task;
        Process2(XRect xRect, Rect b, Stats stats, Stats.Task task){
            this.rect = b;
            this.stats = stats;
            this.xRect = xRect;
            this.task = task;
        }

        class EditRect {
            final int points;
            final int mesh;
            final int first_vertex;
            int rows;
            private int last_vertex;
            private boolean active;
            EditRect(int mesh, int points, int rows, int last_vertex){
                this.mesh = mesh;
                this.points = points;
                this.rows = rows;//editable
                this.active = true;
                this.last_vertex = last_vertex;
                this.first_vertex = last_vertex;
            }

            @NonNull
            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("[{").append(mesh).append("/").append(first_vertex).append("}");
                StringBuilder sb2 = new StringBuilder();
                for (int i = 0; i < points; i++) {
                    sb2.append("■");
                }
                String s2 = sb2.toString();
                for (int i = 0; i < rows; i++) {
                    sb.append(s2);
                    sb.append("\n");
                }
                sb.append("]");
                return sb.toString();
            }

            boolean addRow(int current_vertex){


                if(current_vertex>last_vertex+1){
                    this.close();
                    inMod.remove(this);
                    spinning.add(this);



                    return false;
                }
                this.last_vertex = current_vertex+1;
                rows++;
                return true;
            }


            boolean same(int mesh, int points){
                return this.mesh == mesh && this.points == points && active;
            }
            void close(){
                active = false;
            }

            public int getMesh() {
                return mesh;
            }

            public int getLast_vertex() {
                return last_vertex;
            }

            public int getFirst_vertex() {
                return first_vertex;
            }
        }

        private ArrayList<EditRect> inMod = new ArrayList<>();//current
        private ArrayList<EditRect> spinning = new ArrayList<>();//all

        private void findEquals(int vertex, ArrayList<ArrayList<Point>> a1, ArrayList<ArrayList<Point>> a2){
            if(a2==null){

                //creating

                for (ArrayList<Point> points : a1) {
                    EditRect rect = new EditRect(points.get(0).getX(), points.size(), 1,vertex);
                    inMod.add(rect);
                }
                return;
            }

            for (int i = 0; i < a2.size(); i++) {

                    boolean fin = false;
                    int var0 = 0;

                        for (EditRect editRect : inMod.toArray(new EditRect[0])) {
                            if (editRect.same(a2.get(i).get(0).getX(), a2.get(i).size())) {
                                if (editRect.addRow(vertex)) {

                                    fin = true;
                                    break;
                                } else

                                if(inMod.contains(editRect))inMod.set(var0, editRect);
                            }
                            var0++;
                        }



                    if(!fin){

                        //create
                        EditRect rect = new EditRect(a2.get(i).get(0).getX(), a2.get(i).size(), 1,vertex);
                        inMod.add(rect);


                    }

            }


        }

        @Override
        public int run() {

            HashMap<Integer,ArrayList<ArrayList<Point>>> map = new HashMap<>();
            ArrayList<ArrayList<Point>> mash = new ArrayList<>();
            for (int i = 0; i < rect.getHeight(); i++) {
                ArrayList<Point> rox = new ArrayList<>();
                for (int j = 0; j < rect.getWidth(); j++) {
                    Point p = new Point(j,i);

                    if(rect.hasPoint(p)){
                        rox.add(p);

                        //rect.removePoint(p);

                        if(j == rect.getWidth()-1){
                            mash.add(rox);
                            rox = new ArrayList<>();
                        }
                    }
                    else if(rox.size()>0){
                        mash.add(rox);
                        rox = new ArrayList<>();
                    }
                }
                map.put(i,mash);
                if(map.size()==1)findEquals(i, map.get(i),null);
                else findEquals(i, map.get(i-1),map.get(i));
                mash = new ArrayList<>();
            }


            if(inMod.size()!=0)spinning.addAll(inMod);

            task.addTask(spinning);
            stats.addTask(task);

            return spinning.size();




            //copy helix
        }





    }

    public static Rect minimalRect(Rect r){
        //lowest x in all points
        //lowest y in all points

        int[] x = new int[r.getPoints().size()];
        int[] y = new int[r.getPoints().size()];
        int var0 = 0;
        for (Point p:r.getPoints()){
            x[var0] = p.getX();
            y[var0] = p.getY();



            var0++;
        }

        Arrays.sort(x);
        Arrays.sort(y);

        Point min = new Point(x[0],y[0]);
        Point max = new Point(x[x.length-1]+1,y[y.length-1]+1);


        Rect rect = new Rect(max.getY()-min.getY(),max.getX()-min.getX());
        for (int i = 0; i < max.getX()-min.getX(); i++) {
            for (int j = 0; j < max.getY()-min.getY(); j++) {

                if(r.hasPoint(min.getX()+i,min.getY()+j))rect.addPoint(new Point(i,j));
            }
        }

        return rect;

    }





}
