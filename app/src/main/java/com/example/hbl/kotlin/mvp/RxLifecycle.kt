package com.example.hbl.kotlin.mvp

import io.reactivex.subjects.BehaviorSubject

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class RxLifecycle {
    enum class Event {
        CREATE, ATTACH, CREATE_VIEW, RESTART, START, RESUME,
        PAUSE, STOP, DESTROY_VIEW, DETACH, DESTROY
    }

    val behavior: BehaviorSubject<Event> = BehaviorSubject.create()

    fun onCreate() {
        behavior.onNext(Event.CREATE)
    }

    fun onAttach() {
        behavior.onNext(Event.ATTACH)
    }

    fun onCreateView() {
        behavior.onNext(Event.CREATE_VIEW)
    }

    fun onRestart() {
        behavior.onNext(Event.RESTART)
    }

    fun onStart() {
        behavior.onNext(Event.START)
    }

    fun onResume() {
        behavior.onNext(Event.RESUME)
    }

    fun onPause() {
        behavior.onNext(Event.PAUSE)
    }

    fun onStop() {
        behavior.onNext(Event.STOP)
    }

    fun onDestroyView() {
        behavior.onNext(Event.DESTROY_VIEW)
    }

    fun onDetach() {
        behavior.onNext(Event.DETACH)
    }

    fun onDestroy() {
        behavior.onNext(Event.DESTROY)
    }
}