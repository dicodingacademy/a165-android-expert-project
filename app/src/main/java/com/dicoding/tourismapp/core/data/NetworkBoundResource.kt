package com.dicoding.tourismapp.core.data

import com.dicoding.tourismapp.core.data.source.remote.network.ApiResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class NetworkBoundResource<ResultType: Any, RequestType> {

    private val result = PublishSubject.create<Resource<ResultType>>()
    private val mCompositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        val dbSource = loadFromDB()
        val db = dbSource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { value ->
                if (shouldFetch(value)) {
                    fetchFromNetwork()
                } else {
                    result.onNext(Resource.Success(value))
                }
            }
        mCompositeDisposable.add(db)
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flowable<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): Flowable<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork() {

        val apiResponse = createCall()

        result.onNext(Resource.Loading(null))
        val response = apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        saveCallResult(response.data)
                        val dbSource = loadFromDB()
                        val subscribe = dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe {
                                result.onNext(Resource.Success(it))
                            }
                        subscribe.dispose()
                    }
                    is ApiResponse.Empty -> {
                        val dbSource = loadFromDB()
                        val subscribe = dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe {
                                result.onNext(Resource.Success(it))
                            }
                        subscribe.dispose()
                    }
                    is ApiResponse.Error -> {
                        onFetchFailed()
                        result.onNext(Resource.Error(response.errorMessage, null))
                    }
                }
            }
        mCompositeDisposable.add(response)
    }

    fun asFlowable(): Flowable<Resource<ResultType>> =
        result.toFlowable(BackpressureStrategy.BUFFER)
}