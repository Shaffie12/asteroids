package game1;


public class WanderNShoot extends Controllers implements Controller
{
    public Action action;
    Game game;
    Ship aimer;
    int mvTimer;

    public WanderNShoot()
    {

    }

    public Action action()
    {
        Action a= new Action();
        //move for a random amount of time l/r inverting x axis
        a.turn=Controllers.aimInaccurate(aimer,game.playerShip);



        return a;
    }


    private void randomInvert(double ttm)
    {
        if(mvTimer<ttm)
        {
            action.turn=dir; //will this work?
            rotTimer+=Constants.DT;
        }
        else
        {
            randomMov(Math.random()*(2-1)+1);
            action.turn=0;
            rotTimer=0;
        }
    }
}


