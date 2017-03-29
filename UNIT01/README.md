UNIT 01 HOME WORK - Java Fundamentals
==================================

Welcome to Unit 01 home work page!

Task01 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT01/RunFromConsole)
----------------------------------------
The goal of this first basic task is to compile and run java [files](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT01/RunFromConsole/src/javase01/t01) from 
console.

Here is two java source files:
 
    /src/javase01/t01/logic/Logic.java
    /src/javase01/t01/main/Main.java
    
 
To compile thees two files we have to run:

    javac -cp ./src -d ./bin ./src/javase01/t01/main/Main.java
    
[Main](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/RunFromConsole/src/javase01/t01/main/Main.java) class uses [Logic](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/RunFromConsole/src/javase01/t01/logic/Logic.java) class, so we do not need to compile [Logic](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/RunFromConsole/src/javase01/t01/logic/Logic.java) class
separately.

To run main method from [Main](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/RunFromConsole/src/javase01/t01/main/Main.java) class just run:

    java -cp ./bin javase01.t01.main.Main
    
[Bat files](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT01/RunFromConsole) for given commands are available from this repository.
    

Task02 [(source)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task02/Task2.java) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/test/java/my/epam/unit01/task02/Task2Test.java)
------------------------------
One [method](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task02/Task2.java) that find the most less index of element 
for which condition is correct.

Condition:
![condition](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/unit01task02Condition.png)

Task03 [(source)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task03/Task3.java) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/test/java/my/epam/unit01/task03/Task3Test.java)
-----------------------------
One [method](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task03/Task3.java) that calculates all values of function 

    f(x) = tg(2*x) - 3
    
by given arguments array.


Task04 [(source)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task04/Task4.java) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/test/java/my/epam/unit01/task04/Task4Test.java)
----------------------------------------
One [method](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task04/Task4.java) that calculates

    max(a1 + a2n, a2 + a2n-1, ... , an + an-1);
    
by given array {a1, a2, ... , an}.


Task05 [(source)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task05/Task5.java) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/test/java/my/epam/unit01/task05/Task5Test.java)
------------------------------
One [method](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task05/Task5.java) that builds matrix by given size.

![matrix](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/unit01task05TheMatrix.png)


Task06 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT01/src/main/java/my/epam/unit01/task06) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/test/java/my/epam/unit01/task06/NotePadTest.java)
-----------------------------
Here we have two classes [NotePad](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task06/NotePad.java) and [Note](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task06/Note.java).

[NotePad](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task06/NotePad.java) is able to save, remove, modify and get [Notes](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task06/Note.java).

[Note](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/task06/Note.java) do nothing more then just contains the String.


Additional task [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT01/src/main/java/my/epam/unit01/collections) [(test)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT01/src/test/java/my/epam/unit01/collections)
-----------------------------
The additional task was to realize custom implementation of BitSet, that
can contains Integer values.

Here is implementation named [FastIntSet](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/collections/FastIntSet.java).

It named 'fast' cause it takes huge memory for storing huge values,
but works faster than over possible implementations which takes fewer
memory space for storing huge values.

[Custom implementation](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/collections/MyArrayList.java) of list also available in this [package](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT01/src/main/java/my/epam/unit01/collections).
 
[MyArrayList](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT01/src/main/java/my/epam/unit01/collections/MyArrayList.java) class contains merge sorts (upstream and downstream) and
binary search methods.



