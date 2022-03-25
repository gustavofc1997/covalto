package com.gustavofc97.data.di

import com.gustavofc97.data.network.NetworkClient
import com.gustavofc97.data.network.PostServices
import com.gustavofc97.data.repositories.CommentsRepositoryImpl
import com.gustavofc97.data.repositories.PostRepositoryImpl
import com.gustavofc97.data.repositories.UserRepositoryImpl
import com.gustavofc97.domain.repositories.CommentsRepository
import com.gustavofc97.domain.repositories.PostRepository
import com.gustavofc97.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesPostRepository(
        service: PostServices, networkClient: NetworkClient
    ): PostRepository {
        return PostRepositoryImpl(service, networkClient)
    }

    @Provides
    @Singleton
    fun providesCommentsRepository(
        service: PostServices, networkClient: NetworkClient
    ): CommentsRepository {
        return CommentsRepositoryImpl(service, networkClient)
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        service: PostServices, networkClient: NetworkClient
    ): UserRepository {
        return UserRepositoryImpl(service, networkClient)
    }

}