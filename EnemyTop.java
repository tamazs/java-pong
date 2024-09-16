import greenfoot.*;

/**
 * A RedEnemy paddle at the ceiling that moves towards the ball.
 * It stops moving when it hits the ball and waits for the next player return.
 */
public class EnemyTop extends Actor
{
    private int width;
    private int height;
    private int speed = 2;
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
        image.setColor(Color.RED);
        image.fill();
        setImage(image);
    }

    /**
     * Act - do whatever the RedEnemy wants to do.
     * It will move towards the ball if it hasn't hit it yet.
     */
    public void act()
    {
        if (!hasHitBall)
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

    /**
     * Resets the paddle movement, allowing it to track the ball again after the ball is returned.
     */
    public void reset()
    {
        hasHitBall = false;  // Enable movement again
    }
}
