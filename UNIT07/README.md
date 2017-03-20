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

Task03 [(source)](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT07/src/main/java/my/epam/unit07/task03) [(app)](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task03/App.java)
-----------------------------
The task is to make [IntegerSetterGetters](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task03/IntegerSetterGetter.java) not able to became readers all together.

### _The first added feature is watchdog timer into [SharedResource](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task03/SharedResource.java)._

[SharedResource](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task03/SharedResource.java) can not control how much requests for data it will receive,
and do not know how much data it can provide.

So, [SharedResouce](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task03/SharedResource.java) should check the situation when it's no more data available,
but unsatisfied clients still waiting.

The watchdog timer just solve this problem.

WDT start counting from 10 to zero (one count per second) on resource creation.

When WDT comes to zero, _noMoreData_ flag sets up.

If thread tries to get data when _noMoreData_ flag is true, the NoSuchElementException
 is thrown.

 Every time [SharedResource](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task03/SharedResource.java) get new data, _noMoreData_ flag sets to false,
 and WDT value sets back to 10.

### _The **second** added feature is check [IntegerSetterGetter](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task03/IntegerSetterGetter.java) role before each iteration._

All [IntegerSetterGetters](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task03/IntegerSetterGetter.java) are joined into one ThreadGroup.

When thread make choice of role (reader or writer), it checks if it
is last thread in the group that can became a writer, and if it so,
it becomes a writer, otherwise - the random choice.

### _Some minor code refactoring was performed:_
- UserResourceThread renamed to App;
- IntegerSetterGetter class was placed to separated file;
- all outputs redirected to logger.info;
- synchronized block now starts before thread will choice its role;
- some more minor refactoring...


