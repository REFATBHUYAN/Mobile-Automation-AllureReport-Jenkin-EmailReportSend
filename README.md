# Appium Automation Test Project

This project demonstrates how to automate mobile application testing for an Android app using **Appium**, **TestNG**, and **Allure** for reporting. The tests simulate a user login scenario in the "General Store" app, including selecting a country, entering a name, selecting a gender, and clicking the "Let's Shop" button.

## Table of Contents

- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running Tests Locally](#running-tests-locally)
- [Generating Allure Reports](#generating-allure-reports)
- [Integrating with Jenkins](#integrating-with-jenkins)
- [Project Structure](#project-structure)
- [License](#license)

## Technologies

- **Appium**: For mobile app test automation
- **TestNG**: For running and managing tests
- **Allure**: For generating test reports
- **Jenkins**: For continuous integration and automation
- **Maven**: For project dependency management

## Prerequisites

Before running the tests, ensure the following are set up on your machine:

1. **Appium** installed (download from [Appium website](http://appium.io/)).
2. **Android SDK** installed and configured.
3. **Java** 8 or higher installed.
4. **Maven** installed (for managing dependencies).
5. **Allure** CLI installed (for generating the report).

### Appium Server

Ensure the Appium server is running before executing the tests. You can start Appium using the command:

```bash
appium
```

### Android Emulator or Device

Make sure an Android emulator or physical device is connected and available for testing. If using an emulator, ensure that it is running.

## Setup

1. Clone this repository:

```bash
git clone https://github.com/REFATBHUYAN/Mobile-Automation-AllureReport-Jenkin-EmailReportSend.git
cd Mobile-Automation-AllureReport-Jenkin-EmailReportSend
```

2. Install the required dependencies using Maven:

```bash
mvn clean install
```

3. Download the APK for the app you're testing (in this case, **General Store APK**) and place it in the `src/test/resources` directory.

## Running Tests Locally

To run the tests locally, use the following Maven command:

```bash
mvn test
```

This will:

- Launch the Appium server (ensure it's running).
- Execute the test on the connected Android device/emulator.
- Generate an Allure test report.

## Generating Allure Reports

After running the tests, an Allure report will be generated in the `target/allure-results` directory. To generate the Allure report, follow these steps:

1. Install Allure CLI:

```bash
choco install allure  
```

2. Generate the report:

```bash
allure serve target/allure-results
```

This will start a web server with the Allure report. Open it in your browser to see detailed test results.

## Integrating with Jenkins

This project can be integrated with Jenkins to automate the test execution and reporting process. Here's a basic setup guide for Jenkins:

```groovy

pipeline {
    agent any
    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/REFATBHUYAN/Mobile-Automation-AllureReport-Jenkin-EmailReportSend.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Generate Allure Report') {
            steps {
                bat 'mvn allure:report'
            }
        }
        stage('Archive Artifacts') {
            steps {
                // Archive the allure report HTML file
                archiveArtifacts artifacts: 'target/site/allure-maven-plugin/allure-maven.html', allowEmptyArchive: true
            }
        }
    }
    post {
        always {
            // Send the email with the Allure report HTML file as an attachment
            emailext(
                to: 'refatbubt@gmail.com',
                subject: 'App Test Automation Report',
                body: 'Check the attached Allure report for app test results.',
                attachmentsPattern: 'target/site/allure-maven-plugin/allure-maven.html'  // Attach the allure-maven.html file
            )
        }
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}


```

## Project Structure

Here is an overview of the project structure:

```plaintext
.
├── src
│   └── test
│       ├── java
│       │   └── org
│       │       └── example
│       │           └── AppTest.java  # Main Appium test script
│       └── resources
│           └── General-Store.apk  # APK file for testing
├── pom.xml  # Maven configuration file
└── README.md  
```

- **AppTest.java**: Contains the test logic for interacting with the app.
- **General-Store.apk**: The Android app being tested.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

