# ImageBoard API
 
ImageBoard API is a simple asynchronous Java API wrapper around the most popular danbooru-compatible booru imageboard APIs.
The interface also supports other types of custom boards given a little tweaking. Releases are published in [JitPack](https://jitpack.io/#Kodehawa/imageboard-api).

This helps you craft requests to most of the danbooru-compatible imageboards (boorus) that exist out there. 

#### Supported Booru Image Boards
 * Rule34
 * e621
 * Konachan
 * Yande.re
 * Danbooru
 * Safebooru
 * Gelbooru
 * e926

Creating your own ImageBoard instance is possible, but would require a little tweaking.
    Please refer to the `ImageBoards.java` and `CustomBoard.java` to set up boards that are not included
    with this library. The boorus listed above are available by default with no further configuration required by the user.

## Adding ImageBoardAPI to your project
[Check instructions here for the latest version, in case this is outdated](https://jitpack.io/#Kodehawa/imageboard-api)

#### Gradle
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies { 
    implementation 'com.github.Kodehawa:imageboard-api:2.6.0'
}
```

You can replace 1907d46a98 for any tag.
Check https://jitpack.io/#Kodehawa/imageboard-api for more instructions.

## Set Up
There is a `ImageBoards` class located under utils, that one contains static, pre-created 
    ImageBoardAPI objects for you, but you can roll your own.
   

### Implementation
You can find implementation details and a lot of examples in the [tests for this project](https://github.com/Kodehawa/imageboard-api/blob/master/src/test/java/net/kodehawa/lib/imageboards/ImageBoardTest.java) and in [Mantaro](https://github.com/Mantaro/MantaroBot/blob/master/src/main/java/net/kodehawa/mantarobot/commands/image/ImageboardUtils.java#L54)

The User-Agent must be initialized to make requests. You can initialize it with `ImageBoard.setUserAgent()`. The default one was blocked by most imageboards, setting your own is the best to avoid UA blocking.

## Examples
### Random Images
```java
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.DefaultImageBoards;

public class RandomImages {
    public static void main(String[] args) {
        // Asynchronous GET
        // 60 random images
        DefaultImageBoards.KONACHAN.get().async(images -> {
            for (BoardImage image : images) System.out.println(image.getURL());
        });
        
        // Asynchronous GET
        // 30 random images
        ImageBoards.KONACHAN.get(30).async(images -> {
            for (BoardImage image : images) System.out.println(image.getURL());
        });

        // Blocking GET
        // 5 random image
        BoardImage image = DefaultImageBoards.KONACHAN.get(5).blocking().get(0);
        System.out.println(image.getURL());
        System.out.println(image.getRating());
        System.out.println(image.getTags());
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());
    }
}
```

### Image Tag Search
```java
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.DefaultImageBoards;

public class TagImages {
    public static void main(String[] args) {
        // Asynchronous GET
        // 20 images tagged with animal_ears
        DefaultImageBoards.KONACHAN.search(20, "animal_ears").async(images -> {
            for (BoardImage image : images) System.out.println(image.getURL());
        });

        // Blocking GET
        // 60 images tagged with animal_ears
        BoardImage image = DefaultImageBoards.KONACHAN.search("animal_ears").blocking().get(0);
        System.out.println(image.getURL());
        System.out.println(image.getRating());
        System.out.println(image.getTags());
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());
    }
}
```

### Filter bad results

You might want to filter BoardImage#isPending, as the tags/rating of those is almost guaranteed to never be correct.

For filtering other results, you can filter tags from BoardImage#getTags and use a .contains call on the list.
