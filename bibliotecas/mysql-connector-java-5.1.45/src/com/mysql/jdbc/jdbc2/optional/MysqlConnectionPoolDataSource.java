/*
  Copyright (c) 2002, 2014, Oracle and/or its affiliates. All rights reserved.

  The MySQL Connector/J is licensed under the terms of the GPLv2
  <http://www.gnu.org/licenses/old-licenses/gpl-2.0.html>, like most MySQL Connectors.
  There are special exceptions to the terms and conditions of the GPLv2 as it is applied to
  this software, see the FOSS License Exception
  <http://www.mysql.com/about/legal/licensing/foss-exception.html>.

  This program is free software; you can redistribute it and/or modify it under the terms
  of the GNU General Public License as published by the Free Software Foundation; version 2
  of the License.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this
  program; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth
  Floor, Boston, MA 02110-1301  USA

 */

package com.mysql.jdbc.jdbc2.optional;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

/**
 * This class is used to obtain a physical connection and instantiate and return a MysqlPooledConnection. J2EE application servers map client calls to
 * dataSource.getConnection to this class based upon mapping set within deployment descriptor. This class extends MysqlDataSource.
 */
public class MysqlConnectionPoolDataSource extends MysqlDataSource implements ConnectionPoolDataSource {

    static final long serialVersionUID = -7767325445592304961L;

    /**
     * Returns a pooled connection.
     *
     * @return a PooledConnection
     * @throws SQLException if an error occurs
     */
    public synchronized PooledConnection getPooledConnection() throws SQLException {
        Connection connection = getConnection();
        MysqlPooledConnection mysqlPooledConnection = MysqlPooledConnection.getInstance((com.mysql.jdbc.Connection) connection);

        return mysqlPooledConnection;
    }

    /**
     * This method is invoked by the container. Obtains physical connection
     * using mySql.Driver class and returns a mysqlPooledConnection object.
     *
     * @param s  user name
     * @param s1 password
     * @return a PooledConnection
     * @throws SQLException if an error occurs
     */
    public synchronized PooledConnection getPooledConnection(String s, String s1) throws SQLException {
        Connection connection = getConnection(s, s1);
        MysqlPooledConnection mysqlPooledConnection = MysqlPooledConnection.getInstance((com.mysql.jdbc.Connection) connection);

        return mysqlPooledConnection;
    }
}
