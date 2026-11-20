package co.edu.iub.myapphero.domain.repository

import co.edu.iub.myapphero.domain.model.Curso

interface CursoRepository {
    suspend fun listar(): List<Curso>
    suspend fun obtener(id: Int): Curso?
    suspend fun crear(titulo: String, descripcion: String): Curso
    suspend fun actualizar(id: Int, titulo: String, descripcion: String, completado: Boolean): Curso
    suspend fun eliminar(id: Int)
}