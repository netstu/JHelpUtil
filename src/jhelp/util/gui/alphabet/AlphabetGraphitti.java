/**
 * <h1>License :</h1> <br>
 * The following code is deliver as is. I take care that code compile and work, but I am not responsible about any damage it may
 * cause.<br>
 * You can use, modify, the code as your need for any usage. But you can't do any action that avoid me or other person use,
 * modify this code. The code is free for usage and modification, you can't change that fact.<br>
 * <br>
 * 
 * @author JHelp
 */
package jhelp.util.gui.aimport java.awt.Dimension;

import jhelp.util.gui.GIF;
import jhelp.util.gui.JHelpImage;
elpImage;

/**
 * Alphabet with "graphitti" letters
 * 
 * @author JHelp
 */
public class AlphabetGraphitti
      extends Alphabet
{
   /** Gaphitti letters images */
   private static final GIF              BASE       = AlphabetResources.RESOURCES.obtaingGIF("alphabetGraphitti.gif");
   /** Graphitti letters alphabet with big size (64 pixels width and height) */
   public static final AlphabetGraphitti BIG        = new AlphabetGraphitti(64);
   /** Graphitti letters alphabet with normal size (32 pixels width and height) */
   public static final AlphabetGraphitti NORMAL     = new AlphabetGraphitti(32);
   /** Graphitti letters alphabet with small size (16 pixels width and height) */
   public static final AlphabetGraphitti SMALL      = new AlphabetGraphitti(16);
   /** Graphitti letters alphabet with very big size (128 pixels width and height) */
   public static final AlphabetGraphitti VERY_BIG   = new AlphabetGraphitti(128);
   /** Graphitti letters alphabet with very small size (8 pixels width and height) */
   public static final AlphabetGraphitti VERY_SMALL = new AlphabetGraphitti(8);

   /**
    * Create a new instance of AlphabetGraphitti
    * 
    * @param size
    *           Size of alphabet in pixels
    */
   private AlphabetGraphitti(final int size)
   {
      this(size, size);
   }

   /**
    * Create a new instance of AlphabetGraphitti
    * 
    * @param characterWidth
    *           Character width in pixels
    * @param characterHeight
    *           Character height in pixels
    */
   private AlphabetGraphitti(final int characterWidth, final int characterHeight)
   {
      super(characterWidth, characterHeight, false);
   }

   /**
    * Create image for a letter <br>
    * <br>
    * <b>Parent documentation:</b><br>
    * {@inheritDoc}
    * 
    * @param character
    *           Letter
    * @return Image associated with
    * @see Alphabet#createImageFor(char)
    */
   @Override
   protected JHelpImage createImageFor(final char character)
   {
      if((character < 'A') || (character > 'Z'))
      {
         return null;
      }

      final Dimension dimension = this.getCharacterDimension();

      return JHelpImage.createResizedImage(AlphabetGraphitti.BASE.getImage(character - 'A'), dimension.width, dimension.height);
   }
}