package controllers

import play.api.libs.json.Json
import play.api.mvc._


object Application extends Controller {

  def index = Action {
    Ok(views.html.main())
  }

  def info = Action {

    /*def groupViewToJson[T](entityList: List[T])(implicit writer:Writes[T]): JsValue = {
      val jsonList = Json.toJson(
        entityList.map( m => Json.toJson(m))
      )
      jsonList
    }*/

    implicit val tweetFormat = Json.format[Twig]
    val str = Json.toJson(Twig("44" ))
    Ok(str)
  }

}
