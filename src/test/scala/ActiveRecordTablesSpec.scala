package com.github.aselab.activerecord

import org.specs2.mutable._
import org.specs2.specification._

import dsl._
import models._

object ActiveRecordTablesSpec extends ActiveRecordSpecification {
  override def before = {
    super.before
    TestTables.createTestData
  }

  "session" should {
    "clean throws exception when not start session" >> {
      TestTables.clean must throwA[ActiveRecordException]
    }

    "start and clean" >> {
      TestTables.start
      val users: List[User] = User.all.toList
      User("testuser").save
      User("testuser2").save
      User("testuser3").save
      User.all.toList
      TestTables.clean
      User.all.toList mustEqual users
    }
  }

  "database schema" should {
    def ddl = {
      var lines = List.empty[String]
      transaction { TestTables.printDdl(lines +:= _) }
      lines.mkString("\n")
    }

    "except @Transient column" >> {
      ddl must not matching("""(?ms).* transient_field .*""".r)
    }

    "be able to change column name by @Column" >> {
      ddl must not matching("""(?ms).* column_field .*""".r)
      ddl must matching("""(?ms).* columnName .*""".r)
    }

    "create unique index by @Unique" >> {
      ddl must matching("""(?ms).*create unique index \w+ on annotation_models \(unique_field\);.*""".r)
    }

    "except confirmation column" >> {
      ddl must not matching("""(?ms).* confirmation_field_confirmation .*""".r)
    }

    "except renamed confirmation column" >> {
      ddl must not matching("""(?ms).* confirmation_name .*""".r)
    }

  }
}