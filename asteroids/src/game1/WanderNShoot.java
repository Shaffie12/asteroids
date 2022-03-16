package game1;


public class WanderNShoot extends Controllers implements Controller
{
    public Action action;
    private final Game game;
    public Ship aimer;
    boolean ac_up;

    public WanderNShoot(Game game, Ship aimer)
    {
        this.game=game;
        this.aimer=aimer;
        ac_up = Math.random()>0.5; //randomly generate a movement

    }

    public Action action()
    {

        action=new Action();
        action.turn=Controllers.aimInaccurate(aimer,game.playerShip);


        GameObject closest = findClosestHittable(aimer,game.objects);

        if(closest instanceof Asteroid || closest instanceof PlayerShip && aimer.position.dist(closest.position)<80) //if something enters range do the action
        {
            if(ac_up)
                action.mvUp=true;
            else
                action.mvDown=true;

        }
        else //once nothing is in range, reset action to do and currently doing
        {
            action.mvUp=false;
            action.mvDown=false;
            ac_up=Math.random()>0.5;
        }

        if(angleToTarget(aimer,game.playerShip)<1)
            action.shoot=true;


        return action;


    }



}




