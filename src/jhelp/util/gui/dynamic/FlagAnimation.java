/**
 * <h1>License :</h1> <br>
 * The following code is deliver as is. I take care that code compile and work, but I am not responsible about any
 * damage it may
 * cause.<br>
 * You can use, modify, the code as your need for any usage. But you can't do any action that avoid me or other person use,
 * modify this code. The code is free for usage and modification, you can't change that fact.<br>
 * <br>
 *
 * @author JHelp
 */
package jhelp.util.gui.dynamic;

import jhelp.util.gui.JHelpImage;
import jhelp.util.gui.JHelpSprite;
import jhelp.util.gui.transformation.Transformation;
import jhelp.util.math.UtilMath;

/**
 * Animation image to dance like a flag in the wind
 *
 * @author JHelp
 */
public class FlagAnimation
        implements DynamicAnimation, Positionable
{
    /** Waves size */
    private final int            amplitude;
    /** Image to animate */
    private final JHelpImage     flagImage;
    /** Number of frame for do a complete loop */
    private final float          numberFrameForLoop;
    /** Number of waves */
    private final int            numberOfWave;
    /** Transformation to use to do flag effect */
    private final Transformation transformation;
    /** Actual start angle */
    private       double         angle;
    /** Sprite where draw flag */
    private       JHelpSprite    sprite;
    /** Start absolute frame */
    private       float          startAbsoluteFrame;
    /** Position X */
    private       int            x;
    /** Y position */
    private       int            y;

    /**
     * Create a new instance of FlagAnimation
     *
     * @param x
     *           Start X
     * @param y
     *           Start Y
     * @param flagImage
     *           Image to animate
     * @param numberOfWave
     *           Number of waves
     * @param amplitude
     *           Wave sizes
     * @param numberFrameForLoop
     *           Number of frame for a complete loop
     */
    public FlagAnimation(final int x, final int y, final JHelpImage flagImage, final int numberOfWave, final int amplitude,
                         final float numberFrameForLoop)
    {
        this.x = x;
        this.y = y;
        this.flagImage = flagImage;
        this.transformation = new Transformation(flagImage.getWidth(), flagImage.getHeight());
        this.numberOfWave = Math.max(1, numberOfWave);
        this.amplitude = Math.max(0, amplitude);
        this.numberFrameForLoop = Math.max(0.1f, numberFrameForLoop);
    }

    /**
     * Called each time animation refreshed <br>
     * <br>
     * <b>Parent documentation:</b><br>
     * {@inheritDoc}
     *
     * @param absoluteFrame
     *           Absolute frame
     * @param image
     *           Image parent
     * @return {@code true} because never ending animation (Except by remove)
     * @see jhelp.util.gui.dynamic.DynamicAnimation#animate(float, jhelp.util.gui.JHelpImage)
     */
    @Override
    public boolean animate(final float absoluteFrame, final JHelpImage image)
    {
        this.angle = UtilMath.modulo(((absoluteFrame - this.startAbsoluteFrame) * 2 * Math.PI) / this.numberFrameForLoop,
                                     Math.PI * 2);
        this.refreshSprite();
        return true;
    }

    /**
     * Refresh the flag in the sprite
     */
    private void refreshSprite()
    {
        this.transformation.toHorizontalSin(this.numberOfWave, this.amplitude, this.angle);

        final JHelpImage image = this.sprite.getImage();
        image.startDrawMode();
        image.clear(0);
        image.drawImage(0, this.amplitude, this.flagImage, this.transformation, false);
        image.endDrawMode();
    }

    /**
     * Called when animation stopped <br>
     * <br>
     * <b>Parent documentation:</b><br>
     * {@inheritDoc}
     *
     * @param image
     *           Image parent
     * @see jhelp.util.gui.dynamic.DynamicAnimation#endAnimation(jhelp.util.gui.JHelpImage)
     */
    @Override
    public void endAnimation(final JHelpImage image)
    {
        image.removeSprite(this.sprite);
    }

    /**
     * Called when animation started <br>
     * <br>
     * <b>Parent documentation:</b><br>
     * {@inheritDoc}
     *
     * @param startAbsoluteFrame
     *           Start absolute frame
     * @param image
     *           Image parent
     * @see jhelp.util.gui.dynamic.DynamicAnimation#startAnimation(float, jhelp.util.gui.JHelpImage)
     */
    @Override
    public void startAnimation(final float startAbsoluteFrame, final JHelpImage image)
    {
        this.startAbsoluteFrame = startAbsoluteFrame;
        final int width  = this.flagImage.getWidth();
        final int height = this.flagImage.getHeight() + (this.amplitude << 1);
        this.sprite = image.createSprite(this.x, this.y, width, height);
        this.angle = 0;
        this.refreshSprite();
        this.sprite.setVisible(true);
    }

    /**
     * Actual position <br>
     * <br>
     * <b>Parent documentation:</b><br>
     * {@inheritDoc}
     *
     * @return Current position
     * @see jhelp.util.gui.dynamic.Positionable#getPosition()
     */
    @Override
    public Position getPosition()
    {
        return new Position(this.x, this.y);
    }

    /**
     * Change current position <br>
     * <br>
     * <b>Parent documentation:</b><br>
     * {@inheritDoc}
     *
     * @param position
     *           New position
     * @see jhelp.util.gui.dynamic.Positionable#setPosition(jhelp.util.gui.dynamic.Position)
     */
    @Override
    public void setPosition(final Position position)
    {
        this.x = position.getX();
        this.y = position.getY();

        if (this.sprite != null)
        {
            this.sprite.setPosition(this.x, this.y);
        }
    }
}