UNIT 05 HOME WORK - Exceptions and Errors
=================

Welcome to Unit 05 home work page!

Task01 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT05/src/main/java/my/epam/unit05/task01) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/test/java/my/epam/unit05/task01/CustomExplorerTest.java) [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task01/App.java)
----------------------------------------
Java console application that allows to browse disc folders,
create and remove files and folders, append and read text files.

Application [configured](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/pom.xml) for building jar package, so it can
be runned from console.

Main classes of the application are:
 - [CustomExplorer](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task01/util/CustomExplorer.java) for browsing disc folders;
 - [ConsoleCommandProcessor](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task01/processor/ConsoleCommandProcessor.java) implementation of abstract [CommandProcessor](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task01/processor/CommandProcessor.java)
 for proceeding commands;
 - [AlreadyExistException](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task01/exceptions/AlreadyExistException.java) and [DoesNotExistException](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task01/exceptions/DoesNotExistException.java) to catch exception situations;
 - [TextFileAppender](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task01/util/TextFileAppender.java) for appending text to file.


 Available commands:
 - _'dir'_ - list current folder;
 - _'cd \<folder name>'_ - change folder;
 - _'exit'_ - exit;
 - _'mkdir \<folder name>'_ - make new dir;
 - _'rmdir \<folder name>'_ - remove dir;
 - _'mkfile \<file name>'_ - make new file;
 - _'rmfile \<file name>'_ - remove file;
 - _'append \<file name> \<text>'_ - append new text to existing file;
 - _'read \<file name>'_ - read text file;
 - _'help'_ - commands list.



Task02 [(source)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task02/PropertiesFileBundle.java) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/test/java/my/epam/unit05/task02/PropertiesFileReaderTest.java)
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
[PropertiesFileBundle](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task02/PropertiesFileBundle.java) class extends abstract java.util.ResourceBundle
class.

The main goal of [PropertiesFileBundle](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task02/PropertiesFileBundle.java) class is to catch exceptions
from ResourceBundle.

In [PropertiesFileBundle](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task02/PropertiesFileBundle.java) if there is no such properties file, or
no such property element no exception will be thrown and
empty result will be returned.

[PropertiesFileBundle](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task02/PropertiesFileBundle.java) reads properties file once on
creating, and stores properties to inner HashMap, just like
java.util.PropertyResourceBundle.



