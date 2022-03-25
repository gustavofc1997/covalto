package com.gustavofc97.domain.usecases

import com.gustavofc97.domain.MockableTest
import com.gustavofc97.domain.given
import com.gustavofc97.domain.model.Comment
import com.gustavofc97.domain.repositories.CommentsRepository
import com.gustavofc97.domain.then
import com.gustavofc97.domain.whenever
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetCommentsByPostUseCaseTest : MockableTest {

    @MockK
    lateinit var getCommentsRepository: CommentsRepository

    private val comments = listOf(
        Comment("Title comment", "Title description 0", "gustavo@forero.com"),
        Comment("Title comment", "Title description 1", "gustavo@forero.com")
        )

    @Before
    override fun setup() {
        super.setup()
        coEvery {
            getCommentsRepository.getCommentsByPost(any())
        }.answers {
            comments
        }
    }

    @Test
    fun `Give a post id when get comments by post then should return a comment list`() {
        val useCase = given {
            GetCommentsByPostUseCase(getCommentsRepository)
        }

        val result = whenever {
            runBlocking {
                useCase("23")
            }
        }

        then {
            Assert.assertEquals(result.size, comments.size)
            result.forEachIndexed { index, comment ->
                Assert.assertEquals(comment.description, comments[index].description)
                Assert.assertEquals(comment.title, comments[index].title)
                Assert.assertEquals(comment.email, comments[index].email)
            }
        }
    }
}