package actors

/**
  * Created by archit kapoor on 27/6/16.
  */
import akka.actor.{Actor, Props}
import akka.routing.{RoundRobinGroup, RoundRobinPool}
import distributedwordcountapp._

class MasterActor extends Actor{

//  val mapActor = context.actorOf(Props[MapActor].withRouter(new RoundRobinGroup(nrofInstances = 5)), name = "mapactor")
  val mapActor = context.actorOf(Props[MapActor].withRouter(new RoundRobinPool(nr=5)), name = "mapactor")

//  val reduceActor = context.actorOf(Props[ReduceActor].withRouter(new RoundRobinGroup(nrofInstances = 5)), name = "reduceactor")
  val reduceActor = context.actorOf(Props[ReduceActor].withRouter(new RoundRobinPool(nr=5)), name = "reduceactor")

    val aggregatorActor = context.actorOf(Props[AggregatorActor], name = "aggregatoractor")

  def receive: Receive = {
    case line: String =>
      mapActor ! line

    case mapData: MapData =>
      reduceActor ! mapData

    case reduceData: ReduceData =>
      aggregatorActor ! reduceData

    case Result =>
      aggregatorActor forward Result

  }
}
