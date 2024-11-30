package htlleonding.jonasfroeller.x2048.engine

data class GameState(
    val board: List<List<Int>> = List(4) { List(4) { 0 } }
)