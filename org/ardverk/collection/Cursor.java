/*
 * Copyright 2005-2009 Roger Kapsi, Sam Berlin
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.ardverk.collection;

import java.util.Map;
import java.util.Map.Entry;

/**
 * A {@link org.ardverk.collection.Cursor} can be used to traverse a {@link org.ardverk.collection.Trie}, visit each node
 * step by step and make {@link org.ardverk.collection.Cursor.Decision}s on each step how to continue with
 * traversing the {@link org.ardverk.collection.Trie}.
 */
public interface Cursor<K, V> {
    
    /**
     * The {@link org.ardverk.collection.Cursor.Decision} tells the {@link org.ardverk.collection.Cursor} what to do on each step
     * while traversing the {@link org.ardverk.collection.Trie}.
     * 
     * NOTE: Not all operations that work with a {@link org.ardverk.collection.Cursor} support all
     * {@link org.ardverk.collection.Cursor.Decision} types
     */
    public static enum Decision {
        
        /**
         * Exit the traverse operation
         */
        EXIT, 
        
        /**
         * Continue with the traverse operation
         */
        CONTINUE, 
        
        /**
         * Remove the previously returned element
         * from the {@link org.ardverk.collection.Trie} and continue
         */
        REMOVE, 
        
        /**
         * Remove the previously returned element
         * from the {@link org.ardverk.collection.Trie} and exit from the
         * traverse operation
         */
        REMOVE_AND_EXIT;
    }
    
    /**
     * Called for each {@link java.util.Map.Entry} in the {@link org.ardverk.collection.Trie}. Return
     * {@link org.ardverk.collection.Cursor.Decision#EXIT} to finish the {@link org.ardverk.collection.Trie} operation,
     * {@link org.ardverk.collection.Cursor.Decision#CONTINUE} to go to the next {@link java.util.Map.Entry},
     * {@link org.ardverk.collection.Cursor.Decision#REMOVE} to remove the {@link java.util.Map.Entry} and
     * continue iterating or {@link org.ardverk.collection.Cursor.Decision#REMOVE_AND_EXIT} to
     * remove the {@link java.util.Map.Entry} and stop iterating.
     * 
     * Note: Not all operations support {@link org.ardverk.collection.Cursor.Decision#REMOVE}.
     */
    public Decision select(Entry<? extends K, ? extends V> entry);
}