package com.example.rxjavaandroidsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Function
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
            add(myObservable
//                .map(object : Function<Student, Employee> {
//                override fun apply(s: Student): Employee {
//                    val employee = Employee(s.name, s.age)
//                    return employee
//                    }
//                })
                .flatMap(object : Function<Student, Observable<Employee>>{
                    override fun apply(s: Student): Observable<Employee> {
                        val employee1 = Employee("new member", 22)
                        val employee2 = Employee("new member2", 2)
                        val employee = Employee(s.name, s.age)
                        return Observable.just(employee, employee1, employee2)
                    }
                })
                .subscribeWith(getObserver()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
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
