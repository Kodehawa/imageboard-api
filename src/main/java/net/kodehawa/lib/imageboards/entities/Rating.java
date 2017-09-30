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

package net.kodehawa.lib.imageboards.entities;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Board image ratings. Just remember that God is watching.
 * @author Avarel
 */
public enum Rating {
    /**
     * Safe for family and friends. If you had any.
     */
    @JsonProperty("s")
    SAFE,

    /**
     * Questionable board images. Borderline explicit.
     * Would you show this to your grandma?
     */
    @JsonProperty("q")
    QUESTIONABLE,

    /**
     * Board images with explicit/NSFW ratings.
     * Dirty af. Go see a therapist.
     */
    @JsonProperty("e")
    EXPLICIT,

    @JsonEnumDefaultValue
    UNKNOWN,
}
