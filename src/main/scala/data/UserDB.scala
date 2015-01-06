package data


import scala.beans.BeanProperty
import scala.collection.mutable.HashMap

object UserDB {
  
   @BeanProperty var mapOfUSers =  HashMap[String,User]()

}