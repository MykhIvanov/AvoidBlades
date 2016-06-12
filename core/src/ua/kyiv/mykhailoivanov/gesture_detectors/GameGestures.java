package ua.kyiv.mykhailoivanov.gesture_detectors;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import ua.kyiv.mykhailoivanov.AvoidBladesGame;
import ua.kyiv.mykhailoivanov.Fingure;
import ua.kyiv.mykhailoivanov.screens.GameScreen;

/**
 * Created by MykhailoIvanov on 3/29/2016.
 */
public class GameGestures implements GestureDetector.GestureListener {
    private AvoidBladesGame game;
    public GameGestures(AvoidBladesGame gameIn)
    {
        game = gameIn;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        game.getGameScreen().started = true;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (!GameScreen.loose) {
            GameScreen.stopTouched = true;
            if (game.canPlayMoveSound)
            {
                game.moveSound.play();
                game.canPlayMoveSound = false;
            }
        }
        //GameScreen.loose = true;
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if (!GameScreen.loose) {
            GameScreen.stopTouched = true;
            if (game.canPlayMoveSound)
            {
                game.moveSound.play();
                game.canPlayMoveSound = false;
            }
        }
        //GameScreen.loose = true;
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
