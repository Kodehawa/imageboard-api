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

package net.kodehawa.lib.imageboards.util;

import net.kodehawa.lib.imageboards.boards.DefaultBoards;
import net.kodehawa.lib.imageboards.ImageBoardAPI;
import net.kodehawa.lib.imageboards.entities.impl.*;

/**
 * Utility class represting already-created objects around the most used imageboards.
 *
 * @author Kodehawa
 */
public class ImageBoards {
    public static final ImageBoardAPI<FurryImage> E621 = new ImageBoardAPI<>(DefaultBoards.E621, FurryImage.class);
    public static final ImageBoardAPI<KonachanImage> KONACHAN = new ImageBoardAPI<>(DefaultBoards.KONACHAN, KonachanImage.class);
    public static final ImageBoardAPI<Rule34Image> RULE34 = new ImageBoardAPI<>(DefaultBoards.R34, Rule34Image.class);
    public static final ImageBoardAPI<YandereImage> YANDERE = new ImageBoardAPI<>(DefaultBoards.YANDERE, YandereImage.class);
    public static final ImageBoardAPI<DanbooruImage> DANBOORU = new ImageBoardAPI<>(DefaultBoards.DANBOORU, DanbooruImage.class);
    public static final ImageBoardAPI<SafebooruImage> SAFEBOORU = new ImageBoardAPI<>(DefaultBoards.SAFEBOORU, SafebooruImage.class);
}
