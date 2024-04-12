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
 * Information about the board.
 *
 * @author Avarel
 * @author Kodehawa
 */
public interface Board {
    /**
     * @return Scheme of the board. ie. http/https
     */
    String getScheme();

    /**
     * @return Base url of the board.
     */
    String getHost();

    /**
     * @return Path segment.
     */
    String getPath();

    /**
     * @return The string that separates the base url from the queries.
     */
    String getQuery();

    /**
     * @return Page marker.
     */
    String getPageMarker();

    String getOuterObject();

    String getAutoCompletePath();

    String getAutoCompleteParameter();
    
    Class<? extends IAutoComplete> getAutoCompletePOJO();
}
