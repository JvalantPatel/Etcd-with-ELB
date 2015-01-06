package scala.data

import com.fasterxml.jackson.annotation.JsonProperty
import scala.beans.BeanProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class RouteResponse {

  @JsonProperty("code") @BeanProperty var code: String = ""
  @JsonIgnoreProperties @JsonProperty("customer_name") @BeanProperty var customer_name: String = ""

  @JsonProperty("message") @BeanProperty var message: String = ""
  @JsonIgnoreProperties @JsonProperty("change_date") @BeanProperty var change_date: String = ""
  @JsonIgnoreProperties @JsonProperty("office_code") @BeanProperty var office_code: String = ""
  @JsonIgnoreProperties @JsonProperty("record_type_code") @BeanProperty var record_type_code: String = ""
  @JsonIgnoreProperties @JsonProperty("new_routing_number") @BeanProperty var new_routing_number: String = ""
  @JsonIgnoreProperties @JsonProperty("state") @BeanProperty var state: String = ""
  @JsonIgnoreProperties @JsonProperty("address") @BeanProperty var address: String = ""
  @JsonIgnoreProperties @JsonProperty("telephone") @BeanProperty var telephone: String = ""
  @JsonIgnoreProperties @JsonProperty("data_view_code") @BeanProperty var data_view_code: String = ""
  @JsonIgnoreProperties @JsonProperty("city") @BeanProperty var city: String = ""
  @JsonIgnoreProperties @JsonProperty("routing_number") @BeanProperty var routing_number: String = ""
  @JsonIgnoreProperties @JsonProperty("institution_status_code") @BeanProperty var institution_status_code: String = ""
  @JsonIgnoreProperties @JsonProperty("zip") @BeanProperty var zip: String = ""
  @JsonProperty("rn") @BeanProperty var rn: String = ""

}