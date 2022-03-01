import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import core.viewModel

@Composable
@Preview
fun App() {
  val viewModel = viewModel { CounterViewModel() }

  Surface(color = Color.Black, contentColor = Color.White) {
    Row(
      modifier = Modifier
        .height(IntrinsicSize.Min)
        .padding(vertical = 27.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(painterResource("bagel.png"), contentDescription = null, Modifier.padding(start = 106.dp).size(160.dp))

      Text("Bagel counter", Modifier.padding(start = 74.dp), fontSize = 56.sp)

      Text(
        text = viewModel.counterValue.toString(),
        modifier = Modifier.padding(start = 110.dp),
        fontSize = 76.sp,
        fontWeight = FontWeight.Bold
      )

      Box(
        Modifier
          .padding(start = 66.dp)
          .width(1.dp)
          .fillMaxHeight()
          .background(color = Color(0xFFC4C4C4))
      )

      IconButton(
        modifier = Modifier.padding(start = 60.dp),
        onClick = viewModel::onDecrementClicked,
        enabled = viewModel.isDecrementEnabled
      ) {
        Icon(Icons.Outlined.ExpandMore, contentDescription = null, Modifier.size(104.dp))
      }

      IconButton(
        modifier = Modifier.padding(start = 21.dp, end = 77.dp),
        onClick = viewModel::onIncrementClicked,
        enabled = viewModel.isIncrementEnabled
      ) {
        Icon(Icons.Outlined.ExpandLess, contentDescription = null, Modifier.size(104.dp))
      }
    }
  }
}

fun main() = application {
  Window(
    onCloseRequest = ::exitApplication,
    state = rememberWindowState(
      position = WindowPosition(Alignment.Center),
      width = Dp.Unspecified,
      height = Dp.Unspecified,
    ),
    resizable = false,
    icon = painterResource("bagel.png"),
    title = "Bagel Counter"
  ) {
    App()
  }
}
