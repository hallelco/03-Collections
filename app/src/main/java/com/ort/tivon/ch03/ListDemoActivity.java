package com.ort.tivon.ch03;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * פרק 03, סעיף ArrayList (הקובץ ListDemo מהפרק).
 *
 * החלק העליון מריץ בדיוק את הקוד של ListDemo ומדפיס את הפלט למסך,
 * במקום ל-System.out שאין לו מקום באפליקציה.
 * החלק התחתון הוא ניסוי חי: אותן פעולות עצמן על רשימה שמוצגת ב-ListView.
 */
public class ListDemoActivity extends BaseActivity {

    private TextView tvLog, tvInfo;
    private EditText etValue;
    private ListView lvItems;

    // הרשימה של הניסוי החי — אותה ArrayList<String> מהדוגמה
    private ArrayList<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_demo);

        tvLog   = findViewById(R.id.tvLog);
        tvInfo  = findViewById(R.id.tvInfo);
        etValue = findViewById(R.id.etValue);
        lvItems = findViewById(R.id.lvItems);

        tvLog.setText(runListDemo());

        items.add("דני");
        items.add("רנא");
        items.add("ח'אלד");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(adapter);

        button(R.id.btnAdd, new Runnable() {
            @Override public void run() {
                String v = value();
                if (v == null) return;
                items.add(v);                       // מוסיף בסוף
                done("add(\"" + v + "\")");
            }
        });

        button(R.id.btnAddAt, new Runnable() {
            @Override public void run() {
                String v = value();
                if (v == null) return;
                items.add(0, v);                    // מוסיף בהתחלה ודוחף את השאר
                done("add(0, \"" + v + "\")");
            }
        });

        button(R.id.btnSet, new Runnable() {
            @Override public void run() {
                String v = value();
                if (v == null) return;
                if (items.isEmpty()) { toast("הרשימה ריקה"); return; }
                items.set(0, v);                    // מחליף את התוכן של תא 0
                done("set(0, \"" + v + "\")");
            }
        });

        button(R.id.btnFind, new Runnable() {
            @Override public void run() {
                String v = value();
                if (v == null) return;
                boolean found = items.contains(v);  // סורק אחד-אחד
                int where = items.indexOf(v);       // 1- אם לא נמצא
                toast("contains = " + found + "  |  indexOf = " + where);
            }
        });

        button(R.id.btnRemove, new Runnable() {
            @Override public void run() {
                String v = value();
                if (v == null) return;
                boolean ok = items.remove(v);       // לפי ערך — משתמש ב-equals
                done(ok ? "remove(\"" + v + "\")" : "הערך לא נמצא ברשימה");
            }
        });

        button(R.id.btnClear, new Runnable() {
            @Override public void run() {
                items.clear();                      // מרוקן הכול
                done("clear()");
            }
        });

        // לחיצה על שורה = מחיקה לפי מקום
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String removed = items.remove(position);   // לפי מקום
                done("remove(" + position + ") → " + removed);
            }
        });

        refresh();
    }

    /** מריץ את הקוד של ListDemo ומחזיר את מה שהיה מודפס למסך המסוף. */
    private String runListDemo() {
        StringBuilder out = new StringBuilder();

        // ===== 1. יצירה =====
        ArrayList<String> names = new ArrayList<>();

        // ===== 2. הוספה =====
        names.add("דני");              // מוסיף בסוף
        names.add("רנא");
        names.add("ח'אלד");
        names.add(1, "מיכל");          // מוסיף במקום 1 ודוחף את השאר ימינה
        out.append("אחרי ההוספות: ").append(names).append("\n");

        // ===== 3. חיפוש =====
        boolean found = names.contains("רנא");   // true  — סורק אחד-אחד
        int where    = names.indexOf("רנא");     // 2     — או 1- אם לא נמצא
        String third = names.get(2);             // רנא   — גישה ישירה לפי אינדקס
        out.append("contains=").append(found)
           .append("  indexOf=").append(where)
           .append("  get(2)=").append(third).append("\n");

        // ===== 4. עדכון =====
        names.set(0, "דניאל");         // מחליף את התוכן של תא 0
        out.append("אחרי set(0): ").append(names).append("\n");

        // ===== 5. מחיקה =====
        names.remove("מיכל");          // לפי ערך  — משתמש ב-equals
        names.remove(0);               // לפי מקום — מוחק את דניאל
        out.append("אחרי המחיקות: ").append(names).append("\n");

        // ===== 6. מידע =====
        out.append("size() = ").append(names.size()).append("\n");
        out.append("isEmpty() = ").append(names.isEmpty()).append("\n");
        names.clear();                        // מרוקן הכול
        out.append("אחרי clear(): isEmpty() = ").append(names.isEmpty());

        return out.toString();
    }

    // ===================== עזר =====================

    private void button(int id, final Runnable action) {
        Button b = findViewById(id);
        b.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { action.run(); }
        });
    }

    /** מחזיר את הערך שהוקלד, או null (עם הודעה) אם השדה ריק. */
    private String value() {
        String v = etValue.getText().toString().trim();
        if (v.isEmpty()) {
            toast("יש להקליד ערך");
            return null;
        }
        return v;
    }

    private void done(String what) {
        adapter.notifyDataSetChanged();   // ★ בלי זה המסך לא יזוז
        refresh();
        toast(what);
    }

    private void refresh() {
        tvInfo.setText("size() = " + items.size() + "   |   " + items);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
