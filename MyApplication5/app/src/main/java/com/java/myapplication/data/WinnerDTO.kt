package com.java.myapplication.data

import android.provider.ContactsContract.CommonDataKinds.Nickname
import kotlinx.serialization.Serializable


@Serializable
data class WinnerDTO(
    var nickname: String,
    var capital: Int
) {
}