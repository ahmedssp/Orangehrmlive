# 🚀 OrangeHRM Test Automation Framework

## 📌 Project Overview

This project is a **Hybrid Test Automation Framework** developed using:

* **Java**
* **Selenium WebDriver**
* **TestNG**
* **Maven**
* **Extent Reports**
* **REST Assured (API Testing)**
* **Jackson (JSON handling)**

The framework follows:

* ✅ Page Object Model (POM)
* ✅ Factory Design Pattern
* ✅ Listener Design Pattern
* ✅ Data-Driven Testing
* ✅ YAML-based Configuration Management

---

# 📂 Detailed Project Structure

```
Project
│
├── Reports/
│   └── (Generated Extent Reports & Screenshots)
│
├── src/
│   │
│   ├── main/
│   │   ├── java/
│   │   │   ├── Handler/
│   │   │   │   └── (Reusable actions & wrapper methods)
│   │   │   │
│   │   │   ├── Pages/
│   │   │   │   └── (Page Object classes - locators & methods)
│   │   │   │
│   │   │   └── org.example/
│   │   │       └── (Core logic / base models if used)
│   │   │
│   │   └── resources/
│   │       └── (Application level resources if required)
│   │
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java
│       │   │
│       │   ├── factory/
│       │   │   └── DriverFactory.java
│       │   │
│       │   ├── testing_package/
│       │   │   ├── TestClass.java
│       │   │   └── TestListener.java
│       │   │
│       │   └── utils/
│       │       ├── ConfigReader.java
│       │       ├── ExtentManager.java
│       │       ├── ReporterUtil.java
│       │       ├── ScreenshotUtil.java
│       │       ├── TestDataUtil.java
│       │       └── testing_methods.java
│       │
│       └── resources/
│           ├── Config.yaml
│           └── TestData.json
│
├── target/
│   └── (Auto-generated build files)
│
└── .gitignore
```

---

# 🧩 Framework Architecture Explanation

## 🔹 1. Handler Package (`src/main/java/Handler`)

Contains reusable wrapper methods such as:

* Click actions
* SendKeys
* Wait methods
* Common element interactions
* Custom Selenium utilities

👉 Purpose: Reduce code duplication and improve readability.

---

## 🔹 2. Pages Package (`src/main/java/Pages`)

Implements **Page Object Model (POM)**:

Each page class contains:

* WebElement locators
* Page methods (business logic)
* Page-specific validations

Example:

```
LoginPage.java
DashboardPage.java
RecruitmentPage.java
```

👉 Improves maintainability and scalability.

---

## 🔹 3. Base Package (`src/test/java/base`)

### BaseTest.java

Responsible for:

* Initializing WebDriver
* Loading configuration
* Setup (@BeforeMethod / @BeforeClass)
* Teardown (@AfterMethod / @AfterClass)
* Browser lifecycle management

---

## 🔹 4. Factory Package (`src/test/java/factory`)

### DriverFactory.java

Implements Factory Design Pattern:

* Creates WebDriver instance
* Supports multiple browsers
* Manages ThreadLocal driver (parallel execution ready)

---

## 🔹 5. Testing Package (`src/test/java/testing_package`)

Contains:

### 🧪 TestClass.java

* UI test cases

### 🔌 orangeHRMAddCandidateAPI.java

* API test cases using REST Assured

### 🎧 TestListener.java

* Implements TestNG Listeners
* Captures screenshots on failure
* Logs test results
* Integrates with Extent Reports

---

## 🔹 6. Utils Package (`src/test/java/utils`)

### ConfigReader.java

* Reads `Config.yaml`
* Manages environment variables

### ExtentManager.java

* Initializes Extent Report
* Manages report instance

### ReporterUtil.java

* Handles logging inside reports

### ScreenshotUtil.java

* Captures screenshots on failure
* Stores images inside Reports folder

### TestDataUtil.java

* Reads test data from `TestData.json`
* Supports data-driven testing

### testing_methods.java

* Common reusable test methods

---

## 🔹 7. Test Resources

### 📄 Config.yaml

Stores environment configuration:

```yaml
browser: chrome
baseUrl: https://opensource-demo.orangehrmlive.com
timeout: 10
headless: false
```

---

### 📄 TestData.json

Used for:

* Login credentials
* Candidate data
* Form inputs
* API payloads

Example:

```json
{
  "username": "Admin",
  "password": "admin123"
}
```

---

# ⚙️ Design Patterns Used

* Page Object Model (POM)
* Factory Pattern
* Singleton (ExtentManager)
* Listener Pattern
* Data-Driven Design

---

# 🧪 Testing Scope

### ✔ UI Automation

* Login functionality
* Candidate management
* Form validation
* Navigation flows

### ✔ API Automation

* Add Candidate API
* Response validation
* Status code verification
* JSON schema validation

---

# 📊 Reporting & Logging

* Extent Reports generated after execution
* Screenshots attached for failed tests
* Logs integrated with TestNG Listener

Report Location:

```
/Reports
```

---

# ▶️ How to Execute Tests

## 🔹 Run Using Maven

```bash
mvn clean test
```

## 🔹 Run Specific Test

```bash
mvn test -Dtest=TestClass
```

## 🔹 Run via IDE

* Right-click on `testng.xml`
* Click Run

---

# 🌍 Environment Support

* Chrome
* Edge
* Firefox (if configured)
* Headless execution support

---

# 🔐 Parallel Execution

DriverFactory supports:

* ThreadLocal WebDriver
* Parallel execution through TestNG XML

---

# 📦 Key Dependencies

* Selenium WebDriver
* TestNG
* REST Assured
* WebDriverManager
* ExtentReports
* Jackson
* Maven

---

# 🚀 Future Enhancements

* CI/CD integration (Jenkins / GitHub Actions)
* Docker execution
* Allure Reporting
* Cross-browser grid support
* Environment profiles (QA / UAT / Prod)

---

# 👨‍💻 Author

Ahmed Abdelsalame
Automation Test Engineer
Java | Selenium | API | TestNG

