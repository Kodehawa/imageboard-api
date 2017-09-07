/*
 * Copyright (C) 2016-2017 David Alejandro Rubio Escares / Kodehawa
 *
 * Mantaro is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Mantaro is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mantaro.  If not, see http://www.gnu.org/licenses/
 */

package net.kodehawa.lib.imageboards.util;

import net.kodehawa.lib.imageboards.ImageboardAPI;
import net.kodehawa.lib.imageboards.entities.FurryImage;
import net.kodehawa.lib.imageboards.entities.Rule34Image;
import net.kodehawa.lib.imageboards.entities.KonachanWallpaper;
import net.kodehawa.lib.imageboards.entities.YandereImage;

/**
 * Utility class represting already-created objects around the most used imageboards.
 */
public class Imageboards {
    public static final ImageboardAPI<FurryImage> E621 = new ImageboardAPI<>(ImageboardAPI.Boards.E621, ImageboardAPI.Type.JSON, FurryImage[].class);
    public static final ImageboardAPI<KonachanWallpaper> KONACHAN = new ImageboardAPI<>(ImageboardAPI.Boards.KONACHAN, ImageboardAPI.Type.JSON, KonachanWallpaper[].class);
    public static final ImageboardAPI<Rule34Image> RULE34 = new ImageboardAPI<>(ImageboardAPI.Boards.R34, ImageboardAPI.Type.XML, Rule34Image[].class);
    public static final ImageboardAPI<YandereImage> YANDERE = new ImageboardAPI<>(ImageboardAPI.Boards.YANDERE, ImageboardAPI.Type.JSON, YandereImage[].class);
}
