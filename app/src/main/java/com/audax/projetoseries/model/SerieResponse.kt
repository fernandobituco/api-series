package com.audax.projetoseries.model

import java.io.Serializable

class SerieResponse : Serializable{
    var nome = ""
    var descricao = ""
    var capa = ""
    var id = ""
    override fun toString(): String {
        return "SerieResponse(nome='$nome', descricao='$descricao', capa='$capa', id='$id')"
    }

}