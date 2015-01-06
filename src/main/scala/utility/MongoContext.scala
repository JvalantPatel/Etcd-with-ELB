package utility

import java.util.Properties
import com.mongodb.ServerAddress
import com.mongodb.casbah.MongoCredential
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.MongoDB
import com.mongodb.casbah.MongoClient



class MongoContext {  
  private var _db: MongoDB = null

   var users :MongoCollection=_ 

  def connect() {
    val properties = loadProperties()

    val host = properties.getProperty("host")
    val port = properties.getProperty("port")
    val username = properties.getProperty("username")
    val password = properties.getProperty("password")
    val dbName = properties.getProperty("dbName")
    val collectionName = properties.getProperty("collection")

    val server = new ServerAddress(host, port.toInt)
    val credentials = MongoCredential(username, dbName, password.toCharArray)
    val client = MongoClient(server, List(credentials))
    
    /*var client = MongoClient("localhost",27017)*/
    client(dbName)
    _db = client(dbName)
    users = _db(collectionName)
    println("collection in context"+users.getFullName)
    /*if(!_db.isAuthenticated)
      throw new Exception("Database authentication failed.")*/
  }

  def loadProperties(fileName: String = "/mongo.properties"): Properties = {
    val properties = new Properties()

    properties.load(getClass.getResourceAsStream(fileName))

    properties
  }
}