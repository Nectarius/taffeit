package models

import java.util.Date

import _root_.anorm.SqlParser._
import _root_.anorm._
import _root_.anorm.~
import models.shared.{QueryUtils, Page}
import play.api.db.DB
import play.api.Play.current
import play.api.libs.json.{JsSuccess, JsResult, JsValue, Reads}

/**
 * @author adelfiri,
 * @since 02 February 2016
 */

case class Guest(id: Option[Long], name: String, surname: String, brief: Option[String], description: Option[String], byWhomWasAdded: Option[String], transport: Option[String]) {

}

object Guest {

  val simple = {
    get[Long]("guest.id") ~
      get[String]("guest.name") ~
      get[String]("guest.surname") ~
      get[Option[String]]("guest.brief") ~
      get[Option[String]]("guest.description") ~
      get[Option[String]]("guest.byWhomWasAdded") ~
      get[Option[String]]("guest.transport") map {
      case id ~ name ~ surname ~ brief ~ description ~ byWhomWasAdded ~ transport => Guest(Option(id), name, surname, brief, description, byWhomWasAdded, transport)

    }
  }

  implicit val guestsReads = new Reads[Guest] {
    def reads(js: JsValue): JsResult[Guest] = JsSuccess({
      Guest(
        (js \ "id").asOpt[String].map(f => java.lang.Long.valueOf(f)),
        (js \ "name").as[String],
        (js \ "surname").as[String],
        (js \ "brief").asOpt[String],
        (js \ "description").asOpt[String],
        (js \ "byWhomWasAdded").asOpt[String],
        (js \ "transport").asOpt[String]
      )
    })
  }

}

