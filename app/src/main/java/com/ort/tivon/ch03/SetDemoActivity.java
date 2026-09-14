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
import java.util.HashSet;

/**
 * פרק 03, סעיף HashSet (הקובץ SetDemo מהפרק).
 *
 * קבוצה אינה מרשה כפילויות ואינה שומרת על סדר ההוספה.
 * שימו לב לערך שמחזירה add: true אם נוסף, false אם הערך כבר היה שם.
 */
public class SetDemoActivity extends BaseActivity {

    private TextView tvLog, tvInfo;
    private EditText etId;
    private ListView lvItems;

    // הקבוצה של הניסוי החי — ת"ז של תלמידים שנכחו
    private HashSet<String> present = new HashSet<>();
    private ArrayList<String> rows = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_demo);

        tvLog   = findViewById(R.id.tvLog);
        tvInfo  = findViewById(R.id.tvInfo);
        etId    = findViewById(R.id.etId);
        lvItems = findViewById(R.id.lvItems);

        tvLog.setText(runSetDemo());

        present.add("312456789");
        present.add("204567123");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rows);
        lvItems.setAdapter(adapter);

        button(R.id.btnAdd, new Runnable() {
            @Override public void run() {
                String id = id();
                if (id == null) return;
                boolean added = present.add(id);   // false אם הערך כבר קיים
                refresh();
                toast("add החזיר " + added + (added ? " — נוסף" : " — כבר קיים"));
            }
        });

        button(R.id.btnContains, new Runnable() {
            @Override public void run() {
                String id = id();
                if (id == null) return;
                toast("contains = " + present.contains(id) + "  (חיפוש ב-O(1))");
            }
        });

        button(R.id.btnRemove, new Runnable() {
            @Override public void run() {
                String id = id();
                if (id == null) return;
                boolean ok = present.remove(id);
                refresh();
                toast(ok ? "הוסר" : "הערך לא היה בקבוצה");
            }
        });

        refresh();
    }

    /** מריץ את הקוד של SetDemo ומחזיר את מה שהיה מודפס למסך המסוף. */
    private String runSetDemo() {
        StringBuilder out = new StringBuilder();

        HashSet<String> present = new HashSet<>();   // ת"ז של תלמידים שנכחו

        // ===== הוספה =====
        out.append("add(312456789) = ").append(present.add("312456789")).append("   — נוסף\n");
        out.append("add(312456789) = ").append(present.add("312456789")).append("   — כבר קיים!\n");
        present.add("204567123");
        present.add("318889000");

        // ===== חיפוש =====
        out.append("contains(204567123) = ").append(present.contains("204567123")).append("\n");
        out.append("size() = ").append(present.size()).append("\n");

        // ===== מחיקה =====
        present.remove("318889000");

        // ===== מעבר =====
        out.append("מעבר על הקבוצה (הסדר אינו סדר ההוספה!):\n");
        for (String id : present) {
            out.append("  ").append(id).append("\n");
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

    private String id() {
        String v = etId.getText().toString().trim();
        if (v.isEmpty()) {
            toast("יש להקליד מספר תעודת זהות");
            return null;
        }
        return v;
    }

    private void refresh() {
        rows.clear();
        for (String id : present) {   // סדר המעבר נקבע על ידי ה-hash, לא על ידנו
            rows.add(id);
        }
        adapter.notifyDataSetChanged();
        tvInfo.setText("size() = " + present.size());
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
