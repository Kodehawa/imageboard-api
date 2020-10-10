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

import java.util.List;

/**
 * Common board image interface.
 * @author NatchRaben
 */
public interface BoardImage {
    /**
     * @return Width of the image.
     */
    int getWidth();

    /**
     * @return Height of the image.
     */
    int getHeight();

    /**
     * @return Score of the image.
     */
    int getScore();

    /**
     * @return Rating of the image.
     */
    Rating getRating();

    /**
     * @return Tags of the image.
     */
    List<String> getTags();

    /**
     * @return Image url.
     */
    String getURL();

    /**
     * @return Whether the image depicts children or not. Not all image boards detect this, so don't rely on this alone!.
     */
    boolean hasChildren();
}
