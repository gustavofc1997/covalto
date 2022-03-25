package com.gustavofc97.domain.di

import com.gustavofc97.domain.repositories.CommentsRepository
import com.gustavofc97.domain.repositories.PostRepository
import com.gustavofc97.domain.repositories.UserRepository
import com.gustavofc97.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun providesGetUserByIdUseCase(userRepository: UserRepository) =
        GetUserByIdUseCase(userRepository)

    @Provides
    fun providesGetPostListUseCase(postRepository: PostRepository) =
        GetPostListUseCase(postRepository)

    @Provides
    fun providesGetCommentsByPostUseCase(repository: CommentsRepository) =
        GetCommentsByPostUseCase(repository)

}