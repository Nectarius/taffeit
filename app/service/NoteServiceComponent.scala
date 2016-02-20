package service

import models.Note
import models.shared.Page

/**
 * @author adelfiri, " .. "
 * @since 07 February 2016
 */
trait NoteServiceComponent {


  trait NoteService {

    def findAllNoteList(author: String, pageNumber: Int, pageSize: Int, direction: String, column: String): Page[Note]

    def findOne(noteId: Long): Option[Note]

    def saveNote(note: Note, author: String)

    def updateNote(note: Note)

    def deleteNote(noteId: Long)

  }

}
