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

/**
 * Custom board information.
 *
 * @author Avarel
 */
public class CustomBoard implements Board {
    private final String host;
    private final String scheme;
    private final String pathSegment;
    private final String separator;
    private final String pageMarker;
    private final String outerObject;

    public CustomBoard(String scheme, String host, String pathSegment, String separator, String pageMarker, String outerObject) {
        this.scheme = scheme;
        this.pathSegment = pathSegment;
        this.separator = separator;
        this.host = host;
        this.pageMarker = pageMarker;
        this.outerObject = outerObject;
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
}
