UNIT 04 HOME WORK
=================

Welcome to Unit 04 home work page!

Task01 [(source)]() [(test)]() [(app)]()
----------------------------------------
Application calculates java keywords in java source file and write
results file.

 For calculation uses [JavaCodeKeyWordsCalculator]() which uses
 [ArrayFileReader]() and [ArrayFileWriter]() implementations for
 input and output data and [StringWordsCounter]() for calculating
 keywords. [JavaCodeKeyWordsCalculator]() also contains
 all java keywords as array of String values.

In this task the case was to use only byte array input and output, so
as implementations of [ArrayFileReader]() and [ArrayFileWriter]() used
 classes [ByteArrayFileReader]() and [ByteArrayFileWriter]().


Task02 [(source)]() [(app)]()
-----------------------------
The same as Task01 case. Difference is that in this case char array input
and output should be used.
So, in this task used [CharArrayFileReader]() and [CharArrayFileWriter]() as
implementations of [ArrayFileReader]() and [ArrayFileWriter]().

Task03 [(app)]()
----------------
Very simple application that convert UTF-8 text file to UTF-16.
Application uses standart BufferedReader and BufferedWriter classes
 with specifying encoding by _'StandardCharsets.UTF_16'_ option.

Task04 [(source)]() [(test)]()
------------------------------
Solution of this task represented by [FilmList]() collection and
[FilmListDao]() for saving and loading collection.
[FilmListDao]() uses [SingleObjectDao]() object for saving
 and loading, so it's easy to use any implementation of it.
  In this task for [testing]() was used [SerialisedFileSignleStorage]().

