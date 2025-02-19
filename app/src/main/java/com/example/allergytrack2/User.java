package com.example.allergytrack2;

// מחלקה המייצגת משתמש באפליקציה.
public class User {

    // משתנים לשמירת פרטי המשתמש
    public String userId;
    public String name;
    public String email;
    public String profileImage;

    // פרטי אלרגיה - רק משתנים במקום רשימות ומפות
    public String allergy1;
    public String allergy1Severity;
    public String allergy2;
    public String allergy2Severity;
    public String allergy3;
    public String allergy3Severity;

    // תרופה רלוונטית
    public String medication;

    // אנשי קשר לחירום (במקום רשימה)
    public String emergencyContact1;
    public String emergencyContact2;
    public String emergencyContact3;

    // 🔹 בנאי ברירת מחדל (נדרש על ידי Firebase)
    public User() {}

    // 🔹 בנאי מלא
    public User(String userId, String name, String email, String profileImage,
                String allergy1, String allergy1Severity,
                String allergy2, String allergy2Severity,
                String allergy3, String allergy3Severity,
                String medication,
                String emergencyContact1, String emergencyContact2, String emergencyContact3) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;

        this.allergy1 = allergy1;
        this.allergy1Severity = allergy1Severity;
        this.allergy2 = allergy2;
        this.allergy2Severity = allergy2Severity;
        this.allergy3 = allergy3;
        this.allergy3Severity = allergy3Severity;

        this.medication = medication;

        this.emergencyContact1 = emergencyContact1;
        this.emergencyContact2 = emergencyContact2;
        this.emergencyContact3 = emergencyContact3;
    }

    // 🔹 בנאי מקוצר (רק עם מידע בסיסי)
    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profileImage = "";

        this.allergy1 = "";
        this.allergy1Severity = "";
        this.allergy2 = "";
        this.allergy2Severity = "";
        this.allergy3 = "";
        this.allergy3Severity = "";

        this.medication = "";

        this.emergencyContact1 = "";
        this.emergencyContact2 = "";
        this.emergencyContact3 = "";
    }
}
