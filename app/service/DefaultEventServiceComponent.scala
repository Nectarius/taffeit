package service

import models.{Event, Note}
import models.shared.Page
import repository.{EventRepositoryComponent, NoteRepositoryComponent}

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait DefaultEventServiceComponent extends EventServiceComponent {

  // (self-type annotation ) loosely speaking it means that this trait depends upon an implementation of the NoteRepositoryComponent being injected

  this: EventRepositoryComponent =>

  def eventService = new DefaultEventService()

  class DefaultEventService extends EventService {

    def findEventList(pageNumber: Int, pageSize: Int, direction: String, column: String): Page[Event] = {
      eventLocator.findEventList(pageNumber, pageSize, direction, column)
    }

    def findOne(eventId: Long): Option[Event] = {
      eventLocator.findOne(eventId)
    }

    def saveOrUpdateEvent(event: Event) = {
      if (event.id == None) eventUpdator.saveEvent(event) else eventUpdator.updateEvent(event)
    }

    def deleteEvent(eventId: Long) = {
      eventUpdator.deleteEvent(eventId)
    }
  }

}
