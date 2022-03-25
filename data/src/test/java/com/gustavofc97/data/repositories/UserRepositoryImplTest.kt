package com.gustavofc97.data.repositories

import com.gustavofc97.data.MockableTest
import com.gustavofc97.data.given
import com.gustavofc97.data.model.APIUser
import com.gustavofc97.data.model.toDomainModel
import com.gustavofc97.data.network.NetworkClient
import com.gustavofc97.data.network.PostServices
import com.gustavofc97.data.then
import com.gustavofc97.data.whenever
import com.gustavofc97.domain.exceptions.UserNotFoundException
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class UserRepositoryImplTest : MockableTest {

    @MockK
    lateinit var postServices: PostServices

    @MockK
    lateinit var networkClient: NetworkClient

    @MockK
    lateinit var failingNetworkClient: NetworkClient

    @MockK
    lateinit var failingApiResponse: Response<APIUser>

    @MockK
    lateinit var failingService: PostServices

    @MockK
    lateinit var apiResponse: Response<APIUser>

    private val apiUser =
        APIUser("Gustavo Forero", "www.gustavoforero.com", "gustavo@forero.com", "3013588304")

    @Before
    override fun setup() {
        super.setup()
        coEvery {
            networkClient.apiCall(postServices.getUser(any()))
        }.answers {
            apiResponse
        }
        coEvery {
            failingNetworkClient.apiCall(failingService.getUser(any()))
        }.answers {
            failingApiResponse
        }

        coEvery {
            apiResponse.isSuccessful
        }.answers { true }

        coEvery {
            failingApiResponse.isSuccessful
        }.answers { true }

        coEvery {
            failingApiResponse.body()
        }.answers { null }

        coEvery { apiResponse.body() }.answers { apiUser }

    }

    @Test
    fun `when get user then should return user model`() {

        val repositoryImpl = given {
            UserRepositoryImpl(postServices, networkClient)
        }

        val result = whenever {
            runBlocking {
                repositoryImpl.getUserById("2")
            }
        }

        then {
            Assert.assertEquals(apiUser.toDomainModel(), result)
        }
    }

    @Test(expected = UserNotFoundException::class)
    fun `when get user then should throw exception`() {

        val repositoryImpl = given {
            UserRepositoryImpl(failingService, failingNetworkClient)
        }

        whenever {
            runBlocking {
                repositoryImpl.getUserById("2")
            }
        }
    }

}