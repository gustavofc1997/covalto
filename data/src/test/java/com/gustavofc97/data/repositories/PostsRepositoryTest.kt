package com.gustavofc97.data.repositories

import com.gustavofc97.data.MockableTest
import com.gustavofc97.data.given
import com.gustavofc97.data.model.APIPost
import com.gustavofc97.data.network.NetworkClient
import com.gustavofc97.data.network.PostServices
import com.gustavofc97.data.then
import com.gustavofc97.data.whenever
import com.gustavofc97.domain.exceptions.PostsNotFoundException
import com.gustavofc97.domain.model.Post
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PostsRepositoryTest : MockableTest {

    @MockK
    lateinit var postServices: PostServices


    @MockK
    lateinit var networkClient: NetworkClient

    @MockK
    lateinit var apiResponse: Response<List<APIPost>>

    @MockK
    lateinit var failingPostServices: PostServices

    @MockK
    lateinit var failingNetworkClient: NetworkClient

    @MockK
    lateinit var failingApiResponse: Response<List<APIPost>>

    private val apiPosts = listOf(APIPost("Title", "Description", 1, 1))

    private val posts = listOf(Post(1, 1, "Title", "Description"))

    @Before
    override fun setup() {
        super.setup()

        coEvery {
            networkClient.apiCall(postServices.getAllPosts())
        }.answers {
            apiResponse
        }

        coEvery {
            apiResponse.isSuccessful
        }.answers { true }

        coEvery { apiResponse.body() }.answers { apiPosts }

        coEvery {
            failingNetworkClient.apiCall(failingPostServices.getAllPosts())
        }.answers {
            failingApiResponse
        }

        coEvery {
            failingApiResponse.isSuccessful
        }.answers { true }

        coEvery { failingApiResponse.body() }.answers { null }
    }

    @Test
    fun `when get posts then should return a list with posts`() {

        val repositoryImpl = given {
            PostRepositoryImpl(postServices, networkClient)
        }

        val result = whenever {
            runBlocking {
                repositoryImpl.getPosts().first()
            }
        }

        then {
            Assert.assertEquals(posts.size, result.size)
            Assert.assertEquals(posts, result )
        }
    }

    @Test(expected = PostsNotFoundException::class)
    fun `when get posts then should throw exception`() {

        val repositoryImpl = given {
            PostRepositoryImpl(failingPostServices, failingNetworkClient)
        }

        whenever {
            runBlocking {
                repositoryImpl.getPosts()
            }
        }
    }
}