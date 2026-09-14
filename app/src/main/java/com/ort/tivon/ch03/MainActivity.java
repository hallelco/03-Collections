package com.ort.tivon.ch03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * פרק 03 — אוספים (ArrayList, HashMap, HashSet, גנריות ומיון).
 *
 * זהו מסך התפריט של הפרק: כל כפתור פותח מסך שמריץ דוגמה אחת מהפרק.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle("תפריט הפרק — בחרו דוגמה");
        }

        open(R.id.btnList,    ListDemoActivity.class);
        open(R.id.btnMap,     MapDemoActivity.class);
        open(R.id.btnSet,     SetDemoActivity.class);
        open(R.id.btnContact, ContactDemoActivity.class);
        open(R.id.btnSort,    SortDemoActivity.class);
        open(R.id.btnPhone,   PhoneBookActivity.class);
    }

    /** מחבר כפתור למסך: לחיצה פותחת את ה-Activity המבוקשת. */
    private void open(int buttonId, final Class<?> target) {
        Button b = findViewById(buttonId);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, target));
            }
        });
    }
}
