package service

import models.{Guest, Event}
import models.shared.{PageGuestView, Page}

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait GuestServiceComponent {

  trait GuestService {

    def findGuestList(eventId: Long, pageNumber: Int, pageSize: Int, direction: String, column: String): PageGuestView

    def findGuest(guestId: Long): Option[Guest]

    def saveGuest(guest: Guest, eventId: Long)

    def updateGuest(guest: Guest)

    def deleteGuest(guestId: Long)

  }

}
