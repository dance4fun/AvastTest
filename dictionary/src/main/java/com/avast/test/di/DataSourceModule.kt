package com.avast.test.di

import com.avast.test.data.source.DictionarySource
import com.avast.test.data.source.InMemoryDictionarySource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {

    @Provides
    fun bindDictionarySource(): DictionarySource = InMemoryDictionarySource()
}
