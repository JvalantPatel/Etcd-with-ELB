package utility

import scala.util.parsing.json.JSON
import data._
import org.apache.log4j.Logger
import utility.JSONParser



object JSONParser {
  
  var logger = Logger.getLogger("ISONParser")
  
  def parseJson(inputString :String):Map[String,String]={
              logger.info(inputString)
              var result = JSON.parseFull(inputString)
    		  var map=Map[String,String]()
    		  var user:User=null
    		 
    		  result match {    		    
    		      case Some(map:Map[String,String]) =>{ logger.info(map) ;map}
    		      case None =>{logger.info("Parsing failed"); Map[String,String]()}
    		  }
    
  }
  
   def checkNull(input:String,map:Map[String,String]):String =
  {
    if(map.keySet.contains(input))
      map(input)
    else
      ""
  }

}