package models


import anorm.SqlParser._
import anorm._
import play.api.db.DB
import play.api.Play.current

/**
 * @author adelfiri,
 * @since 31 January 2016
 */
case class Account(id: Long, login: String, password: String, name: Option[String], privileges: List[Privilege]) {


  //val list : List[(String, String)] = List("55","77","88","99").sliding(2).map(x => (x.head, x.tail.head)).toList
  //List((55,77), (77,88), (88,99))
  // var newList = list.map {case(a,b) => (b)}.toList

  //.map {
  //  case (account, privileges) => account.copy(privileges = privileges.map {case(a,b) => (b)}.toList)
  // }

}

object Account {

  val simple = {
    get[Long]("account.id") ~
      get[String]("account.login") ~
      get[String]("account.password") ~
      get[Option[String]]("account.name") map {
      case id ~ login ~ password ~ name => Account(id, login, password, name, List())

    }
  }

  val withPrivileges = {
    get[Long]("account.id") ~
      get[String]("account.login") ~
      get[String]("account.password") ~
      get[Option[String]]("account.name") ~
      Privilege.simple map {
      case id ~ login ~ password ~ name ~ privilege => (Account(id, login, password, name, List()), privilege)
    }
  }

  /**
   * Authenticate a User.
   */
  def authenticate(login: String, password: String): Option[Account] = {
    DB.withConnection { implicit connection =>
      SQL("select * from account where login = {login} and password = {password}"
      ).on(
        'login -> login,
        'password -> password
      ).as(Account.simple.singleOpt)
    }
  }

  /**
   * It something to think about ....
   *
   * @param login
   * @return
   */
  def findIdByName(login: String): Option[Long] = {
    val result = DB.withConnection { implicit connection =>
      SQL("select id from   account  a where a.login = {login} ").on("login" -> login).as(scalar[Long].singleOpt)
    }
    result
  }

  def findByIdWithPrivileges(id: Long): Account = {
    // singleOpt will just return None when there is no result
    val resultSeq = DB.withConnection { implicit connection =>
      SQL("select * from   account as a left join account_privilege as r on  a.id = r.account_id  left join privilege as p on p.id = r.privilegelist_id  where a.id = {id} ").on('id -> id).as(Account.withPrivileges *).groupBy(_._1)
    }

    // dreadful ...

    val privileges = resultSeq.toSeq.unzip._2.head.map { case (a, b) => (b) }

    val account = resultSeq.toSeq.unzip._1.head

    val result = account.copy(privileges = privileges)

    result
  }

  def findById(id: Long): Option[Account] = {
    // singleOpt will just return None when there is no result
    DB.withConnection { implicit connection =>
      SQL("select * from account where id = {id}").on('id -> id).as(Account.simple.singleOpt)
    }
  }

  def findByLogin(login: String): Option[Account] = {
    // singleOpt will just return None when there is no result
    DB.withConnection { implicit connection =>
      SQL("select * from account where login = {login}").on('login -> login).as(Account.simple.singleOpt)
    }
  }


}