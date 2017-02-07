# SeniorProject
Program to take .csv predictions eliminate simular, vote and produce submission for competition

Main.java makes a 2d ArrayList that allows it to take in as many different predictions that are in the Results.csv file.
It then creates an object called Process results named "process"
Then it reads the file in Results.csv and adds the predictions to arrayLists.
Then it creates an object called CheckSim which checks for simularity.
ArrayLists is then passed to make collums where is then compares collumns into an arraylist rather then Tranversing down 
          calling a different ArrayLIst position. For example
          
          |a b c d|
          |a b c d|
          |a b c d|
          |a b c d|
          
          Rather than checking a against b by doing arrayList[a][1] == arraylist[a][1] incrementing through a which would cause paging and different problems. Make collumns makes it look like this.
          
          |a a a a|
          |b b b b|
          |c c c c|
          |d d d d|
          
          Which allow us to do arrayList[0][a] == arrayList[1][a]
          
          Then after make collumns and those that are not simular then sim.process then swaps it back to format below to allow for voting by process.getPredictions 
          
          |a b c d|
          |a b c d|
          |a b c d|
          |a b c d|
          
          After getPredictions, creatSubmission then writes .csv file to be submitted.
