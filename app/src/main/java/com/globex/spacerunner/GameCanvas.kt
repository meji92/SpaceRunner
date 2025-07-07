package com.globex.spacerunner

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.whenStarted

@Composable
fun GameCanvas(
    state: GameState,
    onAction: (DrawingAction, Offset) -> Unit,
    modifier: Modifier = Modifier
) {
    var frameTime by remember { mutableStateOf(0L) }
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.whenStarted {
            while (true) {
                // this will be called for each frame
                // by updating `remember` value we initiating EachFrameUpdatingCanvas redraw
                frameTime = withFrameMillis { it }
            }
        }
    }
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .clipToBounds()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { tapOffset ->
                        onAction(DrawingAction.OnTap, tapOffset)
                    }
                )
            }
            .drawWithContent {
                drawContent()
                drawGame(state, textMeasurer)
            }
            /*.pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        onAction(DrawingAction.OnNewPathStart)
                    },
                    onDragEnd = {
                        onAction(DrawingAction.OnPathEnd)
                    },
                    onDrag = { change, _ ->
                        onAction(DrawingAction.OnDraw(change.position))
                    },
                    onDragCancel = {
                        onAction(DrawingAction.OnPathEnd)
                    },
                )
            }*/
    ) {
        state.update(frameTime)
    }
}

private fun DrawScope.drawGame(state: GameState, textMeasurer: TextMeasurer){
    drawPlayer(state)
    drawSquares(state)
    drawScore(state, textMeasurer)
}

private fun DrawScope.drawScore(state: GameState, textMeasurer: TextMeasurer){
    val offset = Offset((state.screenHeight / 25).toFloat(), (state.screenWidth / 20).toFloat())
    val fontSize = TextUnit((state.screenHeight / 20).toFloat(), TextUnitType.Unspecified)
    val style = TextStyle(Color.Green, fontSize)
    drawText(
        textMeasurer,
        state.score.scoreString,
        offset,
        style
    )
}

private fun DrawScope.drawPlayer(state: GameState){
    val playerSquareOffset = state.playerSquare?.getPosition()?.x?.toFloat()?.let { Offset(it, state.playerSquare.getPosition().y.toFloat()) }
    val playerSize = state.playerSquare?.getSize()?.toFloat()?.let { Size(dpToPx(state, it), dpToPx(state, it)) }
    drawRect(
        Color.Green,
        playerSquareOffset ?: Offset(0.0F, 0.0F),
        playerSize ?: Size(0.0F, 0.0F)
    )
}

private fun DrawScope.drawSquares(state: GameState){
    for (a in state.squares) {
        val squareOffset = a?.getPosition()?.x?.toFloat()?.let { Offset(it, a.getPosition().y.toFloat()) }
        val size = a?.getSize()?.toFloat()?.let { Size(dpToPx(state, it), dpToPx(state, it)) }

        drawRect(
            Color.White,
            squareOffset ?: Offset(0.0F, 0.0F),
            size ?: Size(0.0F, 0.0F)
        )
    }
}

private fun dpToPx(state: GameState, dp: Float): Float {
    return dp * state.density
}