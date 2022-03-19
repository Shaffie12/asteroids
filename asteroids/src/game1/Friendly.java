package game1;

import util.SoundManager;
import util.Vector2D;


import java.awt.*;

public class Friendly extends GameObject {
    public AnimSprite idleSpr;
    public static final int SPRITE_SIZE = 32;
    public Controller ctrl;
    private Vector2D move;
    private Vector2D idle;
    long timeSpawned;

    public Friendly(Vector2D pos, Vector2D vel)
    {
        super(pos, vel, 5);
        idleSpr = new AnimSprite(Constants.F_IDLE, pos, SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
        move = randMove();
        idle = randMove();
        timeSpawned= System.currentTimeMillis();

    }


    @Override
    public void update() {
        super.update();


        Action a = ctrl.action();
        if (a.move)
        {
            velocity=move;
            idle=randMove();
        }
        else
        {
            velocity= idle;
            move = randMove();
        }

        idleSpr.update();


    }

    @Override
    public void draw(Graphics2D g) {
        //g.fillOval((int) (position.x-radius), (int) (position.y-radius), (int)( (radius*2)), (int)( radius*2));

        idleSpr.draw(g);

    }

    public boolean canHit(GameObject o) {
        return !(o instanceof Friendly || o instanceof Particle);

    }

    public static Vector2D randMove()
    {
        double x, y;
        x = Math.random() * (200 - 150) + 150;
        y = Math.random() * (200 - 150) + 150;

        if (Math.random() > 0.5)
            x = x * -1;
        if(Math.random()>0.5)
            y=y*-1;

        return new Vector2D(x,y);


    }

    @Override
    public void collisionHandling(GameObject other)
    {
        if (other instanceof PlayerShip p)
        {
            this.hit();
            p.invulnerable=true;
            Game.lives++;
            SoundManager.play(SoundManager.extraShip);
            p.timeInvuln= System.currentTimeMillis();
        }
        if(other instanceof Saucer)
        {
            this.hit();
        }

    }

}
