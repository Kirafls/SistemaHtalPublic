/*
  Copyright (c) 2013, 2014, Oracle and/or its affiliates. All rights reserved.

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

package com.mysql.fabric;

import java.util.Set;

/**
 * Factory for creating {@link ShardMapping} objects. This is generally based on the {@link ShardingType} but can be modified as necessary.
 */
public class ShardMappingFactory {
    public ShardMapping createShardMapping(int mappingId, ShardingType shardingType, String globalGroupName, Set<ShardTable> shardTables,
                                           Set<ShardIndex> shardIndices) {
        ShardMapping sm = null;
        switch (shardingType) {
            case RANGE:
                sm = new RangeShardMapping(mappingId, shardingType, globalGroupName, shardTables, shardIndices);
                break;
            case HASH:
                sm = new HashShardMapping(mappingId, shardingType, globalGroupName, shardTables, shardIndices);
                break;
            default:
                throw new IllegalArgumentException("Invalid ShardingType");
        }
        return sm;
    }
}
