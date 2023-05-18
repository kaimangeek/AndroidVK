package com.example.hw2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hw2.model.Cat
import com.example.hw2.network.ApiFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val cats = MutableLiveData<List<Cat>>()

    var isLoading = MutableLiveData(false)
    var isError = MutableLiveData(false)
    var compositeDisposable = CompositeDisposable()

    init {
        loadCats()
    }

    fun loadCats(){
        val loading = isLoading.value
        if (loading!=null && loading==true)
            return

        val disposable = ApiFactory.apiService.loadMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(
                object : Consumer<Disposable> {
                    override fun accept(t: Disposable) {
                        isLoading.value = true
                    }
                }
            )
            .doAfterTerminate(
                object : Action {
                    override fun run() {
                        isLoading.value = false
                    }
                }
            )
            .subscribe(
                object : Consumer<List<Cat>> {
                    override fun accept(listCats: List<Cat>) {
                        isError.value = false
                        val loadedCats = ArrayList<Cat>(1)
                        cats.value?.let { loadedCats.addAll(it) }

                        if(loadedCats.isNotEmpty()) {
                            loadedCats.addAll(listCats)
                            cats.value = loadedCats
                        } else
                            cats.value = listCats
                    }
                },
                object : Consumer<Throwable> {
                    override fun accept(t: Throwable) {
                        isError.value = true
                    }
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}