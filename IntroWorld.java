import greenfoot.*;

/**
 * IntroWorld for the Pong game.
 * Displays instructions and title before starting the game.
 */
public class IntroWorld extends World
{
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;
    private int cursorPosition = 0;
    private boolean keyDown = false;
    private boolean keyUp = false;
    private Cursor cursor = new Cursor();

    /**
     * Constructor for objects of class IntroWorld.
     */
    public IntroWorld()
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        prepareBackground();
    }

    /**
     * Prepares the background by setting a black background and adding white text.
     */
    private void prepareBackground()
    {
        GreenfootImage background = new GreenfootImage(WORLD_WIDTH, WORLD_HEIGHT);
        background.setColor(Color.BLACK);  // Set background color to black
        background.fill();  // Fill the entire background with black

        // Set color and font for the title (PONG)
        background.setColor(Color.WHITE);
        background.setFont(new Font("Arial", true, false, 50));  // Bigger font size for the title
        background.drawString("PONG", WORLD_WIDTH / 2 - 60, WORLD_HEIGHT / 4);  // Centered at the top

        // Set a smaller font for the instructions
        background.setFont(new Font("Arial", false, false, 18));  // Normal font size for instructions
        background.drawString("Hit Enter to start the game", WORLD_WIDTH / 2 - 100, WORLD_HEIGHT / 2 - 50);
        background.drawString("Press right arrow to move right", WORLD_WIDTH / 2 - 120, WORLD_HEIGHT / 2);
        background.drawString("Press left arrow to move left", WORLD_WIDTH / 2 - 110, WORLD_HEIGHT / 2 + 50);
        background.drawString("For player2: A - left, and D - right", WORLD_WIDTH / 2 - 125, WORLD_HEIGHT / 2 + 100);

        // 1 player or 2 players
        background.drawString("1 player", WORLD_WIDTH / 2 - 50, WORLD_HEIGHT / 2 + 170);
        background.drawString("2 players", WORLD_WIDTH / 2 - 50, WORLD_HEIGHT / 2 + 200);
        addObject(cursor, WORLD_WIDTH/2-70, WORLD_HEIGHT/2+165+(cursorPosition*30));

        setBackground(background);  // Set the updated background
    }

    public void act()
    {
        String key = Greenfoot.getKey();
        if (Greenfoot.isKeyDown("down") && !keyDown) {
            if (cursorPosition >= 1)
                cursorPosition = 1;
            else
                cursorPosition++;
            keyDown = true;
        }
        if (!Greenfoot.isKeyDown("down") && keyDown) {
            keyDown = false;
        }
        if (Greenfoot.isKeyDown("up") && !keyUp) {
            if (cursorPosition <= 0)
                cursorPosition = 0;
            else
                cursorPosition--;
            keyUp = true;
        }
        if (!Greenfoot.isKeyDown("up") && keyUp) {
            keyUp = false;
        }
        setCursor();
        if (key != null && key.equals("enter"))
        {
            PingWorld game = new PingWorld(true);
            game.setHowManyPLayers(cursorPosition+1);
            Greenfoot.setWorld(game);
        }
    }

    private void setCursor() {
        cursor.setLocation(WORLD_WIDTH/2-70, WORLD_HEIGHT/2+165+(cursorPosition*30));
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
