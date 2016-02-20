package repository

import models.{Event, Note}
import models.shared.Page

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait EventRepositoryComponent {

  def eventLocator: EventLocator

  def eventUpdator: EventUpdator

  trait EventLocator {

    def findEventList(pageNumber: Int, pageSize: Int, direction: String, column: String): Page[Event]

    def findOne(eventId: Long): Option[Event]

  }

  trait EventUpdator {

    def saveEvent(event: Event)

    def deleteEvent(eventId: Long)

    def updateEvent(event: Event)

  }

}
