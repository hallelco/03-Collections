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
import java.util.HashMap;
import java.util.Map;

/**
 * פרק 03, סעיף HashMap (הקובץ MapDemo מהפרק).
 *
 * מפה שומרת זוגות של מפתח→ערך. המפתח ייחודי: put על מפתח קיים מעדכן ולא מוסיף.
 * החלק העליון מריץ את הקוד של MapDemo, החלק התחתון הוא ניסוי חי על מפה אמיתית.
 */
public class MapDemoActivity extends BaseActivity {

    private TextView tvLog, tvInfo;
    private EditText etKey, etValue;
    private ListView lvEntries;

    // המפה של הניסוי החי: מפתח = מספר טלפון, ערך = שם
    private HashMap<String, String> phoneBook = new HashMap<>();
    private ArrayList<String> rows = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);

        tvLog     = findViewById(R.id.tvLog);
        tvInfo    = findViewById(R.id.tvInfo);
        etKey     = findViewById(R.id.etKey);
        etValue   = findViewById(R.id.etValue);
        lvEntries = findViewById(R.id.lvEntries);

        tvLog.setText(runMapDemo());

        phoneBook.put("050-1112223", "דני");
        phoneBook.put("054-9876543", "רנא");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rows);
        lvEntries.setAdapter(adapter);

        button(R.id.btnPut, new Runnable() {
            @Override public void run() {
                String k = key();
                String v = etValue.getText().toString().trim();
                if (k == null) return;
                if (v.isEmpty()) { toast("יש להקליד ערך (שם)"); return; }
                boolean existed = phoneBook.containsKey(k);
                phoneBook.put(k, v);       // מפתח קיים = עדכון, לא רשומה שנייה
                refresh();
                toast(existed ? "put עדכן מפתח קיים" : "put הוסיף רשומה חדשה");
            }
        });

        button(R.id.btnGet, new Runnable() {
            @Override public void run() {
                String k = key();
                if (k == null) return;
                String v = phoneBook.get(k);                       // null אם לא קיים
                String safe = phoneBook.getOrDefault(k, "לא נמצא"); // מונע null
                toast("get = " + v + "   |   getOrDefault = " + safe);
            }
        });

        button(R.id.btnHas, new Runnable() {
            @Override public void run() {
                String k = key();
                if (k == null) return;
                toast("containsKey = " + phoneBook.containsKey(k));
            }
        });

        button(R.id.btnRemove, new Runnable() {
            @Override public void run() {
                String k = key();
                if (k == null) return;
                String removed = phoneBook.remove(k);   // מחזיר את הערך שהוסר, או null
                refresh();
                toast(removed == null ? "המפתח לא קיים" : "הוסר: " + removed);
            }
        });

        refresh();
    }

    /** מריץ את הקוד של MapDemo ומחזיר את מה שהיה מודפס למסך המסוף. */
    private String runMapDemo() {
        StringBuilder out = new StringBuilder();

        HashMap<String, String> phoneBook = new HashMap<>();

        // ===== 1. הוספה =====
        phoneBook.put("050-1112223", "דני");
        phoneBook.put("054-9876543", "רנא");
        phoneBook.put("052-4445556", "ח'אלד");

        // put על מפתח קיים = עדכון! לא נוצרת רשומה שנייה
        phoneBook.put("050-1112223", "דניאל");

        // ===== 2. חיפוש =====
        String name = phoneBook.get("054-9876543");   // רנא
        String none = phoneBook.get("03-1234567");    // null — המפתח לא קיים
        boolean has = phoneBook.containsKey("050-1112223");   // true

        // getOrDefault מונע null ומחזיר ערך חלופי
        String safe = phoneBook.getOrDefault("03-1234567", "לא נמצא");

        out.append("get(054...) = ").append(name).append("\n");
        out.append("get(03-1234567) = ").append(none).append("\n");
        out.append("containsKey(050...) = ").append(has).append("\n");
        out.append("getOrDefault = ").append(safe).append("\n");

        // ===== 3. עדכון =====
        phoneBook.put("054-9876543", "רנא ח'ורי");    // דורס את הערך הישן

        // ===== 4. מחיקה =====
        phoneBook.remove("052-4445556");   // מחזיר את הערך שהוסר, או null

        // ===== 5. מידע ומעבר =====
        out.append("size() = ").append(phoneBook.size()).append("\n\n");

        // מעבר על המפתחות בלבד
        out.append("מעבר על keySet:\n");
        for (String key : phoneBook.keySet()) {
            out.append("  ").append(key).append(" -> ").append(phoneBook.get(key)).append("\n");
        }

        // מעבר על הזוגות — הדרך היעילה יותר
        out.append("מעבר על entrySet:\n");
        for (Map.Entry<String, String> e : phoneBook.entrySet()) {
            out.append("  ").append(e.getKey()).append(" -> ").append(e.getValue()).append("\n");
        }

        // מעבר על הערכים בלבד
        out.append("מעבר על values:\n");
        for (String v : phoneBook.values()) {
            out.append("  ").append(v).append("\n");
        }

        return out.toString().trim();
    }

    // ===================== עזר =====================

    private void button(int id, final Runnable action) {
        Button b = findViewById(id);
        b.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { action.run(); }
        });
    }

    private String key() {
        String k = etKey.getText().toString().trim();
        if (k.isEmpty()) {
            toast("יש להקליד מפתח (מספר טלפון)");
            return null;
        }
        return k;
    }

    /** בונה מחדש את השורות שמוצגות — מעבר על entrySet של המפה. */
    private void refresh() {
        rows.clear();
        for (Map.Entry<String, String> e : phoneBook.entrySet()) {
            rows.add(e.getKey() + " -> " + e.getValue());
        }
        adapter.notifyDataSetChanged();
        tvInfo.setText("size() = " + phoneBook.size());
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
