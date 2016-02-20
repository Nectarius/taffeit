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
 * Created by adelfiri on 3/16/15.
 */
case class Note(id: Option[Long], text: Option[String], theme: String, lastUpdateTime: Option[Date], createTime: Option[Date]) {

}


object Note {

  val simple = {
    get[Option[Long]]("note.id") ~
      get[Option[String]]("note.text") ~
      get[String]("note.theme") ~
      get[Option[Date]]("note.lastUpdateTime") ~
      get[Option[Date]]("note.createTime") map {
      case id ~ text ~ theme ~ lastUpdateTime ~ createTime => Note(id, text, theme, lastUpdateTime, createTime)

    }
  }

  implicit val noteReads = new Reads[Note] {
    def reads(js: JsValue): JsResult[Note] = JsSuccess({
      Note(
        (js \ "id").asOpt[String].map(f => java.lang.Long.valueOf(f)),
        (js \ "text").asOpt[String],
        (js \ "theme").as[String],
        (js \ "lastUpdateTime").asOpt[Date],
        (js \ "createTime").asOpt[Date]
      )
    })
  }

  /** re-usable mapping */
  def noteToParams(note: Note): Seq[NamedParameter] = Seq(
    "theme" -> note.theme,
    "text" -> note.text,
    "lastUpdateTime" -> note.lastUpdateTime,
    "createTime" -> note.createTime)


}