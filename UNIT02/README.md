UNIT 02 HOME WORK
=================

Welcome to Unit 02 home work page!

All tasks represent by small class App with main method.
Each App class located into package named "my.epam.unit02.task" + number;

This links will help you to navigate threw the code.
Look to the comments for aditional information.

Task01 [(source)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT02/src/main/java/my/epam/stationery/model/Pen.java) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT02/src/test/java/my/epam/stationery/model/PenTest.java) [app](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT02/src/main/java/my/epam/unit02/task01/App.java)
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Simple pen class, represents the instance of stationery writer.
Pen class contains some default pens (black, blue, red).

    Pen pen = Pen.DEFAULT_BLACK_PEN;

Its a part of stationery manager application model.

Pen class, like other model classes, contains inner entity class.
Inner entity class used by [StringParser]() for building new unparsed objects.

You can use Pen types constants to specify type of pen also.

     Pen penRed = new Pen(Color.RED, Color.RED, "Parker", Pen.ROLLER_BALL_PEN_TYPE, "RedPen");

Task02 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT02/src/main/java/my/epam/stationery) [(test)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT02/src/test/java/my/epam/stationery) [app](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT02/src/main/java/my/epam/unit02/task02/App.java)
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Application that allow to manage stationery for employees.

The most powerfull application of the unit02 package.

Features:
- stores serialised data into simple text file;
- use original [StringParser]() for serailization/deserialization;
- supports adding new [Stationery]() childs;
- easy to switch [dao]() to DB or another data source.

Task03 [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT02/src/main/java/my/epam/unit02/task03/App.java)
---------------
Build newbie set using Stationer, Pen, Pencil classes.
The newbie set represent by Stationery[], and contains:
- two pens (blue, red);
- one pencil;
- one clip.

To get newbie set just:

    Stationery[] newbieSet = my.epam.unit02.task03.App.buildNewbieSet();

Task04 [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT02/src/main/java/my/epam/unit02/task04/App.java)
----------------------------------------
Sort newbie set from [Task03]() by different orders:
- by Stationery price;
- by Stationery label;
- by price and label.

Task05 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT02/src/main/java/my/epam/unit02/task05) [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT02/src/main/java/my/epam/unit02/task05/App.java)
----------------------------------------
Application allows to set marks for students for different disciplines.
The case is that marks for disciplines
could be different types (Long, Integer, String, ...).

[Student]() class have method setMark, its allow to set mark for discipline
only if mark type is correct.

Example:

    someStudent.setMark(Discipline.PHYSICS, 9.5D);

Discipline.PHYSICS requires marks as double value, in this case
if we change "9.5D" to "9.5F" we will get compilation error.

Task06 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT02/src/main/java/my/epam/unit02/task06) [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT02/src/main/java/my/epam/unit02/task06/App.java)
----------------------------------------
Is simple [Submarine]() class whith inner Engine class.
Submarine would not go without engine installed.

Task07 [(source)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT02/src/main/java/my/epam/unit02/task07/Unit02.java)
----------------------------------------
Is simple annotation for classes, witch can be readed
during compilation.
[Submarine]() class is annotated by this annotation.






