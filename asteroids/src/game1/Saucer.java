package game1;

import util.Vector2D;

import java.awt.*;

import static game1.Constants.*;

public class Saucer extends Ship
{

    public Sprite s;

    //atm you specify a controller when you create one, but all of the targeting AIs require being set after saucer creation.

    public Saucer(Vector2D pos, Vector2D vel, int rad)
    {
        super(pos,vel,rad);

        clr = Color.red;
        MUZZLE_VELOCITY=200;
        direction = new Vector2D(0,1);
        s =new Sprite(Constants.UFO,position,direction,radius,radius);
        STEER_RATE=2*Math.PI;
        ctrl = new RotateNShoot(); //default AT.  make it just wander



    }
    //randomly generate powerup on death of saucer (give shield?)

    public static Saucer makeRandomSaucer(boolean big)
    {
        int rad;
        double rx;
        double ry=Math.random()*(Constants.FRAME_HEIGHT-200)+200;

        if(big)
        {
            rad = 20;
            MAX_SPEED=170;
        }
        else
        {
            rad = 10;
            MAX_SPEED=300;

        }

        //randomly position saucer on left or right
        if(Math.random()>0.5)
        {
            rx = 0;
        }
        else
        {
            rx= FRAME_WIDTH;
        }
        return new Saucer(new Vector2D(rx,ry),new Vector2D(MAX_SPEED,0),rad);


    }


    @Override
    public void update()
    {
        super.update();
        Action a = ctrl.action();
        double time = System.currentTimeMillis();
        if(ctrl instanceof AccurateShoot)
            direction.rotate(a.turn*STEER_RATE);
        else
            direction.rotate(a.turn*STEER_RATE*Constants.DT);

        if(ctrl instanceof AimNShoot)
        {
            if(a.shoot && time > lastFire)
            {
                makeBullet();
                lastFire=System.currentTimeMillis()+1500;
                a.shoot=false;
            }
        }
        else
        {
            if(a.shoot && time > lastFire)
            {
                makeBullet();
                lastFire=System.currentTimeMillis()+700;
                a.shoot=false;
            }
        }
        if(a.avoid)
        {
            moveUp();
        }
        else
        {
            velocity.y=0;
        }




    }


    @Override
    public void draw(Graphics2D g)
    {
        s.draw(g);
        /*
        g.fillOval((int) (position.x-radius), (int) (position.y-radius), (int)( (radius*2)), (int)( radius*2));

        Vector2D p = new Vector2D();
        p.x=position.x;
        p.y=position.y;
        p.addScaled(direction,radius+5);

        g.drawLine((int)(position.x),(int)(position.y),(int)p.x,(int)p.y);

         */




    }

    @Override
    public void makeBullet()
    {
        if(ctrl instanceof AccurateShoot)
        {
            Vector2D bulletPos = new Vector2D(position);
            bulletPos.addScaled(direction, radius * 2);

            Vector2D bulletVel = new Vector2D(((AccurateShoot) ctrl).getTargetPos());
            bulletVel.addScaled(direction, MUZZLE_VELOCITY);

            bullet = new Bullet(bulletPos, bulletVel);

        }
        else
            super.makeBullet();

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
            if(radius==20)
                Game.incScore(300);
            else
                Game.incScore(500);
            this.hit();
            other.hit();

        }

    }

    @Override
    public boolean canHit(GameObject other)
    {
        return other instanceof PlayerShip || other instanceof Asteroid;
    }

    void moveDown()
    {
        velocity.y=200;
    }

    void moveUp()
    {
        velocity.y =-200;
    }

}
