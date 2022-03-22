package game1;

import util.Vector2D;

import java.awt.*;
import java.util.Random;

public class Star
{
    public Vector2D position;
    double z;
    public int radius;
    public Color color;

    public Star()
    {
        z=Math.random()*(100-1)+1;
        radius=z>50? 1:2;
        color=z>50? new Color(255,255,255):new Color(204,204,204);

        Random r = new Random();
        double rx,ry;
        rx = Constants.rand.nextDouble()*Constants.FRAME_WIDTH;
        ry= Constants.rand.nextDouble()*Constants.FRAME_HEIGHT;
        this.position=new Vector2D(rx,ry);
    }

    public void draw(Graphics2D g)
    {
        g.setColor(color);
        g.drawOval((int)position.x,(int)position.y,radius,radius);
    }

    public void update()
    {
        this.position.y+=Game.worldSpeed*Constants.DT*this.z;
        if(position.y>Constants.FRAME_HEIGHT)
            repos();

    }

    private void repos()
    {
        ;
        position.x=Constants.rand.nextDouble()*Constants.FRAME_WIDTH;
        position.y=0;
    }
}
