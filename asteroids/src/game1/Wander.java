package game1;

public class Wander extends Controllers implements Controller
{
    public Action action;
    boolean ac_up;
    Game game;
    Friendly self;

    public Wander(Game game, Friendly self)
    {
        this.game=game;
        this.self=self;


    }


    @Override
    public Action action()
    {
        action=new Action();

        GameObject closest = findClosest(self,game.objects);

        if(!(closest instanceof Particle) && self.position.dist(closest.position)<100)
            action.move=true;

        else
            action.move=false;


        return action;
    }

    public GameObject findClosest(Friendly self, Iterable<GameObject> gobjects)
    {

        GameObject closest=null;

        for(GameObject o:gobjects)
        {
            double distFromSelf = self.position.dist(o.position);
            if(closest==null)
            {
                if(self.canHit(o))
                    closest=o;
            }
            else
            {
                if(self.canHit(o) && distFromSelf < self.position.dist(closest.position))
                {
                    closest=o;
                }
            }


        }
        return closest;
    }





}
