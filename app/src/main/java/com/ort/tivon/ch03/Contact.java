package com.ort.tivon.ch03;

/**
 * פרק 03, סעיף "מחלקה משלנו בתוך אוסף".
 *
 * מחלקת איש קשר. היא מדגימה שלושה דברים שכל מחלקה שנכנסת לאוסף צריכה:
 *  1. toString  — ArrayAdapter מציג בדיוק את מה שהפעולה הזו מחזירה.
 *  2. equals + hashCode — בלעדיהם HashMap ו-HashSet לא יזהו כפילויות.
 *  3. compareTo (Comparable) — הסדר הטבעי, שלפיו Collections.sort ממיין.
 */
public class Contact implements Comparable<Contact> {

    private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.name  = name;
        this.phone = phone;
    }

    public String getName()  { return name; }
    public String getPhone() { return phone; }

    public void setName(String name)   { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }

    // ★ ArrayAdapter מציג בדיוק את מה שהפעולה הזו מחזירה
    @Override
    public String toString() {
        return name + " · " + phone;
    }

    // ★ שני אנשי קשר שווים אם מספר הטלפון זהה
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact other = (Contact) o;
        return phone.equals(other.phone);
    }

    // ★ חובה יחד עם equals — אחרת HashMap ו-HashSet יישברו
    @Override
    public int hashCode() {
        return phone.hashCode();
    }

    // ★ הסדר הטבעי: לפי שם
    @Override
    public int compareTo(Contact other) {
        return this.name.compareTo(other.name);
    }
}
