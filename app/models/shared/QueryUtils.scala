package models.shared

import anorm.NamedParameter

/**
 * @author adelfiri,
 * @since 05 February 2016
 */
object QueryUtils {

  def paramsToInsert(params: Seq[NamedParameter]): String = "(id, " + params.map(_.name).mkString(",") + ") VALUES " +
    "(nextval('hibernate_sequence')," + params.map("{" + _.name + "}").mkString(",") + ")"

  def paramsToUpdate(params: Seq[NamedParameter]): String = " SET " +
    params.map { case (parameter) => parameter.name + " = {" + parameter.name + "}" }.mkString(",")


  //_.name + "={"   + "}").mkString(",")

  def paramsToList(column: String, direction: String) : String = " ORDER BY "+ column + "  " + direction + "  LIMIT {pagesize} OFFSET {offset}"

  def listParams(pagesize: Int, offset: Int, column: String, direction: String): Seq[NamedParameter] = Seq(
    "pagesize" -> pagesize,
    "offset" -> offset,
    "column" -> column,
    "direction" -> direction)

  def getTotalPages(size: Long, total: Long): Int = {
     if (size == 0) 1 else Math.ceil(total.toDouble / size.toDouble).toInt
  }


}
