package com.example.rxjavaandroidsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Predicate
import io.reactivex.rxjava3.observers.DisposableObserver

class MainActivity : AppCompatActivity(){

    private lateinit var myObservable: Observable<Int>
    private val TAG = "MainActivity"
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myObservable = Observable.range(1, 20)
        myObservable
            .filter(object : Predicate<Int>{
                override fun test(t: Int): Boolean {
                    return t%3 == 0
                }
            })
            .subscribe(object : DisposableObserver<Int>() {
                override fun onNext(t: Int) {
                    Log.v(TAG, "onNext: $t")
                }

                override fun onError(e: Throwable?) {
                    Log.v(TAG, "onError: $e")
                }

                override fun onComplete() {
                    Log.v(TAG, "onComplete")
                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        //compositeDisposable.clear()
    }

    private fun getObserver(): DisposableObserver<Employee> {
        return object : DisposableObserver<Employee>() {

            override fun onNext(t: Employee?) {
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
