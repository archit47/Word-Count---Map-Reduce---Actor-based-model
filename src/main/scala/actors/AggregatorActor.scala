package actors

/**
  * Created by archit kapoor on 27/6/16.
  */
import akka.actor.Actor
import distributedwordcountapp.{ReduceData, Result}

//import scala.collection.immutable.HashMap
import scala.collection.mutable.HashMap

class AggregatorActor extends  Actor{
    val final_reduced_map = new HashMap[String, Int]

  def receive: Receive = {
    case ReduceData(reduceDataMap) =>
      aggregate_map_reduce_result(reduceDataMap)

    case Result =>
      sender ! final_reduced_map.toString

    case _ =>
        unhandled("Unexpected message received..!!")
  }

  def aggregate_map_reduce_result(reduceDataMap: Map[String, Int]): Unit = {
    for((key, value) <- reduceDataMap){
          if(final_reduced_map contains key)
          {
            final_reduced_map(key) = (value + final_reduced_map.get(key).get)
          }
          else
          {
            final_reduced_map += (key -> value)
          }

    }
  }

}
