package game1;

import util.JEasyFrame;
import util.SoundManager;


import java.util.ArrayList;
import java.util.List;

public class Game
{
    public static final  int N_INITIAL_ASTEROIDS = 5;
    public List<GameObject> objects;
    public PlayerShip playerShip;
    public static long score;
    public static int lives =3;
    public static int level=1;
    public static boolean gameOver=false;
    long nextSpawn;


    public Game()
    {
        nextSpawn=(long)(System.currentTimeMillis()/1000.0+(Math.random()*(25-10)+10)); //first enemy can only spawn in 5-10 seconds after start
        score=0;
        objects = new ArrayList<>();
        playerShip = new PlayerShip();

        for(int i=0;i<N_INITIAL_ASTEROIDS;i++)
        {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        objects.add(playerShip);

        Saucer s = Saucer.makeRandomSaucer(false);
        s.ctrl = new WanderNShoot(this,s);
        objects.add(s);

    }

    //randomly generate saucers, at higher scores only hard AI will spawn
    public Saucer makeAISaucer()
    {
        boolean big;
        Saucer s;
        double probSmall= (10.0*(level*1.5))/100; //flat rate  *(level*scaler)
        double probHard = score/Math.pow(100,2);


        if(level <3)
        {
            big = true;
            s = Saucer.makeRandomSaucer(big);

        }
        else
        {
            big = Math.random()>probSmall;
            boolean hard = Math.random()<probHard;
            s=Saucer.makeRandomSaucer(big);
            if (hard)
                s.ctrl=new AccurateShoot(this,s);
        }




        return s;

    }

    public void update()
    {

        int numEnemy=0;
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
                numEnemy++;
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
            long time = System.currentTimeMillis()/1000;

            /*
            if(time>nextSpawn && numEnemy<2)
            {
                nextSpawn=(long)(time+Math.random()*(25-10)+10);
                objects.add(makeAISaucer());


            }

             */


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
        catch (Exception e){}

        synchronized (Game.class)
        {
            objects.clear();
            int numAsteroids = N_INITIAL_ASTEROIDS+level;
            for(int i=0;i<numAsteroids;i++)
            {
                objects.add(Asteroid.makeRandomAsteroid());
            }
            objects.add(playerShip);

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
        new JEasyFrame(view, "Asteroids").addKeyListener((Keys)game.playerShip.ctrl);
        while (!gameOver)
        {
            game.update();
            view.repaint();
            Thread.sleep(Constants.DELAY);
        }
       view.repaint();



    }



}
