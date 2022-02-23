package game1;

import util.Vector2D;
import java.awt.*;
import java.awt.geom.AffineTransform;


import static game1.Constants.*;

public class Ship extends GameObject
{

    public int XP[] = { -6, 0, 6, 0 }, YP[] = { 8, 4, 8, -8 };
    public static final int RADIUS = 8; //hitbox
    public static final double STEER_RATE = 2*Math.PI; //rotation velocity in rad/sec
    public static final double MAG_ACC = 200; //acceleration when thrust is applied
    public static final double DRAG = 0.01; //constant speed loss factor
    public static final Color COLOR = Color.cyan;
    public static final double MUZZLE_VELOCITY = 400;
    private static double lastFire=0.0;
    public Vector2D direction; //where the ship is pointing
    private final Controller ctrl;
    public Bullet bullet = null;

    public Ship(Controller c)
    {
        super(new Vector2D((double) FRAME_WIDTH/2,(double)FRAME_HEIGHT/2),new Vector2D(0,0),RADIUS);
        this.ctrl=c;
        this.direction=new Vector2D(0,-1);

    }

    @Override
    public void update()
    {
        super.update();
        Action a = ctrl.action();
        direction.rotate(a.turn*STEER_RATE*DT);
        velocity.addScaled(direction,MAG_ACC*DT*a.thrust);
        velocity.addScaled(velocity,-DRAG);
        double time = System.currentTimeMillis();

        if(a.shoot && time > lastFire)
        {
            makeBullet();
            lastFire=System.currentTimeMillis()+100; //every half a second player can fire again
            a.shoot=false;
        }

    }

    @Override
    public void draw(Graphics2D g)
    {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        g.rotate(direction.angle()+Math.PI/2);
        g.scale(1.2,1.2);
        g.setColor(COLOR);
        g.fillPolygon(XP,YP,XP.length);
        g.setTransform(at);
        //because the affineTransform of g has been modified it will apply these transformations to any object it draws now?
        //so the bullets wont be drawn properly since they are adjusted by all the transformations we made before so reset it to default

    }

    private void makeBullet()
    {
        Vector2D bulletPos = new Vector2D(position);
        bulletPos.addScaled(direction,radius*2);

        Vector2D bulletVel = new Vector2D(velocity);
        bulletVel.addScaled(direction,MUZZLE_VELOCITY);

        bullet = new Bullet(bulletPos, bulletVel);
        bullet.shipParent=true;

    }

    //delete later
    @Override
    public void hit()
    {
        Game.playerHit();
        if(Game.lives<=0)
            dead=true;
    }




}
