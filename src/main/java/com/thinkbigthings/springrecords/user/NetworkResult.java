package com.thinkbigthings.springrecords.user;

// algebraic data types FTW
// if all subtypes are in this file then we do not need "permits"
sealed interface NetworkResult<V> {

    record Success<V>(V result) implements NetworkResult<V> { }
    record Failure<V>(Throwable cause) implements NetworkResult<V> { }
    record Timeout<V>() implements NetworkResult<V> { }
}
