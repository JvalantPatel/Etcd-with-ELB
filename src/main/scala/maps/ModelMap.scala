package maps

import com.mongodb.DBObject
import com.mongodb.casbah.Imports.wrapDBObj
import com.mongodb.casbah.commons.MongoDBList
import com.mongodb.casbah.commons.MongoDBObject

import data.BankAccount
import data.IdCard
import data.User
import data.WebLogin

object UserMap {
  def toBson(user: User): DBObject = {
    MongoDBObject(
      "id" -> user.id,
      "name" -> user.name,
      "password"->user.password, 
      "email" -> user.email,
      "createdAt"->user.createdAt, 
      "updatedAt"->user.updatedAt, 
      "lstOfCard" -> user.lstOfCard.map(IdCardMap.toBson),
      "lstOfWebLogin" -> user.lstOfWebLogin.map(WebLoginMap.toBson),
      "lstOfBanks" -> user.lstOfBanks.map(BankAccountMap.toBson)
      )
  }
  def fromBson(o: DBObject): User = {
    var user:User=new User(o.as[String]("name"),o.as[String]("password"),o.as[String]("email"))
    user.id =o.as[String]("id")
    user.createdAt =o.as[String]("createdAt")
    user.updatedAt =o.as[String]("updatedAt")
    user.lstOfCard = o.as[MongoDBList]("lstOfCard").toVector.map(doc => IdCardMap.fromBson(doc.asInstanceOf[DBObject]))
    user.lstOfWebLogin = o.as[MongoDBList]("lstOfCard").toVector.map(doc => WebLoginMap.fromBson(doc.asInstanceOf[DBObject]))
    user.lstOfBanks = o.as[MongoDBList]("lstOfCard").toVector.map(doc => BankAccountMap.fromBson(doc.asInstanceOf[DBObject]))
    user  
  }
}
object IdCardMap {
  def toBson(idCard: IdCard): DBObject = {
    MongoDBObject(
      "id" -> idCard.id ,
      "cardName" -> idCard.cardName ,
      "cardNumber" -> idCard.cardNumber ,
      "expirationDate" -> idCard.expirationDate 
      )
  }
  def fromBson(o: DBObject): IdCard = {
    var idCard:IdCard = new IdCard(o.as[String]("cardName"),o.as[String]("cardNumber"),o.as[String]("expirationDate"))
      idCard.id  = o.as[Int]("id")
      idCard     
  }
}
object WebLoginMap {
  def toBson(webLogin: WebLogin): DBObject = {
    MongoDBObject(
      "loginId" -> webLogin.loginId,
      "url" -> webLogin.url,
      "login" -> webLogin.login,
      "password" -> webLogin.password
      )
  }
  def fromBson(o: DBObject): WebLogin = {
    var webLogin:WebLogin = new WebLogin(o.as[String]("url"),o.as[String]("login"),o.as[String]("password"))
      webLogin.loginId  = o.as[Int]("loginId")
      webLogin     
  }
}
object BankAccountMap {
  def toBson(bankAccount: BankAccount): DBObject = {
    MongoDBObject(
      "bankId" -> bankAccount.bankId ,
      "accountName" -> bankAccount.accountName,
      "routingNumber" -> bankAccount.routingNumber ,
      "accountNumber" -> bankAccount.accountNumber 
      )
  }
  def fromBson(o: DBObject): BankAccount = {
    var bankAccount:BankAccount = new BankAccount(o.as[String]("accountName"),o.as[String]("routingNumber"),o.as[String]("accountNumber"))
      bankAccount.bankId  = o.as[Int]("bankId")
      bankAccount     
  }
}