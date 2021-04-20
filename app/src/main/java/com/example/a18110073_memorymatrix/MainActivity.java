package com.example.a18110073_memorymatrix;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static int time = 3;
    GridView gvShow, gvResult;
    TextView mTime, tvTiles, tvScores, tvStatus;
    AdapterRender adapterResult, adapterShow;
    List<Boolean> results, shows;
    Button btnReset, btnExit;
    LinearLayout btnLayout;
    int tiles = 3, wins = 0, loses = 0, mScore = 0;
    private static final int SIZE = 64;
    private static final int nCOL = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        Init();

        gvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(time == 0) {
                    if (results.get(position)) { // Correct
                        shows.set(position, true);
                        adapterShow.notifyDataSetChanged();

                        if(results.equals(shows)) {
                            tvStatus.setText("You win!!");
                            wins++;
                            loses = 0;

                            mScore++;

                            if(wins == 3 && tiles < 10) {
                                tiles++;
                                wins = 0;
                            }

                            RestartGame();
                        }
                    }else {
                        tvStatus.setText("You lose!");
                        loses++;
                        wins = 0;
                        // Calculator point
                        if(mScore != 0) mScore--;


                        if (loses == 3 && tiles > 3) {
                            tiles--;
                            loses = 0;
                        }

                        btnLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartGame();
                tvStatus.setText("");
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    private  void mapID(){
        btnLayout = findViewById(R.id.control);
        gvShow = findViewById(R.id.myGridView);
        gvResult = findViewById(R.id.myGridViewResult);
        mTime = findViewById(R.id.mTime);
        tvTiles = findViewById(R.id.mLevel);
        tvScores = findViewById(R.id.mScore);
        tvStatus = findViewById(R.id.status);
        btnReset = findViewById(R.id.btnReset);
        btnExit = findViewById(R.id.btnExit);
    }
    private void Init() {
        mapID();
        results = new ArrayList<>();
        shows = new ArrayList<>();

        CreateRandomList();
        ChangedTextView();
        startGame();
    }

    private void startGame(){
        adapterResult = new AdapterRender(this, results);
        adapterShow = new AdapterRender(this, shows);
        gvShow.setAdapter(adapterShow);
        gvShow.setNumColumns(nCOL);
        gvResult.setAdapter(adapterResult);
        StartTimer();
    }
    private void RestartGame() {
        try {
            ChangedTextView();
            CreateRandomList();
            gvResult.setVisibility(View.VISIBLE);
            adapterShow.notifyDataSetChanged();
            adapterResult.notifyDataSetChanged();
            StartTimer();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
    }

    private void CreateRandomList() {
        Random random = new Random();
        results.clear();
        shows.clear();
        results.addAll(Collections.nCopies(SIZE, false));
        shows.addAll(results);
        gvResult.setNumColumns(nCOL);
        for (int i = 0; i < tiles; i++) {
            int ranNum = random.nextInt(SIZE);
            while (results.get(ranNum)) {
                ranNum = random.nextInt(SIZE);
            }
            results.set(ranNum, true);
        }
    }

    private void StartTimer() {
        Timer timer = new Timer();
        time = 3;
        mTime.setVisibility(View.VISIBLE);
        btnLayout.setVisibility(View.INVISIBLE);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (time == 0) {
                    timer.cancel();
                    timer.purge();
                    mTime.setVisibility(View.INVISIBLE);
                    gvResult.setVisibility(View.INVISIBLE);
                    tvStatus.setText("Playing...");
                } else {
                    time--;
                    mTime.setText(String.valueOf("Time : "+time));
                }
            }
        }, 1000, 1000);
    }

    private void ChangedTextView() {
        tvTiles.setText("Tiles: " + tiles);
        tvScores.setText("Score: " + mScore);
    }

}