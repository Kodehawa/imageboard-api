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

package net.kodehawa.lib.imageboards.entities.exceptions;

/**
 * Exception thrown when the image board return a response
 * that causes errors during parsing.
 *
 * @author Kodehawa
 */
public class QueryParseException extends RuntimeException {
    private final String response;

    public QueryParseException(String response, Throwable e) {
        super("Failed to parse response from an ImageBoard.", e);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
