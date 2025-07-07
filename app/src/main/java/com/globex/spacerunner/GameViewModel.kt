package com.globex.spacerunner

import android.util.DisplayMetrics
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameState(
    var screenWidth: Int = 540,
    var screenHeight: Int = 960,
    var density: Float = 0F,
    var playerRadius: Int = 50,
    var x: Int = 0,
    var y: Int = 0,
    var speed: Int = 10,
    var moveX: Int = 0,
    var moveY: Int = 0,
    var initialSquares: Int = 20,
    var score: Score = Score(),
    var playerSquare: Square = Square(0, 0, 0, 0, 0),
    var squares: ArrayList<Square> = ArrayList(),
    var rubbishAux: ArrayList<Square> = ArrayList()
) {
    fun update(frametime: Long): Boolean {
        var addSquare = false
        /*if ((moveX > 0) && (playerSquare.getPosition().getX() < (normalizedWidth - playerRadius))) {
            playerSquare.setPosition(
                playerSquare.getPosition().getX() + moveX,
                playerSquare.getPosition().getY() + moveY
            )
        } else if ((moveX < 0) && (playerSquare.getPosition().getX() > 0)) {
            playerSquare.setPosition(
                playerSquare.getPosition().getX() + moveX,
                playerSquare.getPosition().getY() + moveY
            )
        }*/

        val iterator = squares.iterator()
        while (iterator.hasNext()) {
            val iteratedSquare = iterator.next()
            if (iteratedSquare.getPosition().getY() > screenHeight) {
                score.addScore(iteratedSquare.getSize())
                iteratedSquare.reloadSquare(this)
            } else {
                if ((iteratedSquare.getPosition()
                        .getX() > screenWidth) || (iteratedSquare.getPosition().getX() < 0)
                ) {
                    iteratedSquare.changeDir()
                }
                iteratedSquare.update()
                if (iteratedSquare.isCollision(playerSquare)) {
                    playerSquare.collision(iteratedSquare)
                    if (playerSquare.getSize() <= 0) {
                        return true
                    }
                    rubbishAux.addAll(iteratedSquare.getRubbishAndReload(this))
                    addSquare = true
                }
            }
        }
        if (addSquare) {
            squares.addAll(rubbishAux)
            rubbishAux.clear()
        }
        return false
    }
}

sealed interface DrawingAction {
    data object OnTap: DrawingAction
}

class GameViewModel: ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    fun onAction(action: DrawingAction, offset: Offset) {
        when(action) {
            DrawingAction.OnTap -> onTap(offset)
        }
    }

    fun onTap(offset: Offset){
        System.out.println("onTap!")
        /**
         * float x = e.getX();
         * 		switch (e.getAction()) {
         * 			case MotionEvent.ACTION_MOVE:
         * 				if (x > screenWidth / 2) {
         * 					moveX = speed;
         * 				} else {
         * 					moveX = -speed;
         * 				}
         * 				break;
         * 			case MotionEvent.ACTION_DOWN:
         * 				if (x > screenWidth / 2) {
         * 					moveX = speed;
         * 				} else {
         * 					moveX = -speed;
         * 				}
         * 				break;
         * 			case MotionEvent.ACTION_UP:
         * 				moveX = 0;
         * 				moveY = 0;
         * 				break;
         *
         * 		}
         */
    }

    fun setScreenDimensions(displayMetrics: DisplayMetrics) {
        val x = (displayMetrics.widthPixels / 2) - (_state.value.playerRadius / 2)
        val y = displayMetrics.heightPixels - (_state.value.playerRadius * 1.5).toInt()
        _state.value.screenWidth = displayMetrics.widthPixels
        _state.value.screenHeight = displayMetrics.heightPixels
        _state.value.x = x
        _state.value.y = y
        _state.value.density = displayMetrics.density
        _state.value.playerSquare = Square(x, y, _state.value.speed, _state.value.speed, _state.value.playerRadius)
        for (i in 0..<_state.value.initialSquares) {
            _state.value.squares.add(Square(_state.value))
        }
    }
}