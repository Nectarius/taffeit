package controllers


import models.shared.{PageGuestView, Page}
import play.api.Play.current
import controllers.Application._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.cache._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.data._
import play.api.data.Forms._
import play.api._
import models._
import repository.anormimpl.{NoteRepositoryAnormComponent, GuestRepositoryAnormComponent, EventRepositoryAnormComponent}
import service.{DefaultGuestServiceComponent, DefaultEventServiceComponent, DefaultNoteServiceComponent}

import scala.concurrent.duration._

object Dashboard extends Controller with Secured{

  /*def index = Action {
    Ok(views.html.main())
  }*/

  def index = IsAuthenticated { login => _ =>
    Account.findByLogin(login).map { account =>
      Ok(
        views.html.dashboard(account)
      )
    }.getOrElse(Forbidden)
  }

  /**
   * @deprecated
   *
   * @param account
   * @return
   */
  private def getInfo(account: Account):play.api.libs.json.JsValue = {
    implicit val tweetFormat = Json.format[Twig]
    Json.toJson(Twig("44", account.login))
  }

  // notes

  val noteServiceComponent = new DefaultNoteServiceComponent with NoteRepositoryAnormComponent {

  }

  val noteService = noteServiceComponent.noteService


  def findNoteList(pageNumber: Integer, pageSize: Integer,  direction: String, column: String) = IsAuthenticated { login => _ =>
    Account.findByLogin(login).map { account =>
      //, pageSize: Integer,  direction: String, column: String
      //implicit val tweetFormat = Json.format[Page[Note]]

      implicit val tweetFormat: Format[Note] = Json.format[Note]

      implicit def pageFormat[T : Format]: Format[Page[T]] = (
    (__ \ "content").format[List[T]] ~ (__ \ "page").format[Int] ~
    (__ \ "offset").format[Long] ~
    (__ \ "totalPages").format[Long]) (Page.apply, unlift(Page.unapply))

      Ok(Json.toJson(noteService.findAllNoteList(login, pageNumber,  pageSize, direction, column)))

    }.getOrElse(Forbidden)
  }

 /* def findNoteList(pageNumber: Integer, pageSize: Integer,  direction: String, column: String ) = Action {

    //, pageSize: Integer,  direction: String, column: String
    //implicit val tweetFormat = Json.format[Page[Note]]

    implicit val tweetFormat: Format[Note] = Json.format[Note]

    implicit def pageFormat[T : Format]: Format[Page[T]] = (
    (__ \ "content").format[List[T]] ~ (__ \ "page").format[Int] ~
    (__ \ "offset").format[Long] ~
    (__ \ "totalPages").format[Long]) (Page.apply, unlift(Page.unapply))

    Ok(Json.toJson(noteService.findAllNoteList("fake", pageNumber,  pageSize, direction, column)))

  }*/

  def findOne(noteId: Long) = Action {

    implicit val tweetFormat: Format[Note] = Json.format[Note]

    Ok(Json.toJson(noteService.findOne(noteId)))

  }

  /**
   *  It will only be for preview version ....
   * @param f
   * @return
   */

  def ajaxAuth(f: String => Request[JsValue] => Result) = Security.Authenticated(
    request => request.session.get("login"),
    _ => Results.Redirect(routes.Application.login)) { login =>
    Action(BodyParsers.parse.json) {
      f(login)
    }
  }

  def onSaveNote(request : Request[JsValue], login : String) = {

    //println(login)

      val b = request.body.validate[Note]
      b.fold(
        errors => {
          BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
        },
        note => {

          if(note.id==None) noteService.saveNote(note, login) else noteService.updateNote(note)

          Ok(Json.obj("status" -> "OK"))
        }
      )
  }

  def saveNote = ajaxAuth { login => request =>
    Account.findByLogin(login).map { account =>
      onSaveNote(request, login)
    }.getOrElse(Forbidden)
  }



  /* def saveNote = Action(BodyParsers.parse.json) {



   }*/

  def deleteNote(noteId: Long) = Action {

    implicit val tweetFormat: Format[Note] = Json.format[Note]

    noteService.deleteNote(noteId)
    Ok(Json.obj("status" -> "OK"))
  }

  // events

  val eventServiceComponent = new DefaultEventServiceComponent with EventRepositoryAnormComponent {

  }

  val eventService = eventServiceComponent.eventService

  def findEventList(pageNumber: Integer, pageSize: Integer,  direction: String, column: String ) = Action {

    //, pageSize: Integer,  direction: String, column: String
    //implicit val tweetFormat = Json.format[Page[Note]]

    implicit val tweetFormat: Format[Event] = Json.format[Event]

    implicit def pageFormat[T : Format]: Format[Page[T]] = (
    (__ \ "content").format[List[T]] ~ (__ \ "page").format[Int] ~
    (__ \ "offset").format[Long] ~
    (__ \ "totalPages").format[Long]) (Page.apply, unlift(Page.unapply))

    Ok(Json.toJson(eventService.findEventList(pageNumber,  pageSize, direction, column)))

  }

  def findEvent(eventId: Long) = Action {

    implicit val tweetFormat: Format[Event] = Json.format[Event]

    Ok(Json.toJson(eventService.findOne(eventId)))

  }

  def deleteEvent(eventId: Long) = Action {

    eventService.deleteEvent(eventId)
    Ok(Json.obj("status" -> "OK"))

  }

  def saveEvent = Action(BodyParsers.parse.json) { request =>

    val b = request.body.validate[Event]
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      event => {

        eventService.saveOrUpdateEvent(event)
        Ok(Json.obj("status" -> "OK"))
      }
    )

  }



  def deleteGuest(guestId: Long) = Action {

    guestService.deleteGuest(guestId)
    Ok(Json.obj("status" -> "OK"))

  }


  def updateGuest = Action(BodyParsers.parse.json) { request =>

    val b = request.body.validate[Guest]
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      guest => {

        guestService.updateGuest(guest)
        Ok(Json.obj("status" -> "OK"))
      }
    )

  }

  def saveGuest(eventId : Long) = Action(BodyParsers.parse.json) { request =>

    val b = request.body.validate[Guest]
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      guest => {

        guestService.saveGuest(guest, eventId)
        Ok(Json.obj("status" -> "OK"))
      }
    )

  }

  // guests

  val guestServiceComponent = new DefaultGuestServiceComponent with GuestRepositoryAnormComponent {

  }

  val guestService = guestServiceComponent.guestService

  def findGuestList(eventId : Long, pageNumber: Integer, pageSize: Integer,  direction: String, column: String ) = Action {

    //, pageSize: Integer,  direction: String, column: String
    //implicit val tweetFormat = Json.format[Page[Note]]

    implicit val tweetFormat: Format[Guest] = Json.format[Guest]

    implicit val tweetFormat2: Format[Event] = Json.format[Event]

    implicit def pageFormat: Format[PageGuestView] = (
    (__ \ "content").format[List[Guest]]~
    (__ \ "event").format[Event]~
    (__ \ "page").format[Int] ~
    (__ \ "offset").format[Long] ~
    (__ \ "totalPages").format[Long]) (PageGuestView.apply, unlift(PageGuestView.unapply))

    Ok(Json.toJson(guestService.findGuestList(eventId, pageNumber,  pageSize, direction, column)))

  }

  def findGuest(eventId: Long) = Action {

    implicit val tweetFormat: Format[Guest] = Json.format[Guest]

    Ok(Json.toJson(guestService.findGuest(eventId)))

  }


  /**
   * Retrieves all routes via reflection.
   * http://stackoverflow.com/questions/12012703/less-verbose-way-of-generating-play-2s-javascript-router
   * @todo If you have controllers in multiple packages, you need to add each package here.
   */
  val routeCache = {
    val jsRoutesClasses = Seq(classOf[routes.javascript]) // TODO add your own packages
    jsRoutesClasses.flatMap { jsRoutesClass =>
      val controllers = jsRoutesClass.getFields.map(_.get(null))
      controllers.flatMap { controller =>
        controller.getClass.getDeclaredMethods.filter(_.getName != "_defaultPrefix").map { action =>
          action.invoke(controller).asInstanceOf[play.api.routing.JavaScriptReverseRoute]
        }
      }
    }
  }

 /* def Caching(key: String, okDuration: Duration) =
    new Cached(cache)
      .status(_ => key, OK, okDuration.toSeconds.toInt)
      .includeStatus(NOT_FOUND, 5.minutes.toSeconds.toInt)*/

  /**
   * Returns the JavaScript router that the client can use for "type-safe" routes.
   * Uses browser caching; set duration (in seconds) according to your release cycle.
   * @param varName The name of the global variable, defaults to `jsRoutes`
   */
  def jsRoutes(varName: String = "jsRoutes") =  {
    Action { implicit request =>
      Ok(play.api.routing.JavaScriptReverseRouter(varName)(routeCache: _*)).as(JAVASCRIPT)
    }
  }

}
