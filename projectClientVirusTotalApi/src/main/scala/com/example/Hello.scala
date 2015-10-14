package com.example

import play.api.libs.json._
import scalaj.http.Http

object Hello {

  def responseFromApi(apiKey: String, name: String, url: String): String = {
    name match {
      case "scan" => Http("https://www.virustotal.com/vtapi/v2/url/scan").postForm(Seq("url" -> url, "apikey" -> apiKey)).asString.body
      case "report" => Http("https://www.virustotal.com/vtapi/v2/url/report").postForm(Seq("resource" -> url, "apikey" -> apiKey)).asString.body
    }
  }

  def statusScan(json: JsValue) = {
    if ((json \ "response_code").as[Int] == 1) println("Resource '" + (json \ "resource").as[String] + " was send to VirusTotal Public API v2.0")
  }

  def statusReport(json: JsValue) = {
    val resource = (json \ "resource").as[String]

    if ((json \ "positives").as[Int] != 0) {
      val detected = (json \ "positives").as[Int]
      val amountScanners = (json \ "total").as[Int]

      println("****\nDetected " + detected + " warnings from " + amountScanners + " URLs Scanners for resource: " + resource)

      val check = (json \ "scans").as[JsObject]

      check.fieldSet.foreach(x =>
        if ((x._2 \ "detected").as[Boolean] == true) println("Report from Scanner \'" + x._1 + "\' is: " + (x._2) + "\n****"))
    }

    else println("Warnings for \'" + resource + "\' is not detected.")
  }

  def main(args: Array[String]): Unit = {

    val apiKey = args(0)
    val command = args(1)
    val url = args.drop(2).toSeq.grouped(4)

    command match {
      case "scan" =>
        url.foreach(
          x => {
            if (x.size > 1) Json.parse(responseFromApi(apiKey, "scan", x.mkString("\n"))).as[JsArray].value.foreach(statusScan)
            else
              statusScan(Json.parse(responseFromApi(apiKey, "scan", x.mkString)))
          }
        )

      case "report" =>
        url.foreach(
          x => {
            if (x.size > 1) Json.parse(responseFromApi(apiKey, "report", x.mkString("\n"))).as[JsArray].value.foreach(statusReport)
            else
              statusReport(Json.parse(responseFromApi(apiKey, "report", x.mkString)))
          }
        )

    }

  }
}


