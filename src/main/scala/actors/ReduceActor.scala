package actors

/**
  * Created by archit kapoor on 26/6/16.
  */

import akka.actor.Actor
import distributedwordcountapp.{MapData, ReduceData, WordCount}

class ReduceActor extends Actor{
      def receive: Receive = {
        case MapData(dataList) =>
            sender ! reduce(dataList)
        case _ =>
          unhandled("Unexpected message received..!!")
      }

  def reduce(words: IndexedSeq[WordCount]): ReduceData = ReduceData{
    words.foldLeft(Map.empty[String, Int]) {
      (index, words) =>
              if(index contains words.word)
                    index + (words.word -> (index.get(words.word).get + 1))
              else
                    index + (words.word -> 1)
    }
  }
}