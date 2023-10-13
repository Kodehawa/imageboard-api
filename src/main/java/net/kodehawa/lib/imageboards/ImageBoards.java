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

import net.kodehawa.lib.imageboards.boards.DefaultBoards;
import net.kodehawa.lib.imageboards.entities.impl.*;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

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
 * In difference with {@link DefaultImageBoards} this class allow you to configure {@link OkHttpClient} via passing
 * your own client into constructor.
 *
 * @author Kodehawa
 * @author InsanusMokrassar
 */
public class ImageBoards {
    @NotNull
    public final ImageBoard<FurryImage> E621;
    @NotNull
    public final ImageBoard<KonachanImage> KONACHAN;
    @NotNull
    public final ImageBoard<Rule34Image> RULE34;
    @NotNull
    public final ImageBoard<YandereImage> YANDERE;
    @NotNull
    public final ImageBoard<DanbooruImage> DANBOORU;
    @NotNull
    public final ImageBoard<SafebooruImage> SAFEBOORU;
    @NotNull
    public final ImageBoard<SafeFurryImage> E926;
    @NotNull
    public final ImageBoard<GelbooruImage> GELBOORU;

    public ImageBoards(@Nullable OkHttpClient client) {
        OkHttpClient localClient = client;
        if (localClient == null) {
            localClient = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(3, TimeUnit.SECONDS)
                    .build();
        }

        E621 = new ImageBoard<>(localClient, DefaultBoards.E621, FurryImage.class);
        KONACHAN = new ImageBoard<>(localClient, DefaultBoards.KONACHAN, KonachanImage.class);
        RULE34 = new ImageBoard<>(localClient, DefaultBoards.R34, Rule34Image.class);
        YANDERE = new ImageBoard<>(localClient, DefaultBoards.YANDERE, YandereImage.class);
        DANBOORU = new ImageBoard<>(localClient, DefaultBoards.DANBOORU, DanbooruImage.class);
        SAFEBOORU = new ImageBoard<>(localClient, DefaultBoards.SAFEBOORU, SafebooruImage.class);
        E926 = new ImageBoard<>(localClient, DefaultBoards.E926, SafeFurryImage.class);
        GELBOORU = new ImageBoard<>(localClient, DefaultBoards.GELBOORU, GelbooruImage.class);
    }

    public static final ImageBoards Default = new ImageBoards(null);
}
