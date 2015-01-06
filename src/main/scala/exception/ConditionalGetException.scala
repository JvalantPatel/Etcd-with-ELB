package exception

import scala.beans.BeanProperty

class ConditionalGetException(@BeanProperty  var name :String) extends Exception{

}