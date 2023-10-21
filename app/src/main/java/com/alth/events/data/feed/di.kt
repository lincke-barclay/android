package com.alth.events.data.feed

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedModule {
    @Binds
    abstract fun bindFeedElementRepository(
        networkOnlyFeedElementRepository: NetworkOnlyFeedElementRepository,
    ): FeedElementRepository

    @Binds
    abstract fun bindFeedDataSource(
        retrofitRemoteFeedDataSource: RetrofitRemoteFeedDataSource,
    ): RemoteFeedDataSource
}
