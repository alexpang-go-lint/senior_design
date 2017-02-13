Readme

*********Program to take .csv predictions eliminate simular, vote and produce submission for competition.******************

Walkthrough
-----------

1.)Main.java makes a 2d ArrayList that allows it to take in as many different predictions that are in the Results.csv file.

2.)It then creates an object called Process results named "process"

3.)Then it reads the file in Results.csv and adds the predictions to arrayLists.

4.)Then it creates an object called CheckSim which checks for simularity.

5.)ArrayLists is then passed to make collums where is then compares collumns into an arraylist rather then Tranversing down 
          calling a different ArrayLIst position. For example a,b,c, and d are all different predictions.
          
          |a b c d|
          |a b c d|
          |a b c d|
          |a b c d|
          
          Rather than checking a against b by doing arrayList[x][0] == arraylist[x][1] incrementing through x which would cause deleting problems and other issues. Make collumns makes it look like this.
          
          |a a a a|
          |b b b b|
          |c c c c|
          |d d d d|
          
          Which allow us to do arrayList[0][x] == arrayList[1][x]
          
6.)Then after make collumns and those that are not simular then sim.process then swaps it back to format below to allow for voting by           process.getPredictions 
          
          |a b c d|
          |a b c d|
          |a b c d|
          |a b c d|

7.)After getPredictions, creatSubmission then writes .csv file to be submitted.

---------------------------------------------------------------------------------------------------------------------------------

