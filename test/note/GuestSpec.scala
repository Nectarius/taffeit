package note

import models.{Event, Guest, Account}
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test.FakeApplication
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class GuestSpec extends Specification {



  /*  "Guest.findById" in {
      running(FakeApplication()) {

        val id = 8l

        val guest= Guest.findById(id)

        println(guest.get.name)

        //print("id = " + note.get.id)

        guest must be not
      }
    }*/

  "Guest.list" in {
    running(FakeApplication()) {



      val page = Guest.list(6);

      page.content.foreach {
        println
      }


      //print("id = " + note.get.id)

      page  must be not
    }
  }

  /*"Note.deleteNote" in {
    running(FakeApplication()) {

      val id = 5l;

      val note = Note.deleteNote(5l);

      //println(note.get.theme)

      //print("id = " + note.get.id)

      note must be not
    }
  }*/

/*  "Note.save" in {
    running(FakeApplication()) {



      val id = Note.save("test")

      println(id)

      //print("id = " + note.get.id)

      id must be not
    }
  }*/

}