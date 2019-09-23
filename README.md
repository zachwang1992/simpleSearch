# simpleSearch
SimpleSearch is a Scala project to read all the txt files at the given directory and find whether there is any file that can match given words.
# Usage
start services with sbt:
```scala
$ sbt
$ runMain test.SimpleSearch directoryContainingTextFiles
```
This should read all the text files in the given directory, building an in memory representation of the files and their contents, and then give a command prompt at which interactive searches can be performed.

e.g.
```scala
$ sbt
$ runMain test.SimpleSearch E:\study\scala\practice\task
```
An example session is as follows

![image](https://github.com/zachwang1992/simpleSearch/blob/master/images/screenshot.jpg)

# Explanations
1. The program is case-insensitive.
2. The searched words shouldn't contain characters like '. For example, the word "can't" can not be searched in this program.
3. The score is the division of the number of words found in the file and the total number of words. For example, if the words "I love Scala" is searched and the words "I", "love" are found in the file, the score is 2/3 = 67%.
