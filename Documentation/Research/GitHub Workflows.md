# GitHub Actions for Android

## Advantages :

* It allows user to define a workflow for apps and aids in automatically building, testing and deploying.
* For repositories already hosted on GitHub, it allows user to automate the process in one single platform, without having to rely upon any external tools. 
* Used to maintain Continuous Integration and Continuous Delivery, both relating to a much broader topic **DevOps** : set of practices with main goal to create and release software in smaller pieces much faster and more reliably. Having key concepts as Lean Manufacturing.

## Parts : 

**1. Branch workflows** 
Apart from one main git branch, basically the master. All new features and bug fixes must be compiled to a separate branche using pull requests. Which later could be merged into the master branch (after code review by other members).

**2. Enabling Workflow**
To enable workflows in GitHub repository user needs to sign up for it then add .github/workflows folder into the root of the repository. Multiple YAML files can be added which will hold definitions of all workflows (one file per workflow).

**3. config file** 
 .yml file is required specifying all the steps for the Continuous Integration and Continuous Delivery. The files are stored in the folder .github/workflows/file.yml.
 Indentation is to be focused on.
 
**4. Actions** 
These are specific steps that ease the task of automating Continuous Integration and Continuous Delivery. It is used to define workflow of the project. It can be published as open-source and are browsable via GitHub. Many of the functionalities resonating our requirements are likely already there, so it is worth taking a look to avoid reinventing the wheel. Also, it is possible to fork and modify existing actions or create our own ones.

**5. Setup** 
Action: git checkout is used in the repository , so as to proceed with the setup of Java for creating the environment.

**6. Building our artifacts**
Compiling the artifacts to be distributed or uploaded is an essential step. Using actions can call directly Gradle commands and generate the number of artifacts that are relevant( to be uploaded ). Action gradle-command-action is used to execute Gradle command.

**7. Testing**
Testing can be automated by actions as well. Gradle commands can be used to perform testing and analysis.
Espresso tests are the ones that run optimally fast can be used on GitHub Actions. There are several actions that allow us to trigger them.
