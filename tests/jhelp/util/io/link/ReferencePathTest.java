package jhelp.util.io.link;

import org.junit.Assert;
import org.junit.Test;

/**
 * Reference path tests
 *
 * @author JHelp <br>
 */
public class ReferencePathTest
{
   /**
    * String representation test
    *
    * @param string
    *           Path to parse
    */
   private void parseTest(final String string)
   {
      Assert.assertEquals(string, ReferencePath.parseReferencePath(string).toString());
   }

   /**
    * Parse tests
    */
   @Test
   public void testParse()
   {
      this.parseTest("apple/pear/fruit");
      this.parseTest("apple/../fruit");
      this.parseTest("apple/../fruit/./pear");
      this.parseTest("/fruit");

      ReferencePath referencePath = ReferencePath.parseReferencePath("apple/pear/fruit");
      Assert.assertEquals("apple", referencePath.getPathName());
      Assert.assertEquals("pear", referencePath.getChild().getPathName());
      Assert.assertEquals("fruit", referencePath.getChild().getChild().getPathName());

      referencePath = ReferencePath.parseReferencePath("/fruit");
      Assert.assertEquals("", referencePath.getPathName());
      Assert.assertEquals("fruit", referencePath.getChild().getPathName());
   }

   /**
    * Simplify tests
    */
   @Test
   public void testSimplify()
   {
      ReferencePath tested = ReferencePath.parseReferencePath("apple/pear/fruit");
      ReferencePath simplified = tested;
      Assert.assertEquals(simplified, ReferencePath.simplify(tested));

      tested = ReferencePath.parseReferencePath("apple/../fruit");
      simplified = ReferencePath.parseReferencePath("fruit");
      Assert.assertEquals(simplified, ReferencePath.simplify(tested));

      tested = ReferencePath.parseReferencePath("apple/./fruit");
      simplified = ReferencePath.parseReferencePath("apple/fruit");
      Assert.assertEquals(simplified, ReferencePath.simplify(tested));

      tested = ReferencePath.parseReferencePath("../fruit");
      simplified = ReferencePath.parseReferencePath("fruit");
      Assert.assertEquals(simplified, ReferencePath.simplify(tested));

      tested = ReferencePath.parseReferencePath(".././../fruit");
      simplified = ReferencePath.parseReferencePath("fruit");
      Assert.assertEquals(simplified, ReferencePath.simplify(tested));

      tested = ReferencePath.parseReferencePath("./fruit");
      simplified = ReferencePath.parseReferencePath("fruit");
      Assert.assertEquals(simplified, ReferencePath.simplify(tested));

      tested = ReferencePath.parseReferencePath("/./fruit");
      simplified = ReferencePath.parseReferencePath("/fruit");
      Assert.assertEquals(simplified, ReferencePath.simplify(tested));
   }
}