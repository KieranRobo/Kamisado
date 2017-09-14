import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;

/**
 * Created by Kieran on 14/03/2017.
 */
public class Tower {

    private int xLocation;
    private int yLocation;
    private int defaultXLocation;
    private int defaultYLocation;

    private int sumoLevel;
    private String towerColour;
    private String towerImage;
    private int playerOwner; // 0 for main player (black), 1 for opponent player (white)

    public Tower(int startingXPosition, int startingYPosition, int player, String colour) {
        xLocation = startingXPosition;
        yLocation = startingYPosition;
        defaultXLocation = startingXPosition;
        defaultYLocation = startingYPosition;

        sumoLevel = 0;
        towerColour = colour;
        playerOwner = player;
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public int getDefaultXLocation() {
        return defaultXLocation;
    }

    public int getDefaultYLocation() {
        return defaultYLocation;
    }

    public String getTowerColour() {
        return towerColour;
    }

    // Returns the JavaFX ImageView element to use in user interface
    public ImageView getTowerImage() {

        Image towerImage;
        String imageColourName;
        String imageLocation = null;

        if (playerOwner == 0)
            imageColourName = "black";
        else
            imageColourName = "white";

        if (sumoLevel > 0)
            imageLocation = "towers/" + sumoLevel + "_sumo_" + towerColour + "_" + imageColourName + ".png";
        else
            imageLocation = "towers/" + imageColourName + "-" + towerColour + ".png";

        towerImage = new Image(getClass().getResourceAsStream(imageLocation));
        return new ImageView(towerImage);
    }

    // 0 for main player (black), 1 for opponent player (white)
    public int getOwner() {
        return playerOwner;
    }

    public int getSumoLevel() {
        return sumoLevel;
    }


    public void setLocation(int x, int y) {
        xLocation = x;
        yLocation = y;
    }

    public void increaseSumoLevel() {
        if (sumoLevel < 3) // Sumos can only reach maximum of level 3
            sumoLevel++;
        System.out.println("increased sumo level");

    }

}
