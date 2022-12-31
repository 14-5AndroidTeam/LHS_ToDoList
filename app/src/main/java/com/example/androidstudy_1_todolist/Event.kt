package com.example.androidstudy_1_todolist

/** 단일 이벤트 처리*/
// 화면 회전과 같은 동작이 일어나면 Activity는 파괴된 후 재생성.
// 그러나 ViewModel은 유지
// 옵저빙을 중복으로 하는 문제가 발생한다.

class Event<out T>(private val content:T) {
    var hasBeenHandled = false

    fun getContentIfNotHandled():T?{
        return if(hasBeenHandled){ //이벤트가 이미 처리된 상태
            null
        } else {
            hasBeenHandled = true //이벤트 처리 표시하기
            content //값 반환
        }
    }
    /**
     * 이벤트의 처리 여부에 상관 없이 값을 반환
     */

    fun peekContent():T = content
}