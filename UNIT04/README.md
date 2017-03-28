UNIT 04 HOME WORK - Streams
=================

Welcome to Unit 04 home work page!

Task01 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT04/src/main/java/my/epam/unit04/task01) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/test/java/my/epam/unit04/task01/StringWordsCounterTest.java) [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task01/App.java)
----------------------------------------
Application calculates java keywords in java source file and write
results file.

 For calculation uses [JavaCodeKeyWordsCalculator](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/words_calculator/JavaCodeKeyWordsCalculator.java) which uses
 [ArrayFileReader](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/words_calculator/ArrayFileReader.java) and [ArrayFileWriter](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/words_calculator/ArrayFileWriter.java) implementations for
 input and output data and [StringWordsCounter](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/words_calculator/StringWordsCounter.java) for calculating
 keywords.

 [JavaCodeKeyWordsCalculator](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/words_calculator/JavaCodeKeyWordsCalculator.java) also contains
 all java keywords as array of String values.

In this task the case was to use only byte array input and output, so
as implementations of [ArrayFileReader](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/words_calculator/ArrayFileReader.java) and [ArrayFileWriter](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/words_calculator/ArrayFileWriter.java) used
 classes [ByteArrayFileReader](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task01/ByteArrayFileReader.java) and [ByteArrayFileWriter](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task01/ByteArrayFileWriter.java).


Task02 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT04/src/main/java/my/epam/unit04/task02) [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task02/App.java)
-----------------------------
The same as Task01 case. Difference is that in this case char array input
and output should be used.

So, in this task used [CharArrayFileReader](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task02/CharArrayFileReader.java) and [CharArrayFileWriter](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task02/CharArrayFileWriter.java) as
implementations of [ArrayFileReader](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/words_calculator/ArrayFileReader.java) and [ArrayFileWriter](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/words_calculator/ArrayFileWriter.java).

Task03 [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task03/App.java)
----------------
Very simple application that convert UTF-8 text file to UTF-16.
Application uses standart BufferedReader and BufferedWriter classes
 with specifying encoding by _'StandardCharsets.UTF_16'_ option.

Task04 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT04/src/main/java/my/epam/unit04/task04) [(test)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT04/src/test/java/my/epam/unit04/task04)
------------------------------
Solution of this task represented by [FilmList](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task04/collections/FilmsList.java) collection and
[FilmListDao](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task04/dao/FilmsListDao.java) for saving and loading collection.

[FilmListDao](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task04/dao/FilmsListDao.java) uses [SingleObjectDao](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task04/dao/SingleObjectDao.java) object for saving
 and loading, so it's easy to use any implementation of it.

  In this task for [testing](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/test/java/my/epam/unit04/task04/dao/FilmsListDaoTest.java) was used [SerialisedFileSignleStorage](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT04/src/main/java/my/epam/unit04/task04/dao/SerialisedFileSingleStorage.java).

