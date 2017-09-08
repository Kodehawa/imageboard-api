# imageboard-api
Simple asynchronous API wrapper around the most popular danbooru-compatible imageboard APIs.

## To start:
Note: **There is a `Imageboards` class located under utils, that one contains static, pre-created ImageboardAPI objects for you, but you can roll your own**

#### Supported Imageboards:
`Konachan, Yande.re, Danbooru, Rule34, e621` 

### Random images:

```java
//This is a simple example of grabbing a random image
//Please refer to the examples directory for more examples.

private final ImageboardAPI<KonachanImage> konachan = Imageboards.KONACHAN;

public void getImages() { 
    //Async
    konachan.get((images) -> {
        for(KonachanImage image : images) {
            System.out.println(image.file_url + " " + image.getTags() + " " + 
                image.getHeight() + " " + image.getWidth());
        }
    });
    
    //Async with limit
    //limit = how many images at once
    konachan.get(2, (images) -> {
        for(KonachanImage image : images) {
            System.out.println(image.file_url + " " + image.getTags() + " " + 
                image.getHeight() + " " + image.getWidth());
        }
    });
    
    //Blocking
    List<KonachanImage> images = konachan.getBlocking();
    List<KonachanImage> imagesLimited = konachan.getBlocking(2);
    
    //Handling here..
}
```

### Tag search:

```java
//This is a simple example of grabbing a image using tag search
//Please refer to the examples directory for more examples.
//You can find more imageboards on the Imageboards class.
private final ImageboardAPI<KonachanImage> konachan = Imageboards.KONACHAN;

public void getImages() { 
    //Async
    konachan.onSearch("animal_ears", (images) -> {
        for(KonachanImage image : images) {
            System.out.println(image.file_url + " " + image.getTags() + " " + 
                image.getHeight() + " " + image.getWidth());
        }
    });
    
    //Async with limit
    //limit = how many images at once
    konachan.get(2, "animal_ears", (images) -> {
        for(KonachanImage image : images) {
            System.out.println(image.file_url + " " + image.getTags() + " " + 
                image.getHeight() + " " + image.getWidth());
        }
    });
    
    //Blocking
    List<KonachanImage> images = konachan.onSearchBlocking("animal_ears");
    List<KonachanImage> imagesLimited = konachan.getBlocking(2, "animal_ears");
    
    //Handling here..
```

    **Copyright (C) 2017-2018  Kodehawa**

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
