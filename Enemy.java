import greenfoot.*;

public class Enemy extends Actor
{
    private int width;
    private int height;
    private int dx;

    /**
     * Constructs a new paddle with the given dimensions.
     */
    public Enemy(int width, int height)
    {
        this.width = width;
        this.height = height;
        dx = 1;
        createImage();
    }

    /**
     * Act - do whatever the Paddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        tryChangeDirection();
        setLocation(getX() + dx, getY());
    }    

    /**
     * Will rotate the paddle 180 degrees if the paddle is at worlds edge.
     */
    private void tryChangeDirection()
    {
        //Check to see if we are touching the outer boundaries of the world:
        // IF we are touching the right boundary OR we are touching the left boundary:
        if(getX() + width/2 >= getWorld().getWidth() || getX() - width/2 <= 0)
        {
            //Change our 'x' direction to the inverted direction:
            //dx = dx * -1;
            //dx = Greenfoot.getRandomNumber(3);
            width = Greenfoot.getRandomNumber(100) + 50;
            createImage();
            setLocation(0+width/2, Greenfoot.getRandomNumber(getWorld().getHeight()/2)+50);
        }
    }

    /**
     * Creates and sets an image for the paddle, the image will have the same dimensions as the paddles width and height.
     */
    private void createImage()
{
    GreenfootImage image = new GreenfootImage(width, height);

    // White paddle base color
    Color baseColor = Color.WHITE;
    Color shadeColor = Color.LIGHT_GRAY;  // Slightly darker color for shading

    // Fill the paddle with a gradient effect (from white to light gray)
    for (int y = 0; y < height; y++) {
        // Interpolate between base color (white) and shade color (light gray)
        int red = (int) (baseColor.getRed() * (1 - y / (double)height) + shadeColor.getRed() * (y / (double)height));
        int green = (int) (baseColor.getGreen() * (1 - y / (double)height) + shadeColor.getGreen() * (y / (double)height));
        int blue = (int) (baseColor.getBlue() * (1 - y / (double)height) + shadeColor.getBlue() * (y / (double)height));

        Color gradientColor = new Color(red, green, blue);
        image.setColor(gradientColor);
        image.drawLine(0, y, width - 1, y);  // Draw each line with gradient effect
    }

    // Use light gray for the border to give a slight contrast
    image.setColor(Color.LIGHT_GRAY);
    image.drawRect(0, 0, width - 1, height - 1);  // Draw border around the paddle

    // Add a highlight at the top using a very bright white
    image.setColor(new Color(240, 240, 240));  // Slightly off-white for highlight
    image.drawLine(0, 1, width - 1, 1);  // Thin white highlight line at the top

    setImage(image);  // Apply the image to the paddle
}


}
