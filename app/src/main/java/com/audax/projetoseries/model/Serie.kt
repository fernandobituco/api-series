package com.audax.projetoseries.model

import java.io.Serializable

class Serie : Serializable{
    var nome = ""
    var descricao = ""
    var capa = ""
    override fun toString(): String {
        return "Serie{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", capa='" + capa + '\'' +
                '}'
    }
}