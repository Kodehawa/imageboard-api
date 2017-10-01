# ImageBoard API
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
    compile 'net.kodehawa:imageboard-api:2.0'
}
```
#### Maven
Add: https://hastebin.com/uqaquqecur.xml.
```xml
<dependency>
  <groupId>net.kodehawa</groupId>
  <artifactId>imageboard-api</artifactId>
  <version>2.0</version>
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

Creating your own Image Board API instance is possible, but would require a little tweaking.
    Please refer to the `ImageBoards.java` and `CustomBoard.java` to set up boards that are not included
    with this API.

### Random Images
```java
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.DefaultImageBoards;

public class RandomImages {
    public static void main(String[] args) {
        // Asynchronous GET
        // 60 random images
        ImageBoards.KONACHAN.get().async(images -> {
            for (BoardImage image : images) System.out.println(image.getURL());
        });
        
        // Asynchronous GET
        // 30 random images
        ImageBoards.KONACHAN.get(30).async(images -> {
            for (BoardImage image : images) System.out.println(image.getURL());
        });

        // Blocking GET
        // 5 random image
        BoardImage image = ImageBoards.KONACHAN.get(5).blocking().get(0);
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
        ImageBoards.KONACHAN.search(20, "animal_ears").async(images -> {
            for (BoardImage image : images) System.out.println(image.getURL());
        });

        // Blocking GET
        // 60 images tagged with animal_ears
        BoardImage image = ImageBoards.KONACHAN.search("animal_ears").blocking().get(0);
        System.out.println(image.getURL());
        System.out.println(image.getRating());
        System.out.println(image.getTags());
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());
    }
}
```
