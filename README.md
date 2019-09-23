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
