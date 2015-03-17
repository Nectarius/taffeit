package models

import java.util.Date

import _root_.anorm.SqlParser._
import _root_.anorm._
import _root_.anorm.~
import play.api.db.DB
import play.api.Play.current
/**
 * Created by adelfiri on 3/16/15.
 */
case class Note (id : Option[Long], theme : String, lastUpdateTime : Option[Date], createTime : Option[Date])

object Note {

  val simple = {
    get[Option[Long]]("note.id") ~
      get[String]("note.theme") ~
      get[Option[Date]]("note.lastUpdateTime") ~
      get[Option[Date]]("note.createTime")  map {
      case id~theme~lastUpdateTime~createTime => Note(id, theme, lastUpdateTime, createTime)
    }
  }

  def findById(id: Long): Option[Note] = {
    DB.withConnection { implicit connection =>
      SQL("select * from computer where id = {id}").on('id -> id).as(Note.simple.singleOpt)
    }
  }

}