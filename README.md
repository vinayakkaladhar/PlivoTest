# Project Title

Automated requirements as mentioned in the Assignment

## Getting Started

Tools used: Intellij as IDE
Browser: Chrome
Language: JAVA
framework : TestNG
Build tool: Maven
OS: MAC

### Prerequisites

Java 1.8+ version installed
Maven installed

## Running the tests

1. Import the pom.XML
2. Use commands: mvn clean followed by mvn install to install the dependencies
3. Invoke the testNG.xml from IDE or
  via terminal: mvn clean test -Dsurefire.suiteXmlFiles=/path/to/testng.xml

xml includes:
class: BirthdaySurprise
Methods: willTheSurpriseWork(), surpriseEndsHappily()

### Scenarios and exceptions handled

1.Cleartrip loads the site in two versions based on the network strength and time.
have handled both the versions, interacting and clicking on elements in the DOM
based on the version loaded.

2.Have extracted price of the flight during runtime and saving it in xlsx file, which
is later converted to html report using a third party library: com.gembox.spreadsheet,
thus making it dynamic.

3.Better try-catch mechanism to handle, in case if there are no flights displayed/available

4.Proper asserts and reporting used to provide a detailed context, can be witnessed in testNG Report - have checked in the same.

5.Have used method names: willTheSurpriseWork() - the scenario in which raj travels to surprise his wife and surpriseEndsHappily() - the scenario in which raj returns back with his wife after the surprise.

## Authors

Vinayak kaladhar

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
