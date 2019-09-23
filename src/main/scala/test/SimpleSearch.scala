package test

import java.io.File
import java.nio.charset.CodingErrorAction
import java.text.NumberFormat

import scala.annotation.tailrec
import scala.io.{Codec, Source}
import scala.util.Try

object SimpleSearch extends App {
  Program
    .readFile(args)
    .fold(
      println,
      file => Program.iterate(Program.index(file))
    )
}
object Program {

  case class Index(listOfFiles: List[File])

  sealed trait ReadFileError

  case object MissingPathArg extends ReadFileError
  case class NotDirectory(error: String) extends ReadFileError
  case class FileNotFound(t: Throwable) extends ReadFileError

  def readFile(args: Array[String]): Either[ReadFileError, File] = {
    for {
      path <- args.headOption.toRight(MissingPathArg)
      file <- Try(new java.io.File(path))
        .fold(
          throwable => Left(FileNotFound(throwable)),
          file =>
            if (file.isDirectory) Right(file)
            else Left(NotDirectory(s"Path [$path] is not a directory"))
        )
    } yield file
  }

  def index(file: File): Index = {
    val listOfFiles = file.listFiles.filter(_.getName.endsWith("txt")).toList
    if (listOfFiles.isEmpty) {
      println("FileNotFound")
      System.exit(0)
    } else {
      val path = listOfFiles.head.getParentFile.getPath
      print(s"${listOfFiles.length} files read in directory $path")
    }
    Index(listOfFiles)
  }

  @tailrec
  def iterate(indexedFiles: Index): Unit = {
    println()
    println(s"search> ")
    val input = scala.io.StdIn.readLine().toLowerCase
    val inputArray: Array[String] = input.split("\\W+")
    if (input == "quit") System.exit(0)

    val map = scala.collection.mutable.Map[String, Double]()
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val a = indexedFiles.listOfFiles
    for (e <- a) {
      var counter = 0
      val txtSource = Source.fromFile(e)(decoder)
      val contentsOfTxt = txtSource.mkString.toLowerCase.split("\\W+")
      for (i <- inputArray.indices) {
        counter =
          if (contentsOfTxt.contains(inputArray(i))) counter + 1
          else counter
      }

      val score = counter.toDouble / inputArray.length.toDouble
      map += e.getName -> score
      txtSource.close()
    }

    val sortedMap = map.toList.sortBy(-_._2)
    if (sortedMap.head._2 > 0) {
      if (sortedMap.length <= 10) {
        for (e <- sortedMap) {
          print(s"${e._1}: ${NumberFormat.getPercentInstance.format(e._2)}  ")
        }
      } else {
        val newMap = sortedMap.dropRight(sortedMap.length - 10)
        for (e <- newMap) {
          print(s"${e._1}: ${NumberFormat.getPercentInstance.format(e._2)}  ")
        }
      }
    } else print("no matches found")

    iterate(indexedFiles)
  }

}
