package com.gustavofc97.data.network

import com.gustavofc97.data.model.APIComment
import com.gustavofc97.data.model.APIPost
import com.gustavofc97.data.model.APIUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val GET_POSTS = "posts"
private const val GET_COMMENTS_BY_POST = "comments"
private const val GET_USERS = "users/{id}"
private const val ID_KEY = "id"
private const val POST_ID_KEY = "postId"

interface PostServices {

    @GET(GET_POSTS)
    suspend fun getAllPosts(): Response<List<APIPost>>

    @GET(GET_COMMENTS_BY_POST)
    suspend fun getPostComments(@Query(POST_ID_KEY) id: String): Response<List<APIComment>>

    @GET(GET_USERS)
    suspend fun getUser(@Path(ID_KEY) id: String): Response<APIUser>
}