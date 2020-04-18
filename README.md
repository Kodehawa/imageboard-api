# ImageBoard API
 [ ![Download](https://api.bintray.com/packages/kodehawa/maven/imageboard-api/images/download.svg) ](https://bintray.com/kodehawa/maven/imageboard-api/_latestVersion) 
 
ImageBoard API is a simple asynchronous API wrapper around 
    the most popular danbooru-compatible imageboard APIs.
    The interface also supports other types of custom boards
    given a little tweaking.

## Dependencies
#### Gradle
```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/kodehawa/maven" 
    }
}

dependencies { 
    compile 'net.kodehawa:imageboard-api:2.2'
}
```

#### Maven
Add the `https://dl.bintray.com/kodehawa/maven` repository to your pom.
```xml
<dependency>
  <groupId>net.kodehawa</groupId>
  <artifactId>imageboard-api</artifactId>
  <version>2.1</version>
  <type>pom</type>
</dependency>
```
## Set Up
There is a `ImageBoards` class located under utils, that one contains static, pre-created 
    ImageBoardAPI objects for you, but you can roll your own.
    
#### Default Image Boards
 * Rule34
 * e621
 * Konachan
 * Yande.re
 * Danbooru
 * Safebooru
 * Gelbooru
 * e926

Creating your own Image Board API instance is possible, but would require a little tweaking.
    Please refer to the `ImageBoards.java` and `CustomBoard.java` to set up boards that are not included
    with this API.


### Implementation
You can find implementation details and a lot of examples in the [tests for this project](https://github.com/Kodehawa/imageboard-api/blob/master/src/test/java/net/kodehawa/lib/imageboards/ImageBoardTest.java) and in [Mantaro](https://github.com/Mantaro/MantaroBot/blob/master/src/main/java/net/kodehawa/mantarobot/commands/image/ImageboardUtils.java#L57)

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
