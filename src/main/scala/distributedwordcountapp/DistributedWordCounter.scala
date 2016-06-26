package distributedwordcountapp

/**
  * Created by archit kapoor on 26/6/16.
  */


import akka.actor.{ActorSystem, Props}

import scala.collection.mutable.ArrayBuffer
import actors._
import akka.pattern.ask


import akka.util.Timeout
import scala.concurrent.duration._
import scala.language.postfixOps

import scala.concurrent.{Await, Future, TimeoutException}
//import java.util.concurrent.TimeoutException


sealed trait MapReduceMessage

case class WordCount(word: String, count: Int) extends MapReduceMessage
case class MapData(dataList: ArrayBuffer[WordCount]) extends MapReduceMessage
case class ReduceData(reduceDataMap: Map[String, Int]) extends MapReduceMessage
case class Result() extends MapReduceMessage

object DistributedWordCounter extends App{

  val _system = ActorSystem("DistributedWordCounterMapReduceApp")

  implicit  val timeout = Timeout(5 seconds)

  val master_actor = _system.actorOf(Props[MasterActor], name = "masteractor")



  master_actor ! "The quick brown fox tried to jump over the lazy dog and fell on the dog"
  master_actor ! "A dog is man's best friend"
  master_actor ! "Dog and fox both belong to the same family"
  master_actor ! "Hello World, this is very crazy"

  Thread.sleep(3000)

  val future = (master_actor ? Result).mapTo[String]
  try {
    val result = Await.result(future, timeout.duration)
    println(result)
  }
  catch{
    case toe: TimeoutException =>
          println("Timeout exception has occurred. THe operations could not get completed in the stipulated time.")
    case _ : Throwable =>
      println("Some really unknown exception has occurred.")
  }
  finally {
    _system.shutdown()
  }

println("Thank you for choosing to run this application..!! Hope you enjoyed it.")

}