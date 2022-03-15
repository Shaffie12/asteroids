package game1;


import util.Vector2D;

public class WanderNShoot extends Controllers implements Controller
{
    public Action action;
    Game game;
    Ship aimer;

    public WanderNShoot(Game game, Ship aimer)
    {
        this.game=game;
        this.aimer=aimer;

    }

    public Action action()
    {

        action=new Action();


        action.turn=Controllers.aimInaccurate(aimer,game.playerShip);
        GameObject closest = findClosestHittable(aimer,game.objects);
        if(closest instanceof Asteroid || closest instanceof PlayerShip && aimer.position.dist(closest.position)<80)
        {
            if (withinRange(closest.position))
                action.avoid = true;
        }
        else
            action.avoid=false;
        //he needs to be able to move up and down. have him make the smartest choice about where to move depending on what is in either direction?  give him some ability to see forward before moving




        return action;


    }

    private boolean withinRange(Vector2D hittable)
    {
        return Math.abs(hittable.x-aimer.position.x)<aimer.radius+100;
    }







}




