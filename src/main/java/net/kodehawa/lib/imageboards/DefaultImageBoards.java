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

package net.kodehawa.lib.imageboards;

import net.kodehawa.lib.imageboards.entities.impl.*;

/**
 * Utility class representing already-created objects around the most used image boards.
 * As of now, this contains the following boorus / imageboards:
 * <ul>
 *     <li>e621 - Furry pictures, both NSFW and safe.</li>
 *     <li>Konachan (owned by booru.org) - Anime pictures, both NSFW and safe.</li>
 *     <li>Rule34 (owned by booru.org) - Anime pictures, only NSFW</li>
 *     <li>Yande.re - Anime pictures, both NSFW and safe (though tagging is sloppy, would recommend setting this to only NSFW)</li>
 *     <li>Danbooru (the original one) - Anime pictures, both NSFW and safe.</li>
 *     <li>Safebooru (owned by booru.org) - Anime pictures, only safe.</li>
 *     <li>e926 (a safe version of e621) - Furry pictures, only safe.</li>
 * </ul>
 *
 * It's recommended to lock the usage of the boorus to only NSFW channels unless the image is safe. If you're not
 * using this for a discord bot, then just use as your own discretion :P
 *
 * @author Kodehawa
 */
public class DefaultImageBoards {
    public static final ImageBoard<FurryImage> E621 = ImageBoards.Default.E621;
    public static final ImageBoard<KonachanImage> KONACHAN = ImageBoards.Default.KONACHAN;
    public static final ImageBoard<Rule34Image> RULE34 = ImageBoards.Default.RULE34;
    public static final ImageBoard<YandereImage> YANDERE = ImageBoards.Default.YANDERE;
    public static final ImageBoard<DanbooruImage> DANBOORU = ImageBoards.Default.DANBOORU;
    public static final ImageBoard<SafebooruImage> SAFEBOORU = ImageBoards.Default.SAFEBOORU;
    public static final ImageBoard<SafeFurryImage> E926 = ImageBoards.Default.E926;
    public static final ImageBoard<GelbooruImage> GELBOORU = ImageBoards.Default.GELBOORU;
}
