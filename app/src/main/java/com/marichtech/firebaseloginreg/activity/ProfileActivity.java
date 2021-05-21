package com.marichtech.firebaseloginreg.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import com.marichtech.firebaseloginreg.R;

public class ProfileActivity extends AppCompatActivity {
    CardView NameCard, EmailCard, PhoneCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        InitializeToolbar();

        InitializeCards();

    }

    public void InitializeToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> finish());

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.nav_profile);
            ab.setDisplayHomeAsUpEnabled(true);
        }


    }

    public void InitializeCards(){

        NameCard = findViewById(R.id.nameCard);
        NameCard.setOnClickListener(v -> EditName());

        EmailCard = findViewById(R.id.emailCard);
        EmailCard.setOnClickListener(v -> EditEmail());

        PhoneCard = findViewById(R.id.phoneCard);
        PhoneCard.setOnClickListener(v -> EditPhone());

    }

    public void EditName(){
        Log.e("Test","Tedt");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("Test","Tedt");

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void EditEmail(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your Email");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void EditPhone(){
        Log.e("Test","Tedt");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your Phone");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("Test","Tedt");

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


}