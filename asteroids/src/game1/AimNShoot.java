package game1;

import util.Vector2D;

public class AimNShoot extends Controllers implements Controller
{
    public static double SHOOTING_DISTANCE; //how close target must be before it fires
    private Game game;
    Action action;
    public Ship aimer;
    boolean hard;


    public AimNShoot(Game game, boolean hard)
    {
        this.game=game;
        this.hard=hard;
    }

    @Override
    public Action action()
    {
        action=new Action();
        if (hard)
            action.turn=Controllers.aimAccurate(aimer,game.playerShip);
        else
            action.turn=Controllers.aimInaccurate(aimer,game.playerShip);

        if(angleToTarget(aimer,game.playerShip)<0.1)
            action.shoot=true;
        else
            action.shoot=false;

        return action;
    }


    public Vector2D getTargetPos()
    {
        Vector2D p = new Vector2D(game.playerShip.position);
        return new Vector2D(p.subtract(aimer.position));
    }











}
