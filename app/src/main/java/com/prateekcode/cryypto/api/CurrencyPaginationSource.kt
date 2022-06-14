package com.prateekcode.cryypto.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prateekcode.cryypto.model.Currencies
import com.prateekcode.cryypto.repo.Repository
import com.prateekcode.cryypto.utils.CurrencyTrend
import com.prateekcode.cryypto.utils.CurrencyTrend.*
import com.prateekcode.cryypto.utils.STARTING_PAGE
import com.prateekcode.cryypto.utils.showLog
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CurrencyPaginationSource @Inject constructor(
    private val repository: Repository,
    private val currencyTrend: CurrencyTrend
) :
    PagingSource<Int, Currencies>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Currencies> {
        val position = params.key ?: STARTING_PAGE

        return try {
            val response = repository.cryptoRemoteData.getCurrencyList(position)
            val responseData = mutableListOf<Currencies>()
            val currencies = response.body()
            when (currencyTrend) {
                ALL -> {
                    currencies?.let {
                        responseData.addAll(it)
                    }
                }
                GAINER -> {
                    currencies?.let {
                        currencies.filter {
                            it.oneDay?.priceChangePct!! >= 0
                        }.let {
                            responseData.addAll(it)
                        }
                    }
                }
                LOOSER -> {
                    currencies?.filter { it -> it.oneDay!!.priceChangePct <= 0 }?.let {
                        responseData.addAll(it)
                    }
                }
            }
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = if (currencies?.isEmpty() == true) null else position + 1
            )
        } catch (e: IOException) {
            e.showLog()
            LoadResult.Error(e)
        } catch (e: HttpException) {
            e.showLog()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Currencies>): Int? {
        return state.anchorPosition
    }


}