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

import java.util.concurrent.TimeUnit;

/**
 * Utility class represting already-created objects around the most used imageboards.
 *
 * @author Kodehawa
 */
public class DefaultImageBoards {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .build();

    public static final ImageBoard<FurryImage> E621 = new ImageBoard<>(client, DefaultBoards.E621, FurryImage.class);
    public static final ImageBoard<KonachanImage> KONACHAN = new ImageBoard<>(client, DefaultBoards.KONACHAN, KonachanImage.class);
    public static final ImageBoard<Rule34Image> RULE34 = new ImageBoard<>(client, DefaultBoards.R34, Rule34Image.class);
    public static final ImageBoard<YandereImage> YANDERE = new ImageBoard<>(client, DefaultBoards.YANDERE, YandereImage.class);
    public static final ImageBoard<DanbooruImage> DANBOORU = new ImageBoard<>(client, DefaultBoards.DANBOORU, DanbooruImage.class);
    public static final ImageBoard<SafebooruImage> SAFEBOORU = new ImageBoard<>(client, DefaultBoards.SAFEBOORU, SafebooruImage.class);
    public static final ImageBoard<SafeFurryImage> E926 = new ImageBoard<>(client, DefaultBoards.E926, SafeFurryImage.class);
    public static final ImageBoard<GelbooruImage> GELBOORU = new ImageBoard<>(client, DefaultBoards.GELBOORU, GelbooruImage.class);
}
