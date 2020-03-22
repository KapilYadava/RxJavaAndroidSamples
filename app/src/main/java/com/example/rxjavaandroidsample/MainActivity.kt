package com.example.rxjavaandroidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var myObservable: Observable<String>
    private lateinit var myObserver: DisposableObserver<String>
    private val greetings = "Hello RxAndroid"
    private val TAG = "MainActivity"
    //private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myObservable = Observable.just(greetings)

        myObserver = object: DisposableObserver<String>() {

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
        //disposable.dispose()
        myObserver.dispose()
    }
}
