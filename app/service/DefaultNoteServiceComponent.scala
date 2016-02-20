package service

import models.Note
import models.shared.Page
import repository.NoteRepositoryComponent

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait DefaultNoteServiceComponent extends NoteServiceComponent {

  // (self-type annotation ) loosely speaking it means that this trait depends upon an implementation of the NoteRepositoryComponent being injected

  this: NoteRepositoryComponent =>

  def noteService = new DefaultNoteService()

  class DefaultNoteService extends NoteService {
    def findAllNoteList(author: String, pageNumber: Int, pageSize: Int, direction: String, column: String): Page[Note] = {
      noteLocator.findAllNoteList(author, pageNumber, pageSize, direction, column)
    }

    def findOne(noteId: Long): Option[Note] = {
      noteLocator.findOne(noteId)
    }

    def saveNote(note: Note, author: String) = {
      noteUpdator.saveNote(note, author)
    }

    def updateNote(note: Note) = {
      noteUpdator.updateNote(note);
    }

    def deleteNote(noteId: Long) = {
      noteUpdator.deleteNote(noteId)
    }

  }

}
