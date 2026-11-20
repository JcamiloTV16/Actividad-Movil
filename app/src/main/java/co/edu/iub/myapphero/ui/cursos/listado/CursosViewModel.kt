package co.edu.iub.myapphero.ui.cursos.listado

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.iub.myapphero.domain.repository.CursoRepository
import co.edu.iub.myapphero.ui.components.mensajeCursoError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CursosViewModel @Inject constructor(private val repository: CursoRepository) : ViewModel() {
    var uiState by mutableStateOf(CursosUiState())
        private set

    init { cargar() }

    fun cargar() {
        if (uiState.cargando) return
        uiState = uiState.copy(cargando = true, error = null)
        viewModelScope.launch {
            try {
                uiState = uiState.copy(cursos = repository.listar())
            } catch (cancelacion: CancellationException) {
                throw cancelacion
            } catch (error: Exception) {
                uiState = uiState.copy(error = mensajeCursoError(error))
            } finally {
                uiState = uiState.copy(cargando = false)
            }
        }
    }
}
