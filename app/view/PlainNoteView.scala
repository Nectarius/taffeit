package view

import java.util.Date

/**
 * Created by adelfiri on 3/16/15.
 */
case class PlainNoteView (
  id: Long,
  theme: String,
  lastUpdateTime: Date,
  createTime: Date,
  content: String
)
