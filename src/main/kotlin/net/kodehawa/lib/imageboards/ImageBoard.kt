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
package net.kodehawa.lib.imageboards

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import net.kodehawa.lib.imageboards.boards.Board
import net.kodehawa.lib.imageboards.boards.DefaultBoards
import net.kodehawa.lib.imageboards.entities.BoardImage
import net.kodehawa.lib.imageboards.entities.Rating
import net.kodehawa.lib.imageboards.entities.exceptions.QueryFailedException
import net.kodehawa.lib.imageboards.entities.exceptions.QueryParseException
import net.kodehawa.lib.imageboards.requests.RequestAction
import net.kodehawa.lib.imageboards.requests.RequestFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Response
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Image board API instance.
 * @param <T> Board image return type.
 *
 * @author Avarel
 * @author Kodehawa
</T> */
class ImageBoard<T : BoardImage?>(
        client: OkHttpClient,
        landing: Board,
        private val responseFormat: ResponseFormat,
        cls: Class<T>
) {
    companion object {
        private val log = LoggerFactory.getLogger(ImageBoard::class.java)

        /**
         * Current version of the image board library.
         */
        const val VERSION = "@version@"

        /**
         * User agent to send to the services we request data from.
         */
        const val userAgent = "ImageboardAPI/@version@/https://github.com/Kodehawa/imageboard-api"

        /**
         * Changes whether we're gonna throw exceptions on EOF or no
         */
        @JvmField
        var throwExceptionOnEOF = true
    }

    /**
     * The type of the specified Imageboard.
     * This is important because if you specify the wrong type, it'll be impossible to deserialize it properly.
     */
    enum class ResponseFormat(mapper: ObjectMapper) {
        /** JSON response type.  */
        JSON(ObjectMapper()),

        /** XML response type.  */
        XML(XmlMapper());

        val mapper: ObjectMapper = mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false
        )

        private fun <T> readValue(p: String, valueType: JavaType): T {
            return try {
                mapper.readValue(p, valueType)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

    }

    /**
     * Requester client.
     */
    private val requestFactory: RequestFactory = RequestFactory(client)

    /**
     * Get the board landing of this instance.
     * @return Board type.
     */

    val boardType: Board = landing

    /**
     * Get the image return type of this board.
     * @return A java class of the image return type.
     */
    /**
     * Deserialization target.
     */
    val imageType: Class<T> = cls

    /**
     * Create a new image board instance.
     *
     * @param landing Board [API landing][Board].
     * @param cls The class this refers to.
     */
    constructor(landing: Board, cls: Class<T>) : this(landing, ResponseFormat.JSON, cls)

    /**
     * Create a new image board instance.
     *
     * @param landing Board [API landing][Board].
     * @param responseFormat Response format of the board.
     * @param cls The class this refers to.
     */
    constructor(landing: Board, responseFormat: ResponseFormat, cls: Class<T>) : this(OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.SECONDS)
            .build(),
            landing,
            responseFormat,
            cls)

    /**
     * Create a new image board instance.
     *
     * @param client [RequestFactory]'s request client.
     * @param landing Board [API landing][Board].
     * @param cls The class this refers to.
     */
    constructor(
            client: OkHttpClient,
            landing: Board,
            cls: Class<T>
    ) : this(client, landing, ResponseFormat.JSON, cls)

    /**
     * Get the provided page's results of the image board.
     *
     * @param page Page number.
     * @param limit Maximum number of images.
     * @return A [request action][RequestAction] that returns a list of images.
     */
    @Throws(QueryParseException::class, QueryFailedException::class)
    @JvmOverloads
    operator fun get(page: Int = 0, limit: Int = 60, rating: Rating? = null) =
            makeRequest(page, limit, "", rating)


    /**
     * Get the first page's results from the image board search, limited at 60 images.
     *
     * @param page Page number. Default 0
     * @param limit Maximum number of images. Default 60
     * @param query Image tags.
     * @param rating The rating to look for. Default null
     * @return A [request action][RequestAction] that returns a list of images.
     */
    @Throws(QueryParseException::class, QueryFailedException::class)
    @JvmOverloads
    fun search(
            query: String,
            limit: Int = 60,
            page: Int = 0,
            rating: Rating? = null
    ) =
            makeRequest(page, limit, query, rating)

    @Throws(QueryParseException::class, QueryFailedException::class)
    fun search(
            query: Array<String>,
            limit: Int = 60,
            page: Int = 0,
            rating: Rating? = null
    ) =
            makeRequest(page, limit, query.joinToString(" "), rating)

    @Throws(QueryParseException::class, QueryFailedException::class)
    @JvmOverloads
    fun search(
            query: List<String>,
            limit: Int = 60,
            page: Int = 0,
            rating: Rating? = null
    ) =
            makeRequest(page, limit, query.joinToString(" "), rating)

    @Throws(QueryParseException::class, QueryFailedException::class)
    @JvmOverloads
    fun search(
            limit: Int = 60,
            page: Int = 0,
            rating: Rating? = null,
            vararg query: String
    ) =
            makeRequest(page, limit, query.joinToString(" "), rating)

    @Throws(QueryParseException::class, QueryFailedException::class)
    private fun makeRequest(
            page: Int,
            limit: Int,
            search: String?,
            rating: Rating?
    ): RequestAction<List<T>> {

        val urlBuilder = HttpUrl.Builder()
                .scheme(boardType.scheme)
                .host(boardType.host)
                .addPathSegments(boardType.path)
                .query(boardType.query)
                .addQueryParameter("limit", limit.toString())

        if (page != 0) urlBuilder.addQueryParameter(boardType.pageMarker, page.toString())

        if (search != null) {
            val tags = StringBuilder(search.toLowerCase().trim { it <= ' ' })
            if (rating != null) {
                //Fuck you gelbooru you're the only one doing this :(
                tags.append(" rating:").append(
                        if (boardType === DefaultBoards.GELBOORU) rating.longName
                        else rating.shortName)
            }
            urlBuilder.addQueryParameter("tags", tags.toString())
        }
        val url = urlBuilder.build()
        return requestFactory.makeRequest(url) { response: Response ->
            log.debug(
                    "Making request to {} (Response format: {}, Imageboard: {}, Target: {})",
                    url.toString(),
                    responseFormat,
                    boardType,
                    imageType
            )
            try {
                response.body().use { body ->
                    if (body == null) {
                        if (throwExceptionOnEOF) {
                            throw QueryParseException(NullPointerException(
                                    "Received an empty body from the imageboard URL. " +
                                            "(From board: $boardType, Target: $imageType)"
                            ))
                        } else {
                            return@makeRequest emptyList()
                        }
                    }
                    val images: List<T> = responseFormat.mapper.readValue(
                            body.byteStream(),
                            responseFormat.mapper.typeFactory.constructCollectionType(
                                    MutableList::class.java,
                                    imageType
                            )
                    )
                    body.close()
                    return@makeRequest images
                }
            } catch (e: IOException) {
                if (e.message!!.contains("No content to map due to end-of-input") && !throwExceptionOnEOF)
                    return@makeRequest emptyList()
                throw QueryParseException(e)
            }
        }
    }


}