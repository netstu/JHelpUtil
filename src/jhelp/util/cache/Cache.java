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
package jhelp.util.cache;

import java.util.HashMap;
import java.util.Set;

/**
 * Manage a only RAM cache<br>
 * <br>
 * Last modification : 13 avr. 2010<br>
 * Version 0.0.0<br>
 *
 * @author JHelp
 * @param <ELEMENT>
 *           Element type
 */
public class Cache<ELEMENT>
{
    /** The cache itself */
    private HashMap<String, CacheElement<ELEMENT>> cache;

    /**
     * Constructs Cache
     */
    public Cache()
    {
        this.cache = new HashMap<String, CacheElement<ELEMENT>>();
    }

    /**
     * Destroy the cache (Never use it after that)
     */
    public void destroy()
    {
        this.clear();

        this.cache = null;
    }

    /**
     * Clear the cache
     */
    public void clear()
    {
        CacheElement<ELEMENT> cacheElement;

        for (final String key : this.cache.keySet())
        {
            cacheElement = this.cache.get(key);

            if (cacheElement != null)
            {
                cacheElement.clear();
            }
        }

        this.cache.clear();
    }

    /**
     * Obtain an element and give a default value if key is not already present
     *
     * @param key
     *           Key to get
     * @param cacheElement
     *           Cache element to store and use if the key is not already defined
     * @return The element
     */
    public ELEMENT get(final String key, final CacheElement<ELEMENT> cacheElement)
    {
        final ELEMENT element = this.get(key);

        if (element != null)
        {
            return element;
        }

        this.add(key, cacheElement);

        return this.get(key);
    }

    /**
     * Add element inside the cache
     *
     * @param key
     *           Key associate
     * @param element
     *           Describe how create element
     */
    public void add(final String key, final CacheElement<ELEMENT> element)
    {
        if (key == null)
        {
            throw new NullPointerException("key MUST NOT be null");
        }

        if (element == null)
        {
            throw new NullPointerException("element MUST NOT be null");
        }

        final CacheElement<ELEMENT> previous = this.cache.put(key, element);

        if ((previous != null) && (!previous.equals(element)))
        {
            previous.clear();
        }
    }

    /**
     * Obtain an element
     *
     * @param key
     *           Element key
     * @return Element OR {@code null} if no element attached to given key
     */
    public ELEMENT get(final String key)
    {
        if (key == null)
        {
            throw new NullPointerException("key MUST NOT be null");
        }

        final CacheElement<ELEMENT> cacheElement = this.cache.get(key);

        if (cacheElement != null)
        {
            return cacheElement.getElement();
        }

        return null;
    }

    /**
     * List of keys in cache
     *
     * @return List of keys in cache
     */
    public Set<String> obtainKeys()
    {
        return this.cache.keySet();
    }

    /**
     * Remove an element from cache
     *
     * @param key
     *           Element key
     */
    public void remove(final String key)
    {
        if (key == null)
        {
            throw new NullPointerException("key MUST NOT be null");
        }

        final CacheElement<ELEMENT> cacheElement = this.cache.get(key);

        if (cacheElement != null)
        {
            cacheElement.clear();
            this.cache.remove(key);
        }
    }
}