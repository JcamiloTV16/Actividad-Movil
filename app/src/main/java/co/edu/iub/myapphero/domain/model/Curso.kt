package co.edu.iub.myapphero.domain.model

data class Curso(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val completado: Boolean = false
)
