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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Board image ratings. Just remember that God is watching.
 *
 * @author Avarel
 */
@JsonDeserialize(using = RatingDeserializer.class)
public enum Rating {
    /**
     * Safe for family and friends. I'd imagine.
     */
    SAFE("s"),

    /**
     * Safe for family and friends. I'd imagine.
     * DO NOT USE OUTSIDE OF GelbooruImage. This will adjust to SAFE otherwise.
     */
    GENERAL("g"), // Gelbooru bullshit?

    /**
     * Questionable board images. Borderline explicit.
     */
    QUESTIONABLE("q"),

    /**
     * Sensitive board images. Borderline explicit.
     * DO NOT USE OUTSIDE OF GelbooruImage. This will adjust to QUESTIONABLE otherwise.
     */
    SENSITIVE("se"), // gelbooru bullshit, part 2

    /**
     * Default rating, assume the worst.
     * Board images with explicit/NSFW ratings.
     */
    @JsonEnumDefaultValue
    EXPLICIT("e");

    final String shortName;
    final String longName;

    Rating(String shortName) {
        this.shortName = shortName;
        this.longName = this.name().toLowerCase();
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    /**
     * Looks up the rating based on the full name, if nothing is found returns null.
     *
     * @param name The String value to match
     * @return The badge, or null if nothing is found.
     */
    public static Rating lookupFromString(String name) {
        for (Rating b : Rating.values()) {
            if (b.name().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Looks up the rating based on the full name, if nothing is found returns null.
     *
     * @param shortName The short name to match
     * @return The badge, or null if nothing is found.
     */
    public static Rating lookupFromStringShort(String shortName) {
        for (Rating b : Rating.values()) {
            if (b.getShortName().equalsIgnoreCase(shortName)) {
                return b;
            }
        }
        return null;
    }
}
