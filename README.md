# 🧪 OrangeHRM Automation Framework

A **Selenium TestNG-based automation framework** for [OrangeHRM](https://opensource-demo.orangehrmlive.com/), built using the **Page Object Model (POM)** design pattern and **data-driven testing** with JSON files.

---

## 🚀 Features

✅ Built using **Java + Selenium + TestNG**  
✅ **Page Object Model (POM)** for better maintainability  
✅ **JSON-based test data** — no hardcoded values in tests  
✅ **Logging & reporting** with Extent Reports  
✅ **Cross-browser testing** (Chrome, Firefox, Edge)  
✅ Ready for **CI/CD integration** (Jenkins, GitHub Actions)

---

## 🗂️ Project Structure

```
Orangehrmlive/
├── pom.xml
├── src/
│ ├── main/
│ │ └── java/
│ │ ├── Base_Package/
│ │ │ ├── TestBase.java
│ │ │ └── JsonDataReader.java
│ │ ├── Pages/
│ │ │ ├── P0_Login.java
│ │ │ └── P1_Dashbord.java
│ │ └── Utilities/
│ │ └── ...
│ └── test/
│ ├── java/
│ │ └── Testing_Package/
│ │ ├── testDashbord.java
│ │ └── testLogin.java
│ └── resources/
│ └── TestData/
│ └── TestData.json
└── README.md
```

---

## ⚙️ Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **Google Chrome or Firefox** installed
- Internet connection (for OrangeHRM demo site)

---

## 🧰 Libraries Used
```
| Library | Purpose |
|----------|----------|
| Selenium WebDriver | Browser automation |
| TestNG | Test execution framework |
| Jackson | JSON data parsing |
| Extent Reports | HTML test reporting |
| SLF4J | Logging API |
```


## 📦 Installation & Setup

### 1️⃣ Clone the repository
```bash
git clone https://github.com/<your-username>/Orangehrmlive.git
cd Orangehrmlive
```

2️⃣ Install dependencies

```
mvn clean install
```

3️⃣ Ensure test data file exists

```
src/test/resources/TestData/TestData.json
```

Example content:

```{
  "login": {
    "username": "Admin",
    "password": "admin123"
  },
  "newUser": {
    "employeeName": "James Butler",
    "password": "xx123_3f@!wec"
  },
  "expectedUrl": "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index"
}
```
▶️ Running the Tests
```
mvn clean test
```

Run a specific test class
```
mvn -Dtest=Testing_Package.testDashbord test
```
📊 Reports & Logs

Extent HTML Reports:
```
test-output/ExtentReport/
```
Log Files:
```
logs/
```
👨‍💻 Author

A. Abdelsalam
💼 Automation Engineer | QA Specialist | Selenium Expert

📧 https://www.linkedin.com/in/ahmed-mostafa-ab7449101/


