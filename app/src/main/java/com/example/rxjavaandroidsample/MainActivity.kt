package com.example.rxjavaandroidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var myObservable: Observable<Array<String>>
    private lateinit var myObserver: Observer<Array<String>>
    private val greetings =  arrayOf("Hello RxAndroid", "Hello Rx Java", "Hello Android")
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myObservable = Observable.just(greetings)

        myObserver = object: Observer<Array<String>>{

            override fun onSubscribe(d: Disposable?) {
                Log.v(TAG, "onSubscribe")
            }
            override fun onNext(t: Array<String>?) {
                Log.v(TAG, "onNext: $t")
                textview1.text = t!!.size.toString()
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
}
