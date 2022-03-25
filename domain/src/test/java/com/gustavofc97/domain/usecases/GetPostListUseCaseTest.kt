package com.gustavofc97.domain.usecases

import com.gustavofc97.domain.MockableTest
import com.gustavofc97.domain.given
import com.gustavofc97.domain.model.Post
import com.gustavofc97.domain.repositories.PostRepository
import com.gustavofc97.domain.then
import com.gustavofc97.domain.whenever
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetPostListUseCaseTest : MockableTest {

    @MockK
    lateinit var getPostListRepository: PostRepository

    private val posts = listOf(
        Post(
            1,
            1212,
            "This is a title",
            "And this is description for the current post"
        ),
        Post(
            11,
            1213,
            "This is another title",
            "And this is another description for the current post")
    )

    @Before
    override fun setup() {
        super.setup()
        coEvery {
            getPostListRepository.getPosts()
        }.answers {
            flowOf(posts)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when call get post list then should return a post list`() = runTest{

        val useCase = given {
            GetPostListUseCase(getPostListRepository)
        }

        val result = whenever {
            runBlocking {
                useCase().first()
            }
        }


        then {
            Assert.assertEquals(result.size, posts.size)
            result.forEachIndexed { index, post ->
                Assert.assertEquals(post.description, posts[index].description)
                Assert.assertEquals(post.title, posts[index].title)
                Assert.assertEquals(post.id, posts[index].id)
            }
        }

    }
}