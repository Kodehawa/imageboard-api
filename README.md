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
}
```

Copyright 2017 Kodehawa

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
