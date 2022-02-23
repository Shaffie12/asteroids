package game2;

import util.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import static game1.Constants.*;

public class BasicShip
{
    public static final int RADIUS = 8;

    //rotation velocity in rad/sec
    public static final double STEER_RATE = 2*Math.PI;

    //acceleration when thrust applied
    public static final double MAG_ACC = 200;

    //constant speed loss factor
    public static final double DRAG = 0.01;

    public static final Color COLOR = Color.cyan;

    public Vector2D position; //on frame
    public Vector2D velocity; //per second
    public Vector2D direction; //where the ship is pointing
    private BasicController ctrl;

    public BasicShip(BasicController c)
    {
        this.ctrl=c;
        position=new Vector2D((double)FRAME_WIDTH/2,(double)FRAME_HEIGHT/2);
        velocity=new Vector2D(0,0);
        direction=new Vector2D(0,-1);
    }

    public void update()
    {
        Action a = ctrl.action();
        direction.rotate(a.turn*STEER_RATE*DT);
        velocity.addScaled(direction,MAG_ACC*DT*a.thrust);
        velocity.addScaled(velocity,-DRAG);
        position.addScaled(velocity,DT);
        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
    }

    public void draw(Graphics2D g)
    {
        g.setColor(COLOR);
        Ellipse2D circle = new Ellipse2D.Double(position.x-(RADIUS), position.y-(RADIUS),
                RADIUS*2,RADIUS*2);
        g.fill(circle);
        Vector2D shipFront = new Vector2D(position.x,position.y);
        shipFront.addScaled(direction,2*RADIUS);
        g.drawLine((int)position.x,(int)position.y,(int)shipFront.x,(int)shipFront.y);

    }


}
