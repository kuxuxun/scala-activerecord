package com.github.aselab.activerecord

import org.specs2.mutable._
import models._
import java.util.Date
import java.sql.Timestamp

object TimestampsSpec extends DatabaseSpecification {
  "Timestamps" should {
    "create" in {
      val model = TimestampsModel("")
      model.createdAt must beNull
      model.save
      model.createdAt must not(beNull)
      model.updatedAt must not(beNull)
    }
  }

  "Datestamps" should {
    "create" in {
      val model = DatestampsModel("")
      model.createdOn must beNull
      model.save
      model.createdOn must not(beNull)
      model.updatedOn must not(beNull)
    }
  }
}
