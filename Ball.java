import greenfoot.*;

/**
 * A Ball is a thing that bounces off walls, paddles, and ceilings.
 * 
 * @author The teachers 
 * @version 1
 */
public class Ball extends SmoothMover
{
    private static final int BALL_SIZE = 25;
    private static final int BOUNCE_DEVIANCE_MAX = 5;
    private static final int STARTING_ANGLE_WIDTH = 90;
    private static final int DELAY_TIME = 100;

    private int speed;
    private boolean hasBouncedHorizontally;
    private boolean hasBouncedVertically;
    private int delay;

    /**
     * Constructs the ball and sets it in motion!
     */
    public Ball()
    {
        createImage();
        init();
    }

    /**
     * Creates and sets an image of a white ball to this actor.
     */
    private void createImage()
    {
        GreenfootImage ballImage = new GreenfootImage(BALL_SIZE,BALL_SIZE);
        ballImage.setColor(Color.WHITE);
        ballImage.fillOval(0, 0, BALL_SIZE, BALL_SIZE);
        setImage(ballImage);
    }

    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (delay > 0)
        {
            delay--;
        }
        else
        {
            move(speed);
            checkBounceOffWalls();
            checkBounceOffCeiling();
            checkBounceOffPaddle();
            checkBounceOffEnemy();
            checkBounceOffEnemyTop();
            checkRestart();
        }
    }    

    /**
     * Returns true if the ball is touching one of the side walls.
     */
    private boolean isTouchingSides()
    {
        return (getX() <= BALL_SIZE / 2 || getX() >= getWorld().getWidth() - BALL_SIZE / 2);
    }

    /**
     * Returns true if the ball is touching the ceiling.
     */
    private boolean isTouchingCeiling()
    {
        return (getY() <= BALL_SIZE / 2);
    }

    /**
     * Returns true if the ball is touching the floor.
     */
    private boolean isTouchingFloor()
    { 
        return (getY() >= getWorld().getHeight() - BALL_SIZE / 2);
    }

    /**
     * Check to see if the ball should bounce off one of the walls.
     * If touching one of the walls, the ball bounces off.
     */
    private void checkBounceOffWalls()
    {
        if (isTouchingSides())
        {
            if (!hasBouncedHorizontally)
            {
                revertHorizontally();
            }
        }
        else
        {
            hasBouncedHorizontally = false;
        }
    }

    /**
     * Check to see if the ball should bounce off the ceiling.
     */
    private void checkBounceOffCeiling()
    {
        if (isTouchingCeiling())
        {
            if (!hasBouncedVertically)
            {
                revertVertically();
                
                // Notify the world that the ball has hit the ceiling
                PingWorld world = (PingWorld) getWorld();
                world.ceilingHit();
                world.playerScored();
            }
        }
        else
        {
            hasBouncedVertically = false;
        }
    }

    /**
     * Increase the ball speed when leveling up.
     */
    public void increaseSpeed()
    {
        speed++;
    }

    /**
     * Check to see if the ball should bounce off the paddle.
     * If touching the paddle, the ball bounces off.
     */
    private void checkBounceOffPaddle()
    {
        Paddle paddle = (Paddle) getOneIntersectingObject(Paddle.class);
        if (paddle != null)
        {
            if (!hasBouncedVertically)
            {
                // Make the ball bounce upwards off the paddle
                revertVertically();
                
                PingWorld world = (PingWorld) getWorld();
                world.resetEnemyTop();
                
            }
        }
    }
    
    /**
     * Check if the ball should bounce off the enemy paddle.
     * The ball should bounce only when it is coming from above the enemy.
     */
    private void checkBounceOffEnemy()
    {
        Enemy enemy = (Enemy) getOneIntersectingObject(Enemy.class);
        if (enemy != null)
        {
            // Check if the ball's Y coordinate is higher than the enemy's
            if (getRotation() > 180 && getRotation() < 360)
            {
                if (!hasBouncedVertically)
                {
                    // Make the ball bounce off the enemy
                    revertVertically();
                }
            }
            // If the ball is coming from below (from the ceiling), it should pass through
        }
    }
    
    /**
     * Check to see if the ball should bounce off the RedEnemy paddle.
     * If touching the EnemyTop, the ball bounces off.
     */
    private void checkBounceOffEnemyTop()
    {
        EnemyTop enemyTop = (EnemyTop) getOneIntersectingObject(EnemyTop.class);
        if (enemyTop != null)
        {
            if (!hasBouncedVertically)
            {
                // Make the ball bounce off the RedEnemy paddle
                revertVertically();
            }
        }
    }

    /**
     * Check to see if the ball should be restarted.
     * If touching the floor the ball is restarted in initial position and speed.
     */
    private void checkRestart()
    {
        if (isTouchingFloor())
        {
            PingWorld world = (PingWorld) getWorld();
            world.enemyScored();
            world.resetGame();  // Call the resetGame method in PingWorld to reset the level
            
            init();  // Reinitialize the ball’s settings (reset speed, etc.)
            setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2);  // Reset ball position
        }
    }

    
    public void resetSpeed()
    {
        speed = 2;  // Reset to initial speed
    }

    /**
     * Bounces the ball back from a vertical surface (like the sides).
     */
    private void revertHorizontally()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX) - BOUNCE_DEVIANCE_MAX / 2;
        setRotation((180 - getRotation() + randomness + 360) % 360);
        hasBouncedHorizontally = true;
        Greenfoot.playSound("Bell.wav");
    }

    /**
     * Bounces the ball back from a horizontal surface (like the paddle or ceiling).
     */
    private void revertVertically()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX) - BOUNCE_DEVIANCE_MAX / 2;
        setRotation((360 - getRotation() + randomness + 360) % 360);
        hasBouncedVertically = true;
        Greenfoot.playSound("Bell.wav");
    }

    /**
     * Initialize the ball settings.
     */
    private void init()
    {
        speed = 2;
        delay = DELAY_TIME;
        hasBouncedHorizontally = false;
        hasBouncedVertically = false;
        setRotation(Greenfoot.getRandomNumber(STARTING_ANGLE_WIDTH) + STARTING_ANGLE_WIDTH / 2);
    }
}
