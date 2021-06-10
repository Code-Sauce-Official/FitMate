# How to start
Below you will find the process and workflow used to review and merge your changes.  

## Step 0 : Find an issue  🔍
- Take a look at the Existing Issues or create your **own** Issues!  
- Comment on the issue if you want to work on it
- Wait for the Issue to be assigned to you after which you can start working.  
- Note : Every change in this project must have an associated issue.

## Step 1 : Fork the Project 🍴 and clone it
- Fork this Repository. This will create a Local Copy of this Repository on your Github Profile. Keep a reference to the original project in `upstream` remote.  

- After this go to your terminal and clone this project on your desktop( git should be installed before - [link](https://git-scm.com/) ).
  
```  
$ git clone https://github.com/<username>/FitMate.git
$ cd <repo-name>  
$ git remote add upstream https://github.com/Code-Sauce-Official/FitMate.git
```  
- Update your forked repo before working.  
```  
$ git remote update  
$ git checkout <branch-name>  
$ git rebase upstream/<branch-name>  
```  
## Step 2 : Branch  🔖
Create a new branch. Use its name to identify the issue you are addressing.  

```  
# It will create a new branch with name Branch_Name and switch to that branch. 
$ git checkout -b branch_name  
```  

## Step 3 : Work on the issue assigned  📕
- Work on the issue(s) assigned to you.   
- Add all the files/folders needed.  
- After you've made changes or made your contribution to the project add changes to the branch you've just created by using terminal:  
```  
# To add all new files to branch Branch_Name  
$ git add .  

# To add only a few files to Branch_Name
$ git add <some files>
```

## Step 4 : Commit  
- To commit give a descriptive message for the convenience of reviewer by using terminal:  
```
# This message get associated with all files you have changed. 
$ git commit -m "message"  
```

### Commit message guidelines
- Each commit message consists of a header, a body and a footer. The header has a special format that includes a type, a scope and a subject:

The type is contained within the title and can be one of these types:
- feat: a new feature
- update: adding new changes
- fix: a bug fix
- docs: changes to documentation
- style: formatting, missing semi colons, etc; no code change
- refactor: refactoring production code
- test: adding tests, refactoring test; no production code change
- chore: updating build tasks, package manager configs, etc; no production code change
```
<type>(<scope>): <subject>

```
Any line of the commit message cannot be longer than 100 characters! This allows the message to be easier to read on GitHub as well as in various git tools.

### Example commit message
```
feat: added insights layout
```
A good rule of thumb for the commit message is to have a present tense verb, followed by whatever it is you're doing in as much detail as possible in 50 chars. Capitalize words correctly and follow general English.
### For more details, visit
- [Git commit message guidelines](http://karma-runner.github.io/0.13/dev/git-commit-msg.html)
- [Writing Good Commit Messages: A Practical Git Guide](https://www.freecodecamp.org/news/writing-good-commit-messages-a-practical-guide/)
 
## Step 5 : Work Remotely  
- Now you are ready to work in the remote repository.  
- When your work is ready and complies with the project conventions, upload your changes to your fork:  
  
```  
# To push your work to your remote repository.  
$ git push -u origin <branch_name>  
```  
  
## Step 6 : Pull Request  🎣
- Go to your repository in browser and click on compare and pull requests. Then add a title and description to your pull request that explains your contribution. 
- Compare
- Create pull request
- Voila! Your Pull Request has been submitted and will be reviewed by the moderators and merged.🥳  
  
## Need more help?🤔  
You can refer to the following articles on basics of Git and Github and also contact the Project Mentors, in case you are stuck:  
- [Forking a Repo](https://help.github.com/en/github/getting-started-with-github/fork-a-repo)  
- [Cloning a Repo](https://help.github.com/en/desktop/contributing-to-projects/creating-an-issue-or-pull-request)  
- [How to create a Pull Request](https://opensource.com/article/19/7/create-pull-request-github)  
- [Getting started with Git and GitHub](https://towardsdatascience.com/getting-started-with-git-and-github-6fcd0f2d4ac6)  
- [Learn GitHub from Scratch](https://lab.github.com/githubtraining/introduction-to-github)  
  
  
## Tip from us😇  
It always takes time to understand and learn. So, do not worry at all. We know **you have got this**!💪
