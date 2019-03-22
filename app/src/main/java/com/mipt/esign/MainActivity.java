package com.mipt.esign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import math.geom2d.Vector2D;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.idDrawBallView);
        textView = (TextView) findViewById(R.id.xy);

        final DrawBallView drawBallView = new DrawBallView(this);

        drawBallView.setMinimumWidth(500);
        drawBallView.setMinimumHeight(800);

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Curve curve = new Curve();
                    List<float[]> coords = Single.instance.getCoords();
                    for (int i = 0; i < coords.size(); i++) {
                        curve.dots.add(new Vector2D(coords.get(i)[0], coords.get(i)[1]));
                    }
                    curve.normalize();
                    CurveMeta meta = curve.getMeta();
                    Single.instance.flushCoords();
                }

                textView.setText("X: " + String.valueOf(motionEvent.getX()) + "; Y: " + String.valueOf(motionEvent.getY()));
                Single.instance.getCoords().add(new float[] {motionEvent.getX(), motionEvent.getY()});

                drawBallView.setCurrX(motionEvent.getX());
                drawBallView.setCurrY(motionEvent.getY());

                drawBallView.setBallColor(Color.BLUE);
                drawBallView.invalidate();
                return true;
            }
        };

        drawBallView.setOnTouchListener(onTouchListener);

        rootLayout.addView(drawBallView);
    }
}
