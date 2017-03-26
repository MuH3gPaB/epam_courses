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
Given classes:

![Uml diagram](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/unit06task3uml.png)

Table of correctness of assignments represented below.

|Assignment|Correct/<br>Not correct|Comment|
|---|:---:|:---|
|Doctor doctor1 = new Doctor();|![Ok](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/ok.png)|Ordinary variable assignment with same type object.|
|Doctor doctor2 = new MedicalStaff();|![notOk](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/notOk.png)|MedicalStaff object is not a Doctor, and could not be assigned to Doctors type variable.|
|Doctor doctor3 = new HeadDoctor();|![Ok](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/ok.png)|HeadDoctor is a doctor according to the hierarchy of inheritance.|
|Object object1 = new HeadDoctor();|![Ok](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/ok.png)|Every java object extends Object class, so it's valid to assign Object variable to object of any type.|
|HeadDoctor doctor5 = new Object();|![notOk](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/notOk.png)|Created Object is not HeadDoctor, so it's incorrect assignment.|
|Doctor doctor6 = new Nurse();|![notOk](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/notOk.png)|Nurse is not a Doctor, and cant be assigned to Doctor's type variable.|
|Nurse nurse = new Doctor();|![notOk](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/notOk.png)|Doctor is not a Nurse, and cant be assigned to Nurse's type variable.|
|Object object2 = new Nurse();|![Ok](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/ok.png)|Every java object extends Object class, so it's valid to assign Object variable to object of any type.|
|List\<Doctor> list1= new ArrayList\<Doctor>();|![Ok](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/ok.png)|ArrayList implements List, so we can assign generified classes variables with same type.|
|List\<MedicalStaff> list2 = new ArrayList\<Doctor>();|![notOk](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/notOk.png)|Generified classes do not take into account the hierarchy of inheritance.|
|List\<Doctor> list3 = new ArrayList\<MedicalStaff>();|![notOk](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/notOk.png)|Generified classes do not take into account the hierarchy of inheritance.|
|List\<Object> list4 = new ArrayList\<Doctor>();|![notOk](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/notOk.png)|Generified classes do not take into account the hierarchy of inheritance.|
|List\<Object> list5 = new ArrayList\<Object>();|![Ok](https://github.com/MuH3gPaB/epam_courses/blob/master/misc/ok.png)|ArrayList implements List, so we can assign generified classes variables with same type.|