package game2;

import javax.swing.*;
import java.awt.*;

public class BasicView  extends JComponent
{
    public static final Color BG_COLOR = Color.black;
    private BasicGame game;

    public BasicView(BasicGame game)
    {
        this.game=game;
    }

    @Override
    public void paintComponent(Graphics g0)
    {
        Graphics2D g =(Graphics2D) g0;
        g.setColor(BG_COLOR);
        g.fillRect(0,0,getWidth(),getHeight());
        for (BasicAsteroid ba: game.asteroids)
        {
            ba.draw(g);
        }
        game.ship.draw(g);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return Constants.FRAME_SIZE;
    }

}
