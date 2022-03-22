package game1;

import util.JEasyFrame;
import util.SoundManager;
import util.Vector2D;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game
{
    public static final  int N_INITIAL_ASTEROIDS = 5;
    public List<GameObject> objects;
    public List<Particle> particles;
    public List<Star> stars;
    public PlayerShip playerShip;
    public static long score;
    public static int lives =3;
    public static int level=1;
    public static int max_enemy=2;
    public static boolean gameOver=false;
    long nextSpawn;
    public static float worldSpeed=0.1f;


    public Game()
    {
        nextSpawn=(long)(System.currentTimeMillis()/1000.0+(Math.random()*(25-10)+10)); //first enemy can only spawn in 5-10 seconds after start
        score=0;
        stars= new ArrayList<>();
        objects = new ArrayList<>();
        particles = new ArrayList<>();
        playerShip = new PlayerShip();
        for(int i=0;i<1000;i++)
        {
            stars.add(new Star());
        }

        for(int i=0;i<N_INITIAL_ASTEROIDS;i++)
        {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        objects.add(playerShip);


    }

    //randomly generate saucers, at higher scores only hard AI will spawn
    public Saucer makeAISaucer()
    {
        double big,wander,hard;
        big=1-(10.0*(level*1.5))/100;
        wander = (10.0*(level*1.5))/100;
        hard = score/Math.pow(100,2);

        Saucer s;

        if(level <3)
        {
            s = Saucer.makeRandomSaucer(true);
        }
        else
        {
           s = Saucer.makeRandomSaucer(Math.random()<big);
           if(Math.random()<wander)
           {
               if(Math.random()<hard)
               {
                   s.ctrl=new AccurateWanderShoot(this,s);
               }
               else
                   s.ctrl = new WanderNShoot(this,s);
           }
           else
           {
               if(Math.random()<hard)
               {
                   s.ctrl=new AccurateShoot(this,s);
               }
               else
                   s.ctrl = new AimNShoot(this,s);

           }


        }




        return s;

    }

    public void makeFriendly(GameObject source)
    {
        Friendly f = new Friendly(new Vector2D(source.position),new Vector2D(0,0));
        f.ctrl= new Wander(this,f);
        objects.add(f);

    }

    public void update()
    {

        int numEnemy=0; //dont spawn more than 2 on screen at once
        List<GameObject> alive = new ArrayList<>();
        List<Particle> aliveParticles = new ArrayList<>();
        boolean noAsteroid=true;
        boolean noEnemy = true;
        for(Star st: stars)
            st.update();

        for(int i=0;i<objects.size();i++)
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
                if(a.dead)
                    explosion(a);

            }
            else if(objects.get(i) instanceof PlayerShip p)
            {

                if(!(p.bullet == null))
                {
                    alive.add(p.bullet);
                    p.bullet = null;
                }

            }
            if(objects.get(i) instanceof Saucer s)
            {
                noEnemy=false;
                numEnemy++;
                if(s.bullet!=null)
                {
                    alive.add(((Saucer)objects.get(i)).bullet);

                    ((Saucer)objects.get(i)).bullet=null;
                }
                if(s.dead)
                {
                    explosion(s);
                    s.sound.stop();
                    if (Math.random()<0.3)
                        makeFriendly(s);


                }
            }
            if(objects.get(i) instanceof Bullet b)
            {
                if(b.position.x <0 ||b.position.y<0 || b.position.x >Constants.FRAME_WIDTH || b.position.y >Constants.FRAME_HEIGHT)
                {
                    b.dead=true;
                }
            }
            if(objects.get(i) instanceof Friendly f)
            {
                if(Math.abs(f.timeSpawned-System.currentTimeMillis())>10000)
                {
                    f.dead=true;
                }
            }
            if(!objects.get(i).dead)
                alive.add(objects.get(i));

            aliveParticles= updateParticles();

        }
        synchronized (Game.class)
        {
            objects.clear();
            objects.addAll(alive);
            particles.clear();
            particles.addAll(aliveParticles);


            long time = System.currentTimeMillis()/1000;

            if(time>nextSpawn && numEnemy<max_enemy)
            {
                nextSpawn=(long)(time+Math.random()*(25-10)+10);
                objects.add(makeAISaucer());
            }

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

    public void explosion(GameObject ob)
    {
        Color c;
        if(ob instanceof Saucer)
            c=Color.green;
        else
            c=Color.YELLOW;

        for(int i=0;i<10;i++)
            particles.add(new Particle(ob.position,c));
    }

    public List<Particle> updateParticles()
    {
        List<Particle> alive=new ArrayList<>();
        for (Particle p:particles)
        {
            p.update();

            if(Math.abs(p.timeSpawned-System.currentTimeMillis())>p.ttl)
                p.dead=true;

            if(!p.dead)
                alive.add(p);
        }
        return  alive;
    }

    public int getLives(){return lives;}

    public int getLevel(){return level;}

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
