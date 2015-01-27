# Sample Android Code for my CS449 class

Notes to self
-------------

To add a project:

1. create the project with Android Studio.
2. add a .gitignore file to the project's app folder that looks something like:

/build

If you don't, I think the .gitignore file in this root directory will cause the .gradle file for the project not to be included under version control.

3. Open a git bash shell on the root folder

4. Issue:

   $ git add .
   $ git commit -m 'which project add/update'
   $ git push
