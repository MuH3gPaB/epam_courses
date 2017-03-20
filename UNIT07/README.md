UNIT 07 HOME WORK - MULTITHREADING
==================================

Welcome to Unit 07 home work page!

Task01 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT07/src/main/java/my/epam/unit07/task01) [(test)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT07/src/test/java/my/epam/unit07/task01) [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task01/App.java)
----------------------------------------
Three implementations of account manager are here:
 - [AccountManager](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task01/AccountsManager.java) - single thread;
 - [ParallelAccountManager](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task01/parallel/ParallelAccountsManager.java) - multithreaded using _'synchronized'_ keyword;
 - [ConcurrentAccountManager](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task01/parallel/ConcurrentAccountsManager.java) - multithreaded using Locks and Executors.

 All implementations uses [Operations](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task01/model/Operation.java) to perform with [Accounts](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task01/model/Account.java).

 [Application](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task01/App.java) loads operations from [text file](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/resources/my/epam/unit07/task01/operations.txt), parse them into [Operations](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task01/model/Operation.java),
 and apply all operations with different AccountManagers, with timing control.

 After all operations complete, time results are displayed.

 One of output results obtained on developer's machine are:

                AccountsManager { operations : 10000000 time :     2412 ms}

        ParallelAccountsManager { operations : 10000000 time :     1569 ms}

      ConcurrentAccountsManager { operations : 10000000 time :     1783 ms}



Task02 [(source)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task02/CachedPropertiesFileBundle.java) [(test)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/test/java/my/epam/unit07/task02/CachedPropertiesFileBundleTest.java)
------------------------------
[ChachedPropertiesFileBundle](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task02/CachedPropertiesFileBundle.java) class wrapping [PropertiesFileBundle](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT05/src/main/java/my/epam/unit05/task02/PropertiesFileBundle.java)
from [unit05 (task02)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT05).

It stores all properties files that have been read, into cache and provide
thread safe access using double check.

For testing was used custom [LogAppender](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/test/java/my/epam/unit07/task02/LogAppender.java) that storing all log messages.

Task03 [(source)]()
-------------------


