package com.example.rxjavaandroidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var myObservable: Observable<String>
    private lateinit var myObserver: Observer<String>
    private val greetings =  arrayOf("Hello RxAndroid", "Hello Rx Java", "Hello Android")
    private val TAG = "MainActivity"
    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myObservable = Observable.just("Hello", "Hello RX", "Hello Rx Android")

        myObserver = object: Observer<String>{

            override fun onSubscribe(d: Disposable) {
                Log.v(TAG, "onSubscribe")
                disposable = d
            }
            override fun onNext(t: String?) {
                Log.v(TAG, "onNext: $t")
                textview1.text = t
            }
            override fun onError(e: Throwable?) {
                Log.v(TAG, "onError: " + e.toString())
            }
            override fun onComplete() {
                Log.v(TAG, "onComplete")
            }
        }

        myObservable.subscribe(myObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
