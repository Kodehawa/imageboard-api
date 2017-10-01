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
    //Lewd APIs
    R34("https", "rule34.xxx", "index.php", "page=dapi&s=post&q=index&json=1", "pid"),
    E621("https", "e621.net", "post/index.json", null, "page"),
    //Normal APIs
    KONACHAN("http", "konachan.com", "post.json", null, "page"),
    YANDERE("https", "yande.re", "post.json", null, "page"),
    DANBOORU("https", "danbooru.donmai.us", "posts.json", null, "page"),
    SAFEBOORU("https", "safebooru.org", "index.php", "page=dapi&s=post&q=index&json=1", "pid");

    private final String scheme;
    private final String pathSegment;
    private final String host;
    private final String query;
    private final String pageMarker;

    DefaultBoards(String scheme, String host, String pathSegment, String query, String pageMarker) {
        this.scheme = scheme;
        this.host = host;
        this.query = query;
        this.pathSegment = pathSegment;
        this.pageMarker = pageMarker;
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
}
