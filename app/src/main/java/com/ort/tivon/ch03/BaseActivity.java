package com.ort.tivon.ch03;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * מחלקת־על לכל מסכי הדוגמה בפרק.
 *
 * למה היא קיימת: כל מסך דוגמה נפתח מתוך תפריט הפרק, וצריך שתמיד תהיה
 * דרך ברורה לחזור. במקום לחזור על אותו קוד בכל מסך — כותבים אותו פעם אחת כאן,
 * וכל מסך פשוט יורש ממנה במקום מ-AppCompatActivity.
 *
 * מה היא עושה:
 *   1. מוסיפה חץ חזרה בפס העליון (ActionBar).
 *   2. מוסיפה שורת הסבר קטנה מתחת לכותרת המסך.
 *   3. כשלוחצים על החץ — סוגרת את המסך וחוזרת לתפריט הפרק.
 *
 * זו בדיוק אותה תבנית ירושה שלמדתם בפרק 2: מה שמשותף עולה למחלקת־האב.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);   // החץ שמחזיר לתפריט
            bar.setSubtitle("\u2190 חזרה לתפריט הפרק");
        }
    }

    /**
     * נקרא כשלוחצים על פריט בפס העליון.
     * android.R.id.home הוא המזהה הקבוע של חץ החזרה.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();          // סוגר את המסך הזה — מתחתיו מחכה התפריט
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
