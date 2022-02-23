package game1;

import javax.swing.*;
import javax.xml.crypto.dsig.Transform;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class View extends JComponent
{
    public static final Color BG_COLOR = Color.black;
    private Game game;

    public View(Game game)
    {
        this.game=game;
    }

    @Override
    public void paintComponent(Graphics g0)
    {
        Graphics2D g =(Graphics2D) g0;
        g.setColor(BG_COLOR);
        g.fillRect(0,0,getWidth(),getHeight());
        if(!Game.gameOver)
        {
            g.setColor(Color.WHITE);
            g.drawString("SCORE "+Long.toString(game.getScore()),7,15);
            g.drawString("LIVES: "+Integer.toString(game.getLives()),7,30);
            g.drawString("LEVEL: "+Integer.toString(game.getLevel()),7,getHeight()-15);
            synchronized (Game.class)
            {
                for (GameObject go:game.objects)
                {
                    go.draw(g);
                }
            }

        }
        else
        {
            String text = "GAME OVER";
            g.setColor(Color.WHITE);
            Font goFont = new Font(Font.MONOSPACED, Font.PLAIN, 40);
            FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

            g.setFont(goFont);
            g.drawString(text, getWidth() / 2 - ((int) goFont.getStringBounds(text, frc).getWidth() / 2), getHeight() / 2);
        }

        //flicker maybe?



    }

    @Override
    public Dimension getPreferredSize()
    {
        return Constants.FRAME_SIZE;
    }

}
