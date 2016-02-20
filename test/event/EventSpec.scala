package event

import anorm.NotAssigned
import models.Event
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test.FakeApplication
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class EventSpec extends Specification {


  "Event.list" in {
    running(FakeApplication()) {



      val page = Event.list();

      page.content.foreach {
        println
      }


      //print("id = " + note.get.id)

      page  must be not
    }
  }

    /*"Event.findById" in {
      running(FakeApplication()) {

        val id = 6l

        val event= Event.findById(id)

        println(event.get.name)

        //print("id = " + note.get.id)

        event must be not
      }
    }*/

/*  "Event.save" in {
    running(FakeApplication()) {

      val event = Event(None, "test59", true ,"Something to do", "http://nowhere", "path")

      val id = Event.save(event)

      id must be not
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

/*  "Note.save" in {
    running(FakeApplication()) {



      val id = Note.save("test")

      println(id)

      //print("id = " + note.get.id)

      id must be not
    }
  }*/

}