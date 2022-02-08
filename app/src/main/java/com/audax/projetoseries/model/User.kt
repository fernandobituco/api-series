package com.audax.projetoseries.model

class User {
    var email = ""
    var senha = ""

    override fun toString(): String {
        return "User{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}'
    }

}