package service

import data._
import dao._

object UserDataService {

  def insertUser(user: User) = {
    UserDataDao.inserUser(user)
  }

  def getUser(userId: String): User = {
    UserDataDao.getUser(userId)
  }

  def updateUser(userId: String, user: User): User = {
    var updatedUser: User = UserDataDao.updateUser(userId, user)
    updatedUser
  }

  def addIdCard(userId: String, idCard: IdCard) = {
    if(idCard.expirationDate ==null)idCard.expirationDate =""
    UserDataDao.addIdCard(userId, idCard)
  }

  def getIdcardsForUser(userId: String): Vector[IdCard] = {
    UserDataDao.getIdCardsForUser(userId)
  }

  def deleteIdCard(userId: String, cardId: Int) = {
    UserDataDao.deleteIdCard(userId, cardId)
  }
  
  def addWebLogin(userId: String, webLogin: WebLogin) = {
    UserDataDao.addWebLogin(userId, webLogin)
  }

  def getWebLoginForUser(userId: String): Vector[WebLogin] = {
    UserDataDao.getWebLoginForUser(userId)
  }

  def deleteWebLogin(userId: String, webLoginId: Int) = {
    UserDataDao.deleteWebLogin(userId, webLoginId)
  }
  
   def addBankAccount(userId: String, bankAccount: BankAccount) = {
    UserDataDao.addBankAccount(userId, bankAccount)
  }

  def getBankAccountsForUser(userId: String): Vector[BankAccount] = {
    UserDataDao.getBankAccountsForUser(userId)
  }

  def deleteBankAccount(userId: String, bankAccountId: Int) = {
    UserDataDao.deleteBankAccount(userId, bankAccountId)
  }

}