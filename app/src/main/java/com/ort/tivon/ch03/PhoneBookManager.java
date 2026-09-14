package com.ort.tivon.ch03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/**
 * פרק 03, סעיף "מחלקת ניהול הנתונים".
 *
 * מנהל ספר הטלפונים. מחזיק שני אוספים במקביל:
 *  ArrayList — שומר על הסדר, מאפשר מיון והצגה ב-ListView.
 *  HashMap   — נותן חיפוש מיידי לפי מספר טלפון, ב-O(1).
 * המחלקה אינה יודעת דבר על המסך — וזה בכוונה.
 */
public class PhoneBookManager {

    // הרשימה שומרת על הסדר ומאפשרת מיון והצגה ב-ListView
    private ArrayList<Contact> contacts;

    // המפה נותנת חיפוש מיידי לפי מספר טלפון
    private HashMap<String, Contact> byPhone;

    public PhoneBookManager() {
        contacts = new ArrayList<>();
        byPhone  = new HashMap<>();
    }

    // ================= הוספה =================
    public boolean add(Contact c) {
        if (c == null) return false;
        if (byPhone.containsKey(c.getPhone())) {   // בדיקת כפילות ב-O(1)
            return false;
        }
        contacts.add(c);                    // O(1)
        byPhone.put(c.getPhone(), c);       // O(1)
        return true;
    }

    // ================= חיפוש =================
    public Contact findByPhone(String phone) {
        return byPhone.get(phone);          // O(1) — או null אם אינו קיים
    }

    public ArrayList<Contact> searchByName(String part) {
        ArrayList<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {        // O(n) — אין דרך אחרת בחיפוש חלקי
            if (c.getName().contains(part)) {
                result.add(c);
            }
        }
        return result;
    }

    // ================= עדכון =================
    public boolean updateName(String phone, String newName) {
        Contact c = byPhone.get(phone);
        if (c == null) return false;
        c.setName(newName);   // אותו עצם נמצא גם ברשימה, ולכן מתעדכן בשניהם
        return true;
    }

    // ================= מחיקה =================
    public boolean remove(String phone) {
        Contact removed = byPhone.remove(phone);   // מחזיר את הערך שהוסר
        if (removed == null) return false;

        Iterator<Contact> it = contacts.iterator();
        while (it.hasNext()) {
            if (it.next().getPhone().equals(phone)) {
                it.remove();     // המחיקה הבטוחה תוך כדי מעבר
                break;
            }
        }
        return true;
    }

    // ================= מיון =================
    public void sortByName() {
        Collections.sort(contacts);        // לפי compareTo של Contact
    }

    public void sortByPhone() {
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact a, Contact b) {
                return a.getPhone().compareTo(b.getPhone());
            }
        });
    }

    // ================= גישה =================
    public ArrayList<Contact> getAll() { return contacts; }

    public int size() { return contacts.size(); }
}
