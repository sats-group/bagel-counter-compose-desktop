package core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

abstract class ViewModel {
  private val coroutineContext = Job()
  protected val viewModelScope = CoroutineScope(coroutineContext)

  fun dispose() {
    coroutineContext.cancelChildren()
  }
}

@Composable
fun <T : ViewModel> viewModel(builder: () -> T): T {
  val viewModel = remember { builder() }

  DisposableEffect(Unit) {
    onDispose { viewModel.dispose() }
  }

  return viewModel
}
