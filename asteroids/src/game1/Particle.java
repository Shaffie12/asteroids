package game1;

import util.Vector2D;

import java.awt.*;
import java.util.Random;

public class Particle extends GameObject
{
    public static final int P_SPEED=30;
    public static final int TTL = 1; //second
    public static final double DRAG =0.2;
    //public static final int SIZE = 3;
    Color COlOR = Color.YELLOW;
    public long timeSpawned;
    double ttl;



    public Particle(Vector2D pos, Color col)
    {
        super(new Vector2D(pos),randomVelocity(),3);
        this.COlOR=col;
        this.ttl=(Math.random()*(3-2)+2)*500;
        timeSpawned=System.currentTimeMillis();

    }


    public static Vector2D randomVelocity()
    {
        return Vector2D.polar(Math.random()*2*Math.PI,
                Math.abs(Math.random()*P_SPEED));
    }



    @Override
    public void hit() {}

    @Override
    public void draw(Graphics2D g)
    {

        g.setColor(COlOR);
        g.fillOval((int)position.x,(int)position.y,(int)radius,(int)radius);

    }

    public boolean canHit(GameObject other){return false;}


}
