package game1;

public class RandomAction implements Controller
{
    Action action;

    double moveTimer=0;
    double rotTimer=0;
    double shootTimer=0;


    @Override
    public Action action()
    {
        action=new Action();
        performActions(Math.random(), Math.random() > 0.5 ? 1 : -1, Math.random());
        return action;
    }

    void performActions(double shoottime,int dir, double rottime)
    {
        randomRot(rottime,dir);
        randomShoot(shoottime);
    }

    private void randomRot(double ttr,int dir)
    {
        if(rotTimer<ttr)
        {
            action.turn=dir;
            rotTimer+=Constants.DT;
        }
        else
        {
            randomMov(Math.random()*(2-1)+1);
            action.turn=0;
            rotTimer=0;
        }
    }

    private void randomMov(double ttm)
    {
        if(moveTimer < ttm)
        {
            action.thrust=1;
            moveTimer+=Constants.DT;

        }
        else
        {
            action.thrust=0;
            moveTimer=0;

        }
    }

    private void randomShoot(double tts)
    {
        if(shootTimer<tts)
        {
            action.shoot=true;
            shootTimer+=Constants.DT;
        }
        else
        {
            action.shoot=false;
            shootTimer=0;
        }
    }




}
