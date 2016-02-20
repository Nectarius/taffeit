package models

/**
 * @author adelfiri,
 * @since 02 February 2016
 */

import java.util.Date
import play.api.mvc._
import anorm.SqlParser._
import anorm._
import models.shared.{QueryUtils, Page}
import play.api.db.DB
import play.api.Play.current
import play.api.libs.json._

import play.api.libs.functional.syntax._

/**
 * @author adelfiri,
 * @since 31 January 2016
 */
case class Event(id: Option[Long], name: String, status: Boolean, description: Option[String], webSite: Option[String], path: Option[String]) {

}

/**
 * Helper for pagination.
 */
/*case class Page[A](events : List[Event], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1)
  lazy val next = Option(page + 1)
}*/

object Event {

  // https://www.playframework.com/documentation/2.4.x/ScalaJsonHttp

  //implicit val eventWrites = Json.writes[Event]

  //implicit val eventReads = Json.reads[Event]

  //https://www.playframework.com/documentation/2.1.1/ScalaJsonCombinators

  //implicit val eventFormatter = Json.format[Event]

  implicit val eventsReads = new Reads[Event] {
    def reads(js: JsValue): JsResult[Event] = JsSuccess({
      Event(
        (js \ "id").asOpt[String].map(f => java.lang.Long.valueOf(f)),
        (js \ "name").as[String],
        (js \ "status").as[Boolean],
        (js \ "description").asOpt[String],
        (js \ "webSite").asOpt[String],
        (js \ "path").asOpt[String]
      )
    })
  }


  /*  implicit val placeReads: Reads[Event] = (
    (JsPath \ "id").read[Long] ,
    (JsPath \ "name").read[String],
    (JsPath \ "status").read[Boolean],
    (JsPath \ "description").read[String],
    (JsPath \ "webSite").read[String],
    (JsPath \ "path").read[String]
  )(Event.apply _)*/

  val simple = {
    get[Long]("event.id") ~
      get[String]("event.name") ~
      get[Option[String]]("event.description") ~
      get[Boolean]("event.status") ~
      get[Option[String]]("event.webSite") ~
      get[Option[String]]("event.path") map {
      case id ~ name ~ description ~ status ~ webSite ~ path => Event(None, name, status, description, webSite, path)

    }
  }

  val simple2 = {
    get[Long]("event.id") ~
      get[String]("event.name") ~
      get[Option[String]]("event.description") ~
      get[Boolean]("event.status") ~
      get[Option[String]]("event.webSite") ~
      get[Option[String]]("event.path") map {
      case id ~ name ~ description ~ status ~ webSite ~ path => Event(Option(id), name, status, description, webSite, path)

    }
  }


  def findById(id: Long): Option[Event] = {
    // singleOpt will just return None when there is no result
    DB.withConnection { implicit connection =>
      SQL("select * from event where id = {id}").on('id -> id).as(Event.simple2.singleOpt)
    }
  }


}