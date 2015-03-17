import models.Note
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import _root_.anorm.SqlParser._
import _root_.anorm._
import _root_.anorm.~
import play.api.db.DB
import play.api.test.FakeApplication
import play.api.test.Helpers._
import play.api.Play.current

@RunWith(classOf[JUnitRunner])
class DraftSpec extends Specification {

  // -- Date helpers
  

  
  // --

  //val c = Computer.findById(1)
  //println(list)

  "Application" should {

    "redirect to the computer list on /" in {
      running(FakeApplication()) {

        val id = 5l;

        val note = DB.withConnection { implicit connection =>
          SQL("select * from note where id = {id}").on('id -> id).as(Note.simple.singleOpt)
        }

        //print("id = " + note.get.id)

        note must be not
      }
    }
  }
}