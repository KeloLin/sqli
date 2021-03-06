/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.xream.sqli.builder;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author Sim
 */
public interface SqlNormalizer {


    Set<String> OP_SET = new HashSet() {
        {
            add("=");
            add("!");
            add(">");
            add("<");
            add("+");
            add("-");
            add("*");
            add("/");
            add("(");
            add(")");
            add(";");
        }
    };

    default String normalizeSql(final String handwritten) {
        StringBuilder valueSb = new StringBuilder();

        boolean ignore = false;
        int length = handwritten.length();
        for (int j = 0; j < length; j++) {
            String strEle = String.valueOf(handwritten.charAt(j));
            if (SqlScript.SPACE.equals(strEle)) {
                ignore = true;
                continue;
            }
            if (OP_SET.contains(strEle)) {

                valueSb.append(SqlScript.SPACE);

                valueSb.append(strEle);
                if (j + 1 < length) {
                    String nextOp = String.valueOf(handwritten.charAt(j + 1));
                    if (OP_SET.contains(nextOp)) {
                        valueSb.append(nextOp);
                        j++;
                    }
                }
                valueSb.append(SqlScript.SPACE);
            } else {
                if (ignore)
                    valueSb.append(SqlScript.SPACE);
                valueSb.append(strEle);
            }
            ignore = false;
        }

        return valueSb.toString();

    }


}
