package game1;

import util.JEasyFrame;
import util.SoundManager;

import java.util.ArrayList;
import java.util.List;

public class Game
{
    public static final  int N_INITIAL_ASTEROIDS = 5;
    public List<GameObject> objects;
    public Keys ctrl;
    public PlayerShip playerShip;
    public static long score;
    public static int lives =3;
    public static int level=1;
    public static boolean gameOver=false;

    public Game()
    {
        score=0;
        objects = new ArrayList<>();
        ctrl = new Keys();
        playerShip = new PlayerShip(ctrl);
        Saucer bigSaucer = Saucer.makeRandomSaucer(true);
        Saucer smallSaucer = Saucer.makeRandomSaucer(false);

        for(int i=0;i<N_INITIAL_ASTEROIDS;i++)
        {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        objects.add(playerShip);
        objects.add(bigSaucer);
        objects.add(smallSaucer);
        //targeting AIs
        AimNShoot hard= new AimNShoot(this, true);
        hard.aimer = smallSaucer;
        smallSaucer.ctrl=hard;

    }

    //use this
    public Saucer makeAISaucer()
    {
        boolean big = Math.random() > 0.5;
        boolean hard = Math.random() >0.5;
        Saucer s = Saucer.makeRandomSaucer(big);
        if (!big)
        {
            s.ctrl=new AimNShoot(this,hard);
        }

        return s;

    }

    public void update()
    {
        List<GameObject> alive = new ArrayList<>();
        boolean noAsteroid=true;
        boolean noEnemy = true;

        for(int i=0;i<objects.size();i++) //because of this loop, object at the end of the array is never checked against anything.  Its collision handling has to be in the other object's. (UFO becomes (this), but other part of the loop never executes)
        {
            for(int j=i+1;j<objects.size();j++)
            {
                if(objects.get(i).getClass()!=objects.get(j).getClass())
                {
                    if(objects.get(i).overlap(objects.get(j)))
                        objects.get(i).collisionHandling(objects.get(j));
                }
            }

            objects.get(i).update();
            if(objects.get(i) instanceof Asteroid a)
            {
                noAsteroid=false;
                if(!(a.shard==null))
                {
                    alive.add(a.shard);
                    a.shard=null;
                }

            }
            else if(objects.get(i) instanceof PlayerShip)
            {
                if(!(playerShip.bullet == null))
                {
                    alive.add(playerShip.bullet);
                    playerShip.bullet = null;
                }

            }
            if(objects.get(i) instanceof Saucer)
            {
                noEnemy=false;
                if(((Saucer) objects.get(i)).bullet!=null)
                {
                    alive.add(((Saucer)objects.get(i)).bullet);

                    ((Saucer)objects.get(i)).bullet=null;
                }
            }
            if(objects.get(i).getClass()==Bullet.class)
            {
                if(objects.get(i).position.x <0 || objects.get(i).position.y<0 || objects.get(i).position.x >Constants.FRAME_WIDTH || objects.get(i).position.y >Constants.FRAME_HEIGHT)
                {
                    objects.get(i).dead=true;
                }
            }
            if(!objects.get(i).dead)
                alive.add(objects.get(i));

        }
        synchronized (Game.class)
        {
            objects.clear();
            objects.addAll(alive);
        }
        if(noAsteroid && noEnemy)
        {
            newLevel();
        }


    }

    public static void incScore(int amount)
    {
        score+=amount;
        if(score%10000==0)
        {
            SoundManager.play(SoundManager.extraShip);
            lives+=1;
        }
    }

    public long getScore(){return score;}

    public int getLives(){return lives;}

    public int getLevel(){return level;}

    public void newLevel()
    {
        level++;
        try
        {
            Thread.sleep(500);
        }
        catch (Exception e){};

        synchronized (Game.class)
        {
            objects.clear();
            int numAsteroids = N_INITIAL_ASTEROIDS+level;
            for(int i=0;i<numAsteroids;i++)
            {
                objects.add(Asteroid.makeRandomAsteroid());

            }
            objects.add(playerShip);
            objects.add(Saucer.makeRandomSaucer(false));
        }
    }

    public static void playerHit()
    {
        lives--;
        if (lives<=0)
        {
            SoundManager.play(SoundManager.bangLarge);
            gameOver=true;

        }

    }

    //main
    public static void main(String[] args) throws Exception
    {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "Asteroids").addKeyListener(game.ctrl);
        while (!gameOver)
        {
            game.update();
            view.repaint();
            Thread.sleep(Constants.DELAY);
        }
       view.repaint();



    }



}
