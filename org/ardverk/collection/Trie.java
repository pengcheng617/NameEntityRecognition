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
import java.util.SortedMap;

import org.ardverk.collection.Cursor.Decision;

/**
 * Defines the interface for a prefix tree, an ordered tree mmseg structure. For
 * more information, see <a href="http://en.wikipedia.org/wiki/Trie">Tries</a>.
 * 
 * @author Roger Kapsi
 * @author Sam Berlin
 */
public interface Trie<K, V> extends SortedMap<K, V> {

    /**
     * Returns the {@link java.util.Map.Entry} whose key is closest in a bitwise XOR
     * metric to the given key. This is NOT lexicographic closeness.
     * For example, given the keys:
     *
     * <ol>
     * <li>D = 1000100
     * <li>H = 1001000
     * <li>L = 1001100
     * </ol>
     * 
     * If the {@link org.ardverk.collection.Trie} contained 'H' and 'L', a lookup of 'D' would
     * return 'L', because the XOR distance between D &amp; L is smaller 
     * than the XOR distance between D &amp; H. 
     * 
     * @return The {@link java.util.Map.Entry} whose key is closest in a bitwise XOR metric
     * to the provided key.
     */
    public Entry<K, V> select(K key);
    
    /**
     * Returns the key that is closest in a bitwise XOR metric to the 
     * provided key. This is NOT lexicographic closeness!
     * 
     * For example, given the keys:
     * 
     * <ol>
     * <li>D = 1000100
     * <li>H = 1001000
     * <li>L = 1001100
     * </ol>
     * 
     * If the {@link org.ardverk.collection.Trie} contained 'H' and 'L', a lookup of 'D' would
     * return 'L', because the XOR distance between D &amp; L is smaller 
     * than the XOR distance between D &amp; H. 
     * 
     * @return The key that is closest in a bitwise XOR metric to the provided key.
     */
    public K selectKey(K key);
    
    /**
     * Returns the value whose key is closest in a bitwise XOR metric to 
     * the provided key. This is NOT lexicographic closeness!
     * 
     * For example, given the keys:
     * 
     * <ol>
     * <li>D = 1000100
     * <li>H = 1001000
     * <li>L = 1001100
     * </ol>
     * 
     * If the {@link org.ardverk.collection.Trie} contained 'H' and 'L', a lookup of 'D' would
     * return 'L', because the XOR distance between D &amp; L is smaller 
     * than the XOR distance between D &amp; H. 
     * 
     * @return The value whose key is closest in a bitwise XOR metric
     * to the provided key.
     */
    public V selectValue(K key);
    
    /**
     * Iterates through the {@link org.ardverk.collection.Trie}, starting with the entry whose bitwise
     * value is closest in an XOR metric to the given key. After the closest
     * entry is found, the {@link org.ardverk.collection.Trie} will call select on that entry and continue
     * calling select for each entry (traversing in order of XOR closeness,
     * NOT lexicographically) until the cursor returns {@link org.ardverk.collection.Cursor.Decision#EXIT}.
     * 
     * <p>The cursor can return {@link org.ardverk.collection.Cursor.Decision#CONTINUE} to continue traversing.
     * 
     * <p>{@link org.ardverk.collection.Cursor.Decision#REMOVE_AND_EXIT} is used to remove the current element
     * and stop traversing.
     * 
     * <p>Note: The {@link org.ardverk.collection.Cursor.Decision#REMOVE} operation is not supported.
     * 
     * @return The entry the cursor returned {@link org.ardverk.collection.Cursor.Decision#EXIT} on, or null
     * if it continued till the end.
     */
    public Entry<K,V> select(K key, Cursor<? super K, ? super V> cursor);
    
    /**
     * Traverses the {@link org.ardverk.collection.Trie} in lexicographical order.
     * {@link org.ardverk.collection.Cursor#select(java.util.Map.Entry)} will be called on each entry.
     * 
     * <p>The traversal will stop when the cursor returns {@link org.ardverk.collection.Cursor.Decision#EXIT},
     * {@link org.ardverk.collection.Cursor.Decision#CONTINUE} is used to continue traversing and
     * {@link org.ardverk.collection.Cursor.Decision#REMOVE} is used to remove the element that was selected
     * and continue traversing.
     * 
     * <p>{@link org.ardverk.collection.Cursor.Decision#REMOVE_AND_EXIT} is used to remove the current element
     * and stop traversing.
     *   
     * @return The entry the cursor returned {@link org.ardverk.collection.Cursor.Decision#EXIT} on, or null
     * if it continued till the end.
     */
    public Entry<K,V> traverse(Cursor<? super K, ? super V> cursor);
    
    /**
     * Returns a view of this {@link SortedTrie} of all elements that are prefixed 
     * by the given key.
     * 
     * <p>In a {@link SortedTrie} with fixed size keys, this is essentially a 
     * {@link #get(Object)} operation.
     * 
     * <p>For example, if the {@link SortedTrie} contains 'Anna', 'Anael', 
     * 'Analu', 'Andreas', 'Andrea', 'Andres', and 'Anatole', then
     * a lookup of 'And' would return 'Andreas', 'Andrea', and 'Andres'.
     */
    public SortedMap<K, V> getPrefixedBy(K key);
    
    /**
     * Returns a view of this {@link SortedTrie} of all elements that are prefixed 
     * by the length of the key.
     * 
     * <p>{@link SortedTrie}s with fixed size keys will not support this operation 
     * (because all keys are the same length).
     * 
     * <p>For example, if the {@link SortedTrie} contains 'Anna', 'Anael', 'Analu', 
     * 'Andreas', 'Andrea', 'Andres', and 'Anatole', then a lookup for 'Andrey' 
     * and a length of 4 would return 'Andreas', 'Andrea', and 'Andres'.
     */
    public SortedMap<K, V> getPrefixedBy(K key, int length);
    
    /**
     * Returns a view of this {@link SortedTrie} of all elements that are prefixed
     * by the key, starting at the given offset and for the given length.
     * 
     * <p>{@link SortedTrie}s with fixed size keys will not support this operation 
     * (because all keys are the same length).
     * 
     * <p>For example, if the {@link SortedTrie} contains 'Anna', 'Anael', 'Analu', 
     * 'Andreas', 'Andrea', 'Andres', and 'Anatole', then a lookup for 
     * 'Hello Andrey Smith', an offset of 6 and a length of 4 would return 
     * 'Andreas', 'Andrea', and 'Andres'.
     */
    public SortedMap<K, V> getPrefixedBy(K key, int offset, int length);
    
    /**
     * Returns a view of this {@link SortedTrie} of all elements that are prefixed
     * by the number of bits in the given Key.
     * 
     * <p>In {@link SortedTrie}s with fixed size keys like IP addresses this method
     * can be used to lookup partial keys. That is you can lookup all addresses
     * that begin with '192.168' by providing the key '192.168.X.X' and a 
     * length of 16.
     */
    public SortedMap<K, V> getPrefixedByBits(K key, int lengthInBits);
    
    /**
     * Returns a view of this {@link SortedTrie} of all elements that are prefixed
     * by the number of bits in the given Key.
     */
    public SortedMap<K, V> getPrefixedByBits(K key,
                                             int offsetInBits, int lengthInBits);
}
