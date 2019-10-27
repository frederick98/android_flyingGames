package com.example.tugasbesar_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,FragmentListener {
    protected Bitmap mBitmap;
    protected Canvas mCanvas;
    protected ImageView ivCanvas;
    protected Paint strokePaint;
    protected Button btn_start;
    protected FloatingActionButton fab_pause;
    protected FloatingActionButton fab_menu;
    protected TextView tv_playerName;
    protected TextView tv_score;

    private ViewGroup mainLayout;
    private int xDelta;
    private int yDelta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ivCanvas = findViewById(R.id.iv_canvas);
        this.btn_start = findViewById(R.id.btn_start);
        this.fab_pause = findViewById(R.id.fab_pause);
        this.fab_menu = findViewById(R.id.fab_menu);
        this.tv_score = findViewById(R.id.tv_score);
        this.tv_playerName = findViewById(R.id.tv_playerName);

        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        this.ivCanvas.setOnTouchListener(onTouchListener());

        this.btn_start.setOnClickListener(this);
        this.fab_pause.setOnClickListener(this);
        this.fab_menu.setOnClickListener(this);

    }


    public void initiateCanvas() {
        // 1. Create Bitmap
        this.mBitmap = Bitmap.createBitmap(this.ivCanvas.getWidth(), this.ivCanvas.getHeight(), Bitmap.Config.ARGB_8888);

        // 2. Associate the bitmap to the ImageView.
        this.ivCanvas.setImageBitmap(mBitmap);

        // 3. Create a Canvas with the bitmap.
        this.mCanvas = new Canvas(mBitmap);
        this.resetCanvas();

        // new paint for stroke + style (Paint.Style.STROKE)
        this.strokePaint = new Paint();
        this.strokePaint.setColor(Color.BLUE);

        //create pesawat
        Pesawat p = new Pesawat(100, 100, 100);
        p.drawTriangle(this.mCanvas, this.strokePaint);
        p.setOnTouchListener(onTouchListener());
        p.getOnTouchListener();


        //resetCanvas
        this.ivCanvas.invalidate();
    }

    public void resetCanvas() {
        // 4. Draw canvas background
        // Fill the entire canvas with solid color.
        int mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
        mCanvas.drawColor(mColorBackground);

        // 5. force draw
        this.ivCanvas.invalidate();
    }

    @Override
    public void onClick(View view) {
       if(btn_start.getId() == view.getId()) {
           this.initiateCanvas();
       }
       else if(view.getId() == this.fab_pause.getId()){
           //jika ic_pause >> pause game
           FragmentTransaction ft = getFragmentManager().beginTransaction();
           PauseFragment pauseDialogFragment = new PauseFragment();
           //pauseDialogFragment.show(ft,this.tv_score.getText().toString()); <<Ini bingung salah dimana
       }
       else if(view.getId() == this.fab_menu.getId()){
           //jika ic_menu >> tampilkan menu
           FragmentTransaction ft = getFragmentManager().beginTransaction();
           MenuFragment menuDialogFragment = new MenuFragment();
           //menuDialogFragment.show(ft,this.tv_playerName.getText().toString()); <<Ini bingung salah dimana
       }
    }

    private View.OnTouchListener onTouchListener(){
       return new View.OnTouchListener() {
           @SuppressLint("ClickableViewAccessibility")
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               final int x = (int) motionEvent.getRawX();
               final int y = (int) motionEvent.getRawY();

               switch(motionEvent.getAction() & MotionEvent.ACTION_MASK){
                   case MotionEvent.ACTION_DOWN:
                       LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams)
                               view.getLayoutParams();

                       xDelta = x - lParams.leftMargin;
                       yDelta = y - lParams.topMargin;
                       break;

                   case MotionEvent.ACTION_UP:
                       Toast.makeText(MainActivity.this,
                               "thanks for new location!", Toast.LENGTH_SHORT)
                               .show();
                       break;

                   case MotionEvent.ACTION_MOVE:
                       LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view
                               .getLayoutParams();
                       layoutParams.leftMargin = x - xDelta;
                       layoutParams.topMargin = y - yDelta;
                       layoutParams.rightMargin = 0;
                       layoutParams.bottomMargin = 0;
                       view.setLayoutParams(layoutParams);
                       break;
               }
               mainLayout.invalidate();
               return true;
           }
       };
    }

    public void drawMusuh(Musuh musuh)
    {
        this.resetCanvas();
        this.strokePaint = new Paint();
        this.strokePaint.setColor(Color.BLUE);

        this.mCanvas.drawCircle(musuh.getX(),musuh.getY(),musuh.getRadian() ,this.strokePaint);

        this.ivCanvas.invalidate();

    }

    @Override
    public void closeApp() {
        this.moveTaskToBack(true);
        this.finish();
    }



}
