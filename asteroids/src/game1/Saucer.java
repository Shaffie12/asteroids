package game1;

import game1.Controller;
import game1.Ship;
import util.ImageManager;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game1.Constants.*;

public class Saucer extends Ship
{
    private static final int HITBOX =15;
    private static final double max_speed = 200;
    public Sprite s;


    public Saucer(Vector2D pos, Vector2D vel, Controller c)
    {
        super(pos,vel,HITBOX,c);

        clr = Color.red;
        MUZZLE_VELOCITY=400;
        direction = new Vector2D(0,1);
        s =new Sprite(Constants.UFO,position,direction,radius,radius);
        STEER_RATE=3*Math.PI;

    }

    public static Saucer makeRandomSaucer()
    {
        double rvx= (Math.random()>0.5? Math.random()*1:Math.random()*-1);
        double rvy=(Math.random()>0.5?Math.random()*1:Math.random()*-1);

        double rand = Math.random();
        if(rand > 0.5)
        {
            double rx = (Math.random() < 0.5? Math.random()*Constants.leftx:Math.random()*(FRAME_WIDTH-Constants.rightx)+Constants.rightx);
            Saucer s = new Saucer(new Vector2D(rx, Math.random()*FRAME_HEIGHT),new Vector2D(rvx*max_speed,rvy*max_speed),new RandomAction());
            return s;

        }
        else
        {
            double ry = (Math.random() < 0.5? Math.random()*Constants.topy:Math.random()*(FRAME_HEIGHT-Constants.boty)+Constants.boty);
            Saucer s = new Saucer(new Vector2D(Math.random()*FRAME_WIDTH,ry),new Vector2D(rvx*max_speed,rvy*max_speed),new RandomAction());
            return s;
        }


    }


    @Override
    public void update()
    {
        super.update();
        Action a = ctrl.action();
        double time = System.currentTimeMillis();

        direction.rotate(a.turn*STEER_RATE*DT);
        if(a.shoot && time > lastFire)
        {
            makeBullet();
            lastFire=System.currentTimeMillis()+500; // make him shoot in random directions
            a.shoot=false;
        }

    }


    @Override
    public void draw(Graphics2D g)
    {
        double imW = s.width;
        double imH = s.height;
        AffineTransform t = new AffineTransform();
        t.scale(s.width/10,s.height/10);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(s.image, t, null);
        g.setTransform(t0);

    }

    @Override
    public void makeBullet()
    {
        super.makeBullet();
        bullet.playerBullet=false;
    }

    @Override
    public void collisionHandling(GameObject other)
    {
        if (other instanceof PlayerShip)
        {
            this.hit();
            other.hit();
        }
        if(other instanceof Bullet && (((Bullet)other).playerBullet))
        {
            this.hit();
            other.hit();
            Game.incScore(300);
        }

    }

}
