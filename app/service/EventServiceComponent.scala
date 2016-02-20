package service

import models.{Event, Note}
import models.shared.Page

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait EventServiceComponent {

  trait EventService {

    def findEventList(pageNumber: Int, pageSize: Int, direction: String, column: String): Page[Event]

    def findOne(noteId: Long): Option[Event]

    def saveOrUpdateEvent(event: Event)

    def deleteEvent(eventId: Long)

  }

}
