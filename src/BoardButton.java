import javafx.scene.control.*;


/**
 * Created by Kieran on 14/03/2017.
 */
public class BoardButton extends javafx.scene.control.Button {

    Button fxButton;
    String cellColourHex; // literal cell colour in html format
    String cellColourName; // cell colour in english
    int xLocation;
    int yLocation;

    boolean occupied;

    // Assigns default colour to zone
    public BoardButton(int column, int row) {
        xLocation = column;
        yLocation = row;
        cellColourHex = calculateColourBoard(column, row);
        cellColourName = colourToEnglish(cellColourHex);

        fxButton = new Button();

        occupied = false;
    }

    // Assigns user provided colour to zone
    public BoardButton(int column, int row, String hexColour) {
        xLocation = column;
        yLocation = row;
        cellColourHex = hexColour;
        cellColourName = colourToEnglish(cellColourHex);

        fxButton = new Button();

        occupied = false;
    }

    public Button getButton() {
        return fxButton;
    }

    public String getCellColourHex() {
        return cellColourHex;
    }

    public String getCellColour() {
        return cellColourName;
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }


    public void setOccupied(boolean occ) {
        occupied = occ;
    }

    public boolean isOccupied() {
        return occupied;
    }

    private String colourToEnglish(String hexColour) {
        String englishColour = null;
        if (hexColour == "#FFA500")
            englishColour = "orange";
        if (hexColour == "#0000FF")
            englishColour = "blue";
        if (hexColour == "#7D3C98")
            englishColour = "purple";
        if (hexColour == "#FFB6C1")
            englishColour = "pink";
        if (hexColour == "#FFFF00")
            englishColour = "yellow";
        if (hexColour == "#FF0000")
            englishColour = "red";
        if (hexColour == "#008000")
            englishColour = "green";
        if (hexColour == "#641E16")
            englishColour = "brown";

        return englishColour;
    }

    // Takes in board cell locations and returns Kamisado cell colour for constructor (DEFAULT COLOURS)
    private String calculateColourBoard(int col, int row) {
        // ORANGE
        if (col == 0  && row == 0 ||
                col == 1  && row == 1 ||
                col == 2  && row == 2 ||
                col == 3  && row == 3 ||
                col == 4  && row == 4 ||
                col == 5  && row == 5 ||
                col == 6  && row == 6 ||
                col == 7  && row == 7) {
            return "#FFA500";
        }

        // BLUE
        if (col == 1  && row == 0 ||
                col == 4  && row == 1 ||
                col == 7  && row == 2 ||
                col == 2  && row == 3 ||
                col == 5  && row == 4 ||
                col == 0  && row == 5 ||
                col == 3  && row == 6 ||
                col == 6  && row == 7) {
            return "#0000FF";
        }

        // PURPLE
        if (col == 2  && row == 0 ||
                col == 7  && row == 1 ||
                col == 4  && row == 2 ||
                col == 1  && row == 3 ||
                col == 6  && row == 4 ||
                col == 3  && row == 5 ||
                col == 0  && row == 6 ||
                col == 5  && row == 7) {
            return "#7D3C98";
        }

        // PINK
        if (col == 3  && row == 0 ||
                col == 2  && row == 1 ||
                col == 1  && row == 2 ||
                col == 0  && row == 3 ||
                col == 7  && row == 4 ||
                col == 6  && row == 5 ||
                col == 5  && row == 6 ||
                col == 4  && row == 7) {
            return "#FFB6C1";
        }

        // YELLOW
        if (col == 4  && row == 0 ||
                col == 5  && row == 1 ||
                col == 6  && row == 2 ||
                col == 7  && row == 3 ||
                col == 0  && row == 4 ||
                col == 1  && row == 5 ||
                col == 2  && row == 6 ||
                col == 3  && row == 7) {
            return "#FFFF00";
        }

        // RED
        if (col == 5  && row == 0 ||
                col == 0  && row == 1 ||
                col == 3  && row == 2 ||
                col == 6  && row == 3 ||
                col == 1  && row == 4 ||
                col == 4  && row == 5 ||
                col == 7  && row == 6 ||
                col == 2  && row == 7) {
            return "#FF0000";
        }

        //  GREEN
        if (col == 6  && row == 0 ||
                col == 3  && row == 1 ||
                col == 0  && row == 2 ||
                col == 5  && row == 3 ||
                col == 2  && row == 4 ||
                col == 7  && row == 5 ||
                col == 4  && row == 6 ||
                col == 1  && row == 7) {
            return "#008000";
        }

        //  BROWN
        if (col == 7  && row == 0 ||
                col == 6  && row == 1 ||
                col == 5  && row == 2 ||
                col == 4  && row == 3 ||
                col == 3  && row == 4 ||
                col == 2  && row == 5 ||
                col == 1  && row == 6 ||
                col == 0  && row == 7) {
            return "#641E16";
        }


        return "meh";
    }
}
