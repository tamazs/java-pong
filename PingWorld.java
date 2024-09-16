import greenfoot.*;

/**
 * The PingWorld is where Balls and Paddles meet to play pong.
 * 
 * @author The teachers 
 * @version 1
 */
public class PingWorld extends World
{
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;
    
    private Counter levelCounter;
    private int level = 1;
    private int ceilingHitCount = 0;
    private Ball ball;
    private EnemyTop enemyTop;
    private int playerScore = 0;
    private int enemyScore = 0;

    // Score counters for display
    private Counter playerScoreCounter;
    private Counter enemyScoreCounter;
    
    /**
     * Constructor for objects of class PingWorld.
     */
    public PingWorld(boolean gameStarted)
    {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1); 
        
        if (gameStarted)
        {
            prepareBackground();  // Set black background
            initializeGameObjects();  // Add paddles and ball
            
            // Add the level counter at the top-left corner of the world
            levelCounter = new Counter("Level: ");
            addObject(levelCounter, 80, 30);
            levelCounter.setValue(level);
            
            // Add score counters
            playerScoreCounter = new Counter("");  // Display only the number
            addObject(playerScoreCounter, WORLD_WIDTH / 2, 680);  // Position for player score

            enemyScoreCounter = new Counter("");  // Display only the number
            addObject(enemyScoreCounter, WORLD_WIDTH / 2, 20);  // Position for enemy score

            updateScores();  // Initially update the scores on the screen
        }
        else
        {
            Greenfoot.setWorld(new IntroWorld());
        }
    }
    
    /**
     * Prepares the background for the game.
     */
    private void prepareBackground()
    {
        GreenfootImage background = new GreenfootImage(WORLD_WIDTH, WORLD_HEIGHT);
        background.setColor(Color.BLACK);  // Set background color to black
        background.fill();  // Fill the background with black
        setBackground(background);  // Set the background
    }
    
    /**
     * Adds the ball and paddle objects to the world.
     */
    private void initializeGameObjects()
    {
        ball = new Ball();  // Store a reference to the ball
        addObject(ball, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);  // Center the ball
        addObject(new Paddle(100, 20), 60, WORLD_HEIGHT - 50);  // Position the paddle
        addObject(new Enemy(100, 20), WORLD_WIDTH / 2, Greenfoot.getRandomNumber(300) + 150);  // Position the enemy at the top
        enemyTop = new EnemyTop(100, 20);
        addObject(enemyTop, WORLD_WIDTH / 2, 50);
    }
    
    /**
     * Increment the player's score when the ball hits the ceiling.
     */
    public void playerScored()
    {
        playerScore++;
        updateScores();  // Update the score display on the screen
    }

    /**
     * Increment the enemy's score when the ball hits the floor.
     */
    public void enemyScored()
    {
        enemyScore++;
        updateScores();  // Update the score display on the screen
    }

    /**
     * Updates the score display on the screen.
     */
    private void updateScores()
    {
        playerScoreCounter.setValue(playerScore);
        enemyScoreCounter.setValue(enemyScore);
    }

    /**
     * Increments the ceiling hit count and checks if the level should increase.
     */
    public void ceilingHit()
    {
        ceilingHitCount++;
        
        if (ceilingHitCount >= 10)
        {
            levelUp();
        }
    }

    /**
     * Level up the game: increase the ball speed and reset the ceiling hit count.
     */
    private void levelUp()
    {
        level++;
        ceilingHitCount = 0;
        levelCounter.setValue(level);  // Update the level counter display
        
        // Increase ball speed
        ball.increaseSpeed();
    }
    
    /**
     * Resets the RedEnemy's state after the ball has hit the player's paddle.
     */
    public void resetEnemyTop()
    {
        enemyTop.reset();
    }
    
    /**
     * Resets the game state (level, ceiling hit count) when the ball touches the floor.
     */
    public void resetGame()
    {
        level = 1;
        ceilingHitCount = 0;
        levelCounter.setValue(level);  // Reset the level counter display
        
        // Optionally, reset the ball speed
        ball.resetSpeed();
    }
}
