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

/**
 * פרק 03, הפרויקט המסכם: ספר טלפונים (במקור MainActivity של הפרק).
 *
 * המסך אינו מחזיק את הנתונים בעצמו — הוא מדבר רק עם PhoneBookManager,
 * שמחזיק ArrayList להצגה ולמיון ו-HashMap לחיפוש מיידי.
 */
public class PhoneBookActivity extends BaseActivity {

    private EditText etName, etPhone, etSearch;
    private TextView tvCount;
    private ListView lvContacts;

    private PhoneBookManager manager;
    private ArrayAdapter<Contact> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book);

        etName     = findViewById(R.id.etName);
        etPhone    = findViewById(R.id.etPhone);
        etSearch   = findViewById(R.id.etSearch);
        tvCount    = findViewById(R.id.tvCount);
        lvContacts = findViewById(R.id.lvContacts);

        manager = new PhoneBookManager();

        // ★ המתאם מקבל הפניה לאותה רשימה שהמנהל מחזיק
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                manager.getAll());
        lvContacts.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addContact(); }
        });

        Button btnFind = findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { findContact(); }
        });

        Button btnSort = findViewById(R.id.btnSort);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.sortByName();
                adapter.notifyDataSetChanged();
            }
        });

        // לחיצה ארוכה על שורה = מחיקה
        lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Contact c = manager.getAll().get(position);
                manager.remove(c.getPhone());
                adapter.notifyDataSetChanged();
                refreshCount();
                toast("נמחק: " + c.getName());
                return true;
            }
        });

        refreshCount();
    }

    // ================= הוספה =================
    private void addContact() {
        String name  = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            toast("יש למלא גם שם וגם מספר");
            return;
        }

        boolean ok = manager.add(new Contact(name, phone));
        if (!ok) {
            toast("המספר הזה כבר קיים בספר");
            return;
        }

        adapter.notifyDataSetChanged();   // ★ בלי זה המסך לא יזוז
        etName.setText("");
        etPhone.setText("");
        refreshCount();
    }

    // ================= חיפוש =================
    private void findContact() {
        String phone = etSearch.getText().toString().trim();
        Contact c = manager.findByPhone(phone);     // חיפוש ב-HashMap, O(1)

        if (c == null) {
            toast("לא נמצא איש קשר עם המספר הזה");
        } else {
            toast("נמצא: " + c.getName());
        }
    }

    private void refreshCount() {
        tvCount.setText("מספר אנשי הקשר: " + manager.size());
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
