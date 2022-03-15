package game1;

import util.Vector2D;

import java.awt.*;

public class Item extends GameObject
{
    public Sprite sprite;

    public Item(Vector2D pos, int rad)
    {
        super(pos,new Vector2D(0,0),rad);
    }

    @Override
    public void draw(Graphics2D g)
    {
        sprite.draw(g);

    }
}
