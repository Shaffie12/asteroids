package game1;

import util.SoundManager;
import util.Vector2D;
import java.awt.*;

public abstract class GameObject
{
    public Vector2D position;
    public Vector2D velocity;
    public boolean dead;
    public double radius;

    public GameObject(Vector2D pos, Vector2D vel, double rad)
    {
        this.position=pos;
        this.velocity=vel;
        this.radius=rad;
    }

    public void hit()
    {
        dead=true;
    }

    public boolean overlap(GameObject other)
    {
        return (this.position.dist(other.position)<(this.radius+ other.radius) || other.position.dist(this.position)<(other.radius+this.radius));
    }

    public void collisionHandling(GameObject other)
    {
        this.hit();
        other.hit();
    }


    public void update()
    {
        position.addScaled(velocity,Constants.DT);
        position.wrap(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT);
    }

    public abstract void draw(Graphics2D g);


}
