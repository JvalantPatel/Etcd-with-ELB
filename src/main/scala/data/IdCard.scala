package data

import java.util.Date
import scala.beans.BeanProperty
import scala.util.Random
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotNull
import scala.annotation.meta.beanGetter

class IdCard(@JsonProperty("card_name") @BeanProperty @(NotNull @beanGetter) var cardName:String,
             @JsonProperty("card_number") @BeanProperty @(NotNull @beanGetter) var cardNumber:String,
             @JsonProperty("expiration_date") @BeanProperty var expirationDate:String) {
  
  @BeanProperty var id :Int = this.hashCode()

}