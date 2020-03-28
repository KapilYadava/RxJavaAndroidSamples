package com.example.rxjavaandroidsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver

class MainActivity : AppCompatActivity(){

    private lateinit var myObservable: Observable<Student>
    private val TAG = "MainActivity"
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myObservable = Observable.create(ObservableOnSubscribe { emitter->
            val students = getStudents();
            for(s in students){
                emitter.onNext(s)
            }
            emitter.onComplete()
        })

        compositeDisposable.apply {
            add(myObservable.subscribeWith(getObserver()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun getObserver(): DisposableObserver<Student> {
        return object : DisposableObserver<Student>() {

            override fun onNext(t: Student?) {
                Log.v(TAG, "onNext: $t")
                //textview1.text = t
            }
            override fun onError(e: Throwable?) {
                Log.v(TAG, "onError: " + e.toString())
            }
            override fun onComplete() {
                Log.v(TAG, "onComplete")
            }
        }
    }

    private fun getStudents() : List<Student>{
        val list = ArrayList<Student>()
        var student = Student("Kapil", "Kapilyadava.isa@gmail.com", 32)
        list.add(student)

        student = Student("Takshii", "Takshiiyadava.isa@gmail.com", 2)
        list.add(student)

        student = Student("Alka", "alkayadava.isa@gmail.com", 28)
        list.add(student)

        student = Student("Mahendra", "mahendrasingh.isa@gmail.com", 65)
        list.add(student)

        student = Student("Santresh", "santresh.devi66@gmail.com", 62)
        list.add(student)

        return list
    }
}
