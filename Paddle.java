import greenfoot.*;

/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce off it.
 * 
 * @author The teachers 
 * @version 1
 */
public class Paddle extends Actor
{
    private int width;
    private int height;
    private int speed = 3;  // Speed at which the paddle moves

    /**
     * Constructs a new paddle with the given dimensions.
     */
    public Paddle(int width, int height)
    {
        this.width = width;
        this.height = height;
        createImage();
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkKeyPress();
        stayWithinBounds();  // Ensure paddle doesn't move out of bounds
    }    

    /**
     * Checks for user input to move the paddle left or right.
     */
    private void checkKeyPress()
    {
        if (Greenfoot.isKeyDown("left"))
        {
            moveLeft();
        }
        if (Greenfoot.isKeyDown("right"))
        {
            moveRight();
        }
    }

    /**
     * Moves the paddle to the left.
     */
    private void moveLeft()
    {
        setLocation(getX() - speed, getY());
    }

    /**
     * Moves the paddle to the right.
     */
    private void moveRight()
    {
        setLocation(getX() + speed, getY());
    }

    /**
     * Ensures that the paddle stays within the bounds of the world.
     */
    private void stayWithinBounds()
    {
        if (getX() - width / 2 < 0)
        {
            setLocation(width / 2, getY());
        }
        else if (getX() + width / 2 > getWorld().getWidth())
        {
            setLocation(getWorld().getWidth() - width / 2, getY());
        }
    }

    /**
     * Creates and sets an image for the paddle, the image will have the same dimensions as the paddle's width and height.
     */
    private void createImage()
    {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(Color.WHITE);
        image.fill();
        setImage(image);
    }
}
