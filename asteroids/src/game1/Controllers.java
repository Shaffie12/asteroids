package game1;

import util.Vector2D;

public class Controllers
{
    public Controllers(){}

    public static GameObject findTarget(Ship aimer, Iterable<GameObject> gobjects)
    {

        GameObject closest=null;

        for(GameObject o:gobjects)
        {
            double distFromAimer = aimer.position.dist(o.position);
            if(closest==null)
            {
                if(aimer.canHit(o) && distFromAimer<=AimNShoot.SHOOTING_DISTANCE)
                {
                    closest=o;
                }
            }
            else
            {
                if(aimer.canHit(o) && distFromAimer<=AimNShoot.SHOOTING_DISTANCE && distFromAimer < aimer.position.dist(closest.position))
                {
                    closest=o;
                }
            }


        }
        return closest;
    }



    public static double angleToTarget(Ship ship, GameObject target) {
        Vector2D targetPosition = new Vector2D(target.position).addScaled(target.velocity, Constants.DT );
        return ship.direction.angle(targetPosition.subtract(ship.position));
    }


    public static int aimAccurate(Ship ship, GameObject target)
    {
        ship.STEER_RATE=angleToTarget(ship,target);
        return 1;
    }

    public static int aimInaccurate(Ship ship, GameObject target)
    {
        double angle = angleToTarget(ship,target);

        if (Math.abs(angle) < 1 * ship.STEER_RATE*Constants.DT )
            return 0;
        else
            return angle > 0 ? 1 : -1; //angle > 0? return 1, else -1

    }



}
