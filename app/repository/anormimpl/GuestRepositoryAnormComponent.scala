package repository.anormimpl

import anorm.SqlParser._
import anorm._
import models.shared.{PageGuestView, QueryUtils}
import models.{Event, Guest}
import play.api.Play.current
import play.api.db.DB
import repository.GuestRepositoryComponent

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait GuestRepositoryAnormComponent extends GuestRepositoryComponent {

  def guestLocator = new GuestLocatorAnorm

  def guestUpdator = new GuestUpdatorAnorm

  def toParams(guest: Guest): Seq[NamedParameter] = Seq(
    "name" -> guest.name,
    "surname" -> guest.surname,
    "brief" -> guest.brief,
    "description" -> guest.description,
    "byWhomWasAdded" -> guest.byWhomWasAdded,
    "transport" -> guest.transport)


  class GuestUpdatorAnorm extends GuestUpdator {

    def saveGuest(guest: Guest, eventId: Long) = {

      val fieldParams = toParams(guest)

      val advanced: Seq[NamedParameter] = Seq("event_id" -> eventId)

      val params: Seq[NamedParameter] = fieldParams ++ advanced

      val sql = "INSERT INTO guest" + QueryUtils.paramsToInsert(params)

      //println(sql)

      val id = DB.withConnection { implicit connection =>
        SQL(sql).on(params: _*).executeInsert()
      }

    }

    def updateGuest(guest: Guest) = {

      val fieldParams = toParams(guest)

      val sql = "UPDATE guest" + QueryUtils.paramsToUpdate(fieldParams) + " where id = {id}"

      val advanced: Seq[NamedParameter] = Seq("id" -> guest.id)

      val params: Seq[NamedParameter] = fieldParams ++ advanced

      val id = DB.withConnection { implicit connection =>
        SQL(sql).on(params: _*).executeUpdate()
      }

    }

    def deleteGuest(guestId: Long) = {

      val id = DB.withTransaction { implicit connection =>
        SQL("delete from Guest where id = {guest_id}").on("guest_id" -> guestId).executeUpdate()
      }

    }

  }

  class GuestLocatorAnorm extends GuestLocator {


    def findGuestList(eventId: Long, pageNumber: Int, pageSize: Int, direction: String, column: String): PageGuestView = {


      //var direction = "DESC"

      // it's something to think about

      //if (direction.equalsIgnoreCase("ASC")) {
      //  direction = "ASC"
      //}

      val offset = pageSize * pageNumber

      val advanced: Seq[NamedParameter] = Seq("event_id" -> eventId)

      val listParams: Seq[NamedParameter] = QueryUtils.listParams(pageSize, offset, column, direction) ++ advanced



      // println(listParams)

      val sql = "select  * from guest where event_id = {event_id}" + QueryUtils.paramsToList(column, direction)

      // println(sql)

      val sqlSelectEvent = "select  * from event where id = {event_id}"

      // println(sqlSelectEvent)

      DB.withConnection { implicit connection =>

       // println(sqlSelectEvent)
        val guests =
          SQL(sql).on(listParams: _*).as(Guest.simple.*)

        val event: Event = SQL(sqlSelectEvent).on("event_id" -> eventId).as(Event.simple2.single)

        val totalRows = SQL("select count(*) from guest where event_id = {event_id}").on("event_id" -> eventId).as(scalar[Long].single)

        val totalPages = QueryUtils.getTotalPages(pageSize, totalRows)

        PageGuestView(guests, event, pageNumber, offset, totalPages)


      }
    }

    def findLastEventId(): Long = {
      DB.withConnection { implicit connection =>
        SQL("select * from event where  id=(select max(id) from event)").as(scalar[Long].single)
      }
    }


    def findOne(noteId: Long): Option[Guest] = {
      DB.withConnection { implicit connection =>
        SQL("select * from guest where id = {id}").on('id -> noteId).as(Guest.simple.singleOpt)
      }
    }

  }

}
