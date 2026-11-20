package co.edu.iub.myapphero.data.repository

import co.edu.iub.myapphero.data.remote.request.CursoRequest
import co.edu.iub.myapphero.data.remote.response.CursoApi
import co.edu.iub.myapphero.domain.model.Curso
import co.edu.iub.myapphero.domain.repository.CursoException
import co.edu.iub.myapphero.domain.repository.CursoNoEncontradoException
import co.edu.iub.myapphero.domain.repository.CursoRepository
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CursoRepositoryImpl @Inject constructor(private val api: CursoApi) : CursoRepository {

    override suspend fun listar(): List<Curso> = solicitar {
        api.listar().map { it.toCurso() }
    }

    override suspend fun obtener(id: Int): Curso? = try {
        solicitar { api.obtener(id).toCurso() }
    } catch (_: CursoNoEncontradoException) {
        null
    }

    override suspend fun crear(titulo: String, descripcion: String): Curso = solicitar {
        api.crear(CursoRequest(titulo, descripcion)).toCurso()
    }

    override suspend fun actualizar(
        id: Int,
        titulo: String,
        descripcion: String,
        completado: Boolean
    ): Curso = solicitar {
        api.actualizar(id, CursoRequest(titulo, descripcion, completado)).toCurso()
    }

    override suspend fun eliminar(id: Int) = solicitar {
        val respuesta = api.eliminar(id)
        if (!respuesta.isSuccessful) throw HttpException(respuesta)
    }

    // La presentación recibe errores del contrato, no excepciones de Retrofit.
    private suspend fun <T> solicitar(accion: suspend () -> T): T = try {
        accion()
    } catch (cancelacion: CancellationException) {
        throw cancelacion
    } catch (error: HttpException) {
        if (error.code() == 404) throw CursoNoEncontradoException()
        throw CursoException(
            "El servidor no pudo completar la solicitud (HTTP ${error.code()}).",
            error
        )
    } catch (error: IOException) {
        throw CursoException(
            "No se pudo conectar. Revisa tu conexión e inténtalo de nuevo.",
            error
        )
    } catch (error: Exception) {
        throw CursoException("No se pudo procesar la respuesta del servidor.", error)
    }
}
