package data

import java.util.Date
import scala.beans.BeanProperty
import scala.util.Random
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotNull
import scala.annotation.meta.beanGetter

class WebLogin(@JsonProperty("url") @BeanProperty @(NotNull @beanGetter) var url :String ,
			   @JsonProperty("login") @BeanProperty @(NotNull @beanGetter) var login :String ,
			   @JsonProperty("password") @BeanProperty @(NotNull @beanGetter) var password :String ) {

  @BeanProperty var loginId :Int = this.hashCode()
  
}