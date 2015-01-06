package data

import scala.beans.BeanProperty

class ErrorMessage(@BeanProperty var request_method:String ,@BeanProperty var message:String ) {

}