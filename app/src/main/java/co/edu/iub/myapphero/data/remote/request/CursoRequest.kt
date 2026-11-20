package co.edu.iub.myapphero.data.remote.request

data class CursoRequest(
    val titulo: String,
    val descripcion: String,
    val completado: Boolean = false
)