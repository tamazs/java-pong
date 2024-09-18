import greenfoot.*;

/**
 * A RedEnemy paddle at the ceiling that moves towards the ball.
 * It stops moving when it hits the ball and waits for the next player return.
 */
public class EnemyTop extends Actor
{
    private int width;
    private int height;
    private int speed = 1;
    private boolean hasHitBall = false;  // Tracks whether the paddle has hit the ball

    /**
     * Constructs a new RedEnemy paddle with the given dimensions.
     */
    public EnemyTop(int width, int height)
    {
        this.width = width;
        this.height = height;
        createImage();
    }

    /**
     * Creates and sets an image for the paddle, the image will have the same dimensions and be red.
     */
    private void createImage()
    {
        GreenfootImage image = new GreenfootImage(width, height);

        // Red paddle base color
        Color baseColor = Color.RED;
        Color shadeColor = baseColor.darker();  // Darker shade of red for shading

        // Fill the paddle with a gradient effect (from red to darker red)
        for (int y = 0; y < height; y++) {
            // Interpolate between base color (red) and shade color (darker red)
            int red = (int) (baseColor.getRed() * (1 - y / (double)height) + shadeColor.getRed() * (y / (double)height));
            int green = (int) (baseColor.getGreen() * (1 - y / (double)height) + shadeColor.getGreen() * (y / (double)height));
            int blue = (int) (baseColor.getBlue() * (1 - y / (double)height) + shadeColor.getBlue() * (y / (double)height));

            Color gradientColor = new Color(red, green, blue);
            image.setColor(gradientColor);
            image.drawLine(0, y, width - 1, y);  // Draw each line with gradient effect
        }

        // Use light red or pink for the border to make the paddle stand out
        image.setColor(new Color(255, 128, 128));  // Light red/pink border
        image.drawRect(0, 0, width - 1, height - 1);  // Draw border around the paddle

        // Add a white or light pink highlight at the top
        image.setColor(Color.PINK);  // Light pink for the highlight
        image.drawLine(0, 1, width - 1, 1);  // Thin highlight line at the top

        setImage(image);  // Apply the image to the paddle
    }


    /**
     * Act - do whatever the RedEnemy wants to do.
     * It will move towards the ball if it hasn't hit it yet.
     */
    public void act()
    {
        PingWorld world = (PingWorld)getWorld();
    //1 PLAYER - 2 PLAYERS    
        if (world.getHowManyPlayers() == 2) {
            speed = 2;
            checkKeyPress();
        }
        else if (!hasHitBall)
        {
            moveTowardsBall();
        }
    }

    /**
     * Moves the paddle horizontally towards the ball's X position.
     */
    private void moveTowardsBall()
    {
        Ball ball = (Ball) getWorld().getObjects(Ball.class).get(0);  // Get the ball reference
        int ballX = ball.getX();

        // Move the paddle towards the ball's X position
        if (getX() < ballX)
        {
            setLocation(getX() + speed, getY());
        }
        else if (getX() > ballX)
        {
            setLocation(getX() - speed, getY());
        }

        // If the ball hits the paddle, stop moving
        if (isTouching(Ball.class))
        {
            hasHitBall = true;  // Stop the paddle's movement
        }
    }
    //1 PLAYER - 2 PLAYERS
    private void checkKeyPress()
    {
        if (Greenfoot.isKeyDown("a"))
        {
            moveLeft();
        }
        if (Greenfoot.isKeyDown("d"))
        {
            moveRight();
        }
    }

    /**
     * Resets the paddle movement, allowing it to track the ball again after the ball is returned.
     */
    public void reset()
    {
        hasHitBall = false;  // Enable movement again
    }
    //1 PLAYER - 2 PLAYERS
    /**
     * Moves the paddle to the left.
     */
    private void moveLeft()
    {
        setLocation(getX() - speed, getY());
    }
//1 PLAYER - 2 PLAYERS
    /**
     * Moves the paddle to the right.
     */
    private void moveRight()
    {
        setLocation(getX() + speed, getY());
    }
//1 PLAYER - 2 PLAYERS
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
}
