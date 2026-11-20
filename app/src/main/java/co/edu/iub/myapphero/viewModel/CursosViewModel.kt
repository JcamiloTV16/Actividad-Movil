package co.edu.iub.myapphero.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.edu.iub.myapphero.domain.model.Curso
import kotlin.collections.listOf

class CursosViewModel : ViewModel() {
    var cursos by mutableStateOf(
        listOf(
            Curso(
                id = 1,
                titulo = "Android con Kotlin",
                descripcion = "Desarrollo de aplicaciones móviles."
            ),
            Curso(
                id = 2,
                titulo = "Spring Boot",
                descripcion = "Desarrollo de APIs REST."
            ),
            Curso(
                id = 3,
                titulo = "Angular",
                descripcion = "Desarrollo de aplicaciones web."
            ),
            Curso(
                id = 4,
                titulo = "Base de datos",
                descripcion = "Fundamentos de MySQL"
            ),
            Curso(
                id = 5,
                titulo = "Programación Web",
                descripcion = "Fundamentos de html, css  & js"
            ),
            Curso(
                id = 6,
                titulo = "Programación Web",
                descripcion = "Fundamentos de html, css  & js"
            ),
            Curso(
                id = 7,
                titulo = "Programación Web",
                descripcion = "Fundamentos de html, css  & js"
            )
        )
    )
        private set

    private var siguienteId = (cursos.maxOfOrNull { it.id } ?: 0) + 1

    fun buscar(cursoId: Int): Curso? {
        return cursos.find { it.id == cursoId }
    }

    fun crear(titulo: String, descripcion: String): Boolean {
        if (titulo.isBlank() || descripcion.isBlank()) {
            return false
        }
        val nuevo = Curso(
            id = siguienteId,
            titulo = titulo,
            descripcion = descripcion
        )
        cursos = cursos + nuevo
        siguienteId++
        return true
    }

    fun actualizar(id: Int, titulo: String, descripcion: String, estado: Boolean ): Boolean {
        if (titulo.isBlank() || descripcion.isBlank()) {
            return false
        }
        val indice = cursos.indexOfFirst { it.id == id }
        if (indice == -1) {
            return false
        }
        val nuevaLista = cursos.toMutableList()
        nuevaLista[indice] = cursos[indice].copy(
            titulo = titulo,
            descripcion = descripcion,
            completado = estado
        )
        cursos = nuevaLista

        return true

    }

    fun eliminar(id: Int): Boolean {
        if (buscar(id) == null) {
            return false
        }
        cursos = cursos.filter { it.id != id }
        return true
    }

}