UNIT 06 HOME WORK - Generic and Collections
===================

Welcome to Unit 06 home work page!

Task01
------
In table below represented most popular java collections available from java.util.* package.

|Name   |Ordering|Random Access|Key-Value Pairs|Allows Duplicates|Allows Null Values|Thread Safe|Blocking Operations|
|---|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|ArrayBlockingQueue|FIFO|No|No|Yes|No|Yes|Yes|
|ArrayDeque|FIFO<br>/LIFO|No|No|Yes|No|No|No|
|ArrayList|Yes|Yes|No|Yes|Yes|No|No|
|ConcurrentHashMap|No|Yes|Yes|No by keys.<br>Yes by values.|No|Yes|Write to same segment|
|ConcurrentSkipListMap|Yes|Yes|Yes|No by keys.<br>Yes by values.|No|Yes|No|
|ConcurrentSkipListSet|Yes|No|No|No|No|Yes|No|
|CopyOnWriteArrayList|Yes|Yes|No|Yes|Yes|Yes|No|
|CopyOnWriteArraySet|No|No|No|No|Yes|Yes|No|
|DelayQueue|Delay<br>expiration|No|No|Yes|No|Yes|Block on take until delay expired|
|HashMap|No|Yes|Yes|No by keys.<br>Yes by values.|Yes|No|No|
|HashSet|No|No|No|No|Yes|No|No|
|LinkedBlockingDeque|FIFO<br>/LIFO|No|No|Yes|No|Yes|Yes|
|LinkedBlockingQueue|FIFO|No|No|Yes|No|Yes|Yes|
|LinkedHashMap|Yes|Yes|Yes|No by keys.<br>Yes by values.|Yes|No|No|
|LinkedHashSet|Yes|No|No|No|Yes|No|No|
|LinkedList|Yes|Yes|No|Yes|Yes|No|No|
|LinkedTransferQueue|FIFO|No|No|Yes|No|Yes|Yes (variate)|
|PriorityBlockingQueue|Yes|No|No|Yes|No|Yes|Yes|
|TreeMap|Yes|Yes|Yes|No by keys.<br>Yes by values.|Yes|No|No|
|TreeSet|Yes|No|No|No|Depends on Comparator|No|No|
|SynchronousQueue|No|No|No|Capacity<br>= 1|No|Yes|Block write until read|


Task02
------
[CachedPropertiesFileBundle](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task02/CachedPropertiesFileBundle.java) from [task2 unit7](https://github.com/MuH3gPaB/epam_courses/tree/master/UNIT07) stores properties to inner HashMap.

Ordinary, on putting value into Map with key which already present
in current map, its value will be overridden.

In [CachedPropertiesFileBundle](https://github.com/MuH3gPaB/epam_courses/blob/master/UNIT07/src/main/java/my/epam/unit07/task02/CachedPropertiesFileBundle.java), when we try to read properties file which already was read,
 it just check inner HashMap if it contains properties for this file,
 and do not add properties with same key again.


Task03
-----------------------------

