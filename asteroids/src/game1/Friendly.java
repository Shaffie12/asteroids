package game1;

import util.Vector2D;

import javax.swing.*;
import java.awt.*;

public class Friendly extends JComponent
{
    public AnimSprite moveSpr, idleSpr;
    public boolean isMv = false;
    public final Vector2D position = new Vector2D();
    public final Vector2D velocity = new Vector2D(50,0);
    public static final int SPRITE_SIZE = 32;

    @Override
    public void paintComponent(Graphics g0)
    {
        Graphics2D g = (Graphics2D) g0;
        if(isMv)
            moveSpr.draw(g);
        else
            idleSpr.draw(g);
    }

    public void update()
    {
        position.add(velocity);
        if(isMv)
            moveSpr.update();

    }
}
