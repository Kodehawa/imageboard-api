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

package net.kodehawa.lib.imageboards.boards;

import net.kodehawa.lib.imageboards.entities.impl.autocomplete.IAutoComplete;
import net.kodehawa.lib.imageboards.entities.impl.autocomplete.pojo.*;

/**
 * The included ImageBoards implementations in this API.
 * <ul>
 *     <li>Rule 34</li>
 *     <li>E621</li>
 *     <li>Konachan</li>
 *     <li>Yande.re</li>
 *     <li>Danbooru</li>
 *     <li>Safebooru</li>
 * </ul>
 *
 * @author Kodehawa
 */
public enum DefaultBoards implements Board {
    R34("https", "api.rule34.xxx", "index.php", "page=dapi&s=post&q=index&json=1", "pid", 
            "autocomplete.php", "q", Rule34AutoComplete.class
    ),
    
    E621("https", "e621.net", "posts.json", null, "page", "posts", 
            "tags/autocomplete.json", "search[name_matches]", FurryAutoComplete.class
    ),

    E926("https", "e926.net", "posts.json", null, "page", "posts",
            "tags/autocomplete.json", "search[name_matches]", FurryAutoComplete.class
    ),
    
    DANBOORU("https", "danbooru.donmai.us", "posts.json", null, "page", 
            "autocomplete.json", "search[query]", DanbooruAutoComplete.class
    ),
    
    SAFEBOORU("https", "safebooru.org", "index.php", "page=dapi&s=post&q=index&json=1", "pid", 
            "autocomplete.php", "q", SafebooruAutoComplete.class
    ),
    
    GELBOORU("https", "gelbooru.com", "index.php", "page=dapi&s=post&q=index&json=1", "pid", 
            "post", "index.php", "term", GelbooruAutoComplete.class
    ),

    KONACHAN("http", "konachan.com", "post.json", null, "page"),
    YANDERE("https", "yande.re", "post.json", null, "page");

    private final String scheme;
    private final String pathSegment;
    private final String host;
    private final String query;
    private final String pageMarker;
    private final String outerObject;
    private final String autoCompletePath;
    private final String autoCompleteParameter;
    private final Class<? extends IAutoComplete> autoCompletePOJO;

    DefaultBoards(String scheme, String host, String pathSegment, String query, String pageMarker) {
        this.scheme = scheme;
        this.host = host;
        this.query = query;
        this.pathSegment = pathSegment;
        this.pageMarker = pageMarker;
        this.autoCompletePath = null;
        this.autoCompleteParameter = null;
        this.autoCompletePOJO = null;
        this.outerObject = null;
    }
    
    DefaultBoards(String scheme, String host, String pathSegment, String query, String pageMarker, String autoCompletePath, 
                  String autoCompleteParameter, Class<? extends IAutoComplete> autoCompletePOJO) {
        this.scheme = scheme;
        this.host = host;
        this.query = query;
        this.pathSegment = pathSegment;
        this.pageMarker = pageMarker;
        this.autoCompletePath = autoCompletePath;
        this.autoCompleteParameter = autoCompleteParameter;
        this.autoCompletePOJO = autoCompletePOJO;
        this.outerObject = null;
    }

    DefaultBoards(String scheme, String host, String pathSegment, String query, String pageMarker, String outerObject) {
        this.scheme = scheme;
        this.host = host;
        this.query = query;
        this.pathSegment = pathSegment;
        this.pageMarker = pageMarker;
        this.outerObject = outerObject;
        this.autoCompleteParameter = null;
        this.autoCompletePath = null;
        this.autoCompletePOJO = null;
    }

    DefaultBoards(String scheme, String host, String pathSegment, String query, String pageMarker, String outerObject, String autoCompletePath, 
                  String autoCompleteParameter, Class<? extends IAutoComplete> autoCompletePOJO) {
        this.scheme = scheme;
        this.host = host;
        this.query = query;
        this.pathSegment = pathSegment;
        this.pageMarker = pageMarker;
        this.outerObject = outerObject;
        this.autoCompleteParameter = autoCompleteParameter;
        this.autoCompletePath = autoCompletePath;
        this.autoCompletePOJO = autoCompletePOJO;
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public String getPath() {
        return pathSegment;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getPageMarker() {
        return pageMarker;
    }

    @Override
    public String getOuterObject() {
        return outerObject;
    }

    @Override
    public String getAutoCompletePath() {
        return autoCompletePath;
    }

    @Override
    public String getAutoCompleteParameter() {
        return autoCompleteParameter;
    }

    @Override
    public Class<? extends IAutoComplete> getAutoCompletePOJO() {
        return autoCompletePOJO;
    }
}
