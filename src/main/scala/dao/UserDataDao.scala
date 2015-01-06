package dao

import utility._
import data._
import maps._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.MongoDBList
import com.mongodb.casbah.Imports._

object UserDataDao {

  val context = new MongoContext
  context.connect()
  var userDB = context.users

  def inserUser(user: User) =
    {
      var doc = UserMap.toBson(user)
      userDB.insert(doc)
    }

  def getUser(userId: String): User =
    {
      val query = MongoDBObject("id" -> userId)
      var user: User = UserMap.fromBson(userDB.find(query).one) /*flatMap(x => x.as[MongoDBList]("payments").toVector.map(doc => UserMap.fromBson(doc.asInstanceOf[DBObject]))).toVector(0)*/
      user
    }

  def updateUser(userId: String, user: User): User = {
    val query = MongoDBObject("id" -> userId)
    val updatePassword = MongoDBObject("$set" -> MongoDBObject("password" -> user.getPassword.toString()))
    val updateTime = MongoDBObject("$set" -> MongoDBObject("updatedAt" -> TimeUtility.getCurrentTime))
    val updateEmail = MongoDBObject("$set" -> MongoDBObject("email" -> user.getEmail))
    var bulk = userDB.initializeOrderedBulkOperation;
    bulk.find(query).updateOne(updatePassword);
    bulk.find(query).updateOne(updateTime);
    bulk.find(query).updateOne(updateEmail);
    bulk.execute();

    var updatedUser: User = UserMap.fromBson(userDB.find(query).one)
    updatedUser
  }
  
  def addIdCard(userId:String,idCard:IdCard)={
    val query = MongoDBObject("id" -> userId)
    val update = MongoDBObject("$addToSet"->MongoDBObject("lstOfCard" -> IdCardMap.toBson(idCard)))    
    userDB.update(query,update)
  }
  
  def getIdCardsForUser(userId:String):Vector[IdCard]={
    val findQuery = MongoDBObject("id"->userId)
    val fields = MongoDBObject("lstOfCard"->1)
    var vectorIdCards:Vector[IdCard]=userDB.find(findQuery,fields).flatMap(x => x.as[MongoDBList]("lstOfCard").
                                     toVector.map(doc => IdCardMap.fromBson(doc.asInstanceOf[DBObject]))).toVector
    vectorIdCards
    
  }
  
  def deleteIdCard(userId:String,cardId:Int)={
    val deleteQuery = MongoDBObject("id"->userId)
    val update = MongoDBObject("$pull"->MongoDBObject("lstOfCard" ->MongoDBObject("id"->cardId)))
    userDB.update(deleteQuery,update)
  }
  
  def addWebLogin(userId:String,webLogin:WebLogin)={
    val query = MongoDBObject("id" -> userId)
    val update = MongoDBObject("$addToSet"->MongoDBObject("lstOfWebLogin" -> WebLoginMap.toBson(webLogin)))    
    userDB.update(query,update)
  }
  
  def getWebLoginForUser(userId:String):Vector[WebLogin]={
    val findQuery = MongoDBObject("id"->userId)
    val fields = MongoDBObject("lstOfWebLogin"->1)
    var vectorWebLogins:Vector[WebLogin]=userDB.find(findQuery,fields).flatMap(x => x.as[MongoDBList]("lstOfWebLogin").
                                     toVector.map(doc => WebLoginMap.fromBson(doc.asInstanceOf[DBObject]))).toVector
    vectorWebLogins    
  }
  
  def deleteWebLogin(userId:String,webLoginId:Int)={
    val deleteQuery = MongoDBObject("id"->userId)
    val update = MongoDBObject("$pull"->MongoDBObject("lstOfWebLogin" ->MongoDBObject("loginId"->webLoginId)))
    userDB.update(deleteQuery,update)
  }
  
  def addBankAccount(userId:String,bankAccount:BankAccount)={
    val query = MongoDBObject("id" -> userId)
    val update = MongoDBObject("$addToSet"->MongoDBObject("lstOfBanks" -> BankAccountMap.toBson(bankAccount)))    
    userDB.update(query,update)
  }
  
  def getBankAccountsForUser(userId:String):Vector[BankAccount]={
    val findQuery = MongoDBObject("id"->userId)
    val fields = MongoDBObject("lstOfBanks"->1)
    var vectorBankAccounts:Vector[BankAccount]=userDB.find(findQuery,fields).flatMap(x => x.as[MongoDBList]("lstOfBanks").
                                     toVector.map(doc => BankAccountMap.fromBson(doc.asInstanceOf[DBObject]))).toVector
    vectorBankAccounts    
  }
  
  def deleteBankAccount(userId:String,bankAccountId:Int)={
    val deleteQuery = MongoDBObject("id"->userId)
    val update = MongoDBObject("$pull"->MongoDBObject("lstOfBanks" ->MongoDBObject("bankId"->bankAccountId)))
    userDB.update(deleteQuery,update)
  }


}