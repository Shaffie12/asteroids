package game1;


public class AccurateWanderShoot extends AccurateShoot implements Controller
{
    private boolean ac_up;

    public AccurateWanderShoot(Game game, Ship aimer)
    {
        super(game,aimer);
    }

    @Override
    public Action action()
    {
        action=new Action();
        action.turn=Controllers.aimAccurate(aimer,game.playerShip);
        action.shoot=angleToTarget(aimer,game.playerShip)<0.1;

        GameObject closest = findClosestHittable(aimer,game.objects);

        if(closest instanceof Asteroid || closest instanceof PlayerShip && aimer.position.dist(closest.position)<80)
        {
            if(ac_up)
                action.mvUp=true;
            else
                action.mvDown=true;

        }
        else
        {
            action.mvUp=false;
            action.mvDown=false;
            ac_up=Math.random()>0.5;
        }

        return action;
    }



}
