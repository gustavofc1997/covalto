package com.gustavofc97.domain.usecases

import com.gustavofc97.domain.MockableTest
import com.gustavofc97.domain.given
import com.gustavofc97.domain.model.User
import com.gustavofc97.domain.repositories.UserRepository
import com.gustavofc97.domain.then
import com.gustavofc97.domain.whenever
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetUserUseCaseTest : MockableTest {

    @MockK
    lateinit var getUserRepository: UserRepository

    private var user =
        User("Gustavo Forero", "gustavo@forero.com", "3013588304", "www.gustavoforero.com")

    @Before
    override fun setup() {
        super.setup()
        coEvery {
            getUserRepository.getUserById(any())
        }.answers {
            user
        }
    }

    @Test
    fun `given a user id then should return user`() {
        val useCase = given {
            GetUserByIdUseCase(getUserRepository)
        }

        val result = whenever {
            runBlocking {
                useCase("2")
            }
        }

        then {
            Assert.assertEquals(result.name, user.name)
            Assert.assertEquals(result.phone, user.phone)
            Assert.assertEquals(result.website, user.website)
            Assert.assertEquals(result.email, user.email)
        }
    }
}