package ua.kyiv.mykhailoivanov;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by MykhailoIvanov on 5/9/2016.
 */
public class Button {
    private Texture textureUntouched;
    private Texture textureTouched;
    private float posX;
    private float posY;
    private float width;
    private float height;

    public boolean touched;

    public static final float ANIMATION_SPEED = Gdx.graphics.getHeight() / 35;//25; // @TODO: make calculation

    public Button(float x, float y, float w, float h, String textureUntouchedPath, String textureTouchedPath)
    {
        posX = x;
        posY = y;
        width = w;
        height = h;
        textureUntouched = new Texture(Gdx.files.internal(textureUntouchedPath));
        textureTouched = new Texture (Gdx.files.internal(textureTouchedPath));
        touched = false;
    }


    public boolean wasClicked(float x, float y)
    {
        if ((x >= posX) && (x <= posX + width) && (y >= posY) && (y <= posY + height))
            return true;
        else
            return false;
    }

    public void setPos(float x, float y)
    {
        posX = x;
        posY = y;
    }

    public float getPosX()
    {
        return posX;
    }

    public float getWidth(){return width;}

    public float getPosY()
    {
        return posY;
    }

    public void draw(SpriteBatch batch)
    {
        if (!touched)
            batch.draw(textureUntouched, posX, posY, width, height);
        else
            batch.draw(textureTouched, posX, posY, width, height);
    }


    public void draw_animationOut(SpriteBatch batch)
    {
        this.touched = false;
        posY -= ANIMATION_SPEED;
        batch.draw(textureUntouched, posX, posY, width, height);
    }

    public void draw_animationIn(SpriteBatch batch)
    {
        this.touched = false;
        posY += ANIMATION_SPEED;
        batch.draw(textureUntouched, posX, posY, width, height);
    }

    public void dispose()
    {
        textureUntouched.dispose();
    }

}
