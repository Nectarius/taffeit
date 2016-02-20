package note

import models.{Privilege, Account, Note}
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test.FakeApplication
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class AccountSpec extends Specification {



    "Account.findById" in {
      running(FakeApplication()) {

        val id = 2l

        //Map[(Account), List[(Account, Privilege)]]

        val account = Account.findByIdWithPrivileges(id)

        // income Map[(Account), List[(Account, Privilege)]]
/*
        val privileges = account.toSeq.unzip._2.head

        val result = privileges.map{case(a,b) => (b)}*/


          println (account)


        //val pr = account.map {case(a,b) => (b)}.toList

        //for pr

       // val result = pr.map {case (a,b) => b}

        //val privileges = account.toSeq.unzip._2

       // val result = privileges.map{case(a,b) => (b)}.toList

       //val result = pr;

        //.map{case(a,b) => (b)}.toList



       // result.foreach {
       //   println
       // }

        //print("id = " + note.get.id)

        account must be not
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