package com.buchi.listdetail.presentation

/**
 * All possible events that can be triggered from main viewmodel
 */
sealed class MainStateEvent {
    // Events to fetch users is routed through this class
    class FetchAllUsers() : MainStateEvent()
    // Events to fetch user detail is routed through this class
    class FetchUserDetail(val userId: String?): MainStateEvent()
    // Idle state of the Main View, No processing here.
    class Idle(): MainStateEvent()
}