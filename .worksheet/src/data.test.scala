package data
import scala.util.parsing.json._
import main.scala.data._
import scala.collection.mutable._

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(215); 
    
 
val result = JSON.parseFull(
"""{"email": "jvalant@gmail.com", "password": "secret"}""");System.out.println("""result  : Option[Any] = """ + $show(result ));$skip(132); 
 
 
result match {
case Some(e) => println(e) // => Map(name -> Naoki, lang -> List(Java, Scala))
case None => println("Failed.")
};$skip(35); 


var list = HashMap[Int,IdCard]();System.out.println("""list  : scala.collection.mutable.HashMap[Int,main.scala.data.IdCard] = """ + $show(list ));$skip(44); 
var card1 = new IdCard("card1","1","date1");System.out.println("""card1  : main.scala.data.IdCard = """ + $show(card1 ));$skip(45); 
var card2 =  new IdCard("card2","2","date2");System.out.println("""card2  : main.scala.data.IdCard = """ + $show(card2 ));$skip(30); 
println("card1-"+card1.getId);$skip(30); 
println("card2-"+card2.getId);$skip(25); 
list(card1.getId)= card1;$skip(24); 
list(card2.getId)=card2;$skip(14); 
println(list);$skip(18); val res$0 = 
list-=card2.getId;System.out.println("""res0: scala.collection.mutable.HashMap[Int,main.scala.data.IdCard] = """ + $show(res$0));$skip(15); 

println(list);$skip(30); 
println("Hello"+"\n"+"Hello")}
}
