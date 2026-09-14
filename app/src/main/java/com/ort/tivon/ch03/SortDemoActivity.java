package com.ort.tivon.ch03;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * פרק 03, סעיף המיון (הפעולות sortByName ו-sortByPhone של PhoneBookManager).
 *
 * שתי דרכים למיין את אותה רשימה:
 *  sortByName  — Collections.sort(list) לפי compareTo של Contact (הסדר הטבעי).
 *  sortByPhone — Collections.sort(list, comparator) עם Comparator חיצוני.
 * הרשימה מוצגת ב-ListView, ולכן רואים את הסדר משתנה בלחיצה.
 */
public class SortDemoActivity extends BaseActivity {

    private TextView tvState, tvSearch;
    private EditText etSearch;
    private ListView lvContacts;

    private PhoneBookManager manager;
    private ArrayAdapter<Contact> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_demo);

        tvState    = findViewById(R.id.tvState);
        tvSearch   = findViewById(R.id.tvSearch);
        etSearch   = findViewById(R.id.etSearch);
        lvContacts = findViewById(R.id.lvContacts);

        manager = new PhoneBookManager();
        fillDemoData();

        // ★ המתאם מקבל הפניה לאותה רשימה שהמנהל מחזיק
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                manager.getAll());
        lvContacts.setAdapter(adapter);

        Button btnByName = findViewById(R.id.btnByName);
        btnByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.sortByName();               // Collections.sort לפי compareTo
                adapter.notifyDataSetChanged();     // ★ בלי זה המסך לא יזוז
                tvState.setText("ממוין לפי שם — compareTo של Contact");
            }
        });

        Button btnByPhone = findViewById(R.id.btnByPhone);
        btnByPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.sortByPhone();              // Collections.sort עם Comparator
                adapter.notifyDataSetChanged();
                tvState.setText("ממוין לפי טלפון — Comparator חיצוני");
            }
        });

        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.getAll().clear();
                fillDemoData();
                adapter.notifyDataSetChanged();
                tvState.setText("סדר ההוספה המקורי — לא ממוין");
            }
        });

        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { search(); }
        });

        tvState.setText("סדר ההוספה המקורי — לא ממוין");
    }

    /** ממלא את המנהל בנתוני דוגמה בסדר לא ממוין. */
    private void fillDemoData() {
        manager.add(new Contact("רנא", "054-9876543"));
        manager.add(new Contact("דני", "050-1112223"));
        manager.add(new Contact("ח'אלד", "052-4445556"));
        manager.add(new Contact("מיכל", "053-2223334"));
        manager.add(new Contact("אורי", "058-7778889"));
    }

    /** חיפוש חלקי לפי שם — searchByName של המנהל, שסורק את כל הרשימה ב-O(n). */
    private void search() {
        String part = etSearch.getText().toString().trim();
        if (part.isEmpty()) {
            Toast.makeText(this, "יש להקליד חלק משם", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<Contact> found = manager.searchByName(part);
        if (found.isEmpty()) {
            tvSearch.setText("searchByName(\"" + part + "\") → לא נמצאו תוצאות");
        } else {
            tvSearch.setText("searchByName(\"" + part + "\") → " + found);
        }
    }
}
