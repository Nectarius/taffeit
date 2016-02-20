package note


import models.Note
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._

import play.api.test.FakeApplication
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class NoteSpec extends Specification {

 /* "Note.list" in {
    running(FakeApplication()) {

      //val id = 98l

      val page = Note.list(0, 10)

      page.collection.foreach {
        println
      }



      //print("id = " + note.get.id)

      page.total must be not
    }
  }*/

    /*"Note.findById" in {
      running(FakeApplication()) {

        val id = 98l

        val note = Note.findById(id)

        println(note.get.theme)

        //print("id = " + note.get.id)

        note must be not
      }
    }*/

  /*"Note.deleteNote" in {
    running(FakeApplication()) {

      val id = 5l;

      val note = Note.deleteNote(5l);

      //println(note.get.theme)

      //print("id = " + note.get.id)

      note must be not
    }
  }*/

  "Note.save" in {
    running(FakeApplication()) {

      val text = "55"

      val theme = "test258"

      val note = Note(None, Option(text), theme, None , None)

      val id = Note.save(note, "Felix")

      println(id)

      //print("id = " + note.get.id)

      id must be not
    }
  }

}