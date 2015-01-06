package scala.exception

import scala.beans.BeanProperty

class InvalidRoutingNumberException(@BeanProperty  var name :String) extends Exception {

}