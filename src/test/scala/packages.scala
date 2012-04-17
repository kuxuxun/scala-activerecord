package com.github.aselab.activerecord

import org.specs2.mutable._
import org.specs2.specification._

import org.squeryl._

object DummyTables extends ActiveRecordTables {
  val dummyModels = table[DummyModel]
  val dummyModels2 = table[DummyModel2]
  val versions = table[VersionRecord]

  def createTestData = (1 to 100).foreach { i =>
    DummyModel.newModel(i, i > 50).save
  }

  override def cleanup = {
    Session.cleanupResources
  }
}

trait ActiveRecordSpecification extends Specification {
  sequential

  def before = {
    schema.initialize(config)
  }

  def after = dsl.transaction {
    schema.cleanup
  }

  def config: Map[String, String]

  def schema: ActiveRecordTables

  override def map(fs: => Fragments) = {
    Step {
      before
    } ^ fs ^ Step {
      after
    }
  }
}
