package models

import anorm.SqlParser._
import anorm.~

/**
 * @author adelfiri,
 * @since 31 January 2016
 */

case class Privilege(id: Long, name: String) {

}

object Privilege {

  val simple = {
    get[Long]("privilege.id") ~
      get[String]("privilege.name") map {
      case id ~ name => Privilege(id, name)
    }


  }

  // It's something to think about

  /* def parser(alias: String): RowParser[User] = {
     getAliased[Long](alias + "_id") ~ getAliased[String](alias + "_name") map {
       case id~name => User(id, name)
     }
   }*/

}