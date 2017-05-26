package com.example.hbl.kotlin

/**
 * Created by hbl on 2017/5/24.
 */
import kotlin.properties.Delegates

class User {
    var name: String by Delegates.observable("no name") {
        d, old, new ->
        println("$old - $new")
    }
    var list: MutableList<String> by Delegates.observable(mutableListOf("1", "2", "3")) {
        d, old, new ->
        print(old)
        print(new)
    }
}

fun indexOfMax(a: IntArray): Int? {
    if (a.isEmpty()) return null
    var largeIndex: Int = 0
    var large = a[0]
    for (index in a.indices) {
        if (a[index] > large) {
            large = a[index]
            largeIndex = index
        }
    }
    return largeIndex
}

fun runs(a: IntArray): Int {
    var runs = 0
    if (a.isEmpty()) return runs
    for (i in a.indices) {
        if (i == a.size) {
            runs++
            return runs
        }
        if (a[i] != a[i + 1]) {
            runs++
        }
    }
    return 0
}

fun isPalindrome(s: String): Boolean {
    for (a in s.indices) {
        if(s[a]!=s[s.length-1-a]){
            return false
        }
        if(a>=s.length-1-a){
            return true
        }
    }
    return true
}
data class Prese(val name:String,val age:Int)
var a:Int?=null
fun main(args: Array<String>) {

//    val user = User()
//    user.name = "Carl"
//    user.list = mutableListOf("A", "B", "C")
//    user.list = mutableListOf("Tom", "Bob", "Cala")
//    a?.let { println(it) }?: a.let { a=10 }
//    println("asdf:"+a)
//    a?.let { println(it) }?:a.let { a=20 }
//    println("end $a")

    println(split("a.java"))
   
}
fun split(s:String):String{
    val lastIndexOf = s.lastIndexOf(".")
    if(lastIndexOf!=-1){
        var substring = s.substring(lastIndexOf+1)
        return substring
    }
    return s
}



