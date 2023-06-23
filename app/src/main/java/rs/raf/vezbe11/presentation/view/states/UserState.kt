package rs.raf.vezbe11.presentation.view.states


import rs.raf.vezbe11.data.models.entities.UserEntity

sealed class UserState {
    object Loading: UserState()
    object DataFetched:UserState()
    data class Success(val users: List<UserEntity>):UserState()
    data class Error(val message: String): UserState()
}