package com.example.allergytrack2;

// מחלקה המייצגת משתמש באפליקציה.
public class User {

    // משתנים לשמירת פרטי המשתמש: מזהה משתמש, שם, אימייל ותמונת פרופיל.
    public String userId, name, email, profileImage;

    // בנאי ברירת מחדל הנדרש על ידי Firebase.
    // Firebase דורש קיום בנאי ריק לצורך שליפת נתונים מהמסד.
    public User() {}

    // בנאי מותאם אישית לצורך יצירת אובייקט משתמש.
    // מקבל את מזהה המשתמש, השם והאימייל כפרמטרים.
    public User(String userId, String name, String email) {
        this.userId = userId; // אתחול מזהה המשתמש.
        this.name = name;     // אתחול שם המשתמש.
        this.email = email;   // אתחול כתובת האימייל.
    }
}
