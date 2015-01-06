package utility

import com.justinsb.etcd.EtcdClient
import java.net.URI

object EtcdUtility {
  var client : EtcdClient  = new EtcdClient(URI.create("http://54.67.103.220:4001"));
  def getClient() : EtcdClient={
    return client
  }

}