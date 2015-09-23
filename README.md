# Sample Android Code for my CS449 class

<h3>Apps and their features:</h3>

CountMuchMore - event handlers, dialog boxes

CountMuchMoreFragment - Same as CountMuchMore but with fragments

CountMuchMoreMenuesAndMore - menues (toolbar and contextual action mode), starting a new activty, start screen icon

CountAloud - preferences and text-to-speech

CountPersistentStorage - screen rotation, app life cycle and persistent storage using Shared Preferences

GenericActivity - Activity with custom data and using internal assets (images)

<h3>If you want to download and run a program here:</h3>

1. click on "Download ZIP" (or better yet, clone the repository to your compture. If you clone you can pull changes and always have the latest projects on your computer)
2. Unzip the file
3. Copy the folder for the project you want to your Android Studio projects folder
4. Open the project in Android Studio

<h3>Notes to self</h3>

To add a project:

1. create the project with Android Studio.
2. add a .gitignore file to the project's app folder that looks something like:

/build

If you don't, I think the .gitignore file in this root directory will cause the .gradle file for the project not to be included under version control.

3. Open a git bash shell on the root folder

4. Issue:<br/>
   $ git add .<br/>
   $ git commit -m 'which project add/update'<br/>
   $ git push<br/>

5. To retrieve changes from from github.com:<br/>
   $ git pull
