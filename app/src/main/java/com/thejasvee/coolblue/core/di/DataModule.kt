package com.thejasvee.coolblue.core.di

import com.thejasvee.coolblue.data.remote.datasource.ProductRemoteDataSource
import com.thejasvee.coolblue.data.remote.datasource.ProductRemoteDataSourceImpl
import com.thejasvee.coolblue.data.repository.ProductRepositoryImpl
import com.thejasvee.coolblue.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindProductRemoteDataSource(
        implements: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource


    @Binds
    @Singleton
    abstract fun bindProductRepository(
        implements: ProductRepositoryImpl
    ): ProductRepository
}