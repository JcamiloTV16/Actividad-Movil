package co.edu.iub.myapphero.data.remote.dto

import co.edu.iub.myapphero.domain.model.Curso

data class CursoDto(
    val id: String?,
    val titulo: String?,
    val descripcion: String?,
    val completado: Boolean?
) {

    fun toCurso(): Curso = Curso(
        id = requireNotNull(id?.toIntOrNull()) { "Identificador de curso inválido" },
        titulo = requireNotNull(titulo) { "Falta el título" },
        descripcion = requireNotNull(descripcion) { "Falta la descripción" },
        completado = requireNotNull(completado) { "Falta el estado" }
    )

}