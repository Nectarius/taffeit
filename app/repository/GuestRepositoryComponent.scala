package repository

import models.{Guest, Event}
import models.shared.{PageGuestView, Page}

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait GuestRepositoryComponent {

  def guestLocator: GuestLocator

  def guestUpdator: GuestUpdator

  trait GuestLocator {

    def findGuestList(eventId: Long, pageNumber: Int, pageSize: Int, direction: String, column: String): PageGuestView

    def findOne(eventId: Long): Option[Guest]

    def findLastEventId(): Long

  }

  trait GuestUpdator {

    def saveGuest(guest: Guest, eventId: Long)

    def updateGuest(guest: Guest)

    def deleteGuest(guestId: Long)

  }

}
