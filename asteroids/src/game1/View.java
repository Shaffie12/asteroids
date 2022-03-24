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
    //Image img = Constants.MILKYWAY;
    //AffineTransform bg;


    public View(Game game)
    {
        this.game=game;
        /*
        double imgWid = img.getWidth(null);
        double imgHeight = img.getHeight(null);
        double stretchx = (imgWid >Constants.FRAME_WIDTH? 1: Constants.FRAME_WIDTH/imgWid);
        double stretchy =(imgHeight >Constants.FRAME_HEIGHT? 1: Constants.FRAME_HEIGHT/imgHeight);

        bg = new AffineTransform();
        bg.scale(stretchx,stretchx);

         */
    }

    @Override
    public void paintComponent(Graphics g0)
    {
        Graphics2D g =(Graphics2D) g0;

        g.setColor(BG_COLOR);
        //g.drawImage(img,bg,null);
        g.fillRect(0,0,getWidth(),getHeight());
        if(!Game.gameOver)
        {
            g.setColor(Color.WHITE);
            g.drawString("SCORE "+Long.toString(game.getScore()),7,15);
            g.drawString("LIVES: "+Integer.toString(game.getLives()),7,30);
            g.drawString("LEVEL: "+Integer.toString(game.getLevel()),7,getHeight()-15);
            g.drawString("HIGH SCORE: "+Long.toString(Game.high_score),7,getHeight()-30);
            synchronized (Game.class)
            {
                for(Star st:game.stars)
                        st.draw(g);

                for(Prop p: game.props)
                    p.draw(g);

                for (GameObject go:game.objects)
                    go.draw(g);

                for(Particle p: game.particles)
                    p.draw(g);
            }

        }
        else
        {
            for(GameObject o:game.objects)
            {
                if(o instanceof Saucer s)
                    s.sound.close();
            }
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
