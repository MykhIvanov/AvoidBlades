package ua.kyiv.mykhailoivanov.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import ua.kyiv.mykhailoivanov.Button;

/**
 * Created by MykhailoIvanov on 3/28/2016.
 */
public class Blade{
    private Random random;
    private Texture textureblade;

    private float width;
    private float height;

    private float posX;
    private float posY;
    private float speed;


    public Blade(int bladeIndex)
    {
        random = new Random();
        textureblade = new Texture(Gdx.files.internal("blade3.png"));
        width = Gdx.graphics.getWidth()/8;
        height = Gdx.graphics.getHeight()/2;
        refreshStartParams(bladeIndex);
    }

    public void refreshStartParams(int bladeIndex)
    {
        speed = Gdx.graphics.getHeight()/55; // was 55
        posY = Gdx.graphics.getHeight()+ width*6*bladeIndex;
        posX = width*2*bladeIndex;
    }

    public void goAwayFromScreen()
    {
        posY -= Button.ANIMATION_SPEED * 3;
    }

    public void calculatePosition(float x1, float x2)
    {
        if (posY<-height)
        {
            posY = Gdx.graphics.getHeight();

            posX = random.nextInt(Math.round(Gdx.graphics.getWidth() - width));
            speed+=0.5;
        }
        else
            posY-=speed;
    }
    public void draw(SpriteBatch batch)
    {
        batch.draw(textureblade, posX, posY, width, height);
    }

    public float getY()
    {
        return posY;
    }
    public float getPosX()
    {
        return posX;
    }
    public float getWidth()
    {
        return width;
    }
    public float getHeight()
    {
        return height;
    }

    public void dispose()
    {
        textureblade.dispose();
    }

}
