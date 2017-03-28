UNIT 03 HOME WORK - Information handling
=================

Welcome to Unit 03 home work page!

Task01 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT03/src/main/java/my/epam/unit03/task01) [(test)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT03/src/test/java/my/epam/unit03/task01)
------------------------------
[CrazyLogger](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task01/CrazyLogger.java) class stores some messages, represented by strings.
[CrazyLogger](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task01/CrazyLogger.java) use [StringStorage](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task01/StringStorage.java) implementation to store messages.
So, it is easy to switch storage to DB or any file storage.

[StringBuilderStringStorage](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task01/StringBuilderStringStorage.java) is a simple implementation of [StringStorage](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task01/StringStorage.java)
used for tests.

Task02 [(source)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task02/Questionarium.java) [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task02/App.java)
-----------------------------
Simple Java Swing one [class](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task02/Questionarium.java) questions and answers application.
Uses [properties files](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT03/src/main/resources/my/epam/unit03/task02/properties) for storing questions and answers for
different locales.

Switch locales with combobox at the bottom panel.

Screenshot:

![screenshot](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/unit03task02screenshot.png)


Task03 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT03/src/main/java/my/epam/unit03/task03) [(test)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT03/src/test/java/my/epam/unit03/task03) [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task03/App.java)
----------------------------------------
Regular expressions example application.

Checks proper order of pictures links "(Рис. №)" inside html document
using [StringChecker](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task03/StringChecker.java) class.

Uses [ClauseSearcher](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT03/src/main/java/my/epam/unit03/task03/ClauseSearcher.java) to find all sentences, which contains pictures
links, and print them out to system console.
