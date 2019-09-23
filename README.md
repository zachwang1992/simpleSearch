# simpleSearch
SimpleSearch is a Scala project that can read all the text files in the given directory, building an in memory representation of the files and their contents, and then give a command prompt at which interactive searches can be performed.
# Usage
The program can be started with sbt:
```scala
$ sbt
$ runMain test.SimpleSearch directoryContainingTextFiles
```

e.g.
```scala
$ sbt
$ runMain test.SimpleSearch E:\study\scala\practice\TextFiles
```
An example session could look like:

![image](https://github.com/zachwang1992/simpleSearch/blob/master/images/screenshot.jpg)

# Explanations
1. The program is case-insensitive.
2. The searched words shouldn't contain special characters like '. For example, the word "can't" can not be searched in this program.
3. The score is the division of the number of words found in the file and the total number of searched words. For example, if the words "I love Scala" are searched and the words "I", "love" are found in the file, the score for this file is 2/3 = 67%.
