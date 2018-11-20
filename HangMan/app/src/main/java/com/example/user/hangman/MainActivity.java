package com.example.user.hangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class MainActivity extends AppCompatActivity {

    String lives = "9";
    String meaning = "yes";
    String option = "barron";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.WHITE, R.drawable.menu,R.drawable.close)
            .addSubMenu(Color.WHITE, R.drawable.play)
            .addSubMenu(Color.WHITE, R.drawable.settings)
            .addSubMenu(Color.WHITE, R.drawable.info)
            .setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
                public void onMenuSelected(int i) {
                    if (i==0){
                        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                        intent.putExtra("a",lives);
                        intent.putExtra("b",meaning);
                        intent.putExtra("c",option);
                        startActivity(intent);
                    }else if (i == 1){
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        final View settingview = getLayoutInflater().inflate(R.layout.setting,null);
                        dialog.setTitle("Setting");

                        final Spinner spinner = settingview.findViewById(R.id.spinner);
                        final Spinner spinner2 = settingview.findViewById(R.id.spinner2);
                        final Switch switchButton = settingview.findViewById(R.id.switch_meaning);
                        final TextView switch_text = settingview.findViewById(R.id.switch_text);
//                        final TextView highscoremsg = settingview.findViewById(R.id.highscore);
//                        // Get from the SharedPreferences
//                        SharedPreferences retrieve = getApplicationContext().getSharedPreferences("sc", 0);
//                        int highScore = retrieve.getInt("HighScore", 0);
//                        highscoremsg.setText("High Score: "+highScore);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.level));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.wordlist));
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);

                        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                                if (bChecked) {
                                    switch_text.setText("Meaning enabled");
                                } else {
                                    switch_text.setText("Meaning disabled");
                                }
                            }
                        });

                        if (switchButton.isChecked()) {
                            switch_text.setText("Meaning enabled");
                        } else {
                            switch_text.setText("Meaning disabled");
                        }

                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (spinner.getSelectedItem().toString().equals("Easy"))lives = "9";
                                else if (spinner.getSelectedItem().toString().equals("Medium")) lives = "7";
                                else lives = "5";

                                if (spinner2.getSelectedItem().toString().equals("Barron HF 333"))option = "barron";
                                else if (spinner2.getSelectedItem().toString().equals("Magoosh Common HF")) option = "common";
                                else if (spinner2.getSelectedItem().toString().equals("Magoosh Basic")) option = "basic";
                                else option = "advanced";

                                if (switchButton.isChecked()) {
                                    switch_text.setText("Meaning enabled");
                                    meaning = "yes";
                                } else {
                                    switch_text.setText("Meaning disabled");
                                    meaning = "no";
                                }

                                dialogInterface.dismiss();
                            }
                        });

                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        dialog.setView(settingview);
                        AlertDialog alertDialog = dialog.create();
                        alertDialog.setCancelable(false);
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();

                        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        nbutton.setTextColor(Color.RED);
                        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setTextColor(Color.parseColor("#32CD32"));
                    }else{
                        AlertDialog.Builder dialogt = new AlertDialog.Builder(MainActivity.this);
                        View tutorialview = getLayoutInflater().inflate(R.layout.about,null);
                        dialogt.setTitle("About");
                        dialogt.setView(tutorialview);
                        AlertDialog alertDialogt = dialogt.create();
                        alertDialogt.show();

                        Button contact = tutorialview.findViewById(R.id.contact);
                        contact.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto","avipartho2@gmail.com", null));
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "About Hangman");
                                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                            }
                        });
                    }
                }
            });

    }
}
