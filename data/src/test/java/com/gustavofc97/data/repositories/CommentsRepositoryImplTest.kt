package com.gustavofc97.data.repositories

import com.gustavofc97.data.MockableTest
import com.gustavofc97.data.model.APIComment
import com.gustavofc97.data.network.NetworkClient
import com.gustavofc97.data.network.PostServices
import com.gustavofc97.data.given
import com.gustavofc97.data.model.toDomainModel
import com.gustavofc97.data.then
import com.gustavofc97.data.whenever
import com.gustavofc97.domain.exceptions.CommentsNotFoundException
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CommentsRepositoryImplTest : MockableTest {

    @MockK
    lateinit var postServices: PostServices

    @MockK
    lateinit var networkClient: NetworkClient

    @MockK
    lateinit var apiResponse: Response<List<APIComment>>

    @MockK
    lateinit var failingPostServices: PostServices

    @MockK
    lateinit var failingNetworkClient: NetworkClient

    @MockK
    lateinit var failingApiResponse: Response<List<APIComment>>

    private val apiComments = listOf(APIComment("Body", "name", "user.random@gmail.com"))

    @Before
    override fun setup() {
        super.setup()
        coEvery {
            networkClient.apiCall(postServices.getPostComments(any()))
        }.answers {
            apiResponse
        }

        coEvery {
            apiResponse.isSuccessful
        }.answers { true }

        coEvery { apiResponse.body() }.answers { apiComments }

        coEvery {
            failingNetworkClient.apiCall(failingPostServices.getPostComments(any()))
        }.answers {
            failingApiResponse
        }

        coEvery {
            failingApiResponse.isSuccessful
        }.answers { true }

        coEvery { failingApiResponse.body() }.answers { null }
    }

    @Test
    fun `when get comments by post then should return comments`() {

        val repositoryImpl = given {
            CommentsRepositoryImpl(postServices, networkClient)
        }

        val result = whenever {
            runBlocking {
                repositoryImpl.getCommentsByPost("2")
            }
        }

        then {
            Assert.assertEquals(apiComments.map { it.toDomainModel() }, result)
        }
    }

    @Test(expected = CommentsNotFoundException::class)
    fun `when get comments by post then should throw exception`() {

        val repositoryImpl = given {
            CommentsRepositoryImpl(failingPostServices, failingNetworkClient)
        }

        val result = whenever {
            runBlocking {
                repositoryImpl.getCommentsByPost("2")
            }
        }
    }

}