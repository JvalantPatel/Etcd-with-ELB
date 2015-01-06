package data
import scala.util.parsing.json._
import main.scala.data._
import scala.collection.mutable._

object test {
    
 
val result = JSON.parseFull(
"""{"email": "jvalant@gmail.com", "password": "secret"}""")
                                                  //> result  : Option[Any] = Some(Map(email -> jvalant@gmail.com, password -> sec
                                                  //| ret))
 
 
result match {
case Some(e) => println(e) // => Map(name -> Naoki, lang -> List(Java, Scala))
case None => println("Failed.")
}                                                 //> Map(email -> jvalant@gmail.com, password -> secret)


var list = HashMap[Int,IdCard]()                  //> list  : scala.collection.mutable.HashMap[Int,main.scala.data.IdCard] = Map()
                                                  //| 
var card1 = new IdCard("card1","1","date1")       //> card1  : main.scala.data.IdCard = main.scala.data.IdCard@2fd66ad3
var card2 =  new IdCard("card2","2","date2")      //> card2  : main.scala.data.IdCard = main.scala.data.IdCard@5d11346a
println("card1-"+card1.getId)                     //> card1-802581203
println("card2-"+card2.getId)                     //> card2-1561408618
list(card1.getId)= card1
list(card2.getId)=card2
println(list)                                     //> Map(1561408618 -> main.scala.data.IdCard@5d11346a, 802581203 -> main.scala.d
                                                  //| ata.IdCard@2fd66ad3)
list-=card2.getId                                 //> res0: scala.collection.mutable.HashMap[Int,main.scala.data.IdCard] = Map(802
                                                  //| 581203 -> main.scala.data.IdCard@2fd66ad3)

println(list)                                     //> Map(802581203 -> main.scala.data.IdCard@2fd66ad3)
println("Hello"+"\n"+"Hello")                     //> Hello
                                                  //| Hello
}