package game1;

import util.Vector2D;

public class PowerUp extends Item
{

    public PowerUp(Vector2D pos, int rad) {
        super(pos, rad);
        sprite= new Sprite(Constants.POWERUP,pos,new Vector2D(0,1),rad,rad);
    }
}
