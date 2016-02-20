package repository.anormimpl

import anorm.SqlParser._
import anorm._
import models.Event
import models.shared.{Page, QueryUtils}
import play.api.Play.current
import play.api.db.DB
import repository.EventRepositoryComponent

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait EventRepositoryAnormComponent extends EventRepositoryComponent {

  def eventLocator = new EventLocatorAnorm

  def eventUpdator = new EventUpdatorAnorm

  /** generic insert */
  /*def insert(table: String, ps: Seq[(Any, ParameterValue)]) = DB.withConnection { implicit c =>
    SQL("INSERT INTO " + table + paramsToInsert(ps)).on(ps).execute
  }*/

  /** re-usable mapping */
  def toParams(event: Event): Seq[NamedParameter] = Seq(
    "name" -> event.name,
    "description" -> event.description,
    "status" -> event.status,
    "website" -> event.webSite,
    "path" -> event.path)

  class EventUpdatorAnorm extends EventUpdator {

    def saveEvent(event: Event) = {

      val params = toParams(event)

      val sql = "INSERT INTO event" + QueryUtils.paramsToInsert(params)

      //println(sql)

      val id = DB.withConnection { implicit connection =>
        SQL(sql).on(params: _*).executeInsert()
      }

    }

    def updateEvent(event: Event) = {

      val fieldParams = toParams(event)

      val sql = "UPDATE event" + QueryUtils.paramsToUpdate(fieldParams) + " where id = {id}"

      val advanced: Seq[NamedParameter] = Seq("id" -> event.id)

      val params: Seq[NamedParameter] = fieldParams ++ advanced

      //println(sql)

      val id = DB.withConnection { implicit connection =>
        SQL(sql).on(params: _*).executeUpdate()
      }

    }

    def deleteEvent(eventId: Long) = {

      val id = DB.withTransaction { implicit connection =>
        SQL("delete from Guest where event_id = {event_id}").on("event_id" -> eventId).executeUpdate()
        SQL("delete from Event where id = {event_id}").on("event_id" -> eventId).executeUpdate()
      }

    }

  }

  class EventLocatorAnorm extends EventLocator {

    def findEventList(pageNumber: Int, pageSize: Int, direction: String, column: String): Page[Event] = {


      val offset = pageSize * pageNumber

      val listParams = QueryUtils.listParams(pageSize, offset, column, direction)

      //println(listParams)

      val sql = "select  * from event" + QueryUtils.paramsToList(column, direction)

      //println(sql)

      DB.withConnection { implicit connection =>
        val events: List[Event] =
          SQL(sql).on(listParams: _*).as(Event.simple2.*)

        val totalRows = SQL("select count(*) from event").as(scalar[Long].single)

        val totalPages = QueryUtils.getTotalPages(pageSize, totalRows)

        Page[Event](events, pageNumber, offset, totalPages)


      }

    }

    def findOne(eventId: Long): Option[Event] = {
      Event.findById(eventId)
    }

  }

}
