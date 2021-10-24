package com.example.lotus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {
    public EditText sira_giris;
    public TextView sira_numarasi;
    public TextView siralar;
    String saver;
    String string;

    private static final long START_TIME_IN_MILLIS = 6000;
    private CountDownTimer RTimer ;
    private boolean TimerRunning;
    private long millsInFuture = START_TIME_IN_MILLIS;



    private RecyclerView recyclerView;
    List<String> itemList = new ArrayList<>();
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Shared preferences : in Progress


        //klavye kapatma
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


       //Icon kısmı
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.cubi);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        sira_numarasi = findViewById(R.id.sira_numarasi);
        siralar = findViewById(R.id.siralar);


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter = new CustomAdapter(itemList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.smoothScrollToPosition(itemList.size());


        sira_giris = findViewById(R.id.sira_giris);
        sira_giris.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((keyCode == KeyEvent.KEYCODE_DPAD_DOWN) ||
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Sirayazma();
                    manageBlinkEffect();
                    listtenSilme();
                    saver= string ;
                    Time();
                    return true;
                }
                if ((keyCode == KeyEvent.KEYCODE_ESCAPE)) {
                    sira_giris.setText("");

                }

            }
            return false;
        });



    }

    public void Time() {

        if (TimerRunning) {
        pauseTimer();
        resetTimer();
        startTimer();
    } else {
        startTimer();
    }

    }


    public void Sirayazma() {

        if ((sira_giris.getText().toString().matches(""))) {
            Toast.makeText(getApplicationContext(), "Sıra giriniz ", Toast.LENGTH_LONG).show();
            sira_numarasi.setText("!");
            sira_giris.setText("");

        }
        if (sira_giris.getText().toString().startsWith("-") && (!itemList.isEmpty())) {

            int updateIndex = 0;
            itemList.remove(updateIndex + 1);
            sira_numarasi.setText("");
            sira_giris.setText("");

            adapter.notifyItemRemoved(itemList.size() + 1);

        } else if ((!sira_giris.getText().toString().matches("")) && (!sira_giris.getText().toString().startsWith("-"))) {

            try {
                string = (sira_giris.getText().toString());
                itemList.add(saver);
                sira_numarasi.setText(string);
                sira_giris.setText("");
                adapter.notifyItemInserted(itemList.size() - 1);

            } catch (NumberFormatException e) {

            }
        }
    }

    @SuppressLint("WrongConstant")
    private void manageBlinkEffect() {

            if (sira_numarasi.getText().toString().matches("")){


                ObjectAnimator anim = ObjectAnimator.ofInt(sira_numarasi, "backgroundColor", Color.BLACK, Color.RED, Color.BLACK
                );
                anim.setDuration(1500);
                anim.setEvaluator(new ArgbEvaluator());
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.RELATIVE_TO_SELF);
                anim.start();


            }
 else {


                ObjectAnimator anim = ObjectAnimator.ofInt(sira_numarasi, "backgroundColor", Color.BLACK, Color.GREEN, Color.BLACK
                );
                anim.setDuration(1500);
                anim.setEvaluator(new ArgbEvaluator());
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(Animation.RELATIVE_TO_SELF);
                anim.start();
            }


        }

    public void listtenSilme(){

        if(itemList.size()==7){
            itemList.remove(0);
            adapter.notifyItemRemoved(0);

        }if (itemList.equals(" ")){
            itemList.remove(0);
            adapter.notifyItemRemoved(itemList.size() + 1);

        }if(saver==null){
            itemList.remove(0);
            adapter.notifyItemRemoved(itemList.size() + 1);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);


        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()== R.id.clean){
            itemList.clear();
            adapter.notifyDataSetChanged();
            sira_numarasi.setText("");
            sira_giris.setText("");

        }



        return super.onOptionsItemSelected(item);
    }

    private void startTimer() {
      RTimer = new CountDownTimer(millsInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millsInFuture = millisUntilFinished;

            }
            @Override
            public void onFinish() {
                TimerRunning = false;
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);

            }
        }.start();
      TimerRunning = true;

    }

    private void pauseTimer() {
        RTimer.cancel();
        TimerRunning = false;

    }

    private void resetTimer() {
        millsInFuture = START_TIME_IN_MILLIS;
    }
}


