package game1;

import util.Vector2D;

import java.awt.*;

public class AnimSprite
{
    public Vector2D position;
    public final Image image;
    public final int cellWidth,cellHeight,rows,cols,destWidth,destHeight;
    public boolean isFlipX;
    public int index=0;
    public final int size;


    public AnimSprite(Image image,Vector2D pos,int cellWidth,int cellHeight,int destWidth,int destHeight)
    {
        this.image=image;
        this.position=pos;
        this.cellWidth=cellWidth;
        this.cellHeight=cellHeight;
        this.destWidth=destWidth*2;
        this.destHeight=destHeight*2;

        rows=image.getHeight(null)/cellHeight;
        cols = image.getWidth(null)/cellWidth;
        size = rows*cols;
    }


    public void draw(Graphics2D g)
    {

        int srcX = (index%cols)*cellWidth;
        int srcY = (index/cols)*cellHeight;

        int destX = (int) position.x-destWidth/2;
        int destY = (int) position.y-destHeight/2;

        int destX1 = (isFlipX ? destX + destWidth : destX);
        int destX2 = (isFlipX ? destX : destX + destWidth);

        g.drawImage(image,destX1,destY,destX2,destY + destHeight,srcX,srcY,srcX+cellWidth,srcY+cellHeight,null);
    }

    public void update()
    {

        index = (index+1) % size;
    }

}
