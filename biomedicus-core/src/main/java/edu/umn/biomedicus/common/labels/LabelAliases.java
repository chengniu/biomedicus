/*
 * Copyright (c) 2017 Regents of the University of Minnesota.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.umn.biomedicus.common.labels;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Singleton
public class LabelAliases {
    private final Map<String, Class<?>> aliases = new HashMap<>();

    public void addAlias(String alias, Class<?> labelableClass) {
        aliases.put(alias, labelableClass);
    }

    @Nullable
    public Class<?> getLabelable(String alias) {
        return aliases.get(alias);
    }
}
