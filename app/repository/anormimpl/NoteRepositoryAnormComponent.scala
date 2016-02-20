package repository.anormimpl

import java.util.Date

import anorm.SqlParser._
import anorm._
import models.{Account, Note}
import models.shared.{QueryUtils, Page}
import play.api.db.DB
import repository.NoteRepositoryComponent
import play.api.Play.current

/**
 * @author adelfiri,
 * @since 07 February 2016
 */
trait NoteRepositoryAnormComponent extends NoteRepositoryComponent {

  def noteLocator = new NoteLocatorAnorm

  def noteUpdator = new NoteUpdatorAnorm

  /** re-usable mapping */
  def noteToParams(note: Note): Seq[NamedParameter] = Seq(
    "theme" -> note.theme,
    "text" -> note.text,
    "lastUpdateTime" -> note.lastUpdateTime,
    "createTime" -> note.createTime)

  class NoteLocatorAnorm extends NoteLocator {

    def findAllNoteList(author: String, pageNumber: Int, pageSize: Int, direction: String, column: String): Page[Note] = {

      val authorId = Account.findIdByName(author)

      val sql = "select * from note   where author_id={author_id} " + QueryUtils.paramsToList(column, direction)

      val offset = pageSize * pageNumber

      DB.withConnection { implicit connection =>

        val notes: List[Note] = SQL(sql
        ).on(
          'pagesize -> pageSize,
          'offset -> offset,
          'author_id -> authorId
        ) as (get[Option[Long]]("note.id") ~ get[Option[String]]("note.text") ~ get[String]("note.theme") ~ get[Option[Date]]("note.lastUpdateTime") ~ get[Option[Date]]("note.createTime") *) map {
          case id ~ text ~ theme ~ lastUpdateTime ~ createTime => Note(id, text, theme, lastUpdateTime, createTime)
        }

        //val totalRows = SQL("select count(*) from note where id = {note_id}").on("note_id" -> noteId).as(scalar[Long].single)

        val totalRows = SQL("select count(*) from note where author_id={author_id}").on(
          'author_id -> authorId
        ).as(scalar[Long].single)

        val totalPages = QueryUtils.getTotalPages(pageSize, totalRows)

        Page[Note](notes, pageNumber, offset, totalPages)


      }
    }

    def findOne(noteId: Long): Option[Note] = {
      DB.withConnection { implicit connection =>
        SQL("select * from note where id = {note_id}").on("note_id" -> noteId).as(Note.simple.singleOpt)
      }
    }

  }

  class NoteUpdatorAnorm extends NoteUpdator {

    def saveNote(note: Note, authorName: String): Option[Long] = {

      val authorId = Account.findIdByName(authorName)

      // think of What we could verify here

      val params = noteToParams(note)

      val advanced: Seq[NamedParameter] = Seq("author_id" -> authorId)

      val listParams: Seq[NamedParameter] = params ++ advanced



      val sql = "INSERT INTO note" + QueryUtils.paramsToInsert(listParams)

      //println(sql)

      val id = DB.withConnection { implicit connection =>
        SQL(sql).on(listParams: _*).executeInsert()
      }
      id
    }

    def updateNote(note: Note) = {

      val fieldParams = noteToParams(note)

      val sql = "UPDATE note" + QueryUtils.paramsToUpdate(fieldParams) + " where id = {id}"

      val advanced: Seq[NamedParameter] = Seq("id" -> note.id)

      val params: Seq[NamedParameter] = fieldParams ++ advanced

      val id = DB.withConnection { implicit connection =>
        SQL(sql).on(params: _*).executeUpdate()
      }

    }

    def deleteNote(noteId: Long) = {

      DB.withConnection { implicit connection =>
        SQL("delete from note where id = {note_id}").on('note_id -> noteId).executeUpdate()
      }

    }

  }

}
