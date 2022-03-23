package game1;

import util.Vector2D;

import java.awt.*;

public class Prop //could have the stars and these objects extend from this class?
{
    Vector2D position;
    Sprite sprite;
    int radius;
    double z;

    public Prop(int spriteNo)
    {
        Image img;
        z=Math.random()*(80-20)+20;
        radius=(int)(Math.random()*(40-20)+20); //make it larger if its closer, and more alpha

        double min = -radius*4;
        double max = Constants.FRAME_HEIGHT;
        double rx = Constants.rand.nextDouble()*Constants.FRAME_WIDTH;
        double ry= Constants.rand.nextDouble()*(max-min)+min;
        position= new Vector2D(rx,ry);

        if(spriteNo==1) //could add more sprites for props
            img= Constants.PLANET;
        else
            img = Constants.MILKYWAY;


        sprite = new Sprite(img,position,new Vector2D(0,1),radius,radius);

    }

    public void draw(Graphics2D g)
    {
        float alpha = 1.0f-(0.01f*(float)z);
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g.setComposite(comp);
        sprite.draw(g);
        comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f);
        g.setComposite(comp);
    }

    public void update()
    {
        this.position.y+=Game.worldSpeed*Constants.DT*this.z;
        if(position.y-radius*4>Constants.FRAME_HEIGHT)
            repos();
    }

    private void repos()
    {
        double min = -radius*4;
        double max = -Constants.FRAME_HEIGHT/2.0;
        position.x=Constants.rand.nextDouble()*Constants.FRAME_WIDTH;
        position.y=Constants.rand.nextDouble()*(max-min)+min;
        z=Math.random()*(80-20)+20;
        radius=(int)(Math.random()*(40-20)+20);

    }
}
