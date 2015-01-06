package data

import scala.beans.BeanProperty
import java.util.Date
import java.util.Calendar
import scala.util.Random
import scala.collection.mutable._
import java.text.SimpleDateFormat
import java.util.Formatter.DateTime
import org.springframework.format.annotation.DateTimeFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotNull
import scala.annotation.meta.beanGetter
import utility.TimeUtility



class User(
		   @JsonProperty("name") @BeanProperty  var name :String,
           @JsonProperty("password") @BeanProperty @(NotNull @beanGetter) var password :String,
           @JsonProperty("email") @BeanProperty @(NotNull @beanGetter) var email :String) {
  
 @BeanProperty var id :String = "u-"+this.hashCode().toString
 
 @BeanProperty var createdAt:String = TimeUtility.getCurrentTime
 
 @BeanProperty var updatedAt:String = TimeUtility.getCurrentTime
 
  var lstOfCard: Vector[IdCard] = Vector()
  var lstOfWebLogin: Vector[WebLogin] = Vector()
  var lstOfBanks: Vector[BankAccount] = Vector()

 }