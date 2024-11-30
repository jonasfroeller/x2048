package htlleonding.jonasfroeller.x2048

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import htlleonding.jonasfroeller.x2048.engine.GameViewModel
import htlleonding.jonasfroeller.x2048.ui.UiBoard
import htlleonding.jonasfroeller.x2048.ui.theme.Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                val viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UiBoard(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}