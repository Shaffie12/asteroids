package game1;
import util.Vector2D;
import java.awt.*;

public abstract class Ship extends GameObject
{
    Color clr;
    public Controller ctrl;
    long lastFire;
    Bullet bullet;
    Vector2D direction;
    double MUZZLE_VELOCITY;
    public double STEER_RATE;
    public static double MAX_SPEED=200;


    public Ship(Vector2D pos, Vector2D vel, int rad)
    {
        super(pos,vel,rad);
        lastFire=0;
        bullet=null;

    }

    @Override
   public void update()
    {
        super.update();
    }

    public void makeBullet()
    {
        Vector2D bulletPos = new Vector2D(position);
        bulletPos.addScaled(direction,radius*2);

        Vector2D bulletVel = new Vector2D(velocity);
        bulletVel.addScaled(direction,MUZZLE_VELOCITY);

        bullet = new Bullet(bulletPos, bulletVel);

    }

   public boolean canHit(GameObject other)
   {
       return true;
   }


}
