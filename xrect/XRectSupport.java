package com.function.xrect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.dx.cf.code.Frame;
import com.exsent.app.R;
import com.function.xrect.ui.SwitchView;

/**
 * Created by Jim-Linus Valentin Ahrend on 2/25/21.
 * Located in com.function.xrect in AndroidAppExample
 **/
public class XRectSupport {

    public static View xRectView(int w, int h, XRect x, Context f){

        ConstraintLayout c = new ConstraintLayout(f);
        c.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout linearLayout = new LinearLayout(f);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(w, h/5*4));

        Space space = new Space(f);
        space.setLayoutParams(new LinearLayout.LayoutParams(1,h/26));

        linearLayout.addView(space);

        ConstraintLayout header = new ConstraintLayout(f);
        header.setLayoutParams(new LinearLayout.LayoutParams(Math.round(w/1.05f), h/16));

        TextView tv = new TextView(f);
        tv.setText("XRect");
        tv.setTextSize(1,26);
        tv.setTextColor(Color.BLACK);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(new LinearLayout.LayoutParams(w/3*2, h/18));
        header.addView(tv);

        Button exit = new Button(f);
        exit.setText("[X]");
        exit.setBackgroundColor(Color.LTGRAY);
        exit.setTextColor(Color.BLACK);
        exit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        exit.setGravity(Gravity.CENTER);
        exit.setOnClickListener(v -> x.exit());
        exit.setLayoutParams(new LinearLayout.LayoutParams(w/10, h/16));
        exit.setX(header.getLayoutParams().width-w/10f);
        header.addView(exit);

        linearLayout.addView(header);

        Space space1 = new Space(f);
        space1.setLayoutParams(new LinearLayout.LayoutParams(1,h/26));

        linearLayout.addView(space1);

        FrameLayout sqq = new FrameLayout(f);
        sqq.setTag("frame2");
        sqq.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout lf = new LinearLayout(f);
        //lf.setBackgroundColor(Color.RED);
        lf.setLayoutParams(new LinearLayout.LayoutParams(Math.round(w/1.05f), h));
        lf.setGravity(Gravity.CENTER_HORIZONTAL);
        lf.setX(w-w/1.025f);
        lf.setOrientation(LinearLayout.VERTICAL);

        TextView header0 = new TextView(f);
        header0.setText("Settings");
        header0.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        header0.setGravity(Gravity.CENTER_VERTICAL);
        header0.setTypeface(Typeface.DEFAULT_BOLD);
        header0.setTextColor(Color.BLACK);
        header0.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h/16));
        header0.setTextSize(1,22);

        lf.addView(header0);

        LinearLayout linearLayout10 = new LinearLayout(f);
        linearLayout10.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout10.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h/16));

        TextView sub1 = new TextView(f);
        sub1.setText("Controlled Rectangle Pattern");
        sub1.setTextColor(Color.BLACK);
        sub1.setGravity(Gravity.CENTER_VERTICAL);
        sub1.setTextSize(1,16);
        sub1.setLayoutParams(new LinearLayout.LayoutParams(Math.round(w/1.4f), ViewGroup.LayoutParams.WRAP_CONTENT));
        sub1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        sub1.setMaxLines(1);
        linearLayout10.addView(sub1);

        Space space2 = new Space(f);
        final int round = Math.round(w / 1.05f - w / 1.4f - w / 7f);
        space2.setLayoutParams(new LinearLayout.LayoutParams(round,1));
        linearLayout10.addView(space2);

        SwitchView switchView = new SwitchView(f);
        switchView.setChecked(false);
        //switchView.setX(w/1.05f-w/7f);
        switchView.setTag("switch1");
        switchView.setLayoutParams(new LinearLayout.LayoutParams(w/7, ViewGroup.LayoutParams.WRAP_CONTENT));

        linearLayout10.addView(switchView);
        lf.addView(linearLayout10);

        LinearLayout linearLayout11 = new LinearLayout(f);
        linearLayout11.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout11.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h/16));

        TextView sub2 = new TextView(f);
        sub2.setText("Fast algorithm");
        sub2.setTextColor(Color.BLACK);
        sub2.setGravity(Gravity.CENTER_VERTICAL);
        sub2.setTextSize(1,16);
        sub2.setLayoutParams(new LinearLayout.LayoutParams(Math.round(w/1.4f), ViewGroup.LayoutParams.WRAP_CONTENT));
        sub2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        sub2.setMaxLines(1);
        linearLayout11.addView(sub2);

        Space space3 = new Space(f);
        space3.setLayoutParams(new LinearLayout.LayoutParams(round,1));
        linearLayout11.addView(space3);

        SwitchView switchView1 = new SwitchView(f);
        switchView1.setChecked(false);
        //switchView.setX(w/1.05f-w/7f);
        switchView1.setTag("switch2");
        switchView1.setLayoutParams(new LinearLayout.LayoutParams(w/7, ViewGroup.LayoutParams.WRAP_CONTENT));

        linearLayout11.addView(switchView1);

        lf.addView(linearLayout11);

        Space space4 = new Space(f);
        space4.setLayoutParams(new LinearLayout.LayoutParams(1,h/33));
        lf.addView(space4);

        TextView header2 = new TextView(f);
        header2.setText("Algorithm");
        header2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        header2.setGravity(Gravity.CENTER_VERTICAL);
        header2.setTypeface(Typeface.DEFAULT_BOLD);
        header2.setTextColor(Color.BLACK);
        header2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h/16));
        header2.setTextSize(1,22);
        lf.addView(header2);

        TextView info = new TextView(f);
        info.setText("Info");
        info.setTextSize(1,13);
        info.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        info.setTextColor(Color.BLACK);
        info.setId(3129);
        lf.addView(info);

        Space space5 = new Space(f);
        space5.setLayoutParams(new LinearLayout.LayoutParams(1,h/33));
        lf.addView(space5);


        TextView header1 = new TextView(f);
        header1.setText("Contact");
        header1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        header1.setGravity(Gravity.CENTER_VERTICAL);
        header1.setTypeface(Typeface.DEFAULT_BOLD);
        header1.setTextColor(Color.BLACK);
        header1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,h/16));
        header1.setTextSize(1,22);

        lf.addView(header1);

        LinearLayout linearLayout_ = new LinearLayout(f);
        linearLayout_.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h/16));
        linearLayout_.setGravity(Gravity.CENTER);

        Button github = new Button(f);
        github.setText("[github]");
        github.setGravity(Gravity.CENTER);
        github.setAllCaps(false);
        github.setBackgroundColor(Color.LTGRAY);
        github.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(XRect.GITHUB));
            f.startActivity(i);
        });
        github.setLayoutParams(new LinearLayout.LayoutParams(w/5, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout_.addView(github);

        Space sss0 = new Space(f);
        sss0.setLayoutParams(new LinearLayout.LayoutParams(w/20,1));
        linearLayout_.addView(sss0);

        Button github1 = new Button(f);
        github1.setText("[Valentin Ahrend]");
        github1.setGravity(Gravity.CENTER);
        github1.setAllCaps(false);
        github1.setBackgroundColor(Color.LTGRAY);
        github1.setOnClickListener(v -> {
            //nothing
        });
        github1.setLayoutParams(new LinearLayout.LayoutParams(w/3, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout_.addView(github1);

        Space sss1 = new Space(f);
        sss1.setLayoutParams(new LinearLayout.LayoutParams(w/20,1));
        linearLayout_.addView(sss1);

        Button github2 = new Button(f);
        github2.setText("[instagram]");
        github2.setGravity(Gravity.CENTER);
        github2.setAllCaps(false);
        github2.setBackgroundColor(Color.LTGRAY);
        github2.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(XRect.INSTAGRAM));
            f.startActivity(i);
        });
        github2.setLayoutParams(new LinearLayout.LayoutParams(w/5, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout_.addView(github2);

        Space sss2 = new Space(f);
        sss2.setLayoutParams(new LinearLayout.LayoutParams(w/20,1));
        linearLayout_.addView(sss2);

        lf.addView(linearLayout_);


        sqq.addView(lf);

        LinearLayout frameLayout = new LinearLayout(f);
        frameLayout.setTag("frame");
        frameLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        frameLayout.setOrientation(LinearLayout.VERTICAL);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ConstraintLayout m = new ConstraintLayout(f);
        m.setLayoutParams(new LinearLayout.LayoutParams(Math.round(w/1.05f), Math.round(w/1.05f)));
        m.setBackgroundColor(Color.BLACK);

        LinearLayout y = new LinearLayout(f);
        y.setGravity(Gravity.CENTER);
        y.setLayoutParams(new LinearLayout.LayoutParams(Math.round(w/1.05f)-4, Math.round(w/1.05f)-4));
        y.setBackgroundColor(Color.LTGRAY);
        y.setX(2);
        y.setY(2);

        GridView grid = new GridView(f);

        grid.setGravity(Gravity.CENTER);
        grid.setTag("grid");
        grid.setNumColumns(16);
        grid.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        grid.setBackgroundColor(Color.LTGRAY);
        y.addView(grid) ;

        m.addView(y);

        frameLayout.addView(m);

        Space space33 = new Space(f);
        space33.setLayoutParams(new LinearLayout.LayoutParams(1,h/33));
        frameLayout.addView(space33);

        FrameLayout btn = new FrameLayout(f);
        btn.setLayoutParams(new LinearLayout.LayoutParams(w/3*2, h/16));

        Button ran = new Button(f);
        ran.setText("[random]");
        ran.setTextColor(Color.BLACK);
        ran.setBackgroundColor(Color.LTGRAY);
        ran.setTextSize(1,13);
        ran.setAllCaps(false);
        ran.setTag("ran");
        ran.setOnClickListener(v -> x.random());
        ran.setGravity(Gravity.CENTER);
        ran.setLayoutParams(new LinearLayout.LayoutParams(w/7, h/16));
        btn.addView(ran);

        Button edit = new Button(f);
        edit.setText("[edit]");
        edit.setTextColor(Color.BLACK);
        edit.setBackgroundColor(Color.LTGRAY);
        edit.setTextSize(1,13);
        edit.setAllCaps(false);
        edit.setTag("edit");
        edit.setGravity(Gravity.CENTER);
        edit.setX(w/3f-w/14f);
        edit.setOnClickListener(v -> x.edit());
        edit.setLayoutParams(new LinearLayout.LayoutParams(w/7, h/16));
        btn.addView(edit);

        Button more = new Button(f);
        more.setText("[more]");
        more.setTextColor(Color.BLACK);
        more.setBackgroundColor(Color.LTGRAY);
        more.setTextSize(1,13);
        more.setAllCaps(false);
        more.setTag("more");
        more.setGravity(Gravity.CENTER);
        more.setX(w/3f*2-w/7f);
        more.setOnClickListener(v -> x.more());
        more.setLayoutParams(new LinearLayout.LayoutParams(w/7, h/16));
        btn.addView(more);

        frameLayout.addView(btn);

        Space space33$ = new Space(f);
        space33$.setLayoutParams(new LinearLayout.LayoutParams(1,h/33));
        frameLayout.addView(space33$);

        sqq.addView(frameLayout);
        linearLayout.addView(sqq);
        c.addView(linearLayout);
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { 45,45,45,45, 0, 0, 0, 0 });
        shape.setColor(Color.BLACK);
        shape.setSize(w+w/22,Math.round(h/2.5f));

        ConstraintLayout bot = new ConstraintLayout(f);
        bot.setBackground(shape);
        bot.setTag("bot&&");
        bot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,Math.round(h/2.5f)));
        bot.setY(linearLayout.getLayoutParams().height);

        LinearLayout linearLayout1 = new LinearLayout(f);
        linearLayout1.setGravity(Gravity.CENTER);
        linearLayout1.setOrientation(LinearLayout.VERTICAL
        );

        linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h/5));

        CardView result= new CardView(f);
        result.setLayoutParams(new LinearLayout.LayoutParams(Math.round(w/1.2f), h/16));
        result.setCardElevation(3.0f);
        result.setRadius(90);
        result.setMaxCardElevation(6.0f);
        result.setCardBackgroundColor(Color.LTGRAY);

        TextView res = new TextView(f);
        res.setText("Results...");
        res.setTextColor(Color.BLACK);
        res.setAllCaps(false);

        res.setTag("res");
        res.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        res.setGravity(Gravity.CENTER);
        res.setTextSize(1,20);
        res.setOnTouchListener((v, event) -> {
            System.err.println("touch");
            if(event.getAction() == MotionEvent.ACTION_DOWN){

                x.runSet();

            }
            if(event.getAction() == MotionEvent.ACTION_UP){

                x.runStop();

            }
            System.err.println("event:"+event.getAction());
            return true;
        });
        res.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        result.addView(res);

        LinearLayout linearLayout2  = new LinearLayout(f);
        linearLayout2.setGravity(Gravity.CENTER);
        linearLayout2.setVisibility(View.INVISIBLE);
        linearLayout2.setTag("l2");

        TextView num = new TextView(f);
        num.setGravity(Gravity.CENTER);
        num.setTextSize(1,16);
        num.setMaxLines(1);
        num.setLayoutParams(new LinearLayout.LayoutParams(w/6, ViewGroup.LayoutParams.MATCH_PARENT));
        num.setText("d");
        num.setTag("d_view");

        linearLayout2.addView(num);

        SeekBar seekBar = new SeekBar(f);
        seekBar.setTag("sb");
        seekBar.setMax(98);
        seekBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        linearLayout2.addView(seekBar);

        result.addView(linearLayout2);

        linearLayout1.addView(result);

        Space spacea2 = new Space(f);
        spacea2.setLayoutParams(new LinearLayout.LayoutParams(1, h/33));
        linearLayout1.addView(spacea2);

        CardView calc= new CardView(f);
        calc.setLayoutParams(new LinearLayout.LayoutParams(Math.round(w/1.2f), h/16));
        calc.setCardElevation(3.0f);
        calc.setRadius(90);
        calc.setMaxCardElevation(6.0f);
        calc.setCardBackgroundColor(Color.LTGRAY);

        TextView cal = new TextView(f);
        cal.setText("Start Algorithm");
        cal.setTextColor(Color.BLACK);
        cal.setAllCaps(true);
        cal.setAllCaps(false);
        cal.setMaxLines(1);
        cal.setTag("cal");
        cal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        cal.setGravity(Gravity.CENTER);
        cal.setTextSize(1,20);
        cal.setOnClickListener(v -> x.calc());
        cal.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout linearLayout3  = new LinearLayout(f);
        linearLayout3.setGravity(Gravity.CENTER);
        linearLayout3.setVisibility(View.INVISIBLE);
        linearLayout3.setTag("l3");

        SeekBar seekBar_c = new SeekBar(f);
        seekBar_c.setTag("sb2");
        seekBar_c.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        linearLayout3.addView(seekBar_c);

        calc.addView(linearLayout3);

        calc.addView(cal);

        linearLayout1.addView(calc);


        bot.addView(linearLayout1);


        c.addView(bot);





        return c;

    }

    public static class MySpannable extends ClickableSpan {

        private boolean isUnderline = true;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#1b76d3"));
        }

        @Override
        public void onClick(View widget) {


        }
    }
    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }
    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

}
