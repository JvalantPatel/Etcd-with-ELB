package controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable

import org.springframework.web.bind.annotation.RequestMethod
import com.justinsb.etcd.EtcdResult
import com.justinsb.etcd.EtcdClient

import java.net.URI
import utility.EtcdUtility

@RequestMapping(value = Array("/api/v1"))
@RestController
class EtcdController {

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/counter"))
  def incrementCounter(): String = {

    var client: EtcdClient = EtcdUtility.getClient
    var key: String = "010028408/counter";

    var result: EtcdResult = client.get(key);
    var value: Int = result.node.value.toInt
    value = value + 1
    result = client.set(key, value.toString);

    return result.node.value
  }
  
  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/healthcheck"))
  def healthCheck(): String = {

    return "OK"
  }

}