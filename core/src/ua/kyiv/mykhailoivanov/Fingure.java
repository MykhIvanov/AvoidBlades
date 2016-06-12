package ua.kyiv.mykhailoivanov;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ua.kyiv.mykhailoivanov.screens.GameScreen;

/**
 * Created by MykhailoIvanov on 3/28/2016.
 */
public class Fingure {
    private Texture texture;

    private float width;
    private float height;

    private float posX;
    private float posY;

    private Score score;



    public Fingure(Score scoreIn)
    {
        score = scoreIn;

        texture = new Texture(Gdx.files.internal("target.png"));

        // FINGER SIZE
        width = 50;
        height = 50;

    }

    public void setNoPos()
    {
        posX = -100000;
        posY = -100000;
    }
    private void refreshPos()
    {
        posX = Gdx.input.getX() - width/2;
        posY = Gdx.graphics.getHeight() - Gdx.input.getY() - width/2;
    }

    public void draw(SpriteBatch batch)
    {
        refreshPos();
//        batch.draw(texture, posX, posY, width, height); // @NOTE: only for testing
    }
    private float hWas;
    public void checkCollision(float x1, float y1, float x2,float y2, float x3, float y3, float w, float h)
    {
        hWas = h;
        h = (float) (h - h * 0.35); // the knife handle

        if (checker(x1, y1 + h, w, (float) (hWas * 0.35)) )
        {
            Gdx.input.vibrate(35);
            score.makePenalty();
        }
        if (checker(x2, y2 + h, w, (float) (hWas * 0.35)) )
        {
            Gdx.input.vibrate(35);
            score.makePenalty();
        }
        if (checker(x3, y3 + h, w, (float) (hWas * 0.35)) )
        {
            Gdx.input.vibrate(35);
            score.makePenalty();
        }



        if (checker(x1, y1, w, h))
        {
            GameScreen.bloodX = posX;
            GameScreen.bloodY = posY;
            GameScreen.loose = true;
        }
        if (checker(x2, y2, w, h))
        {
            GameScreen.bloodX = posX;
            GameScreen.bloodY = posY;
            GameScreen.loose = true;
        }
        if (checker(x3, y3, w, h))
        {
            GameScreen.bloodX = posX;
            GameScreen.bloodY = posY;
            GameScreen.loose = true;
        }
    }
    private boolean checker(float x, float y, float w, float h)
    {
        if ( (x > posX) && (x < posX + width) && (posY > y) && (posY < y + h) )
            return true;

        if ( (x < posX) && (x + w > posX) && (y < posY) && (y + h > posY))
            return true;

        if ( (x < posX) && (x + w > posX) && (posY < y) && (posY + height > y))
            return true;

        if ( (posX < x) && (posX + width > x) && (y > posY) && (posY + height > y))
            return true;


        return false;
    }

    public void dispose()
    {
        texture.dispose();
    }

}
