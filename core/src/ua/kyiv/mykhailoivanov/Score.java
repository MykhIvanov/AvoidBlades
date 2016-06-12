package ua.kyiv.mykhailoivanov;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.nio.sctp.AbstractNotificationHandler;

/**
 * Created by MykhailoIvanov on 5/11/2016.
 */
public class Score {
    private int value;

    private BitmapFont scoreBitmapFont;
    private BitmapFont recordBitmapFont;
    private AvoidBladesGame game;

    private float posY_mainMenu;

    public static final float MAIN_POS_X = Gdx.graphics.getHeight()/25;
    public static final float MAIN_POS_Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/25;

    public Score(AvoidBladesGame gameIn)
    {
        game = gameIn;

        scoreBitmapFont = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
        recordBitmapFont = new BitmapFont(Gdx.files.internal("font/font.fnt"), false);
        scoreBitmapFont.setColor(Color.BLACK);
        recordBitmapFont.setColor(Color.BLACK);
        scoreBitmapFont.getData().setScale(AvoidBladesGame.SCORE_TEXT_SCALING);
        recordBitmapFont.getData().setScale(AvoidBladesGame.SCORE_TEXT_SCALING);
        makeScoreZero();

        posY_mainMenu = MAIN_POS_Y;
    }

    public void draw(SpriteBatch batch)
    {
        wasMadeZero = false;
        if (posY_mainMenu > MAIN_POS_Y)
            posY_mainMenu -= Button.ANIMATION_SPEED;
        scoreBitmapFont.draw(batch, Integer.toString(value), MAIN_POS_X, posY_mainMenu);

    }

    public void drawOut(SpriteBatch batch)
    {
        wasMadeZero = false;
        posY_mainMenu += Button.ANIMATION_SPEED;
        scoreBitmapFont.draw(batch, Integer.toString(value), MAIN_POS_X, posY_mainMenu);
    }

    private boolean wasMadeZero;


    public void getReadyForAnimationSlide()
    {
        if (!wasMadeZero) {
            posY_mainMenu = MAIN_POS_Y;
            wasMadeZero = true;
        }
        posY_mainMenu += Button.ANIMATION_SPEED;
    }

    public void drawRecord_mainMenu(SpriteBatch batch)
    {
        wasMadeZero = false;
        recordBitmapFont.getData().setScale(AvoidBladesGame.SCORE_TEXT_SCALING);
        recordBitmapFont.draw(batch, Integer.toString(game.getRecord()), MAIN_POS_X, MAIN_POS_Y);
    }

    public void drawRecord_mainMenu_animation(SpriteBatch batch)
    {
        wasMadeZero = false;
        recordBitmapFont.getData().setScale(AvoidBladesGame.SCORE_TEXT_SCALING);
        if (game.getMainMenuScreen().startAnimationToGameMenu)
        {
            posY_mainMenu += Button.ANIMATION_SPEED;
        }
        else if (game.getMainMenuScreen().startAnimationFromGameMenu)
        {
            if (posY_mainMenu > MAIN_POS_Y)
                posY_mainMenu -= Button.ANIMATION_SPEED;
        }

        recordBitmapFont.draw(batch, Integer.toString(game.getRecord()), MAIN_POS_X, posY_mainMenu);
    }

    public void increaseValue()
    {
        value++;
    }

    public void makeScoreZero()
    {
        value = 0;
        isItNewCollision = -1;
    }

    public int getValue()
    {
        return value;
    }

    public void dispose()
    {
        scoreBitmapFont.dispose();
        recordBitmapFont.dispose();
    }

    private int isItNewCollision;
    public void makePenalty()
    {

        if (isItNewCollision + 1 >= value)
            return;

        value -= 5;
        if (value < 0)
            value = 0;

        isItNewCollision = value;
    }
}
