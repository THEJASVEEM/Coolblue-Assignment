package com.thejasvee.coolblue.data.repository

import com.thejasvee.coolblue.core.result.AppResult
import com.thejasvee.coolblue.core.result.ErrorMessages
import com.thejasvee.coolblue.core.result.ErrorType
import com.thejasvee.coolblue.data.mapper.toDomain
import com.thejasvee.coolblue.data.remote.datasource.ProductRemoteDataSource
import com.thejasvee.coolblue.domain.model.ProductSearchResult
import com.thejasvee.coolblue.domain.repository.ProductRepository
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDatasource: ProductRemoteDataSource
) : ProductRepository {
    override suspend fun searchProducts(
        query: String,
        page: Int
    ): AppResult<ProductSearchResult> {
        return try {
            val response = productRemoteDatasource.searchProducts(
                query,
                page
            )
            AppResult.Success(response.toDomain())
        } catch (exp: IOException) {
            AppResult.Error(
                message = ErrorMessages.NETWORK,
                type = ErrorType.NETWORK,
                cause = exp
            )
        } catch (exp: HttpException) {
            AppResult.Error(
                message = ErrorMessages.SERVER,
                type = ErrorType.SERVER,
                cause = exp
            )
        } catch (exp: SerializationException) {
            AppResult.Error(
                message = ErrorMessages.SERIALIZATION,
                type = ErrorType.SERIALIZATION,
                cause = exp
            )
        } catch (exp: Exception) {
            AppResult.Error(
                message = ErrorMessages.UNKNOWN,
                type = ErrorType.UNKNOWN,
                cause = exp
            )
        }
    }

}