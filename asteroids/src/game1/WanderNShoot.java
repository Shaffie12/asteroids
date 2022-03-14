package game1;



public class WanderNShoot extends Controllers implements Controller
{
    public Action action;
    Game game;
    Ship aimer;
    int mvTimer=0;

    public WanderNShoot(Game game, Ship aimer)
    {
        this.game=game;
        this.aimer=aimer;

    }

    public Action action()
    {

        action=new Action();

        action.turn=Controllers.aimInaccurate(aimer,game.playerShip);

        if(angleToTarget(aimer,game.playerShip)<0.1)
            action.shoot=true;
        else
            action.shoot=false;

        return action;
        /*
        action= new Action();
        //move for a random amount of time l/r inverting x axis
        action.turn=Controllers.aimInaccurate(aimer,game.playerShip);
        action.shoot= angleToTarget(aimer, game.playerShip) < 0.1;

        //action.invertX = Math.random()>0.5? true:false;




        return action;

         */
    }







}




