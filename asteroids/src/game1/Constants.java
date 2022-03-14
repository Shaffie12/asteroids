package game1;

import util.ImageManager;

import java.awt.*;
import java.io.IOException;

public class Constants
{
    public static final int FRAME_HEIGHT=480;
    public static final int FRAME_WIDTH=640;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT);
    public static final int DELAY = 20;
    public static final double DT = DELAY / 1000.0;
    public static final double topy = FRAME_HEIGHT-(FRAME_HEIGHT+80);
    public static final double boty = FRAME_HEIGHT -80;
    public static final double leftx = FRAME_WIDTH-(FRAME_WIDTH+80);
    public static final double rightx = FRAME_WIDTH-80;
    public static Image ASTEROID1, ASTEROID2, MILKYWAY, UFO;
    public static Image POWERUP;
    static
    {
        try
        {
            ASTEROID1 = ImageManager.loadImage("as1");
            ASTEROID2 = ImageManager.loadImage("as2");
            MILKYWAY = ImageManager.loadImage("milkyway1");
            UFO = ImageManager.loadImage("ufo");
            POWERUP = ImageManager.loadImage("powerup");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
