# Team 6106 README

## National University of Singapore - CP2106 (Orbital)
### Developers: Ethan Ong Song Yang & Neo Peng Rong
**Proposed Level of Achievement:** GEMINI  
**Year:** 2024  

---

## 📢 Foreword by Developers
Welcome to the **BeDamnUpz** project! We are excited to share our journey in building this fitness application. Coming from sports backgrounds, we wanted to apply our programming skills to develop a **simple and effective app for individuals aged 20 and above** to achieve their weight goals.

This README will walk you through our **motivation, features, project structure, and challenges**. We hope you find our work insightful and useful.

For any inquiries, feel free to contact:
- **Ethan Ong**: e1122257@u.nus.edu
- **Neo Peng Rong**: e1121487@u.nus.edu

---

## 🏆 Motivation
Singapore has seen a **concerning rise in obesity rates among adults (20+ years old)**. Our goal is to create an app that simplifies the journey toward a healthier lifestyle by integrating both **calorie tracking and exercise recommendations**.

---

## 🎯 Aim
Our application empowers users with **simple yet effective tools** to:
- Track daily calorie intake
- Monitor progress
- Follow personalized workout routines
- Maintain motivation through reminders

By combining **dietary tracking and fitness guidance**, we provide a **holistic approach to weight management**.

---

## 🔥 How Are We Different from Existing Platforms?
### **1️⃣ Cronometer**
- Strength: **Extensive dietary tracking** with macronutrient breakdown.
- Weakness: **No exercise features**.
- **Our edge**: We **combine food calorie tracking with exercise routines**, encouraging users to build fitness habits.

### **2️⃣ MyNetDiary**
- Strength: **Comprehensive diet guides & barcode scanning for grocery tracking**.
- Weakness: Focused **only on diet**, lacks **exercise integration**.
- **Our edge**: We provide **personalized exercise recommendations** alongside calorie tracking.

---

## 📌 User Stories
1️⃣ *"As someone aiming to lose 10kg, I need a user-friendly app that tracks my calories and suggests workouts tailored to my progress."*  
2️⃣ *"As a gym enthusiast focused on muscle gain, I want an app that combines calorie tracking with structured hypertrophy routines."*  

---

## 📅 Project Timeline
Milestone-based development following:
- **Milestone 1**: Initial setup, UI design, Firebase authentication
- **Milestone 2**: Core feature implementation
- **Milestone 3**: User testing & refinement

---

## 🏗️ Project Scope
| **Phase**      | **Features** |
|---------------|------------------------------------------------------|
| **Setup**     | User registration & login (Firebase Authentication) |
| **Core**      | Calorie Tracker, Recommended Intake Calculator, View Logging, Exercise Planner |
| **Extensions**| Notification Reminders (Work in Progress) |

---

## 🚀 Features
### **1️⃣ Registration & Login (Firebase Authentication)**
- Secure user authentication with **email & password**
- Google login integration (failed attempt at Facebook login)

### **2️⃣ Calorie Tracker**
- Log daily meals with **calorie entries stored in Firestore DB**
- View meal logs **by date** and edit entries **in real-time**

### **3️⃣ Calorie Calculator**
- Uses **BMR (Basal Metabolic Rate) formulas** to suggest daily intake
- Adjustments based on activity level & personal goals

### **4️⃣ Exercise Regime Planner**
- Users choose a **workout plan** tailored to their body type
- Programs are customizable via **Google Sheets**

### **5️⃣ Reminders (Extension - In Progress)**
- Daily push notifications for **meal tracking & workouts**
- Encountered **technical issues**; feature is under debugging

---

## 🛠️ Tech Stack
- **Android Studio** (Mobile development)
- **Firebase (Cloud Firestore, Authentication)** (Database & backend)
- **Canva** (Design & UI/UX prototyping)

---

## 🔍 Challenges Faced
### 1️⃣ Bottom Navigation Panel Issue
- Initial attempt to include **Dashboard, Logs, and Profile tabs** failed due to poor structuring.
- **Lesson learned**: UI layout should be **planned first** before coding features.

### 2️⃣ Facebook Login Failure
- Encountered **multiple bugs & API authentication issues**.
- **Decision**: Dropped it in favor of Google Authentication.

### 3️⃣ Notification Bugs
- Push notifications **failed to trigger**, despite permissions being granted.
- **Current Status**: Investigating possible Firebase messaging issues.

---

## ✅ Conclusion
Our **Orbital journey** has been filled with **learning, challenges, and growth**. We are proud of the **progress we've made as first-year developers** and believe our app has the potential to **help users build healthier habits**.

We hope to continue refining the application and integrating **better features** in future iterations.

Thank you for checking out our work! 🚀

---

## 📂 Codebase & Resources
🔗 **GitHub Repository:** [CalorieCounterRepo](https://github.com/PengRongNeo/CalorieCounterRepo)  
🔗 **Workout Programs:**  
- [Muscle Building](https://docs.google.com/spreadsheets/d/1gALyd6N8JYz9eCKEKBTdxwh5AYiparoNGPxfv2_weUU/edit?usp=sharing)  
- [Weight Loss](https://docs.google.com/spreadsheets/d/1uIIk8XLPmvQoZ-kkshdRmo8U0nBOR9EWFn88WOOHYs4/edit?usp=sharing)  
- [Physique Improvement](https://docs.google.com/spreadsheets/d/1bCNLBD1RYQUHaGmkuEuuQlkQ4SjXIvfcOoTVmyj3IAg/edit?usp=sharing)  

---


