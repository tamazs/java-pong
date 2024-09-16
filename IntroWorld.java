import greenfoot.*;

/**
 * IntroWorld for the Pong game.
 * Displays instructions and title before starting the game.
 */
public class IntroWorld extends World
{
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;

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
        
        setBackground(background);  // Set the updated background
    }

    public void act()
    {
        String key = Greenfoot.getKey();
        if (key != null && key.equals("enter"))
        {
            Greenfoot.setWorld(new PingWorld(true));
        }
    }
}
