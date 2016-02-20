package models.shared

import models.{Guest, Event}

/**
 * Helper for pagination.
 */
case class Page[T](content: List[T], page: Int, offset: Long, totalPages: Long) {
  lazy val prev = Option(page - 1)
  lazy val next = Option(page + 1)
}

/**
 * Helper for pagination.
 */
case class PageGuestView(content: List[Guest], event: Event, page: Int, offset: Long, totalPages: Long) {
  lazy val prev = Option(page - 1)
  lazy val next = Option(page + 1)
}


