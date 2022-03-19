package game1;

import util.Vector2D;

import java.awt.*;

public class Bullet extends GameObject
{
    public static final int RADIUS = 3;
    public boolean playerBullet;


    public Bullet(Vector2D pos, Vector2D vel)
    {
        super(pos,vel,RADIUS);
    }

    @Override
    public void draw(Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.fillOval((int)position.x,(int)position.y,RADIUS*2,RADIUS*2);

    }
    @Override
    public void update()
    {
        position.addScaled(velocity,Constants.DT);
    }

    @Override
    public void collisionHandling(GameObject other)
    {
        if (!playerBullet && other instanceof PlayerShip)
        {
            this.hit();
            other.hit();
        }
        else if(playerBullet && other instanceof Asteroid || playerBullet && other instanceof Saucer)
        {
            this.hit();
            other.hit();
            if(other instanceof Saucer)
            {
                if(other.radius==10)
                {
                    Game.incScore(500);
                }
                else
                {
                    Game.incScore(300);
                }
            }

        }


    }

}
