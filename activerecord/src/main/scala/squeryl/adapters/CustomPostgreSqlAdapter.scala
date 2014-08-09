/*******************************************************************************
 * Copyright 2010 Maxime Lévesque
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************** */
package org.squeryl.adapters

import org.squeryl.dsl.ast._
import org.squeryl.dsl._
import org.squeryl.internals.{StatementWriter, DatabaseAdapter, FieldMetaData}
import org.squeryl.{Schema, Session, Table}
import java.sql._

import org.squeryl._
import dsl.CompositeKey

class CustomPostgreSqlAdapter extends PostgreSqlAdapter{
  override def executeUpdateForInsert(s: Session, sw: StatementWriter, ps: PreparedStatement) = exec(s, sw) { params =>
    if(s.isLoggingEnabled)
      s.log(sw.toString)
    fillParamsInto(params, ps)
    ps.executeUpdate
    1.toInt // for avoiding erro on updating table whitch is patitioned. (executeUpdate always return 0)
  }

}
