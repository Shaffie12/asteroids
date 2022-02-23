package game1;

import java.awt.*;

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

}
