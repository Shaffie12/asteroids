package game1;

import util.Vector2D;

import java.awt.*;

import static game1.Constants.*;

public class Saucer extends Ship
{

    public Sprite s;
    long lastTurn;


    public Saucer(Vector2D pos, Vector2D vel, int rad, Controller c)
    {
        super(pos,vel,rad,c);

        clr = Color.red;
        MUZZLE_VELOCITY=400;
        direction = new Vector2D(0,1);
        s =new Sprite(Constants.UFO,position,direction,radius,radius);
        STEER_RATE=2*Math.PI;



    }

    public static Saucer makeRandomSaucer(boolean big)
    {
        int rad;
        Controller c = new RotateNShoot();

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


        double rand = Math.random();
        if(rand > 0.5)
        {
            double rx = (Math.random() < 0.5? Math.random()*Constants.leftx:Math.random()*(FRAME_WIDTH-Constants.rightx)+Constants.rightx);
            return new Saucer(new Vector2D(rx, Math.random()*FRAME_HEIGHT),new Vector2D(MAX_SPEED,0),rad,c);

        }
        else
        {
            double ry = (Math.random() < 0.5? Math.random()*Constants.topy:Math.random()*(FRAME_HEIGHT-Constants.boty)+Constants.boty);
            return new Saucer(new Vector2D(Math.random()*FRAME_WIDTH,ry),new Vector2D(MAX_SPEED,0),rad,c);

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
            lastFire=System.currentTimeMillis()+500;
            a.shoot=false;
        }
        direction.rotate(a.turn*STEER_RATE);


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
        if(ctrl instanceof AimNShoot) {
            Vector2D bulletPos = new Vector2D(position);
            bulletPos.addScaled(direction, radius * 2);


            Vector2D bulletVel = new Vector2D(((AimNShoot) ctrl).getTargetPos());
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
        return other instanceof PlayerShip;
    }
}
