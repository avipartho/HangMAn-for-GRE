package com.example.user.hangman;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    TextView textView, meaning, live, scoreboard;
    List<String> words = new ArrayList<>();
    List<String> meanings = new ArrayList<>();
    List<View> views = new ArrayList<>();
    int flag = -1;
    int lives = 5;
    int mean = 1;
    int score = 0;
    int high_score = 0;
    int word_count = 0;
    String b;
    String a;
    String aa = "";
    Button next;
    String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        msg = getIntent().getStringExtra("a");
        lives = Integer.parseInt(msg);
        String msg2 = getIntent().getStringExtra("b");
        if (msg2.equals("no")) mean = 0;
        String msg3 = getIntent().getStringExtra("c");

        BufferedReader reader = null;
        BufferedReader reader2 = null;
        try {
            if (msg3.equals("barron")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("wordlist.txt")));
                reader2 = new BufferedReader(new InputStreamReader(getAssets().open("meaning.txt")));
            }else if (msg3.equals("common")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("common_magoosh_words.txt")));
                reader2 = new BufferedReader(new InputStreamReader(getAssets().open("common_magoosh_meaning.txt")));
            }else if (msg3.equals("basic")){
                reader = new BufferedReader(new InputStreamReader(getAssets().open("basic_magoosh_words.txt")));
                reader2 = new BufferedReader(new InputStreamReader(getAssets().open("basic_magoosh_meaning.txt")));
            }else {
                reader = new BufferedReader(new InputStreamReader(getAssets().open("advanced_magoosh_words.txt")));
                reader2 = new BufferedReader(new InputStreamReader(getAssets().open("advanced_magoosh_meaning.txt")));
            }
            String readLine;

            while ((readLine = reader.readLine()) != null) {
                words.add(readLine);
            }
            while ((readLine = reader2.readLine()) != null) {
                meanings.add(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(reader2 != null){
                try {
                    reader2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long seed = new Random().nextLong();
        Collections.shuffle(words, new Random(seed));
        Collections.shuffle(meanings, new Random(seed));

//        startGame();
        a = words.get(word_count);
        for(int i=0;i<a.length();i++){
            aa += "*";
        }

        textView = (TextView)findViewById(R.id.textView);
        meaning = (TextView)findViewById(R.id.textView2);
        live = (TextView)findViewById(R.id.live);
        scoreboard = (TextView)findViewById(R.id.scoreboard);

        textView.setText(aa);
        meaning.setText("This word has "+a.length()+" characters.");
        live.setText(""+lives);
        scoreboard.setText("Score: 0");

    }

    public void onclick(View v){
        textView = (TextView)findViewById(R.id.textView);
        meaning = (TextView)findViewById(R.id.textView2);
        live = (TextView)findViewById(R.id.live);
        next = (Button)findViewById(R.id.next);
        scoreboard = (TextView)findViewById(R.id.scoreboard);

        b = getResources().getResourceEntryName(v.getId());
//        textView.setText(a);
        int index = a.indexOf(b);   //checking button character with word characters and putting the index
        if (lives >= 1) {
            while (index >= 0) {
                flag = 1;
                if (index != 0)
                    aa = aa.substring(0, index) + b + aa.substring(index + 1, aa.length());
                else aa = b + aa.substring(index + 1, aa.length());
                index = a.indexOf(b, index + 1);

            }
            textView.setText(aa);
            if (a.equals(aa)) {
                meaning.setText("Congrats!!! You got it right.");
                if (mean == 1) textView.setText( a + "\nMeaning: " + meanings.get(word_count));
                else textView.setText(a);
                score += 1;
                next.setVisibility(View.VISIBLE);
                scoreboard.setText("Score: "+score);
            } else if (flag == 1) {
                flag = -1;
                live.setText(""+lives);
            } else {
                lives--;
                live.setText(""+lives);
                if (lives == 0){
                    meaning.setTextColor(Color.RED);
                    meaning.setText("Sorry. Try again.");
                    if (mean == 1) textView.setText( a + "\nMeaning: " + meanings.get(word_count));
                    else textView.setText(a);
                    live.setText(""+lives);
                    next.setVisibility(View.VISIBLE);
                }
            }
        }
        v.setEnabled(false);
        views.add(v);
    }

    public void onClickRestart(View v){
        if (word_count == (words.size() - 1)) {    //checking whether the end of list is reached
            word_count = 0;
            Toast.makeText(getApplicationContext(),"You have reached the end of this wordlist!!! A total of "+words.size()+" words.",Toast.LENGTH_LONG).show();
            long seed = new Random().nextLong();
            Collections.shuffle(words, new Random(seed));
            Collections.shuffle(meanings, new Random(seed));
        }
        next = (Button)findViewById(R.id.next);
        lives = Integer.parseInt(msg);
        flag = -1;
        word_count += 1;

        a = words.get(word_count);
        aa = "";
        for(int i=0;i<a.length();i++){
            aa += "*";
        }

        textView = (TextView)findViewById(R.id.textView);
        meaning = (TextView)findViewById(R.id.textView2);
        live = (TextView)findViewById(R.id.live);

        for (int i = 0; i<views.size(); i++){
            views.get(i).setEnabled(true);
        }
        textView.setText(aa);
        meaning.setText("This word has "+a.length()+" characters.");
        live.setText(""+lives);
        meaning.setTextColor(Color.BLACK);
        next.setVisibility(View.GONE);

        SharedPreferences retrieve = getApplicationContext().getSharedPreferences("sc", 0);
        int hScore = retrieve.getInt("HighScore", 0);
        if (hScore<score) hScore = score;
        SharedPreferences hscore_save = getApplicationContext().getSharedPreferences("sc", 0);
        SharedPreferences.Editor editor = hscore_save.edit();
        editor.putInt("HighScore", hScore);

//      Apply the edits!
        editor.apply();
    }

//    @Override
//    public void onBackPressed() {
//    }
}
