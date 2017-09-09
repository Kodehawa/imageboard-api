package net.kodehawa.lib.imageboards.util;

import net.kodehawa.lib.imageboards.ImageboardAPI;
import net.kodehawa.lib.imageboards.entities.*;

/**
 * Utility class represting already-created objects around the most used imageboards.
 */
public class Imageboards {
    public static final ImageboardAPI<FurryImage> E621 = new ImageboardAPI<>(ImageboardAPI.Boards.E621, ImageboardAPI.Type.JSON, FurryImage[].class);
    public static final ImageboardAPI<KonachanImage> KONACHAN = new ImageboardAPI<>(ImageboardAPI.Boards.KONACHAN, ImageboardAPI.Type.JSON, KonachanImage[].class);
    public static final ImageboardAPI<Rule34Image> RULE34 = new ImageboardAPI<>(ImageboardAPI.Boards.R34, ImageboardAPI.Type.JSON, Rule34Image[].class);
    public static final ImageboardAPI<YandereImage> YANDERE = new ImageboardAPI<>(ImageboardAPI.Boards.YANDERE, ImageboardAPI.Type.JSON, YandereImage[].class);
    public static final ImageboardAPI<DanbooruImage> DANBOORU = new ImageboardAPI<>(ImageboardAPI.Boards.DANBOORU, ImageboardAPI.Type.JSON, DanbooruImage[].class);
    public static final ImageboardAPI<SafebooruImage> SAFEBOORU = new ImageboardAPI<>(ImageboardAPI.Boards.SAFEBOORU, ImageboardAPI.Type.JSON, SafebooruImage[].class);
}
