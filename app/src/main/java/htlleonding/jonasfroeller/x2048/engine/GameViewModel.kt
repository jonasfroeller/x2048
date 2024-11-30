package htlleonding.jonasfroeller.x2048.engine

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider

class GameViewModel : ViewModel() {
    private val game2048Engine = Game2048()

    var state: GameState by mutableStateOf(game2048Engine.initializeBoard())
        private set

    fun moveLeft() {
        val newState = game2048Engine.moveLeft(state)
        if (newState != state) {
            state = game2048Engine.placeRandomTile(newState)
        }
    }

    fun moveRight() {
        val newState = game2048Engine.moveRight(state)
        if (newState != state) {
            state = game2048Engine.placeRandomTile(newState)
        }
    }

    fun moveUp() {
        val newState = game2048Engine.moveUp(state)
        if (newState != state) {
            state = game2048Engine.placeRandomTile(newState)
        }
    }

    fun moveDown() {
        val newState = game2048Engine.moveDown(state)
        if (newState != state) {
            state = game2048Engine.placeRandomTile(newState)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GameViewModel() as T
            }
        }
    }
}