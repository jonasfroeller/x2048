package htlleonding.jonasfroeller.x2048.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import htlleonding.jonasfroeller.x2048.engine.GameViewModel
import htlleonding.jonasfroeller.x2048.ui.theme.GameBackground
import htlleonding.jonasfroeller.x2048.ui.theme.GameCellBackground
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun UiBoard(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    var dragStart = Pair(0f, 0f)
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStart = Pair(offset.x, offset.y)
                    },
                    onDragEnd = {
                        // Movement
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val (startX, startY) = dragStart
                        val endX = startX + dragAmount.x
                        val endY = startY + dragAmount.y
                        
                        val deltaX = endX - startX
                        val deltaY = endY - startY
                        
                        if (Math.abs(deltaX) > Math.abs(deltaY)) {
                            if (deltaX > 0) {
                                viewModel.moveRight()
                            } else {
                                viewModel.moveLeft()
                            }
                        } else {
                            if (deltaY > 0) {
                                viewModel.moveDown()
                            } else {
                                viewModel.moveUp()
                            }
                        }
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(Color.LightGray)
        ) {
            Column(Modifier.fillMaxSize()) {
                viewModel.state.board.forEach { row ->
                    Row(Modifier
                        .weight(1f)
                        .background(GameCellBackground)) {
                        row.forEach { value ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(GameBackground)
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = value.toString(),
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
