package com.function.xrect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.JsonReader;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.exsent.core.Function;
import com.function.xrect.ui.SwitchView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Random;

import javax.sql.PooledConnection;

/**
 * Created by Jim-Linus Valentin Ahrend on 2/25/21.
 * Located in com.function.xrect in AndroidAppExample
 **/
public class XRect extends Function {

    public XRect(Function f){super(f);}

    private class ViewHandler {
        private int width,height;
        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void prepare(Activity a){
            int[] ix = getRealXy(a.getWindowManager());
            setHeight(ix[0]);
            setWidth(ix[1]);
        }

        private int[] getRealXy(WindowManager wm){
            Display display = wm.getDefaultDisplay();
            int[] output = new int[2];
            int realWidth;
            int realHeight;

            //new pleasant way to get real metrics
            DisplayMetrics realMetrics = new DisplayMetrics();
            display.getRealMetrics(realMetrics);
            realWidth = realMetrics.widthPixels;
            realHeight = realMetrics.heightPixels;

            output[0]=realHeight;
            output[1]=realWidth;
            return output;
        }
    }

    private ViewHandler vh;
    private View mView;
    private static final String FILE_NAME = "data.json", FILE_NAME2 = "info.txt", COLOR = "color", SPEED = "fast", ALGO = "algo";
    public static final String INSTAGRAM = "https://instagram.com/vali.b05", GITHUB = "https://github.com/ValentinAhrend/XRect";

    @Override
    public void onCreate(Bundle var0) {
        super.onCreate(var0);
        function.onCreate(var0);

        this.vh = new ViewHandler();
        vh.prepare(function);

        this.mView = XRectSupport.xRectView(vh.getWidth(), vh.getHeight(), this, function);

        function.setContentView(mView);

        delayedHide();

        //load settings
        getData();

        if(current_data_setting==null){
            //generate norm
            current_data_setting = new JSONObject();
            try {
                current_data_setting.put(COLOR, Color.BLACK);
                current_data_setting.put(SPEED, true);
                current_data_setting.put(ALGO, false);
            }catch (JSONException ignored){}
        }

        try {

            this.color = current_data_setting.getInt(COLOR);
        } catch (JSONException e) {
           this.color = Color.BLACK;
        }

        try{
            this.fast = current_data_setting.getBoolean(SPEED);
        } catch (JSONException e) {
            this.fast = true;
        }

        try {
            this.randomRectAlgo = current_data_setting.getBoolean(ALGO);
        } catch (JSONException e) {
            this.randomRectAlgo = false;
        }


        setDefault(33);

    }

    private Rect current_rect;

    private void setDefault(int d){

        this.d = d;

        System.err.println("setDefault");

        this.current_rect = new Rect(d,d);

        // d * d = square

        GridView gridView = mView.findViewWithTag("grid");
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.width = (Math.round(vh.getWidth()/1.05f))/d*d;
        params.height = (Math.round(vh.getWidth()/1.05f))/d*d;
        gridView.requestLayout();
        BaseAdapter ad = new BaseAdapter() {

            @Override
            public int getCount() {
                return d*d;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {


                TextView x = new TextView(function);
                x.setLayoutParams(new LinearLayout.LayoutParams((Math.round(vh.getWidth()/1.05f))/d,(Math.round(vh.getWidth()/1.05f))/d));

                int pos_y = position/d;
                int pos_x = position%d;

                x.setTag(pos_x+"/"+pos_y);
                x.setBackgroundColor(Color.LTGRAY);
                x.setOnClickListener(v -> useEdit(new Point(pos_x,pos_y)));

                return x;
            }

        };

        gridView.setAdapter(ad);

        gridView.setNumColumns(d);

    }

    private int d;
    private int color;
    private boolean fast;
    private boolean randomRectAlgo;

    private static final int UI_ANIMATION_DELAY = 300;
    @SuppressWarnings("deprecation")
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            //noinspection deprecation
            mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = () -> {
        // Delayed display of UI elements
        ActionBar actionBar = function.getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    };
    private final Runnable mHideRunnable = this::hide;
    private void delayedHide() {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, 100);
    }

    @Override
    public void onResume() {
        function.onResume();
        System.err.println("resume");
        hide();
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = function.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private JSONObject current_data_setting;
    private JSONObject getData(){

        File f = new File(function.getDir("all"+function.getFunctionID(),MODE_PRIVATE).getAbsolutePath()+File.separator+FILE_NAME);
        if(f.exists()) {
            String s = Utils.readFile(f);
            if (s != null && !s.equals("")) {

                try {
                    this.current_data_setting = new JSONObject(s);
                    return current_data_setting;
                } catch (JSONException e) {
                    return null;
                }

            }else return null;
        }else return null;
    }

    private void putData(JSONObject obj_changed){
        //this.current_data_setting = obj_changed;
        File f = new File(function.getDir("all"+function.getFunctionID(),MODE_PRIVATE).getAbsolutePath()+File.separator+FILE_NAME);
        try {
            FileWriter writer = new FileWriter(f);
            writer.write(obj_changed.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int y__;
    private int x__;
    void calc(){
        boolean fast = false;

        if(current_rect.getPoints()==null || current_rect.getPoints().size()==0)return;

        Rect b = RectHelper.minimalRect(current_rect);

        this.y__ = current_rect.getFirstYPoint().getY();
        this.x__ = current_rect.getFirstXPoint().getX();

        RectHelper.calculateRectInRect(this,b,fast);
        run_load();
    }
    void result(){

        //next

        if(cu_xj_sd==-1){
            return;
        }

        Rect rect = stats.getRect().get(cu_xj_sd);

        {
            for (Point point : stats.getRect().get(cu_xj_sd-1<0?stats.getRect().size()-1:cu_xj_sd-1).getStartingPoints()) {
                mView.findViewWithTag((x__+point.getX())+"/"+(y__+point.getY())).setBackgroundColor(color);
            }
        }

        //highlight
        int highlight_color = color==Color.BLACK?Color.WHITE:Color.BLACK;
        for (Point point : rect.getStartingPoints()) {
            current_rect.hasPoint(new Point(point.getX()+x__,(point.getY()+y__)));
            mView.findViewWithTag((point.getX()+x__)+"/"+(y__+point.getY())).setBackgroundColor(highlight_color);
        }

        cu_xj_sd++;
        if(cu_xj_sd == stats.getRect().size())cu_xj_sd = -1;

    }

    private Handler h0;
    private Runnable rr;
    void runSet(){

        this.click_dur = System.currentTimeMillis();

        if(able) {
            able = false;
            h0 = new Handler();
                    rr = () -> {
                if (cu_xj_sd == -1) {

                    for (Point point : stats.getRect().get(stats.getRect().size() - 1).getStartingPoints()) {
                        mView.findViewWithTag((x__ + point.getX()) + "/" + (y__ + point.getY())).setBackgroundColor(color);
                    }

                    cu_xj_sd++;

                } else {


                    result();

                }

                able = true;

                h0.postDelayed(rr,cu_xj_sd==-1?1000:(20*200/current_rect.sq()*100));


            };
            h0.postDelayed(rr,1000);

        }

    }
    void runStop(){

        click_dur = System.currentTimeMillis() - click_dur;
        System.err.println(click_dur);
        if(click_dur<100) {
            if(cu_xj_sd==-1){

                for (Point point : stats.getRect().get(stats.getRect().size() - 1).getStartingPoints()) {
                    mView.findViewWithTag((x__ + point.getX()) + "/" + (y__ + point.getY())).setBackgroundColor(color);
                }

                cu_xj_sd++;
                h0.removeCallbacks(rr);
                able = true;

            }else {
                result();
                Handler h = new Handler();
                h.postDelayed(() -> {
                    h0.removeCallbacks(rr);
                    able = true;
                }, 222);
            }
        }else{
            h0.removeCallbacks(rr);
            able = true;
        }


    }

    private boolean able = true;
    private long click_dur;
    private int cu_xj_sd = -1;
    private Stats stats;
    void result(Stats stats){


        this.stats = stats;

        final int w = vh.width;
        final int h = vh.height;

        this.cu_xj_sd = 0;



        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { 45,45,45,45, 0, 0, 0, 0 });
        shape.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        shape.setOrientation(GradientDrawable.Orientation.TL_BR);

        long d = stats.getDuration();

        if(d>255*3)d = 255*3;

        int x = (int) (d / 3);
        int e = (int) (d / 3);

        int color = Color.rgb(x,255-e,22);
        int color2 = Color.rgb(x, 255-e, 0);

        shape.setColors(new int[]{color,color2});

        shape.setSize(w+w/22,Math.round(h/2.5f));

        this.runOnUiThread$(() -> {
            ConstraintLayout bot = mView.findViewWithTag("bot&&");
            bot.setBackground(shape);

            TextView tv = mView.findViewWithTag("res");
            tv.setText(stats.getDuration() +"ms – select single rectangle");
        });
        hasStopped = true;
        this.h.removeCallbacks(runnable);
    }
    void result(String msg){

        this.runOnUiThread$(() -> {
            TextView cal = mView.findViewWithTag("cal");
            cal.setText(msg);
        });


            hasStopped = true;
            h.removeCallbacks(runnable);



    }

    private int exec_num;
    void random(){

        //clear btn
        //bot.setBackgroundColor();
        TextView tv = mView.findViewWithTag("res");
        if(!tv.getText().toString().equals("Results...")){
            ConstraintLayout bot = mView.findViewWithTag("bot&&");
            bot.setBackgroundColor(Color.BLACK);
            tv.setText("Results...");
            TextView cal = mView.findViewWithTag("cal");
            cal.setText("Start Algorithm");
        }


        new Thread(() -> {
            try {

            for (Point point : current_rect.getPoints()) {
                mView.findViewWithTag(point.getX() + "/" + point.getY()).setBackgroundColor(Color.LTGRAY);
            }

            current_rect.clear();
            //int min = d/2
            //int max = d*2
            Random random = new Random();
            int exec = random.nextInt((d * d) / 4 * 3);

                if (randomRectAlgo) {


                /*
                10% – large rect in ratio with d
                60% – medium rect in ratio with d
                30% – small rect in ratio with d
                                 */

                /*
                random:
                – random start position
                – random amount of runs
                – random amount of a and b (but in a specific field)
                 */
                    exec = ((int) Math.sqrt(exec));
                    Point start = new Point(random.nextInt(d / 2) + d / 8, random.nextInt(d / 2) + d / 8);


                    int minimal_max = d / 3;
                    if (minimal_max < 2) minimal_max = 2;
                    int minimal_medium = d / 6;
                    if (minimal_medium < 1) minimal_medium = 1;
                    final int minimal_small = 1;

                    boolean elf = false;

                    for (int i = 0; i < exec; i++) {
                        System.err.println(i);
                        float gas = random.nextFloat() / 2f;
                        float gas2 = random.nextFloat() / 2f;
                        float ratio = (float) i / exec  /* additional */ + gas;
                        int[] q = new int[2];
                        if (ratio < 0.1f) {
                            //max
                            q[0] = (int) gas * 10 + /*minimal */ minimal_max;
                            q[1] = (int) gas2 * 10 + minimal_max;
                        }
                        if (ratio < 0.9f && ratio > 0.09f) {
                            //medium
                            q[0] = (int) gas * 10 + /*minimal */ minimal_medium;
                            q[1] = (int) gas2 * 10 + minimal_medium;
                        }
                        if (ratio > 0.89f) {
                            //small
                            q[0] = (int) gas * 10 + /*minimal */ minimal_small;
                            q[1] = (int) gas2 * 10 + minimal_small;
                        }
                        Log.i("TIME", String.valueOf(System.currentTimeMillis() - 1615654165067L));
                        for (int j = start.getX(); j < q[0] + start.getX(); j++) {
                            for (int k = start.getY(); k < q[1] + start.getY(); k++) {
                                Point add = new Point(j, k);
                                if (current_rect.test(add) && !current_rect.hasPoint(add)) {
                                    current_rect.addPoint(add);
                                    mView.findViewWithTag(add.getX() + "/" + add.getY()).setBackgroundColor(this.color);
                                }
                            }
                        }
                        Log.i("TIME2", String.valueOf(System.currentTimeMillis() - 1615654165067L));

                        //generate next starting point


                        Point p_medium = new Point((current_rect.getFirstXPoint().getX() + current_rect.getLastXPoint().getX()) / 2, (current_rect.getFirstYPoint().getY() + current_rect.getLastYPoint().getY()) / 2);
                        Point p2 = current_rect.partnerPoint(p_medium);


                        if (elf) {
                            start = new Point((p2.getX() + p_medium.getX()) / 2, (p2.getY() + p_medium.getY()) / 2);

                            elf = false;
                        } else {
                            start = p2;

                            elf = true;
                        }

                    }

                } else {
                    Point start = new Point(random.nextInt(d / 2) + d / 4, random.nextInt(d / 2) + d / 4);


                    mView.findViewWithTag(start.getX() + "/" + start.getY()).setBackgroundColor(this.color);
                    current_rect.addPoint(start);
                    for (exec_num = 1; exec_num < exec + 1; exec_num++) {
                        Point p = createRoot(start, current_rect, random);
                        if (!current_rect.hasPoint(p)) {
                            current_rect.addPoint(p);
                            mView.findViewWithTag(p.getX() + "/" + p.getY()).setBackgroundColor(this.color);
                        }
                        start = p;
                    }

                    //RectHelper.minimalRect(function,current_rect);


                }
            } catch (ConcurrentModificationException co) {
                function.runOnUiThread(() -> {
                    Toast.makeText(function, "Slow down buddy", Toast.LENGTH_SHORT).show();
                });
            }

        }).start();
    }
    private static int median(int[] numArray){
        Arrays.sort(numArray);
        double median;
        if (numArray.length % 2 == 0)
            median = ((double)numArray[numArray.length/2] + (double)numArray[numArray.length/2 - 1])/2;
        else
            median = numArray[numArray.length/2];
        return (int)median;
    }

    private StringBuilder m;
    private Handler h;
    private Runnable runnable;
    private boolean hasStopped;
    private void run_load(){

        m = new StringBuilder("/////      /////      /////      /////      /////      /////");

        h = new Handler();

        hasStopped = false;

        runnable = new Runnable() {
            @Override
            public void run() {


                if(m.toString().contains("/")) {
                    m.insert(0, m.toString().endsWith("/")?" ":"/");
                    m = new StringBuilder(m.subSequence(0,m.length()-1));
                }else{
                    m = new StringBuilder("/////      /////      /////      /////      /////      /////");
                }

                TextView textView = mView.findViewWithTag("cal");
                textView.setText(m.toString());

                if(!hasStopped)h.postDelayed(this, 1);
            }
        };

        h.post(runnable);

    }

    private Point createRoot(Point p, Rect r, Random random){
        int opt = random.nextInt(4)+1;
        Point px = new Point(-1,-1);
        do {

            int var0 = 0;
            int var1 = 0;
            if(opt/3==0)var0++;
            else var0--;
            if(opt%3==1)var1++;
            else var1--;

            if(var0==1 && var0==var1){
                //up
                px = new Point(p.getX(),p.getY()+1);
            }
            if(var0==1 && var1 == -1){
                //left
                px = new Point(p.getX()+1,p.getY());
            }
            if(var0==-1 && var1 == 1){
                //down
                px = new Point(p.getX(),p.getY()-1);
            }
            if(var0==-1 && var0==var1){
                //right
                px = new Point(p.getX()-1, p.getY());
            }

            opt++;
            if(opt==5)opt=1;

            px.print();
        }while (!r.test(px));
        return px;
    }

    private boolean isEdit;
    void edit(){

        this.before = current_rect==null?null:current_rect.copy();
        this.isEdit = true;
        //back & clear
        Button back = mView.findViewWithTag("ran");
        Button finish = mView.findViewWithTag("edit");
        Button clear = mView.findViewWithTag("more");

        back.setText("[undo]");
        back.setOnClickListener(v -> back(clear));
        finish.setText("[finish]");
        finish.setOnClickListener(v -> finish$());
        clear.setText("[clear]");
        clear.setOnClickListener(v -> clear());

        pos = 0;
        dos_rect = new ArrayList<>();
        dos_rect.add(current_rect);

        showDest();
        showClob();


    }

    private ArrayList<Rect> dos_rect = new ArrayList<>();
    private int pos=-1;

    private void back(Button b){
        if(b.getText().toString().equals("[clear]")){
            b.setText("[redo]");
            b.setOnClickListener(v -> redo(b));
        }
        if(dos_rect.size()==0 || pos==0)return;
        try {
            buildRect(dos_rect.get(pos - 1));
            this.current_rect = dos_rect.get(pos - 1);
            pos--;
        }catch (IndexOutOfBoundsException ignored){}
    }

    private void useEdit(Point p){
        if(!isEdit)return;
        this.before = current_rect.copy();
        dos_rect.add(pos,current_rect.copy());
        if(dos_rect.size()>128){dos_rect.remove(0);pos--;}
        if(current_rect.hasPoint(p)){
            mView.findViewWithTag(p.getX()+"/"+p.getY()).setBackgroundColor(Color.LTGRAY);
            current_rect.removePoint(p);
        }else{
            current_rect.addPoint(p);
            mView.findViewWithTag(p.getX()+"/"+p.getY()).setBackgroundColor(this.color);
        }
        pos++;
    }

    private void buildRect(Rect new_){
        System.err.println(new_.getPoints().size());
        System.err.println(current_rect.getPoints().size());
        for (Point point : current_rect.getPoints()) {
            try {
                if (!new_.hasPoint(point)) {
                    mView.findViewWithTag(point.getX() + "/" + point.getY()).setBackgroundColor(Color.LTGRAY);
                }
            }catch (IllegalArgumentException i){
                i.printStackTrace();
            }
        }
        int var0 = 0;
        for (Point point : new_.getPoints()){
            if(!current_rect.hasPoint(point)) {
                System.err.println("color:"+point);
                        mView.findViewWithTag(point.getX() + "/" + point.getY()).setBackgroundColor(this.color);
            }
            var0++;
        }
    }

    private void redo(Button b){

        try {
            buildRect(dos_rect.get(pos+1));
            this.current_rect = dos_rect.get(pos+1);
        }catch (IndexOutOfBoundsException i){
            i.printStackTrace();
            b.setText("[clear]");
            b.setOnClickListener(v -> clear());
            return;
        }
        pos++;

        if(pos==dos_rect.size()-1){
            b.setText("[clear]");
            b.setOnClickListener(v -> clear());
        }
    }

    private Rect before;
    private void finish$(){

        if(!before.equals(current_rect)){

            TextView tv = mView.findViewWithTag("res");
            if(!tv.getText().toString().equals("Results...")){
                ConstraintLayout bot = mView.findViewWithTag("bot&&");
                bot.setBackgroundColor(Color.BLACK);
                tv.setText("Results...");
                TextView cal = mView.findViewWithTag("cal");
                cal.setText("Start Algorithm");
            }

        }

        //set color
        try {
            if(current_data_setting==null)current_data_setting = new JSONObject();
            current_data_setting.put("color",color);
            putData(current_data_setting);
        } catch (JSONException ignored) {}


        Button back = mView.findViewWithTag("ran");
        Button finish = mView.findViewWithTag("edit");
        Button clear = mView.findViewWithTag("more");

        back.setText("[random]");
        back.setOnClickListener(v -> random());
        finish.setText("[edit]");
        finish.setOnClickListener(v -> edit());
        clear.setText("[more]");
        clear.setOnClickListener(v -> more());



        dos_rect = new ArrayList<>();
        pos = 0;
        stopDest();
        stopClob();
    }

    private void showDest(){
        View l2 = mView.findViewWithTag("l2");
        l2.setVisibility(View.VISIBLE);
        View v2 = mView.findViewWithTag("res");
        v2.setVisibility(View.INVISIBLE);
        SeekBar sb = mView.findViewWithTag("sb");
        TextView d = mView.findViewWithTag("d_view");
        d.setText(String.valueOf(this.d));
        sb.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY));
        GradientDrawable gr = new GradientDrawable();
        gr.setShape(GradientDrawable.OVAL);
        gr.setSize(vh.width/22,vh.width/22);
        gr.setColor(Color.BLUE);
        sb.setThumb(gr);
        sb.setProgress(this.d-2);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress+=2;
                d.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(dos_rect.size()>1){
                    //you sure
                    Snackbar.make(mView,"Change size to "+seekBar.getProgress()+"?", BaseTransientBottomBar.LENGTH_SHORT).setAction("YES!", v -> setDefault(seekBar.getProgress())).show();
                }else setDefault(seekBar.getProgress()+2);
            }
        });
    }

    private void stopDest(){
        View l2 = mView.findViewWithTag("l2");
        l2.setVisibility(View.INVISIBLE);
        View v2 = mView.findViewWithTag("res");
        v2.setVisibility(View.VISIBLE);

    }

    private void showClob(){
        View l3 = mView.findViewWithTag("l3");
        l3.setVisibility(View.VISIBLE);
        View v2 = mView.findViewWithTag("cal");
        v2.setVisibility(View.INVISIBLE);
        SeekBar sb = mView.findViewWithTag("sb2");

        LinearGradient test = new LinearGradient(0.f, 0.f, vh.width/1.2f, 0.0f,
                new int[] { Color.RED,Color.YELLOW,Color.GREEN,Color.CYAN,Color.BLUE, Color.MAGENTA,Color.RED},
                null, Shader.TileMode.CLAMP);
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setShader(test);

        sb.setProgressDrawable(shape);
        sb.setMax(256*5);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int r = 0;
                int g = 0;
                int b = 0;
                if(fromUser) {
                    if (progress < 256) {
                        r = 255;
                        g = progress;
                    } else if (progress < 256 * 2) {
                        r = 255 - progress % 256;
                        g = 255;
                    } else if (progress < 256 * 3) {
                        g = 255;
                        b = progress % 256;
                    } else if (progress < 256 * 4) {
                        g = 255 - progress % 256;
                        b = 255;
                    } else if (progress < 256 * 5) {
                        r = progress % 256;
                        b = 255 - progress % 256;
                    }

                    if(progress==sb.getMax())color = Color.BLACK;
                    else color = Color.rgb(r, g, b);



    GradientDrawable gr = new GradientDrawable();
    gr.setShape(GradientDrawable.OVAL);
    gr.setSize(vh.width / 22, vh.width / 22);
    gr.setColor(Color.argb(255, r, g, b));
    seekBar.setThumb(gr);
}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void stopClob(){
        View l3 = mView.findViewWithTag("l3");
        l3.setVisibility(View.INVISIBLE);
        View v2 = mView.findViewWithTag("cal");
        v2.setVisibility(View.VISIBLE);
    }

    private void clear(){
        try {
            pos = 0;
            Point[] points = new Point[current_rect.getPoints().size()];
            int var0 = 0;
            for (Point p : current_rect.getPoints()) {
                mView.findViewWithTag(p.getX() + "/" + p.getY()).setBackgroundColor(Color.LTGRAY);
                points[var0] = p;
                var0++;
            }
            for (Point point : points) {
                current_rect.removePoint(point);
            }
        }catch (ConcurrentModificationException ignored){}
    }

    void more(){
        LinearLayout linearLayout = mView.findViewWithTag("frame");
        ConstraintLayout cs = mView.findViewWithTag("bot&&");
        linearLayout.setVisibility(View.GONE);
        cs.setVisibility(View.GONE);

        SwitchView switchView = mView.findViewWithTag("switch1");
        if(!switchView.isChecked())switchView.setChecked(randomRectAlgo);
        switchView.setOnCheckedChangeListener((switchView1, isChecked) -> {
            //put data
            try {
                this.randomRectAlgo = isChecked;
                current_data_setting.put(ALGO,isChecked);
                putData(current_data_setting);
            } catch (JSONException ignored) {}
        });

        SwitchView switchView1 = mView.findViewWithTag("switch2");
        if(!switchView1.isChecked())switchView1.setChecked(fast);
        switchView1.setOnCheckedChangeListener((switchView12, isChecked) -> {
            //put data
            try {
                this.fast = isChecked;
                current_data_setting.put(SPEED,isChecked);
                putData(current_data_setting);
            } catch (JSONException ignored) {}
        });


        File file = new File(function.getDir("all"+function.getFunctionID(),MODE_PRIVATE).getAbsolutePath()+File.separator+FILE_NAME2);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader reader1  = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line=reader1.readLine())!=null){
                stringBuilder.append(line).append("\n");
            }

            TextView info = mView.findViewById(3129);
            info.setText(stringBuilder.toString());
            XRectSupport.makeTextViewResizable(info,4,"See more",true);

        } catch (IOException e) {
            e.printStackTrace();
            TextView info = mView.findViewById(3129);
            info.setText("The Minimum Rectangle is used to calculate the minimal amount of rectangles to rebuild a specific area.\n" +
                    "The algorithm starts with building a rectangle without empty rows or columns.\n" +
                    "Now the biggest rectangle of the area will be calculated. This rectangle will be removed and the program repeats the progress of finding the biggest rectangle until the area is empty.\n" +
                    "The result of the \"Biggest Rect Algorithm\" is an accurate amount, but which failure rate increases by increasing area size.\n" +
                    "That's why there is also another algorithm: \"Line Algorithm\". \n" +
                    "The \"Line Algorithm\" starts by finding a line of points. In each row can be multiple lines.\n" +
                    "Then the program checks whether the row underneath has the exact same line. If this is true, both lines become an rectangle.\n" +
                    "This rectangle will get filled with other matching lines.\n" +
                    "In the end, the amount created rectangles is the perfect optimization.\n" +
                    "Each loop run of \"Biggest Rect Algorithm\" gives the \"Line Algorithm\" the current area and that program continues calculating.\n" +
                    "The result is always correct, in other words: I've never seen an error or mistake.\n" +
                    "\n" +
                    "\"Minimum Rectangle Algorithm\" – by Valentin Ahrend\n");
            XRectSupport.makeTextViewResizable(info,4,"See more",true);

        }

    }

    public void exit(){
        LinearLayout linearLayout = mView.findViewWithTag("frame");
        if(linearLayout.getVisibility()==View.GONE){
            ConstraintLayout cs = mView.findViewWithTag("bot&&");
            cs.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }
}
