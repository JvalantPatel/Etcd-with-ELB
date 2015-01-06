package data

import java.util.Date
import scala.beans.BeanProperty
import scala.util.Random
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotNull
import scala.annotation.meta.beanGetter

class BankAccount(@JsonProperty("account_name") @BeanProperty var accountName :String,
				  @JsonProperty("routing_number") @BeanProperty @(NotNull @beanGetter) var routingNumber :String,
				  @JsonProperty("account_number") @BeanProperty @(NotNull @beanGetter) var accountNumber :String) {

  @BeanProperty var bankId :Int = this.hashCode()
  
}