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
package jhelp.util.io.base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import jhelp.util.debug.Debug;
import jhelp.util.io.ByteArray;
import jhelp.util.io.UtilIO;

/**
 * Common operation/constants in Base64
 * 
 * @author JHelp
 */
public final class Base64Common
{
   /** Complement for ending */
   public static final int COMPLEMENT = '=' & 0xFF;

   /**
    * Transform a base 64 representation to a byte array.<br>
    * {@link #toBase64(byte[])} to revert the operation
    * 
    * @param base64
    *           Base 64 representation
    * @return Byte array
    */
   public static byte[] fromBase64(final String base64)
   {
      try
      {
         final ByteArrayInputStream stringInputStream = new ByteArrayInputStream(base64.getBytes("UTF-8"));
         final Base64InputStream base64InputStream = new Base64InputStream(stringInputStream);
         final ByteArray byteArray = new ByteArray();

         final byte[] temp = new byte[UtilIO.BUFFER_SIZE];
         int read = base64InputStream.read(temp);

         while(read >= 0)
         {
            byteArray.write(temp, 0, read);

            read = base64InputStream.read(temp);
         }

         base64InputStream.close();

         return byteArray.toArray();
      }
      catch(final Exception exception)
      {
         Debug.printException(exception, "Issue while read base 64");

         return null;
      }
   }

   /**
    * Index of a symbol
    * 
    * @param symbol
    *           Symbol search
    * @return Symbol index
    */
   public static int getIndex(final int symbol)
   {
      final char c = (char) symbol;

      if((c >= 'A') && (c <= 'Z'))
      {
         return c - 'A';
      }

      if((c >= 'a') && (c <= 'z'))
      {
         return (c - 'a') + 26;
      }

      if((c >= '0') && (c <= '9'))
      {
         return (c - '0') + 52;
      }

      if(c == '+')
      {
         return 62;
      }

      if(c == '/')
      {
         return 63;
      }

      return -1;
   }

   /**
    * Obtain a symbol
    * 
    * @param index
    *           Symbol index
    * @return The symbol
    */
   public static int getSymbol(final int index)
   {
      if((index >= 0) && (index < 26))
      {
         return ('A' + index) & 0xFF;
      }

      if((index >= 26) && (index < 52))
      {
         return (('a' + index) - 26) & 0xFF;
      }

      if((index >= 52) && (index < 62))
      {
         return (('0' + index) - 52) & 0xFF;
      }

      if(index == 62)
      {
         return '+' & 0xFF;
      }

      if(index == 63)
      {
         return '/' & 0xFF;
      }

      return (byte) 0xFF;
   }

   /**
    * Convert a byte array to its base64 representation.<br>
    * {@link #fromBase64(String)} to revert the operation
    * 
    * @param array
    *           Array to convert
    * @return Base 64 representation
    */
   public static String toBase64(final byte[] array)
   {
      try
      {
         final ByteArrayOutputStream stringOutputStream = new ByteArrayOutputStream();
         final Base64OutputStream base64OutputStream = new Base64OutputStream(stringOutputStream);

         base64OutputStream.write(array);
         base64OutputStream.flush();
         base64OutputStream.close();

         return new String(stringOutputStream.toByteArray(), "UTF-8");
      }
      catch(final Exception exception)
      {
         Debug.printException(exception, "Issue while convert to base 64");

         return null;
      }
   }
}