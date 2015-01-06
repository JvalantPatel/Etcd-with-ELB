package utility

import java.util.Calendar
import java.util.Date
import java.text.SimpleDateFormat

object TimeUtility {

  def getCurrentTime: String =
    {
      var today: Date = Calendar.getInstance().getTime();
      var formatter: SimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh.mm.ss");
      var time: String = formatter.format(today);
      time
    }

  def getGivenTime(inputTime: String): String =
    {
      var formatter: SimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh.mm.ss");
      var time: String = formatter.format(inputTime);
      time

    }
  

}