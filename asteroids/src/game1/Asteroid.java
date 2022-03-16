package game1;
import util.SoundManager;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game1.Constants.*;

public class Asteroid extends GameObject
{
    public static final double MAX_SPEED = 100;
    public Asteroid shard=null;
    public Sprite s;


    public Asteroid(double x, double y, double vx, double vy, double radius)
    {

        super(new Vector2D(x,y),new Vector2D(vx,vy),radius);
        Image rsprite = (Math.random() >0.5? ASTEROID1: ASTEROID2);
        this.s = new Sprite(rsprite,position,new Vector2D(0,1),
                radius,radius);


    }

//make so only large slow ones spawn at the start of every level
    public static Asteroid makeRandomAsteroid()
    {

        double rvx= (Math.random()>0.5? Math.random()*1:Math.random()*-1);
        double rvy=(Math.random()>0.5?Math.random()*1:Math.random()*-1);
        double rrad = Math.random()*(20-5)+5;

        double rand = Math.random();
        if(rand > 0.5)
        {
            double rx = (Math.random() < 0.5? Math.random()*Constants.leftx:Math.random()*(FRAME_WIDTH-Constants.rightx)+Constants.rightx);
            Asteroid a = new Asteroid(
                    rx,Math.random()*FRAME_HEIGHT,
                    rvx*MAX_SPEED,rvy*MAX_SPEED,rrad);
            return a;

        }
        else
        {
            double ry = (Math.random() < 0.5? Math.random()*Constants.topy:Math.random()*(FRAME_HEIGHT-Constants.boty)+Constants.boty);
            Asteroid a = new Asteroid(
                    Math.random()*FRAME_WIDTH,ry,
                    rvx*MAX_SPEED,rvy*MAX_SPEED,rrad);
            return a;
        }

    }

    @Override
    public void draw(Graphics2D g)
    {
        s.draw(g);
        //g.fillOval((int) (position.x - radius), (int) (position.y- radius), (int)(2* radius), (int)(2 * radius));
    }

    @Override
    public void collisionHandling(GameObject other)
    {
        if(other instanceof Bullet && (((Bullet)other).playerBullet))
        {
            if(radius>=5 && radius<=7)
                Game.incScore(200);
            else
                Game.incScore(100);
            this.hit();
            other.hit();

        }
        else if (other instanceof  Ship)
        {
            this.hit();
            other.hit();
        }
    }


    @Override
    public void hit()
    {
        radius=radius/2;
        s.width = s.width/2;
        s.height = s.height/2;
        velocity.multi(2);
        if(radius<5)
        {
            SoundManager.play(SoundManager.bangSmall);
            dead=true;
        }
        else
        {
            SoundManager.play(SoundManager.bangLarge);
            shard = new Asteroid(position.x, position.y, -velocity.x, -velocity.y, radius);
        }

    }






}
