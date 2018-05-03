import java.io.{File, PrintWriter}

import com.mifmif.common.regex.Generex
import play.api.libs.json.{JsValue, Json, OFormat}

import scala.io.Source


case class LabelUnTouched(label: String, `type`: String, patterns: List[String])

case class LabelTouched(label: String, `type`: String, patterns: List[Generex])


object Test extends App {

  import java.util.Random

  // ...
  val rand = new Random(System.currentTimeMillis())

  implicit val implicitRead: OFormat[LabelUnTouched] = Json.format[LabelUnTouched]

  private val labelsAsString: JsValue = Json.parse(Source.fromFile("/Users/pvincent/Desktop/labelstore.json").mkString)
  private val labels: List[LabelTouched] = labelsAsString.as[List[LabelUnTouched]].filter(x => x.`type` == "regex" && x.label != "email").map(untocuhed => {
    LabelTouched(untocuhed.label, untocuhed.`type`, untocuhed.patterns.map(new Generex(_)))
  })

  private val header: String = labels.map(_.label).mkString(",")

  private val data: String = (0 to 1000).map(
    rowNumber => {
      val row: List[String] = labels.map(
        label => {
          val random_index = rand.nextInt(label.patterns.length)
          label.patterns(random_index).random()
        }
      )
      row.mkString("*****")
    }
  ).mkString("\n") + "\n"

  val pw = new PrintWriter(new File("/Users/pvincent/Desktop/outputFile"))
  pw.write(header + "\n" + data)
  pw.close()


  //  labels.foreach(
  //    label => {
  //      println(s"processing label $label")
  //      val rows = (0 to 1000).map(
  //        _ => {
  //          val random_index = rand.nextInt(label.patterns.length)
  //          label.patterns(random_index).random()
  //        }
  //      ).mkString("\n")
  //      val pw = new PrintWriter(new File(s"/Users/pvincent/Desktop/data_generate/${label.label}_data"))
  //      pw.write(label.label + "\n" + rows)
  //      pw.close()
  //    }
  //  )


  //data_generate


  //  import nl.flotsam.xeger.Xeger
  //
  //  val regex = "[ab]{4,6}c"
  //  val generator = new Xeger(regex)
  //  val result = generator.generate
  //  assert(result.matches(regex))
}