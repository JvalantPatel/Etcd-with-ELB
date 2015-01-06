package controllers
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.{HttpResponse,JsonNode}
import java.util.concurrent.atomic.AtomicLong
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import scala.collection.JavaConversions._
import org.springframework.context.annotation.ComponentScan
import data._
import java.util.Date
import collection.JavaConverters._
import scala.beans.BeanProperty
import scala.collection.mutable.HashMap
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestBody
import scala.util.parsing.json.JSON
import org.apache.log4j.Logger
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus
import javax.validation.Valid
import org.springframework.validation.BindingResult
import data.ErrorMessage
import org.springframework.web.bind.annotation.ExceptionHandler
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage
import org.springframework.http.HttpHeaders
import exception.ConditionalGetException
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import exception.ConditionalGetException
import utility._
import com.mongodb.casbah.MongoCollection
import exception.ConditionalGetException
import maps.UserMap
import com.mongodb.casbah.MongoClient
import service._
import org.springframework.web.client.RestTemplate
import scala.data.RouteResponse
import scala.exception.InvalidRoutingNumberException
import org.springframework.http.MediaType
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import com.fasterxml.jackson.databind.ObjectMapper

@RequestMapping(value = Array("/api/v1"))
@RestController
class WalletController {

  var logger = Logger.getLogger(classOf[WalletController])

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(method = Array(RequestMethod.POST), value = Array("/users"))
  def addUser(@RequestBody @Valid user: User, bindingResult: BindingResult, httpResponse: HttpServletResponse): Any = {
    checkError(bindingResult, "Adding new User#")
    UserDataService.insertUser(user)
    user
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/users/{user_id}"))
  def viewUser(@PathVariable user_id: String, httpRequest: HttpServletRequest, httpResponse: HttpServletResponse): Any = {
    logger.info(user_id)
    var user = UserDataService.getUser(user_id)
    var currentEtag: String = createEtag(user)
    var previousEtag = httpRequest.getHeader("ETag")
    if (!currentEtag.equals(previousEtag)) {
      httpResponse.setHeader("ETag", currentEtag)
      user
    } else {
      throw new ConditionalGetException("Content not modified")
    }
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(method = Array(RequestMethod.PUT), value = Array("/users/{user_id}"))
  def updateUser(@PathVariable user_id: String,
    @RequestBody @Valid user: User, bindingResult: BindingResult, httpResponse: HttpServletResponse): User = {
    checkError(bindingResult, "Updating new User#")
    var updatedUser = UserDataService.updateUser(user_id, user)
    var etag: String = createEtag(updatedUser)
    httpResponse.setHeader("ETag", etag)
    updatedUser

  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(method = Array(RequestMethod.POST), value = Array("/users/{user_id}/idcards"))
  def addIdCard(@PathVariable user_id: String,
    @RequestBody @Valid idCard: IdCard, bindingResult: BindingResult): IdCard = {
    checkError(bindingResult, "Adding new Card#")
    UserDataService.addIdCard(user_id, idCard)
    idCard
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/users/{user_id}/idcards"))
  def getIdCards(@PathVariable user_id: String): java.util.List[IdCard] = {
    var idCardVector = UserDataService.getIdcardsForUser(user_id)
    idCardVector.asJava
  }

  @RequestMapping(method = Array(RequestMethod.DELETE), value = Array("/users/{user_id}/idcards/{card_id}"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteIdCard(@PathVariable user_id: String,
    @PathVariable card_id: String) = {
    UserDataService.deleteIdCard(user_id, card_id.toInt)
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(method = Array(RequestMethod.POST), value = Array("/users/{user_id}/weblogins"))
  def addWebLogin(@PathVariable user_id: String,
    @RequestBody @Valid webLogin: WebLogin, bindingResult: BindingResult): WebLogin = {
    checkError(bindingResult, "Adding new Web Login#")
    UserDataService.addWebLogin(user_id, webLogin)
    webLogin
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/users/{user_id}/weblogins"))
  def getWebLogins(@PathVariable user_id: String): java.util.List[WebLogin] = {
    logger.info(user_id)
    var webLoginVector = UserDataService.getWebLoginForUser(user_id)
    webLoginVector.asJava
  }

  @RequestMapping(method = Array(RequestMethod.DELETE), value = Array("/users/{user_id}/weblogins/{login_id}"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteWebLogin(@PathVariable user_id: String,
    @PathVariable login_id: String) = {

    UserDataService.deleteWebLogin(user_id, login_id.toInt)
  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(method = Array(RequestMethod.POST), value = Array("/users/{user_id}/bankaccounts"))
  def addBankAccount(@PathVariable user_id: String,
    @RequestBody @Valid bankAccount: BankAccount, bindingResult: BindingResult): BankAccount = {
    checkError(bindingResult, "Adding new Bank Account#")
    checkRoutingNumber(bankAccount)
    UserDataService.addBankAccount(user_id, bankAccount)
    bankAccount
  }

  @RequestMapping(method = Array(RequestMethod.GET), value = Array("/users/{user_id}/bankaccounts"))
  def getBankAccounts(@PathVariable user_id: String): java.util.List[BankAccount] = {
    var bankAccountVector = UserDataService.getBankAccountsForUser(user_id)
    bankAccountVector.asJava
  }

  @RequestMapping(method = Array(RequestMethod.DELETE), value = Array("/users/{user_id}/bankaccounts/{ba_id}"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def deleteBankAccount(@PathVariable user_id: String,
    @PathVariable ba_id: String) = {
    UserDataService.deleteBankAccount(user_id, ba_id.toInt)
  }

  @ExceptionHandler()
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  def handleException(exception: Exception): ErrorMessage = {
    var msgs: Array[String] = exception.getMessage().split('#')
    logger.info(msgs(0))
    new ErrorMessage(msgs(0), msgs(1))
  }

  @ExceptionHandler(value = Array(classOf[InvalidRoutingNumberException]))
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  def handleRoutingException(exception: InvalidRoutingNumberException): ErrorMessage = {
    /*var msgs: Array[String] = exception.getMessage().split('#')
    logger.info(msgs(0))*/
    new ErrorMessage("Adding new bank account", exception.getName)
  }

  @ExceptionHandler(value = Array(classOf[ConditionalGetException]))
  @ResponseStatus(value = HttpStatus.NOT_MODIFIED)
  @ResponseBody
  def handleExceptionCG(exception: ConditionalGetException): Any = {
    ""
  }

  def checkError(bindingResult: BindingResult, msg: String) =
    {
      var errorString: String = ""
      errorString += msg
      if (bindingResult.hasErrors()) {

        for (error <- bindingResult.getFieldErrors().asScala) {
          errorString += error.getField() + " - " + error.getDefaultMessage() + "\n"
        }
        logger.info(errorString.toString)
        throw new Exception(errorString.toString)
      }

    }

  def createEtag(user: User): String =
    {
      var etag: String = ""
      etag = "" + user.getId.toString() + "" + user.getUpdatedAt.toString() + ""
      etag
    }

  def checkRoutingNumber(bankAccount: BankAccount) =
    {
      var objectMapper: ObjectMapper = new ObjectMapper()
      val template = new RestTemplate()
      var headers: HttpHeaders = new HttpHeaders()
      headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
      var request: HttpEntity[String] = new HttpEntity[String](headers)
      var response: ResponseEntity[String] = template.exchange("http://www.routingnumbers.info/api/data.json?rn=" + bankAccount.routingNumber, HttpMethod.GET, request, classOf[String])
      var responseBody: String = response.getBody();
      var routeResponse: RouteResponse = objectMapper.readValue(responseBody, classOf[RouteResponse]);
      if (routeResponse.code.toInt  == 404) {
        throw new InvalidRoutingNumberException("Invalid Routing Number!")
      }else if (routeResponse.code.toInt  == 400) {
        throw new InvalidRoutingNumberException(routeResponse.message)
      } 
      else {       
        bankAccount.setAccountName(routeResponse.getCustomer_name)
      }

    }
  
 /* def checkRoutingNumber(bankAccount: BankAccount) =
    {
    
    
      var jsonresponse : HttpResponse[JsonNode] = Unirest.post("http://www.routingnumbers.info/api/data.json").header("accept", "application/json").field("rn",bankAccount.routingNumber).asJson()
      println(jsonresponse.getCode())
      println(jsonresponse.getBody().getObject().getString("customer_name"))
      

    }*/
}
