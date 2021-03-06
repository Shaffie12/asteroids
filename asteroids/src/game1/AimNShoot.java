package game1;

import util.Vector2D;

public class AimNShoot extends Controllers implements Controller
{
    public static double SHOOTING_DISTANCE; //how close target must be before it fires
    private Game game;
    Action action;
    public Ship aimer;



    public AimNShoot(Game game, Ship aimer)
    {
        this.game=game;
        this.aimer=aimer;
    }

    @Override
    public Action action()
    {
        action=new Action();
        action.turn=Controllers.aimInaccurate(aimer,game.playerShip);
        action.shoot=true;

        return action;
    }


    public Vector2D getTargetPos()
    {
        Vector2D p = new Vector2D(game.playerShip.position);
        return new Vector2D(p.subtract(aimer.position));
    }











}
