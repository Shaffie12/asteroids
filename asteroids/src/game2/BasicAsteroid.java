package game2;

import util.Vector2D;

import java.awt.*;

import static game1.Constants.*;

public class BasicAsteroid
{
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;

    private Vector2D pos;
    private Vector2D velocity;

    public BasicAsteroid(double x, double y, double vx, double vy)
    {
        this.pos=new Vector2D(x,y);
        this.velocity=new Vector2D(vx,vy);
    }


    public static BasicAsteroid makeRandomAsteroid()
    {
        double rvx= (Math.random()>0.5? Math.random()*1:Math.random()*-1);
        double rvy=(Math.random()>0.5?Math.random()*1:Math.random()*-1);

        BasicAsteroid a = new BasicAsteroid(
                Math.random()*FRAME_WIDTH,Math.random()*FRAME_HEIGHT,
                rvx*MAX_SPEED,rvy*MAX_SPEED);


        return a;
    }


    public void update()
    {
        pos.addScaled(velocity,DT);
        pos.wrap(FRAME_WIDTH,FRAME_HEIGHT);
    }

    public void draw(Graphics2D g)
    {
        g.setColor(Color.red);
        g.fillOval((int) pos.x - RADIUS, (int) pos.y- RADIUS, 2* RADIUS, 2 * RADIUS);
    }




}
