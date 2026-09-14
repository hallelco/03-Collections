package com.ort.tivon.ch03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * פרק 03, סעיף "מחלקה משלנו בתוך אוסף" (הקובץ Contact).
 *
 * המסך מריץ את המחלקה Contact בתוך שלושה אוספים גנריים —
 * ArrayList&lt;Contact&gt;, HashSet&lt;Contact&gt; ו-HashMap&lt;String, Contact&gt; —
 * ומראה מה עושות בפועל toString, equals, hashCode ו-compareTo.
 * למטה אפשר להשוות שני אנשי קשר שאתם בונים בעצמכם.
 */
public class ContactDemoActivity extends BaseActivity {

    private TextView tvLog, tvResult;
    private EditText etName1, etPhone1, etName2, etPhone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_demo);

        tvLog    = findViewById(R.id.tvLog);
        tvResult = findViewById(R.id.tvResult);
        etName1  = findViewById(R.id.etName1);
        etPhone1 = findViewById(R.id.etPhone1);
        etName2  = findViewById(R.id.etName2);
        etPhone2 = findViewById(R.id.etPhone2);

        tvLog.setText(runContactDemo());

        Button btnCompare = findViewById(R.id.btnCompare);
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { compare(); }
        });
    }

    /** מריץ את Contact בתוך אוספים ומחזיר את הפלט. */
    private String runContactDemo() {
        StringBuilder out = new StringBuilder();

        // ===== 1. toString — מה שהמשתמש רואה ב-ListView =====
        Contact dani = new Contact("דני", "050-1112223");
        out.append("toString():  ").append(dani).append("\n\n");

        // ===== 2. הסוגריים המשולשים = גנריות =====
        // ArrayList<Contact> מבטיח שרק Contact ייכנס פנימה, ושלא נצטרך המרה בקריאה
        ArrayList<Contact> list = new ArrayList<>();
        list.add(new Contact("רנא", "054-9876543"));
        list.add(dani);
        list.add(new Contact("ח'אלד", "052-4445556"));
        out.append("ArrayList<Contact> לפני מיון:\n").append(list).append("\n\n");

        // ===== 3. compareTo — הסדר הטבעי, לפי שם =====
        Collections.sort(list);          // משתמש ב-compareTo של Contact
        out.append("אחרי Collections.sort (לפי compareTo):\n").append(list).append("\n\n");

        Contact a = new Contact("אורי", "050-0000001");
        Contact b = new Contact("תמר",  "050-0000002");
        out.append("compareTo: אורי מול תמר = ").append(a.compareTo(b))
           .append("   (שלילי = בא לפני)\n\n");

        // ===== 4. equals + hashCode — זהות לפי מספר טלפון =====
        Contact dani2 = new Contact("דניאל", "050-1112223");   // שם אחר, אותו טלפון
        out.append("equals: דני מול דניאל (אותו טלפון) = ").append(dani.equals(dani2)).append("\n");
        out.append("hashCode זהה? ").append(dani.hashCode() == dani2.hashCode()).append("\n\n");

        // HashSet משתמש ב-equals וב-hashCode כדי למנוע כפילות
        HashSet<Contact> set = new HashSet<>();
        out.append("HashSet.add(דני)   = ").append(set.add(dani)).append("\n");
        out.append("HashSet.add(דניאל) = ").append(set.add(dani2))
           .append("   ← אותו טלפון, לכן לא נוסף\n");
        out.append("size() = ").append(set.size()).append("\n\n");

        // ===== 5. HashMap עם עצם שלם כערך =====
        HashMap<String, Contact> byPhone = new HashMap<>();
        for (Contact c : list) {
            byPhone.put(c.getPhone(), c);
        }
        out.append("HashMap<String, Contact>.get(\"054-9876543\") = ")
           .append(byPhone.get("054-9876543"));

        return out.toString();
    }

    /** משווה שני אנשי קשר שהמשתמש הקליד. */
    private void compare() {
        String n1 = etName1.getText().toString().trim();
        String p1 = etPhone1.getText().toString().trim();
        String n2 = etName2.getText().toString().trim();
        String p2 = etPhone2.getText().toString().trim();

        if (n1.isEmpty() || p1.isEmpty() || n2.isEmpty() || p2.isEmpty()) {
            Toast.makeText(this, "יש למלא שם וטלפון לשני אנשי הקשר", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact c1 = new Contact(n1, p1);
        Contact c2 = new Contact(n2, p2);

        int cmp = c1.compareTo(c2);
        String order;
        if (cmp < 0)      order = c1.getName() + " בא לפני " + c2.getName();
        else if (cmp > 0) order = c1.getName() + " בא אחרי " + c2.getName();
        else              order = "אותו שם בדיוק";

        tvResult.setText(
                "toString: " + c1 + "   |   " + c2 + "\n" +
                "equals (לפי טלפון) = " + c1.equals(c2) + "\n" +
                "hashCode זהה? " + (c1.hashCode() == c2.hashCode()) + "\n" +
                "compareTo = " + cmp + "   → " + order);
    }
}
