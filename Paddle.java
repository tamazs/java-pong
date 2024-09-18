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

        // Use a brighter base color for visibility on a black background
        Color baseColor = new Color(0, 255, 0);  // Bright green
        Color shadeColor = baseColor.darker();   // Slightly darker green for shading

        // Fill the paddle with a gradient-like effect (3D illusion)
        for (int y = 0; y < height; y++) {
            // Interpolate between base color and shade color
            int red = (int) (baseColor.getRed() * (1 - y / (double)height) + shadeColor.getRed() * (y / (double)height));
            int green = (int) (baseColor.getGreen() * (1 - y / (double)height) + shadeColor.getGreen() * (y / (double)height));
            int blue = (int) (baseColor.getBlue() * (1 - y / (double)height) + shadeColor.getBlue() * (y / (double)height));

            Color gradientColor = new Color(red, green, blue);
            image.setColor(gradientColor);
            image.drawLine(0, y, width - 1, y);  // Draw each line with gradient effect
        }

        // Modify the border color to white or light gray to stand out on black
        image.setColor(Color.WHITE);  // White border
        image.drawRect(0, 0, width - 1, height - 1);  // Draw border around the paddle

        // Add a bright highlight at the top to create a shiny effect
        image.setColor(Color.LIGHT_GRAY);  // Light gray for the highlight
        image.drawLine(0, 1, width - 1, 1);  // Thin line at the top for highlight

        setImage(image);  // Apply the image to the paddle
    }


}
