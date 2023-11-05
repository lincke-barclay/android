package com.alth.events.networking.sources

import com.alth.events.networking.sources.impl.RetrofitNetworkEventDataSource
import com.alth.events.networking.sources.impl.RetrofitNetworkFriendshipDataSource
import com.alth.events.networking.sources.impl.RetrofitNetworkUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkSourcesDataModule {
    @Binds
    abstract fun bindEventDataSource(
        retrofitRemoteEventDataSource: RetrofitNetworkEventDataSource,
    ): NetworkEventDataSource


    @Binds
    abstract fun bindFriendDataSource(
        rfds: RetrofitNetworkFriendshipDataSource,
    ): NetworkFriendshipDataSource

    @Binds
    abstract fun bindUserDataSource(
        u: RetrofitNetworkUserDataSource,
    ): NetworkUserDataSource
}