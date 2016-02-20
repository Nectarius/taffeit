package repository

import models.Note
import models.shared.Page

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait NoteRepositoryComponent {

  def noteLocator: NoteLocator

  def noteUpdator: NoteUpdator

  trait NoteLocator {

    def findAllNoteList(author: String, pageNumber: Int, pageSize: Int, direction: String, column: String): Page[Note]

    def findOne(noteId: Long): Option[Note]

  }

  trait NoteUpdator {

    def saveNote(note: Note, author: String): Option[Long]

    def updateNote(note: Note)

    def deleteNote(noteId: Long)

  }

}
