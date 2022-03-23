package game1;

import util.ImageManager;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Constants
{
    public static Random rand = new Random();
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
    public static Image ASTEROID1, ASTEROID2, UFO,MILKYWAY,PLANET, F_IDLE;


    static
    {
        try
        {
            ASTEROID1 = ImageManager.loadImage("as1");
            ASTEROID2 = ImageManager.loadImage("as2");
            MILKYWAY = ImageManager.loadImage("milkyway");
            PLANET = ImageManager.loadImage("planet1");
            UFO = ImageManager.loadImage("ufo");
            F_IDLE = ImageManager.loadImage("friendly/idle");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
