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
    private static int players = 1;

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
    GreenfootImage ballImage = new GreenfootImage(BALL_SIZE, BALL_SIZE);

    // Base color for the ball
    Color baseColor = Color.WHITE;
    
    // Darker color for shading
    Color shadeColor = new Color(200, 200, 200);  // Light gray for shading

    // Draw the shaded ball
    for (int y = 0; y < BALL_SIZE; y++) {
        for (int x = 0; x < BALL_SIZE; x++) {
            // Calculate the distance from the center of the ball
            int dx = x - BALL_SIZE / 2;
            int dy = y - BALL_SIZE / 2;
            double distance = Math.sqrt(dx * dx + dy * dy);
            
            // If the point is within the ball, apply shading
            if (distance <= BALL_SIZE / 2) {
                // Calculate the shading effect based on distance from the center
                double shadingFactor = distance / (BALL_SIZE / 2);
                int red = (int) (baseColor.getRed() * (1 - shadingFactor) + shadeColor.getRed() * shadingFactor);
                int green = (int) (baseColor.getGreen() * (1 - shadingFactor) + shadeColor.getGreen() * shadingFactor);
                int blue = (int) (baseColor.getBlue() * (1 - shadingFactor) + shadeColor.getBlue() * shadingFactor);

                Color pixelColor = new Color(red, green, blue);
                ballImage.setColorAt(x, y, pixelColor);  // Set the pixel color with shading
            }
        }
    }

    // Set the shaded ball image
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
            //1 PLAYER - 2 PLAYERS
            PingWorld world = (PingWorld)getWorld();
            players = world.getHowManyPlayers();
            
            
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
                //world.PaddleHit();
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
        // Get the positions of the ball and paddle
        int ballY = getY();
        int ballX = getX();
        int paddleY = paddle.getY();
        int paddleX = paddle.getX();
        int paddleHalfWidth = paddle.getImage().getWidth() / 2;
        int paddleHalfHeight = paddle.getImage().getHeight() / 2;

        // Check if the ball is hitting the top of the paddle
        if (ballY > paddleY - paddleHalfHeight && ballY < paddleY) 
        {
            if (!hasBouncedVertically)
            {
                // Calculate the deviation based on where the ball hit the paddle
                int hitPosition = ballX - paddleX; // Negative = left side, Positive = right side
                int maxDeviation = 45; // Maximum angle deviation (can be adjusted)

                // Calculate bounce angle based on where the ball hit the paddle
                int newAngle = 270 + (hitPosition * maxDeviation / paddleHalfWidth);
                
                setRotation(newAngle); // Set the new angle of the ball
                
                hasBouncedVertically = true;
                
                PingWorld world = (PingWorld) getWorld();
                world.PaddleHit();
                world.resetEnemyTop();
            }
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
            if (getRotation() > 180 && getRotation() < 360 && players == 1)
            {
                if (!hasBouncedVertically)
                {
                    // Make the ball bounce off the enemy
                    revertVertically();
                }
            }
            
            else if (players == 2)
                if (!hasBouncedVertically)
                {
                    // Make the ball bounce off the enemy
                    revertVertically();
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
    
    //1 PLAYER - 2 PLAYERS
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
        
        else if (isTouchingCeiling() && players == 2) {
            PingWorld world = (PingWorld) getWorld();
            world.ceilingHit();
            world.resetGame();  // Call the resetGame method in PingWorld to reset the level
            
            init();  // Reinitialize the ball’s settings (reset speed, etc.)
            setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2); 
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
        //Greenfoot.playSound("Bell.wav");
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
