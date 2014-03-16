##Usage

- Download the project, step into the root folder, then:
```bash
mvn package
cd target/
mv jira-selenium-1.0-SNAPSHOT-jar-with-dependencies.jar jira-selenium.jar
```
- Now, you can run Selenium scenario:
```bash
jira_license='PROVIDE JIRA LICENSE HERE!'
java -jar jira-selenium.jar \
  http://localhost:8080/jira \
  $jira_license
```


##Justification

Couldn't find any better way to automatically configure JIRA on first startup for quick testing. Tried
- `sql scripts` - but JIRA creates too much stuff on startup: tables, columns, indices, rows. You'll end up having a
whole JIRA backup applied to the database in order JIRA to be pre-configured after installation. But that's bad because you
usually want to test stuff on different versions of JIRA - and they may create different SQL structures in each version.
- `curl` - but it doesn't wait for elements to finish. Putting timeouts is useless, you never know how much time it will take the button to finish.
- `lynx` - it can record scenarios, but it can't work with some tricky HTML elements in JIRA startup pages

