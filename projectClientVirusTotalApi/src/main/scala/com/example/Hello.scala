package com.example

import play.api.libs.json._
import scalaj.http.Http

object Hello {

  def response(apiKey: String, name: String, url: String): String = {
    name match {
      case "scan" => Http("https://www.virustotal.com/vtapi/v2/url/scan").postForm(Seq("url" -> url, "apikey" -> apiKey)).asString.body
      case "report" => Http("https://www.virustotal.com/vtapi/v2/url/report").postForm(Seq("resource" -> url, "apikey" -> apiKey)).asString.body
    }
  }

  def statusScan(json: JsValue) = {
    if ((json \ "response_code").as[Int] == 1) println("url '" + (json \ "resource").as[String] + "' was send to VirusTotal Public API v2.0")
  }

  def statusReport(json: JsValue) = {
    if ((json \ "positives").as[Int] != 0) {
      println((json \ "positives").as[Int])
      println((json \ "total").as[Int])
      println((json \ "resource").as[String])

      val check = (json \ "scans").as[JsObject]

      check.fieldSet.foreach(x =>
        if ((x._2 \ "detected").as[Boolean] == true) println("Scanner " + x._1 + " report: " + (x._2)))
    }
  }

  def main(args: Array[String]): Unit = {

    val apiKey = args(0)
    val command = args(1)
    val url = args.drop(2).mkString("\n")
    val amountUrl = args.length - 2

    command match {

      case "scan" =>
        if (amountUrl > 1) {
          Json.parse(response(apiKey, "scan", url)).as[JsArray].value.foreach(statusScan)
        }
        else {
          val json: JsValue = Json.parse(response(apiKey, "scan", url))
          statusScan(json)
        }

      case "report" =>
        if (amountUrl > 1) {
          Json.parse(response(apiKey, "report", url)).as[JsArray].value.foreach(statusReport)
        }
        else {
          val json: JsValue = Json.parse(response(apiKey, "report", url))

          statusReport(json)
        }
    }

  }
}


