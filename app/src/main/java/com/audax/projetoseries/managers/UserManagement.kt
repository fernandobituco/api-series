package com.audax.projetoseries.managers

import android.util.Base64

class UserManagement {
    companion object {
        val authorizaton: String = Base64.encodeToString("DDpfFDZo!@7oQIhxDb%y7JQd3LUB%@IN:DbVHAbP7of2@2ZZL3GYsnWUnDJ5Feq1w".toByteArray(), Base64.NO_WRAP)
    }
}