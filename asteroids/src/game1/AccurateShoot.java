package game1;

import util.Vector2D;

public class AccurateShoot extends Controllers implements Controller
{
    public static double SHOOTING_DISTANCE; //how close target must be before it fires
    private Game game;
    Action action;
    public Ship aimer;


    public AccurateShoot(Game game, Ship aimer)
    {
        this.game=game;
        this.aimer=aimer;
    }

    @Override
    public Action action()
    {
        action=new Action();
        action.turn=Controllers.aimAccurate(aimer,game.playerShip);
        action.shoot=angleToTarget(aimer,game.playerShip)<0.1;

        return action;
    }


    public Vector2D getTargetPos()
    {
        Vector2D p = new Vector2D(game.playerShip.position);
        return new Vector2D(p.subtract(aimer.position));
    }
}
