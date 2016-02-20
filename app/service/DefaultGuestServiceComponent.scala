package service

import models.{Guest, Event}
import models.shared.{PageGuestView, Page}
import repository.{GuestRepositoryComponent, EventRepositoryComponent}

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait DefaultGuestServiceComponent extends GuestServiceComponent {

  // (self-type annotation ) loosely speaking it means that this trait depends upon an implementation of the NoteRepositoryComponent being injected

  this: GuestRepositoryComponent =>

  def guestService = new DefaultGuestService()

  class DefaultGuestService extends GuestService {
    def findGuestList(eventId: Long, pageNumber: Int, pageSize: Int, direction: String, column: String): PageGuestView = {

      val eventIdentity = if (eventId == 0) guestLocator.findLastEventId() else eventId

      guestLocator.findGuestList(eventIdentity, pageNumber, pageSize, direction, column)
    }

    def findGuest(eventId: Long): Option[Guest] = {
      guestLocator.findOne(eventId)
    }

    def saveGuest(guest: Guest, eventId: Long) = {

      guestUpdator.saveGuest(guest, eventId)


    }

    def updateGuest(guest: Guest) = {

      guestUpdator.updateGuest(guest)

    }

    def deleteGuest(guestId: Long) = {

      guestUpdator.deleteGuest(guestId)

    }
  }

}
