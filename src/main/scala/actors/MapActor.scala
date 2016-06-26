package actors

/**
  * Created by archit kapoor on 26/6/16.
  */

import akka.actor.Actor
import distributedwordcountapp.{MapData, WordCount}
import collection.mutable.ArrayBuffer

class MapActor extends Actor{

  val IGNORE_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at", "be", "do", "go", "in", "is", "it", "of", "on", "the", "to")
  val defaultCount: Int = 1

  def receive: Receive = {

    case message: String =>
              sender ! evalauteStringExpression(message)
    case _ =>
      unhandled(message = "Unexpected message received..!!")
  }

  def evalauteStringExpression(message_string: String): MapData = MapData {
      message_string.split("""\s+""").foldLeft(ArrayBuffer.empty[WordCount]){
              (index, word) =>
                      if(!IGNORE_WORDS_LIST.contains(word.toLowerCase))
                            index += WordCount(word.toLowerCase, 1)
                      else
                        index
      }
  }


}
