/*
 * Copyright 2017 Kodehawa
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

package net.kodehawa.lib.imageboards.boards;

import net.kodehawa.lib.imageboards.entities.impl.autocomplete.IAutoComplete;

/**
 * Custom board information.
 *
 * @author Avarel
 * @author Kodehawa
 */
public class CustomBoard implements Board {
    private final String host;
    private final String scheme;
    private final String pathSegment;
    private final String separator;
    private final String pageMarker;
    private final String outerObject;
    private final String autoCompletePath;
    private final String autoCompleteParameter;
    private final Class<? extends IAutoComplete> autoCompletePOJO;

    public CustomBoard(String scheme, String host, String pathSegment, String separator, String pageMarker, String outerObject) {
        this.scheme = scheme;
        this.pathSegment = pathSegment;
        this.separator = separator;
        this.host = host;
        this.pageMarker = pageMarker;
        this.outerObject = outerObject;
        this.autoCompleteParameter = null;
        this.autoCompletePath = null;
        this.autoCompletePOJO = null;
    }

    public CustomBoard(String scheme, String host, String pathSegment, String separator, String pageMarker, String outerObject, String autoCompleteParameter, 
                       String autoCompletePath, Class<? extends IAutoComplete> autoCompletePOJO) {
        this.scheme = scheme;
        this.pathSegment = pathSegment;
        this.separator = separator;
        this.host = host;
        this.pageMarker = pageMarker;
        this.outerObject = outerObject;
        this.autoCompleteParameter = autoCompleteParameter;
        this.autoCompletePath = autoCompletePath;
        this.autoCompletePOJO = autoCompletePOJO;
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getPath() {
        return pathSegment;
    }

    @Override
    public String getQuery() {
        return separator;
    }

    @Override
    public String getPageMarker() {
        return pageMarker;
    }

    @Override
    public String getOuterObject() {
        return outerObject;
    }

    @Override
    public String getAutoCompletePath() {
        return autoCompletePath;
    }

    @Override
    public String getAutoCompleteParameter() {
        return autoCompleteParameter;
    }

    @Override
    public Class<? extends IAutoComplete> getAutoCompletePOJO() {
        return autoCompletePOJO;
    }
}
