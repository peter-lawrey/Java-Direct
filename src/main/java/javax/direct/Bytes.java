/*
 * Copyright 2013 Peter Lawrey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.direct;

import java.nio.*;

/**
 * A container for data of a specific primitive type.
 * <p></p>
 * <p> A buffer is a linear, finite sequence of elements of bytes.
 * Aside from its content, the essential properties of a
 * buffer are its capacity, limit, and position: </p>
 * <p></p>
 * <blockquote>
 * <p></p>
 * <p> A buffer's <i>capacity</i> is the number of elements it contains.  The
 * capacity of a buffer is never negative and never changes.  </p>
 * <p></p>
 * <p> A buffer's <i>limit</i> is the index of the first element that should
 * not be read or written.  A buffer's limit is never negative and is never
 * greater than its capacity.  </p>
 * <p></p>
 * <p> A buffer's <i>position</i> is the index of the next element to be
 * read or written.  A buffer's position is never negative and is never
 * greater than its limit.  </p>
 * <p></p>
 * </blockquote>
 * <p></p>
 * <h4> Deterministic freeing of underlying storage </h4>
 * <p>When <i>freeOnGC</i> is false, the underlying storage can be freed or unmapped when the reference count reaches zero.</p>
 * <p>Whether this flag is enabled or not, it is illegal to get or put this Bytes object after the reference count reaches zero.</p>
 * <p></p>
 * <p>This type supports all primitive and primitive array types, but not nested array types</p>
 * <p></p>
 * <p></p>
 * <h4> Transferring data </h4>
 * <p></p>
 * <p>Bytes comes in two favours, one with full bounds checking and a second without bounds checking.
 * Choosing to drop bounds checking is treated as a hint and is not appropiate in some environments.</p>
 * <p></p>
 * <blockquote>
 * <p></p>
 * <p> <i>Relative</i> operations read or write one or more elements starting
 * at the current position and then increment the position by the number of
 * elements transferred.  If the requested transfer exceeds the limit then a
 * relative <i>get</i> operation throws a {@link java.nio.BufferUnderflowException}
 * and a relative <i>put</i> operation throws a {@link
 * java.nio.BufferOverflowException}; in either case, no data is transferred.  </p>
 * <p></p>
 * <p> <i>Absolute</i> operations take an explicit element index and do not
 * affect the position.  Absolute <i>get</i> and <i>put</i> operations throw
 * an {@link IndexOutOfBoundsException} if the index argument exceeds the
 * limit.  </p>
 * <p></p>
 * </blockquote>
 * <p></p>
 * <p> Data may also, of course, be transferred in to or out of a buffer by the
 * I/O operations of an appropriate channel, which are always relative to the
 * current position.
 * <p></p>
 * <p></p>
 * <h4> Marking and resetting </h4>
 * <p></p>
 * <p> A buffer's <i>mark</i> is the index to which its position will be reset
 * when the {@link #reset reset} method is invoked.  The mark is not always
 * defined, but when it is defined it is never negative and is never greater
 * than the position.  If the mark is defined then it is discarded when the
 * position or the limit is adjusted to a value smaller than the mark.  If the
 * mark is not defined then invoking the {@link #reset reset} method causes an
 * {@link java.nio.InvalidMarkException} to be thrown.
 * <p></p>
 * <p></p>
 * <h4> Invariants </h4>
 * <p></p>
 * <p> The following invariant holds for the mark, position, limit, and
 * capacity values:
 * <p></p>
 * <blockquote>
 * <tt>0</tt> <tt>&lt;=</tt>
 * <i>mark</i> <tt>&lt;=</tt>
 * <i>position</i> <tt>&lt;=</tt>
 * <i>limit</i> <tt>&lt;=</tt>
 * <i>capacity</i>
 * </blockquote>
 * <p></p>
 * <p> A newly-created buffer always has a position of zero and a mark that is
 * undefined.  The initial limit may be zero, or it may be some other value
 * that depends upon the type of the buffer and the manner in which it is
 * constructed.  By default each element of a newly-allocated buffer is initialized
 * to zero.  A BytesFactory may choose to leave the initial state undefined.
 * <p></p>
 * <p></p>
 * <h4> Clearing, flipping, and rewinding </h4>
 * <p></p>
 * <p> In addition to methods for accessing the position, limit, and capacity
 * values and for marking and resetting, this class also defines the following
 * operations upon buffers:
 * <p></p>
 * <ul>
 * <p></p>
 * <li><p> {@link #clear} makes a buffer ready for a new sequence of
 * channel-read or relative <i>put</i> operations: It sets the limit to the
 * capacity and the position to zero.  </p></li>
 * <p></p>
 * <li><p> {@link #flip} makes a buffer ready for a new sequence of
 * channel-write or relative <i>get</i> operations: It sets the limit to the
 * current position and then sets the position to zero.  </p></li>
 * <p></p>
 * <li><p> {@link #rewind} makes a buffer ready for re-reading the data that
 * it already contains: It leaves the limit unchanged and sets the position
 * to zero.  </p></li>
 * <p></p>
 * </ul>
 * <p></p>
 * <p></p>
 * <h4> Thread safety </h4>
 * <p></p>
 * <p> Bytes are not safe for use by multiple concurrent threads.
 * However, the contents of the Bytes can be shared across thread or processes
 * if the appropriate memory barriers and thread safe operations are used.
 * If a Bytes instance is to be used by more than one thread then access to the buffer
 * should be controlled by appropriate synchronization.
 * <p></p>
 * <p></p>
 * <h4> Invocation chaining </h4>
 * <p></p>
 * <p> Methods in this class that do not otherwise have a value to return are
 * specified to return the buffer upon which they are invoked.  This allows
 * method invocations to be chained; for example, the sequence of statements
 * <p></p>
 * <blockquote><pre>
 * b.flip();
 * b.position(23);
 * b.limit(42);</pre></blockquote>
 * <p></p>
 * can be replaced by the single, more compact statement
 * <p></p>
 * <blockquote><pre>
 * b.flip().position(23).limit(42);</pre></blockquote>
 *
 * @author Peter Lawrey
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.9
 */

public interface Bytes extends ReferenceCounted {
    /**
     * Returns this buffer's capacity. </p>
     *
     * @return The capacity of this buffer
     */
    public long capacity();

    /**
     * Returns this buffer's position. </p>
     *
     * @return The position of this buffer
     */
    public long position();

    /**
     * Sets this buffer's position.  If the mark is defined and larger than the
     * new position then it is discarded. </p>
     *
     * @param newPosition The new position value; must be non-negative
     *                    and no larger than the current limit
     * @return This buffer
     * @throws IllegalArgumentException If the preconditions on <tt>newPosition</tt> do not hold
     */
    public Bytes position(long newPosition);

    /**
     * Returns this buffer's limit. </p>
     *
     * @return The limit of this buffer
     */
    public int limit();

    /**
     * Sets this buffer's limit.  If the position is larger than the new limit
     * then it is set to the new limit.  If the mark is defined and larger than
     * the new limit then it is discarded. </p>
     *
     * @param newLimit The new limit value; must be non-negative
     *                 and no larger than this buffer's capacity
     * @return This buffer
     * @throws IllegalArgumentException If the preconditions on <tt>newLimit</tt> do not hold
     */
    public Bytes limit(long newLimit);

    /**
     * Sets this buffer's mark at its position. </p>
     *
     * @return This buffer
     */
    public Bytes mark();

    /**
     * Resets this buffer's position to the previously-marked position.
     * <p></p>
     * <p> Invoking this method neither changes nor discards the mark's
     * value. </p>
     *
     * @return This buffer
     * @throws java.nio.InvalidMarkException If the mark has not been set
     */
    public Bytes reset();

    /**
     * Clears this buffer.  The position is set to zero, the limit is set to
     * the capacity, and the mark is discarded.
     * <p></p>
     * <p> Invoke this method before using a sequence of channel-read or
     * <i>put</i> operations to fill this buffer.  For example:
     * <p></p>
     * <blockquote><pre>
     * buf.clear();     // Prepare buffer for reading
     * in.read(buf);    // Read data</pre></blockquote>
     * <p></p>
     * <p> This method does not actually erase the data in the buffer, but it
     * is named as if it did because it will most often be used in situations
     * in which that might as well be the case. </p>
     *
     * @return This buffer
     */
    public Bytes clear();

    /**
     * Flips this buffer.  The limit is set to the current position and then
     * the position is set to zero.  If the mark is defined then it is
     * discarded.
     * <p></p>
     * <p> After a sequence of channel-read or <i>put</i> operations, invoke
     * this method to prepare for a sequence of channel-write or relative
     * <i>get</i> operations.  For example:
     * <p></p>
     * <blockquote><pre>
     * buf.put(magic);    // Prepend header
     * in.read(buf);      // Read data into rest of buffer
     * buf.flip();        // Flip buffer
     * out.write(buf);    // Write header + data to channel</pre></blockquote>
     * <p></p>
     * <p> This method is often used in conjunction with the {@link
     * java.nio.ByteBuffer#compact compact} method when transferring data from
     * one place to another.  </p>
     *
     * @return This buffer
     */
    public Bytes flip();

    /**
     * Rewinds this buffer.  The position is set to zero and the mark is
     * discarded.
     * <p></p>
     * <p> Invoke this method before a sequence of channel-write or <i>get</i>
     * operations, assuming that the limit has already been set
     * appropriately.  For example:
     * <p></p>
     * <blockquote><pre>
     * out.write(buf);    // Write remaining data
     * buf.rewind();      // Rewind buffer
     * buf.get(array);    // Copy data into array</pre></blockquote>
     *
     * @return This buffer
     */
    public Bytes rewind();

    /**
     * Returns the number of elements between the current position and the
     * limit. </p>
     *
     * @return The number of elements remaining in this buffer
     */
    public long remaining();

    /**
     * Tells whether there are any elements between the current position and
     * the limit. </p>
     *
     * @return <tt>true</tt> if, and only if, there is at least one element
     * remaining in this buffer
     */
    public boolean hasRemaining();

    /**
     * Tells whether or not this buffer is backed by an accessible
     * array.
     * <p></p>
     * <p> If this method returns <tt>true</tt> then the {@link #array() array}
     * and {@link #arrayOffset() arrayOffset} methods may safely be invoked.
     * </p>
     *
     * @return <tt>true</tt> if, and only if, this buffer
     * is backed by an array
     */
    public boolean hasArray();

    /**
     * Returns the array that backs this
     * buffer.
     * <p></p>
     * <p> This method is intended to allow array-backed buffers to be
     * passed to native code more efficiently. Concrete subclasses
     * provide more strongly-typed return values for this method.
     * <p></p>
     * <p> Modifications to this buffer's content will cause the returned
     * array's content to be modified, and vice versa.
     * <p></p>
     * <p> Invoke the {@link #hasArray hasArray} method before invoking this
     * method in order to ensure that this buffer has an accessible backing
     * array.  </p>
     *
     * @return The array that backs this buffer
     * @throws UnsupportedOperationException If this buffer is not backed by an accessible array
     */
    byte[] array();

    /**
     * Returns the offset within this buffer's backing array of the first
     * element of the buffer.
     * <p></p>
     * <p> If this buffer is backed by an array then buffer position <i>p</i>
     * corresponds to array index <i>p</i>&nbsp;+&nbsp;<tt>arrayOffset()</tt>.
     * <p></p>
     * <p> Invoke the {@link #hasArray hasArray} method before invoking this
     * method in order to ensure that this buffer has an accessible backing
     * array.  </p>
     *
     * @return The offset within this buffer's array
     * of the first element of the buffer
     * @throws UnsupportedOperationException If this buffer is not backed by an accessible array
     */
    int arrayOffset();

    /**
     * Tells whether or not this buffer is
     * <a href="ByteBuffer.html#direct"><i>direct</i></a>. </p>
     *
     * @return <tt>true</tt> if, and only if, this buffer is direct
     */
    boolean isDirect();

    /**
     * Creates a new byte buffer whose content is a shared subsequence of
     * this buffer's content.
     * <p></p>
     * <p> The content of the new buffer will start at this buffer's current
     * position.  Changes to this buffer's content will be visible in the new
     * buffer, and vice versa; the two buffers' position, limit, and mark
     * values will be independent.
     * <p></p>
     * <p> The new buffer's position will be zero, its capacity and its limit
     * will be the number of bytes remaining in this buffer, and its mark
     * will be undefined.  The new buffer will be direct if, and only if, this
     * buffer is direct, and it will be read-only if, and only if, this buffer
     * is read-only.  </p>
     *
     * @return The new byte buffer
     */
    Bytes slice();

    /**
     * Moves the Bytes provided to be a shared subsequence of this buffer's content.
     * <p></p>
     * <p> The content of the buffer will start at this buffer's current
     * position.  Changes to this buffer's content will be visible in the new
     * buffer, and vice versa; the two buffers' position, limit, and mark
     * values will be independent.
     * <p></p>
     * <p> The buffer's position will be zero, its capacity and its limit
     * will be the number of bytes remaining in this buffer, and its mark
     * will be undefined.  The new buffer will be direct if, and only if, this
     * buffer is direct</p>
     *
     * @param bytes to re-assign.
     * @return The byte buffer
     */
    Bytes slice(Bytes bytes);

    /**
     * Creates a new byte buffer that shares this buffer's content.
     * <p></p>
     * <p> The content of the new buffer will be that of this buffer.  Changes
     * to this buffer's content will be visible in the new buffer, and vice
     * versa; the two buffers' position, limit, and mark values will be
     * independent.
     * <p></p>
     * <p> The new buffer's capacity, limit, position, and mark values will be
     * identical to those of this buffer.  The new buffer will be direct if,
     * and only if, this buffer is direct, and it will be read-only if, and
     * only if, this buffer is read-only.  </p>
     *
     * @return The new byte buffer
     */
    Bytes duplicate();

    /**
     * Compacts this buffer.
     * <p></p>
     * <p> The bytes between the buffer's current position and its limit,
     * if any, are copied to the beginning of the buffer.  That is, the
     * byte at index <i>p</i>&nbsp;=&nbsp;<tt>position()</tt> is copied
     * to index zero, the byte at index <i>p</i>&nbsp;+&nbsp;1 is copied
     * to index one, and so forth until the byte at index
     * <tt>limit()</tt>&nbsp;-&nbsp;1 is copied to index
     * <i>n</i>&nbsp;=&nbsp;<tt>limit()</tt>&nbsp;-&nbsp;<tt>1</tt>&nbsp;-&nbsp;<i>p</i>.
     * The buffer's position is then set to <i>n+1</i> and its limit is set to
     * its capacity.  The mark, if defined, is discarded.
     * <p></p>
     * <p> The buffer's position is set to the number of bytes copied,
     * rather than to zero, so that an invocation of this method can be
     * followed immediately by an invocation of another relative <i>put</i>
     * method. </p>
     * <p></p>
     * <p></p>
     * <p></p>
     * <p> Invoke this method after writing data from a buffer in case the
     * write was incomplete.  The following loop, for example, copies bytes
     * from one channel to another via the buffer <tt>buf</tt>:
     * <p></p>
     * <blockquote><pre>
     * buf.clear();          // Prepare buffer for use
     * while (in.read(buf) >= 0 || buf.position != 0) {
     *     buf.flip();
     *     out.write(buf);
     *     buf.compact();    // In case of partial write
     * }</pre></blockquote>
     *
     * @return This buffer
     */
    Bytes compact();

    /**
     * <p>Returns a string summarizing the state of this buffer.  </p>
     *
     * @return A summary string
     */
    public String toString();

    /**
     * Returns the current hash code of this buffer.
     * <p></p>
     * <p> The hash code of a byte buffer depends only upon its remaining
     * elements; that is, upon the elements from <tt>position()</tt> up to, and
     * including, the element at <tt>limit()</tt>&nbsp;-&nbsp;<tt>1</tt>.
     * <p></p>
     * <p> Because buffer hash codes are content-dependent, it is inadvisable
     * to use buffers as keys in hash maps or similar data structures unless it
     * is known that their contents will not change.  </p>
     *
     * @return The current hash code of this buffer
     */
    public int hashCode();

    /**
     * Returns the current hash code of this buffer as a long.
     * <p></p>
     * <p> The hash code of a byte buffer depends only upon its remaining
     * elements; that is, upon the elements from <tt>position()</tt> up to, and
     * including, the element at <tt>limit()</tt>&nbsp;-&nbsp;<tt>1</tt>.
     * <p></p>
     * <p> Because buffer hash codes are content-dependent, it is inadvisable
     * to use buffers as keys in hash maps or similar data structures unless it
     * is known that their contents will not change.  </p>
     *
     * @return The current long hash code of this buffer
     */
    public long longHashCode();

    /**
     * Tells whether or not this buffer is equal to another object.
     * <p></p>
     * <p> Two byte buffers are equal if, and only if,
     * <p></p>
     * <p><ol>
     * <li><p> They have the same element type,  </p></li>
     * <li><p> They have the same number of remaining elements, and</p></li>
     * <li><p> The two sequences of remaining elements, considered
     * independently of their starting positions, are pointwise equal.</p></li>
     * </ol>
     * <p></p>
     * <p> A bytes is not equal to any other type of object.  </p>
     *
     * @param ob The object to which this buffer is to be compared
     * @return <tt>true</tt> if, and only if, this buffer is equal to the
     * given object
     */
    public boolean equals(Object ob);

    /**
     * Retrieves this buffer's byte order.
     * <p></p>
     * <p> The byte order is used when reading or writing multibyte values, and
     * when creating buffers that are views of this byte buffer.  The order of
     * a newly-created byte buffer is always {@link ByteOrder#BIG_ENDIAN
     * BIG_ENDIAN}.  </p>
     *
     * @return This buffer's byte order
     */
    ByteOrder order();

    /**
     * Modifies this buffer's byte order.  </p>
     *
     * @param bo The new byte order,
     *           either {@link ByteOrder#BIG_ENDIAN BIG_ENDIAN}
     *           or {@link ByteOrder#LITTLE_ENDIAN LITTLE_ENDIAN}
     * @return This buffer
     */
    ByteBuffer order(ByteOrder bo);

    /**
     * Relative <i>getBoolean</i> method.  Reads the boolean at this buffer's
     * current position, and then increments the position. </p>
     *
     * @return The boolean at the buffer's current position
     * @throws BufferUnderflowException If the buffer's current position is not smaller than its limit
     */
    boolean getBoolean();

    /**
     * Relative <i>putBoolean</i> method.
     * <p></p>
     * <p> Writes the given boolean into this buffer at the current
     * position, and then increments the position. </p>
     *
     * @param b The boolean to be written
     * @return This buffer
     * @throws BufferOverflowException If this buffer's current position is not smaller than its limit
     */
    Bytes putBoolean(boolean b);

    /**
     * Absolute <i>getBoolean</i> method.  Reads the boolean at the given
     * index. </p>
     *
     * @param index The index from which the boolean will be read
     * @return The boolean at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit
     */
    boolean getBoolean(long index);

    /**
     * Absolute <i>putBoolean</i> method.
     * <p></p>
     * <p> Writes the given boolean into this buffer at the given
     * index. </p>
     *
     * @param index The index at which the boolean will be written
     * @param b     The boolean value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException        If <tt>index</tt> is negative
     *                                          or not smaller than the buffer's limit
     */
    Bytes putBoolean(long index, boolean b);

    /**
     * Relative bulk <i>get</i> method.
     * <p></p>
     * <p> This method transfers booleans from this buffer into the given
     * destination array.  If there are fewer booleans remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * booleans are transferred and a {@link BufferUnderflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> booleans from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.get(); </pre>
     *
     * except that it first checks that there are sufficient booleans in
     * this buffer and it is potentially much more efficient. </p>
     *
     * @param dst    The array into which booleans are to be written
     * @param offset The offset within the array of the first boolean to be
     *               written; must be non-negative and no larger than
     *               <tt>dst.length</tt>
     * @param length The maximum number of booleans to be written to the given
     *               array; must be non-negative and no larger than
     *               <tt>dst.length - offset</tt>
     * @return This buffer
     * @throws BufferUnderflowException  If there are fewer than <tt>length</tt> booleans
     *                                   remaining in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes get(boolean[] dst, int offset, int length);

    /**
     * Relative bulk <i>get</i> method.
     * <p></p>
     * <p> This method transfers booleans from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.get(a)</tt> behaves in exactly the same way as the invocation
     * <p></p>
     * <pre>
     *     src.get(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferUnderflowException If there are fewer than <tt>length</tt> booleans
     *                                  remaining in this buffer
     */
    Bytes get(boolean[] dst);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers booleans into this buffer from the given
     * source array.  If there are more booleans to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * booleans are transferred and a {@link BufferOverflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> booleans from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.put(a[i]); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src    The array from which booleans are to be read
     * @param offset The offset within the array of the first boolean to be read;
     *               must be non-negative and no larger than <tt>array.length</tt>
     * @param length The number of booleans to be read from the given array;
     *               must be non-negative and no larger than
     *               <tt>array.length - offset</tt>
     * @return This buffer
     * @throws BufferOverflowException   If there is insufficient space in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes put(boolean[] src, int offset, int length);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the entire content of the given source
     * boolean array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     * <p></p>
     * <pre>
     *     dst.put(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferOverflowException If there is insufficient space in this buffer
     */
    Bytes put(boolean[] src);

    /**
     * Absolute <i>getBoolean</i> method.  Reads the boolean at the given
     * index. </p>
     *
     * @param bitIndex The index from which the boolean will be read
     * @return The boolean at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit
     */
    boolean getBit(long bitIndex);

    /**
     * Absolute <i>putBoolean</i> method.
     * <p></p>
     * <p> Writes the given boolean into this buffer at the given
     * index. </p>
     *
     * @param bitIndex The index at which the boolean will be written
     * @param b     The boolean value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException        If <tt>index</tt> is negative
     *                                          or not smaller than the buffer's limit
     */
    Bytes putBit(long bitIndex, boolean b);

    /**
     * Relative bulk <i>get</i> method.
     * <p></p>
     * <p> This method transfers booleans from this buffer into the given
     * destination array.  If there are fewer booleans remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * booleans are transferred and a {@link BufferUnderflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> booleans from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre> // TODO !!
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.get(); </pre>
     *
     * except that it first checks that there are sufficient booleans in
     * this buffer and it is potentially much more efficient. </p>
     *
     * @param dst    The array into which booleans are to be written
     * @param offset The offset within the array of the first boolean to be
     *               written; must be non-negative and no larger than
     *               <tt>dst.length</tt>
     * @param length The maximum number of booleans to be written to the given
     *               array; must be non-negative and no larger than
     *               <tt>dst.length - offset</tt>
     * @return This buffer
     * @throws BufferUnderflowException  If there are fewer than <tt>length</tt> booleans
     *                                   remaining in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes getBits(boolean[] dst, int offset, int length);

    /**
     * Relative bulk <i>get</i> method.
     * <p></p>
     * <p> This method transfers booleans from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.get(a)</tt> behaves in exactly the same way as the invocation
     * <p></p>
     * <pre>
     *     src.getBits(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferUnderflowException If there are fewer than <tt>length</tt> booleans
     *                                  remaining in this buffer
     */
    Bytes getBits(boolean[] dst);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers booleans into this buffer from the given
     * source array.  If there are more booleans to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * booleans are transferred and a {@link BufferOverflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> booleans from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.put(a[i]); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src    The array from which booleans are to be read
     * @param offset The offset within the array of the first boolean to be read;
     *               must be non-negative and no larger than <tt>array.length</tt>
     * @param length The number of booleans to be read from the given array;
     *               must be non-negative and no larger than
     *               <tt>array.length - offset</tt>
     * @return This buffer
     * @throws BufferOverflowException   If there is insufficient space in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes putBits(boolean[] src, int offset, int length);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the entire content of the given source
     * boolean array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     * <p></p>
     * <pre>
     *     dst.putBits(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferOverflowException If there is insufficient space in this buffer
     */
    Bytes putBits(boolean[] src);

    /**
     * Relative <i>getByte</i> method.  Reads the byte at this buffer's
     * current position, and then increments the position. </p>
     *
     * @return The byte at the buffer's current position
     * @throws BufferUnderflowException If the buffer's current position is not smaller than its limit
     */
    byte getByte();

    /**
     * Relative <i>putByte</i> method.
     * <p></p>
     * <p> Writes the given byte into this buffer at the current
     * position, and then increments the position. </p>
     *
     * @param b The byte to be written
     * @return This buffer
     * @throws BufferOverflowException If this buffer's current position is not smaller than its limit
     */
    Bytes putByte(byte b);

    /**
     * Absolute <i>getByte</i> method.  Reads the byte at the given
     * index. </p>
     *
     * @param index The index from which the byte will be read
     * @return The byte at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit
     */
    byte getByte(long index);

    /**
     * Absolute <i>putByte</i> method.
     * <p></p>
     * <p> Writes the given byte into this buffer at the given
     * index. </p>
     *
     * @param index The index at which the byte will be written
     * @param b     The byte value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException        If <tt>index</tt> is negative
     *                                          or not smaller than the buffer's limit
     */
    Bytes putByte(long index, byte b);


    /**
     * Relative <i>getUnsignedByte</i> method.  Reads the unsigned byte at this buffer's
     * current position, and then increments the position. </p>
     *
     * @return The unsigned byte at the buffer's current position
     * @throws BufferUnderflowException If the buffer's current position is not smaller than its limit
     */
    int getUnsignedByte();

    /**
     * Relative <i>putUnsignedByte</i> method.
     * <p></p>
     * <p> Writes the given unsigned byte into this buffer at the current
     * position, and then increments the position by one. </p>
     *
     * @param b The unsigned byte to be written
     * @return This buffer
     * @throws BufferOverflowException If this buffer's current position is not smaller than its limit
     */
    Bytes putUnsignedByte(int b);

    /**
     * Absolute <i>getUnsignedByte</i> method.  Reads the byte at the given
     * index. </p>
     *
     * @param index The index from which the byte will be read
     * @return The unsigned byte at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit
     */
    int getUnsignedByte(long index);

    /**
     * Absolute <i>putUnsignedByte</i> method.
     * <p></p>
     * <p> Writes the given byte into this buffer at the given
     * index. </p>
     *
     * @param index The index at which the byte will be written
     * @param b     The unsigned byte value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException        If <tt>index</tt> is negative
     *                                          or not smaller than the buffer's limit
     */
    Bytes putUnsignedByte(long index, int b);

    /**
     * Relative bulk <i>get</i> method.
     * <p></p>
     * <p> This method transfers bytes from this buffer into the given
     * destination array.  If there are fewer bytes remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * bytes are transferred and a {@link BufferUnderflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> bytes from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.get(); </pre>
     *
     * except that it first checks that there are sufficient bytes in
     * this buffer and it is potentially much more efficient. </p>
     *
     * @param dst    The array into which bytes are to be written
     * @param offset The offset within the array of the first byte to be
     *               written; must be non-negative and no larger than
     *               <tt>dst.length</tt>
     * @param length The maximum number of bytes to be written to the given
     *               array; must be non-negative and no larger than
     *               <tt>dst.length - offset</tt>
     * @return This buffer
     * @throws BufferUnderflowException  If there are fewer than <tt>length</tt> bytes
     *                                   remaining in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes get(byte[] dst, int offset, int length);

    /**
     * Relative bulk <i>get</i> method.
     * <p></p>
     * <p> This method transfers bytes from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.get(a)</tt> behaves in exactly the same way as the invocation
     * <p></p>
     * <pre>
     *     src.get(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferUnderflowException If there are fewer than <tt>length</tt> bytes
     *                                  remaining in this buffer
     */
    Bytes get(byte[] dst);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the bytes remaining in the given source
     * buffer into this buffer.  If there are more bytes remaining in the
     * source buffer than in this buffer, that is, if
     * <tt>src.remaining()</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no bytes are transferred and a {@link
     * BufferOverflowException} is thrown.
     * <p></p>
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>src.remaining()</tt> bytes from the given
     * buffer into this buffer, starting at each buffer's current position.
     * The positions of both buffers are then incremented by <i>n</i>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src)</tt> has exactly the same effect as the loop
     * <p></p>
     * <pre>
     *     while (src.hasRemaining())
     *         dst.put(src.get()); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src The source buffer from which bytes are to be read;
     *            must not be this buffer
     * @return This buffer
     * @throws BufferOverflowException  If there is insufficient space in this buffer
     *                                  for the remaining bytes in the source buffer
     * @throws IllegalArgumentException If the source buffer is this buffer
     */
    Bytes put(ByteBuffer src);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the bytes remaining in the given source
     * buffer into this buffer.  If there are more bytes remaining in the
     * source buffer than in this buffer, that is, if
     * <tt>src.remaining()</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no bytes are transferred and a {@link
     * BufferOverflowException} is thrown.
     * <p></p>
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>src.remaining()</tt> bytes from the given
     * buffer into this buffer, starting at each buffer's current position.
     * The positions of both buffers are then incremented by <i>n</i>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src)</tt> has exactly the same effect as the loop
     * <p></p>
     * <pre>
     *     while (src.hasRemaining())
     *         dst.put(src.get()); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src The source buffer from which bytes are to be read;
     *            must not be this buffer
     * @return This buffer
     * @throws BufferOverflowException  If there is insufficient space in this buffer
     *                                  for the remaining bytes in the source buffer
     * @throws IllegalArgumentException If the source buffer is this buffer
     */
    Bytes put(Bytes src);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers bytes into this buffer from the given
     * source array.  If there are more bytes to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * bytes are transferred and a {@link BufferOverflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> bytes from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.put(a[i]); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src    The array from which bytes are to be read
     * @param offset The offset within the array of the first byte to be read;
     *               must be non-negative and no larger than <tt>array.length</tt>
     * @param length The number of bytes to be read from the given array;
     *               must be non-negative and no larger than
     *               <tt>array.length - offset</tt>
     * @return This buffer
     * @throws BufferOverflowException   If there is insufficient space in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes put(byte[] src, int offset, int length);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the entire content of the given source
     * byte array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     * <p></p>
     * <pre>
     *     dst.put(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferOverflowException If there is insufficient space in this buffer
     */
    Bytes put(byte[] src);

    /**
     * Relative <i>getChar</i> method for reading a char value.
     * <p></p>
     * <p> Reads the next two bytes at this buffer's current position,
     * composing them into a char value according to the current byte order,
     * and then increments the position by two.  </p>
     *
     * @return The char value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than two bytes
     *                                  remaining in this buffer
     */
    char getChar();

    /**
     * Relative <i>putChar</i> method for writing a char
     * value.
     * <p></p>
     * <p> Writes two bytes containing the given char value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by two.  </p>
     *
     * @param value The char value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than two bytes
     *                                 remaining in this buffer
     */
    Bytes putChar(char value);

    /**
     * Absolute <i>getChar</i> method for reading a char value.
     * <p></p>
     * <p> Reads two bytes at the given index, composing them into a
     * char value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The char value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus one
     */
    char getChar(long index);

    /**
     * Absolute <i>put</i> method for writing a char
     * value.
     * <p></p>
     * <p> Writes two bytes containing the given char value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The char value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus one
     */
    Bytes putChar(long index, char value);

    /**
     * Relative <i>getChar</i> method for reading a char value in UTF-8 encoding.
     * <p></p>
     * <p> Reads the next two bytes at this buffer's current position,
     * composing them into a char value according to the current byte order,
     * and then increments the position by two.  </p>
     *
     * @return The char value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than two bytes
     *                                  remaining in this buffer
     */
    char getCharUTF8();

    /**
     * Relative <i>putChar</i> method for writing a char
     * value in UTF-8 encoding.
     * <p></p>
     * <p> Writes two bytes containing the given char value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by two.  </p>
     *
     * @param value The char value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than two bytes
     *                                 remaining in this buffer
     */
    Bytes putCharUTF8(char value);

    /**
     * Relative bulk <i>getChar</i> method.
     * <p></p>
     * <p> This method transfers chars from this buffer into the given
     * destination array.  If there are fewer chars remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * chars are transferred and a {@link BufferUnderflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> chars from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.getChar(); </pre>
     *
     * except that it first checks that there are sufficient chars in
     * this buffer and it is potentially much more efficient. </p>
     *
     * @param dst    The array into which chars are to be written
     * @param offset The offset within the array of the first char to be
     *               written; must be non-negative and no larger than
     *               <tt>dst.length</tt>
     * @param length The maximum number of chars to be written to the given
     *               array; must be non-negative and no larger than
     *               <tt>dst.length - offset</tt>
     * @return This buffer
     * @throws BufferUnderflowException  If there are fewer than <tt>length</tt> chars
     *                                   remaining in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes get(char[] dst, int offset, int length);

    /**
     * Relative bulk <i>getChar</i> method.
     * <p></p>
     * <p> This method transfers chars from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.get(a)</tt> behaves in exactly the same way as the invocation
     * <p></p>
     * <pre>
     *     src.get(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferUnderflowException If there are fewer than <tt>length</tt> chars
     *                                  remaining in this buffer
     */
    Bytes get(char[] dst);

    /**
     * Relative bulk <i>getUTF8</i> method.
     * <p></p>
     * <p> This method transfers UTF-8 encoded chars  from this buffer into the given
     * destination array.  If there are fewer chars remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, a {@link BufferUnderflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> chars from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.getCharUTF8(); </pre>
     *
     * except that it is potentially much more efficient. </p>
     *
     * @param dst    The array into which chars are to be written
     * @param offset The offset within the array of the first char to be
     *               written; must be non-negative and no larger than
     *               <tt>dst.length</tt>
     * @param length The maximum number of chars to be written to the given
     *               array; must be non-negative and no larger than
     *               <tt>dst.length - offset</tt>
     * @return This buffer
     * @throws BufferUnderflowException  If there are fewer than <tt>length</tt> chars
     *                                   remaining in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes getUTF8(char[] dst, int offset, int length);

    /**
     * Relative bulk <i>get</i> method.
     * <p></p>
     * <p> This method transfers chars from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.getUTF8(a)</tt> behaves in exactly the same way as the invocation
     * <p></p>
     * <pre>
     *     src.getUTF8(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferUnderflowException If there are fewer than <tt>length</tt> chars
     *                                  remaining in this buffer
     */
    Bytes getUTF8(char[] dst);

    /**
     * Relative bulk <i>getUTF8</i> method.
     * <p></p>
     * <p> This method transfers UTF-8 encoded chars  from this buffer into the given
     * destination array.  If there are fewer chars remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, a {@link BufferUnderflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> chars from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = 0; i < len; i++)
     *         dst.append(src.getCharUTF8()); </pre>
     *
     * except that it is potentially much more efficient. </p>
     *
     * @param dst    The array into which chars are to be written
     * @param length The maximum number of chars to be written to the given
     *               array; must be non-negative.
     * @return This buffer
     * @throws BufferUnderflowException  If there are fewer than <tt>length</tt> chars
     *                                   remaining in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes getUTF8(Appendable dst, int length);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the chars remaining in the given source
     * buffer into this buffer.  If there are more chars remaining in the
     * source buffer than in this buffer, that is, if
     * <tt>src.remaining()</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no chars are transferred and a {@link
     * BufferOverflowException} is thrown.
     * <p></p>
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>src.remaining()</tt> chars from the given
     * buffer into this buffer, starting at each buffer's current position.
     * The positions of both buffers are then incremented by <i>n</i>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src)</tt> has exactly the same effect as the loop
     * <p></p>
     * <pre>
     *     while (src.hasRemaining())
     *         dst.put(src.get()); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src The source buffer from which chars are to be read;
     *            must not be this buffer
     * @return This buffer
     * @throws BufferOverflowException  If there is insufficient space in this buffer
     *                                  for the remaining chars in the source buffer
     * @throws IllegalArgumentException If the source buffer is this buffer
     */
    Bytes put(CharBuffer src);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers chars into this buffer from the given
     * source array.  If there are more chars to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * chars are transferred and a {@link BufferOverflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> chars from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.put(a[i]); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src    The array from which chars are to be read
     * @param offset The offset within the array of the first char to be read;
     *               must be non-negative and no larger than <tt>array.length</tt>
     * @param length The number of chars to be read from the given array;
     *               must be non-negative and no larger than
     *               <tt>array.length - offset</tt>
     * @return This buffer
     * @throws BufferOverflowException   If there is insufficient space in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes put(char[] src, int offset, int length);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the entire content of the given source
     * char array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     * <p></p>
     * <pre>
     *     dst.put(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferOverflowException If there is insufficient space in this buffer
     */
    Bytes put(char[] src);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers chars from the given string into this
     * buffer.  If there are more chars to be copied from the string than
     * remain in this buffer, that is, if
     * <tt>end&nbsp;-&nbsp;start</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no chars are transferred and a {@link
     * BufferOverflowException} is thrown.
     * <p></p>
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>end</tt>&nbsp;-&nbsp;<tt>start</tt> chars
     * from the given string into this buffer, starting at the given
     * <tt>start</tt> index and at the current position of this buffer.  The
     * position of this buffer is then incremented by <i>n</i>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;start,&nbsp;end)</tt> has exactly the same effect
     * as the loop
     * <p></p>
     * <pre>
     *     for (int i = start; i < end; i++)
     *         dst.put(src.charAt(i)); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src   The string from which chars are to be read
     * @param start The offset within the string of the first char to be read;
     *              must be non-negative and no larger than
     *              <tt>string.length()</tt>
     * @param end   The offset within the string of the last char to be read,
     *              plus one; must be non-negative and no larger than
     *              <tt>string.length()</tt>
     * @return This buffer
     * @throws BufferOverflowException   If there is insufficient space in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>start</tt> and <tt>end</tt>
     *                                   parameters do not hold
     */
    Bytes put(CharSequence src, int start, int end);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the entire content of the given source string
     * into this buffer.  An invocation of this method of the form
     * <tt>dst.put(s)</tt> behaves in exactly the same way as the invocation
     * <p></p>
     * <pre>
     *     dst.put(s, 0, s.length()) </pre>
     *
     * @return This buffer
     * @throws BufferOverflowException If there is insufficient space in this buffer
     */
    Bytes put(CharSequence src);

    /**
     * Relative bulk <i>putUTF8</i> method.
     * <p></p>
     * <p> This method transfers chars into this buffer from the given
     * source array.  If there are more chars to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * chars are transferred and a {@link BufferOverflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> chars from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.putCharUTF8(a[i]); </pre>
     *
     * except that it is potentially much more efficient. </p>
     *
     * @param src    The array from which chars are to be read
     * @param offset The offset within the array of the first char to be read;
     *               must be non-negative and no larger than <tt>array.length</tt>
     * @param length The number of chars to be read from the given array;
     *               must be non-negative and no larger than
     *               <tt>array.length - offset</tt>
     * @return This buffer
     * @throws BufferOverflowException   If there is insufficient space in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes putUTF8(char[] src, int offset, int length);

    /**
     * Relative bulk <i>putUTF8</i> method.
     * <p></p>
     * <p> This method transfers the entire content of the given source
     * char array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     * <p></p>
     * <pre>
     *     dst.putUTF8(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferOverflowException If there is insufficient space in this buffer
     */
    Bytes putUTF8(char[] src);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers chars from the given string into this
     * buffer.  If there are more chars to be copied from the string than
     * remain in this buffer, that is, if
     * <tt>end&nbsp;-&nbsp;start</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no chars are transferred and a {@link
     * BufferOverflowException} is thrown.
     * <p></p>
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>end</tt>&nbsp;-&nbsp;<tt>start</tt> chars
     * from the given string into this buffer, starting at the given
     * <tt>start</tt> index and at the current position of this buffer.  The
     * position of this buffer is then incremented by <i>n</i>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;start,&nbsp;end)</tt> has exactly the same effect
     * as the loop
     * <p></p>
     * <pre>
     *     for (int i = start; i < end; i++)
     *         dst.putUTF8(src.charAt(i)); </pre>
     *
     * except it is potentially much more efficient. </p>
     *
     * @param src   The string from which chars are to be read
     * @param start The offset within the string of the first char to be read;
     *              must be non-negative and no larger than
     *              <tt>string.length()</tt>
     * @param end   The offset within the string of the last char to be read,
     *              plus one; must be non-negative and no larger than
     *              <tt>string.length()</tt>
     * @return This buffer
     * @throws BufferOverflowException   If there is insufficient space in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>start</tt> and <tt>end</tt>
     *                                   parameters do not hold
     */
    Bytes putUTF8(CharSequence src, int start, int end);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the entire content of the given source string
     * into this buffer.  An invocation of this method of the form
     * <tt>dst.putUTF8(s)</tt> behaves in exactly the same way as the invocation
     * <p></p>
     * <pre>
     *     dst.putUTF8(s, 0, s.length()) </pre>
     *
     * @return This buffer
     * @throws BufferOverflowException If there is insufficient space in this buffer
     */
    Bytes putUTF8(CharSequence src);


    /**
     * Relative <i>getShort</i> method for reading a short value.
     * <p></p>
     * <p> Reads the next two bytes at this buffer's current position,
     * composing them into a short value according to the current byte order,
     * and then increments the position by two.  </p>
     *
     * @return The short value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than two bytes
     *                                  remaining in this buffer
     */
    short getShort();

    /**
     * Relative <i>putShort</i> method for writing a short
     * value.
     * <p></p>
     * <p> Writes two bytes containing the given short value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by two.  </p>
     *
     * @param value The short value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than two bytes
     *                                 remaining in this buffer
     */
    Bytes putShort(short value);

    /**
     * Absolute <i>getShort</i> method for reading a short value.
     * <p></p>
     * <p> Reads two bytes at the given index, composing them into a
     * short value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The short value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus one
     */
    short getShort(long index);

    /**
     * Absolute <i>putShort</i> method for writing a short
     * value.
     * <p></p>
     * <p> Writes two bytes containing the given short value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The short value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus one
     */
    Bytes putShort(long index, short value);

    /**
     * Relative <i>getUnsignedShort</i> method for reading a short value.
     * <p></p>
     * <p> Reads the next two bytes at this buffer's current position,
     * composing them into a short value according to the current byte order,
     * and then increments the position by two.  </p>
     *
     * @return The short value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than two bytes
     *                                  remaining in this buffer
     */
    short getUnsignedShort();

    /**
     * Relative <i>putUnsignedShort</i> method for writing a short
     * value.
     * <p></p>
     * <p> Writes two bytes containing the given short value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by two.  </p>
     *
     * @param value The short value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than two bytes
     *                                 remaining in this buffer
     */
    Bytes putUnsignedShort(short value);

    /**
     * Absolute <i>getUnsignedShort</i> method for reading a unsigned short value.
     * <p></p>
     * <p> Reads two bytes at the given index, composing them into a
     * short value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The short value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus one
     */
    int getUnsignedShort(long index);

    /**
     * Absolute <i>putUnsignedShort</i> method for writing a unsigned short
     * value.
     * <p></p>
     * <p> Writes two bytes containing the given short value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The short value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus one
     */
    Bytes putUnsignedShort(long index, int value);

    /**
     * Relative bulk <i>get</i> method.
     * <p></p>
     * <p> This method transfers shorts from this buffer into the given
     * destination array.  If there are fewer shorts remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * shorts are transferred and a {@link BufferUnderflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> shorts from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.get(); </pre>
     *
     * except that it first checks that there are sufficient shorts in
     * this buffer and it is potentially much more efficient. </p>
     *
     * @param dst    The array into which shorts are to be written
     * @param offset The offset within the array of the first short to be
     *               written; must be non-negative and no larger than
     *               <tt>dst.length</tt>
     * @param length The maximum number of shorts to be written to the given
     *               array; must be non-negative and no larger than
     *               <tt>dst.length - offset</tt>
     * @return This buffer
     * @throws BufferUnderflowException  If there are fewer than <tt>length</tt> shorts
     *                                   remaining in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes get(short[] dst, int offset, int length);

    /**
     * Relative bulk <i>get</i> method.
     * <p></p>
     * <p> This method transfers shorts from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.get(a)</tt> behaves in exactly the same way as the invocation
     * <p></p>
     * <pre>
     *     src.get(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferUnderflowException If there are fewer than <tt>length</tt> shorts
     *                                  remaining in this buffer
     */
    Bytes get(short[] dst);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the shorts remaining in the given source
     * buffer into this buffer.  If there are more shorts remaining in the
     * source buffer than in this buffer, that is, if
     * <tt>src.remaining()</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no shorts are transferred and a {@link
     * BufferOverflowException} is thrown.
     * <p></p>
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>src.remaining()</tt> shorts from the given
     * buffer into this buffer, starting at each buffer's current position.
     * The positions of both buffers are then incremented by <i>n</i>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src)</tt> has exactly the same effect as the loop
     * <p></p>
     * <pre>
     *     while (src.hasRemaining())
     *         dst.put(src.get()); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src The source buffer from which shorts are to be read;
     *            must not be this buffer
     * @return This buffer
     * @throws BufferOverflowException  If there is insufficient space in this buffer
     *                                  for the remaining shorts in the source buffer
     * @throws IllegalArgumentException If the source buffer is this buffer
     */
    Bytes put(ShortBuffer src);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers shorts into this buffer from the given
     * source array.  If there are more shorts to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * shorts are transferred and a {@link BufferOverflowException} is
     * thrown.
     * <p></p>
     * <p> Otherwise, this method copies <tt>length</tt> shorts from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     * <p></p>
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     * <p></p>
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.put(a[i]); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param src    The array from which shorts are to be read
     * @param offset The offset within the array of the first short to be read;
     *               must be non-negative and no larger than <tt>array.length</tt>
     * @param length The number of shorts to be read from the given array;
     *               must be non-negative and no larger than
     *               <tt>array.length - offset</tt>
     * @return This buffer
     * @throws BufferOverflowException   If there is insufficient space in this buffer
     * @throws IndexOutOfBoundsException If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *                                   parameters do not hold
     */
    Bytes put(short[] src, int offset, int length);

    /**
     * Relative bulk <i>put</i> method.
     * <p></p>
     * <p> This method transfers the entire content of the given source
     * short array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     * <p></p>
     * <pre>
     *     dst.put(a, 0, a.length) </pre>
     *
     * @return This buffer
     * @throws BufferOverflowException If there is insufficient space in this buffer
     */
    Bytes put(short[] src);

    /**
     * Relative <i>getInt</i> method for reading an int value.
     * <p></p>
     * <p> Reads the next four bytes at this buffer's current position,
     * composing them into an int value according to the current byte order,
     * and then increments the position by four.  </p>
     *
     * @return The int value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than four bytes
     *                                  remaining in this buffer
     */
    int getInt();

    /**
     * Relative <i>getVolatileInt</i> method for reading an int value.
     * <p></p>
     * <p> Reads the next four bytes at this buffer's current position,
     * composing them into an int value according to the current byte order,
     * and then increments the position by four.  </p>
     *
     * @return The int value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than four bytes
     *                                  remaining in this buffer
     */
    int getVolatileInt();

    /**
     * Relative <i>putInt</i> method for writing an int
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given int value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by four.  </p>
     *
     * @param value The int value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than four bytes
     *                                 remaining in this buffer
     */
    Bytes putInt(int value);

    /**
     * Relative <i>putVolatileInt</i> method for writing an int
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given int value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by four.  </p>
     *
     * @param value The int value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than four bytes
     *                                 remaining in this buffer
     */
    Bytes putVolatileInt(int value);

    /**
     * Relative <i>putOrderedInt</i> method for writing an int
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given int value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by four.  </p>
     *
     * @param value The int value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than four bytes
     *                                 remaining in this buffer
     */
    Bytes putOrderedInt(int value);

    /**
     * Absolute <i>getInt</i> method for reading an int value.
     * <p></p>
     * <p> Reads four bytes at the given index, composing them into a
     * int value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The int value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus three
     */
    int getInt(long index);

    /**
     * Absolute <i>getInt</i> method for reading an int value.
     * <p></p>
     * <p> Reads four bytes at the given index, composing them into a
     * int value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The int value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus three
     */
    int getVolatileInt(long index);

    /**
     * Absolute <i>put</i> method for writing an int
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given int value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The int value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus three
     */
    Bytes putInt(long index, int value);

    /**
     * Absolute <i>put</i> method for writing an int
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given int value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The int value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus three
     */
    Bytes putVolatileInt(long index, int value);

    /**
     * Absolute <i>put</i> method for writing an int
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given int value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The int value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus three
     */
    Bytes putOrderedInt(long index, int value);

    /**
     * Relative <i>getUnsignedInt</i> method for reading an int value.
     * <p></p>
     * <p> Reads the next four bytes at this buffer's current position,
     * composing them into an int value according to the current byte order,
     * and then increments the position by four.  </p>
     *
     * @return The int value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than four bytes
     *                                  remaining in this buffer
     */
    long getUnsignedInt();

    /**
     * Relative <i>putUnsignedInt</i> method for writing an int
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given int value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by four.  </p>
     *
     * @param value The int value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than four bytes
     *                                 remaining in this buffer
     */
    Bytes putUnsignedInt(int value);

    /**
     * Absolute <i>getInt</i> method for reading an int value.
     * <p></p>
     * <p> Reads four bytes at the given index, composing them into a
     * int value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The int value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus three
     */
    long getUnsignedInt(long index);

    /**
     * Absolute <i>putUnsignedInt</i> method for writing an int
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given int value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The int value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus three
     */
    Bytes putUnsignedInt(long index, long value);

    /**
     * Relative bulk <i>get</i> method.
     *
     * <p> This method transfers ints from this buffer into the given
     * destination array.  If there are fewer ints remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * ints are transferred and a {@link BufferUnderflowException} is
     * thrown.
     *
     * <p> Otherwise, this method copies <tt>length</tt> ints from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     *
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.get(); </pre>
     *
     * except that it first checks that there are sufficient ints in
     * this buffer and it is potentially much more efficient. </p>
     *
     * @param  dst
     *         The array into which ints are to be written
     *
     * @param  offset
     *         The offset within the array of the first int to be
     *         written; must be non-negative and no larger than
     *         <tt>dst.length</tt>
     *
     * @param  length
     *         The maximum number of ints to be written to the given
     *         array; must be non-negative and no larger than
     *         <tt>dst.length - offset</tt>
     *
     * @return  This buffer
     *
     * @throws  BufferUnderflowException
     *          If there are fewer than <tt>length</tt> ints
     *          remaining in this buffer
     *
     * @throws  IndexOutOfBoundsException
     *          If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *          parameters do not hold
     */
    Bytes get(int[] dst, int offset, int length);

    /**
     * Relative bulk <i>get</i> method.
     *
     * <p> This method transfers ints from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.get(a)</tt> behaves in exactly the same way as the invocation
     *
     * <pre>
     *     src.get(a, 0, a.length) </pre>
     *
     * @return  This buffer
     *
     * @throws  BufferUnderflowException
     *          If there are fewer than <tt>length</tt> ints
     *          remaining in this buffer
     */
    Bytes get(int[] dst);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers the ints remaining in the given source
     * buffer into this buffer.  If there are more ints remaining in the
     * source buffer than in this buffer, that is, if
     * <tt>src.remaining()</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no ints are transferred and a {@link
     * BufferOverflowException} is thrown.
     *
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>src.remaining()</tt> ints from the given
     * buffer into this buffer, starting at each buffer's current position.
     * The positions of both buffers are then incremented by <i>n</i>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src)</tt> has exactly the same effect as the loop
     *
     * <pre>
     *     while (src.hasRemaining())
     *         dst.put(src.get()); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param  src
     *         The source buffer from which ints are to be read;
     *         must not be this buffer
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *          for the remaining ints in the source buffer
     *
     * @throws  IllegalArgumentException
     *          If the source buffer is this buffer
     *
     */
    Bytes put(IntBuffer src);
    
    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers ints into this buffer from the given
     * source array.  If there are more ints to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * ints are transferred and a {@link BufferOverflowException} is
     * thrown.
     *
     * <p> Otherwise, this method copies <tt>length</tt> ints from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     *
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.put(a[i]); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param  src
     *         The array from which ints are to be read
     *
     * @param  offset
     *         The offset within the array of the first int to be read;
     *         must be non-negative and no larger than <tt>array.length</tt>
     *
     * @param  length
     *         The number of ints to be read from the given array;
     *         must be non-negative and no larger than
     *         <tt>array.length - offset</tt>
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *
     * @throws  IndexOutOfBoundsException
     *          If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *          parameters do not hold
     *
     */
    Bytes put(int[] src, int offset, int length);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers the entire content of the given source
     * int array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     *
     * <pre>
     *     dst.put(a, 0, a.length) </pre>
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *
     */
    Bytes put(int[] src);

    /**
     * Relative <i>getLong</i> method for reading a long value.
     * <p></p>
     * <p> Reads the next eight bytes at this buffer's current position,
     * composing them into a long value according to the current byte order,
     * and then increments the position by eight.  </p>
     *
     * @return The long value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than eight bytes
     *                                  remaining in this buffer
     */
    long getLong();

    /**
     * Relative <i>getLong</i> method for reading a long value.
     * <p></p>
     * <p> Reads the next eight bytes at this buffer's current position,
     * composing them into a long value according to the current byte order,
     * and then increments the position by eight.  </p>
     *
     * @return The long value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than eight bytes
     *                                  remaining in this buffer
     */
    long getVolatileLong();

    /**
     * Relative <i>putLong</i> method for writing a long
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given long value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by eight.  </p>
     *
     * @param value The long value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than eight bytes
     *                                 remaining in this buffer
     */
    Bytes putLong(long value);

    /**
     * Relative <i>putLong</i> method for writing a long
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given long value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by eight.  </p>
     *
     * @param value The long value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than eight bytes
     *                                 remaining in this buffer
     */
    Bytes putVolatileLong(long value);

    /**
     * Relative <i>putLong</i> method for writing a long
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given long value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by eight.  </p>
     *
     * @param value The long value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than eight bytes
     *                                 remaining in this buffer
     */
    Bytes putOrderedLong(long value);

    /**
     * Absolute <i>getLong</i> method for reading a long value.
     * <p></p>
     * <p> Reads eight bytes at the given index, composing them into a
     * long value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The long value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus seven
     */
    long getLong(long index);

    /**
     * Absolute <i>getLong</i> method for reading a long value.
     * <p></p>
     * <p> Reads eight bytes at the given index, composing them into a
     * long value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The long value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus seven
     */
    long getVolatileLong(long index);

    /**
     * Absolute <i>putLong</i> method for writing a long
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given long value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The long value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus seven
     */
    Bytes putLong(long index, long value);

    /**
     * Absolute <i>putLong</i> method for writing a long
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given long value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The long value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus seven
     */
    Bytes putVolatileLong(long index, long value);

    /**
     * Absolute <i>putLong</i> method for writing a long
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given long value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The long value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus seven
     */
    Bytes putOrderedLong(long index, long value);

    /**
     * Relative <i>getUnsignedLong</i> method for reading a long value.
     * <p></p>
     * <p> Reads the next eight bytes at this buffer's current position,
     * composing them into a long value according to the current byte order,
     * and then increments the position by eight.  </p>
     *
     * @return The long value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than eight bytes
     *                                  remaining in this buffer
     */
    long getUnsignedLong();

    /**
     * Relative <i>putUnsignedLong</i> method for writing a long
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given long value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by eight.  </p>
     *
     * @param value The long value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than eight bytes
     *                                 remaining in this buffer
     */
    Bytes putUnsignedLong(long value);

    /**
     * Absolute <i>getUnsignedLong</i> method for reading a long value.
     * <p></p>
     * <p> Reads eight bytes at the given index, composing them into a
     * long value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The long value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus seven
     */
    long getUnsignedLong(long index);

    /**
     * Absolute <i>putUnsignedLong</i> method for writing a long
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given long value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The long value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus seven
     */
    Bytes putUnsignedLong(long index, long value);

    /**
     * Relative bulk <i>get</i> method.
     *
     * <p> This method transfers longs from this buffer into the given
     * destination array.  If there are fewer longs remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * longs are transferred and a {@link BufferUnderflowException} is
     * thrown.
     *
     * <p> Otherwise, this method copies <tt>length</tt> longs from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     *
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.get(); </pre>
     *
     * except that it first checks that there are sufficient longs in
     * this buffer and it is potentially much more efficient. </p>
     *
     * @param  dst
     *         The array into which longs are to be written
     *
     * @param  offset
     *         The offset within the array of the first long to be
     *         written; must be non-negative and no larger than
     *         <tt>dst.length</tt>
     *
     * @param  length
     *         The maximum number of longs to be written to the given
     *         array; must be non-negative and no larger than
     *         <tt>dst.length - offset</tt>
     *
     * @return  This buffer
     *
     * @throws  BufferUnderflowException
     *          If there are fewer than <tt>length</tt> longs
     *          remaining in this buffer
     *
     * @throws  IndexOutOfBoundsException
     *          If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *          parameters do not hold
     */
    Bytes get(long[] dst, int offset, int length);

    /**
     * Relative bulk <i>get</i> method.
     *
     * <p> This method transfers longs from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.get(a)</tt> behaves in exactly the same way as the invocation
     *
     * <pre>
     *     src.get(a, 0, a.length) </pre>
     *
     * @return  This buffer
     *
     * @throws  BufferUnderflowException
     *          If there are fewer than <tt>length</tt> longs
     *          remaining in this buffer
     */
    Bytes get(long[] dst);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers the longs remaining in the given source
     * buffer into this buffer.  If there are more longs remaining in the
     * source buffer than in this buffer, that is, if
     * <tt>src.remaining()</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no longs are transferred and a {@link
     * BufferOverflowException} is thrown.
     *
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>src.remaining()</tt> longs from the given
     * buffer into this buffer, starting at each buffer's current position.
     * The positions of both buffers are then incremented by <i>n</i>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src)</tt> has exactly the same effect as the loop
     *
     * <pre>
     *     while (src.hasRemaining())
     *         dst.put(src.get()); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param  src
     *         The source buffer from which longs are to be read;
     *         must not be this buffer
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *          for the remaining longs in the source buffer
     *
     * @throws  IllegalArgumentException
     *          If the source buffer is this buffer
     *
     */
    Bytes put(LongBuffer src);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers longs into this buffer from the given
     * source array.  If there are more longs to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * longs are transferred and a {@link BufferOverflowException} is
     * thrown.
     *
     * <p> Otherwise, this method copies <tt>length</tt> longs from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     *
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.put(a[i]); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param  src
     *         The array from which longs are to be read
     *
     * @param  offset
     *         The offset within the array of the first long to be read;
     *         must be non-negative and no larger than <tt>array.length</tt>
     *
     * @param  length
     *         The number of longs to be read from the given array;
     *         must be non-negative and no larger than
     *         <tt>array.length - offset</tt>
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *
     * @throws  IndexOutOfBoundsException
     *          If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *          parameters do not hold
     *
     */
    Bytes put(long[] src, int offset, int length);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers the entire content of the given source
     * long array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     *
     * <pre>
     *     dst.put(a, 0, a.length) </pre>
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *
     */
    Bytes put(long[] src);

    /**
     * Relative <i>get</i> method for reading a float value.
     * <p></p>
     * <p> Reads the next four bytes at this buffer's current position,
     * composing them into a float value according to the current byte order,
     * and then increments the position by four.  </p>
     *
     * @return The float value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than four bytes
     *                                  remaining in this buffer
     */
    float getFloat();

    /**
     * Relative <i>put</i> method for writing a float
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given float value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by four.  </p>
     *
     * @param value The float value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than four bytes
     *                                 remaining in this buffer
     */
    Bytes putFloat(float value);

    /**
     * Absolute <i>get</i> method for reading a float value.
     * <p></p>
     * <p> Reads four bytes at the given index, composing them into a
     * float value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The float value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus three
     */
    float getFloat(long index);

    /**
     * Absolute <i>put</i> method for writing a float
     * value.
     * <p></p>
     * <p> Writes four bytes containing the given float value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The float value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus three
     */
    Bytes putFloat(long index, float value);

    /**
     * Relative bulk <i>get</i> method.
     *
     * <p> This method transfers floats from this buffer into the given
     * destination array.  If there are fewer floats remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * floats are transferred and a {@link BufferUnderflowException} is
     * thrown.
     *
     * <p> Otherwise, this method copies <tt>length</tt> floats from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     *
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.get(); </pre>
     *
     * except that it first checks that there are sufficient floats in
     * this buffer and it is potentially much more efficient. </p>
     *
     * @param  dst
     *         The array into which floats are to be written
     *
     * @param  offset
     *         The offset within the array of the first float to be
     *         written; must be non-negative and no larger than
     *         <tt>dst.length</tt>
     *
     * @param  length
     *         The maximum number of floats to be written to the given
     *         array; must be non-negative and no larger than
     *         <tt>dst.length - offset</tt>
     *
     * @return  This buffer
     *
     * @throws  BufferUnderflowException
     *          If there are fewer than <tt>length</tt> floats
     *          remaining in this buffer
     *
     * @throws  IndexOutOfBoundsException
     *          If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *          parameters do not hold
     */
    Bytes get(float[] dst, int offset, int length);

    /**
     * Relative bulk <i>get</i> method.
     *
     * <p> This method transfers floats from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.get(a)</tt> behaves in exactly the same way as the invocation
     *
     * <pre>
     *     src.get(a, 0, a.length) </pre>
     *
     * @return  This buffer
     *
     * @throws  BufferUnderflowException
     *          If there are fewer than <tt>length</tt> floats
     *          remaining in this buffer
     */
    Bytes get(float[] dst);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers the floats remaining in the given source
     * buffer into this buffer.  If there are more floats remaining in the
     * source buffer than in this buffer, that is, if
     * <tt>src.remaining()</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no floats are transferred and a {@link
     * BufferOverflowException} is thrown.
     *
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>src.remaining()</tt> floats from the given
     * buffer into this buffer, starting at each buffer's current position.
     * The positions of both buffers are then incremented by <i>n</i>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src)</tt> has exactly the same effect as the loop
     *
     * <pre>
     *     while (src.hasRemaining())
     *         dst.put(src.get()); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param  src
     *         The source buffer from which floats are to be read;
     *         must not be this buffer
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *          for the remaining floats in the source buffer
     *
     * @throws  IllegalArgumentException
     *          If the source buffer is this buffer
     *
     */
    Bytes put(FloatBuffer src);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers floats into this buffer from the given
     * source array.  If there are more floats to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * floats are transferred and a {@link BufferOverflowException} is
     * thrown.
     *
     * <p> Otherwise, this method copies <tt>length</tt> floats from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     *
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.put(a[i]); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param  src
     *         The array from which floats are to be read
     *
     * @param  offset
     *         The offset within the array of the first float to be read;
     *         must be non-negative and no larger than <tt>array.length</tt>
     *
     * @param  length
     *         The number of floats to be read from the given array;
     *         must be non-negative and no larger than
     *         <tt>array.length - offset</tt>
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *
     * @throws  IndexOutOfBoundsException
     *          If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *          parameters do not hold
     *
     */
    Bytes put(float[] src, int offset, int length);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers the entire content of the given source
     * float array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     *
     * <pre>
     *     dst.put(a, 0, a.length) </pre>
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *
     */
    Bytes put(float[] src);

    /**
     * Relative <i>get</i> method for reading a double value.
     * <p></p>
     * <p> Reads the next eight bytes at this buffer's current position,
     * composing them into a double value according to the current byte order,
     * and then increments the position by eight.  </p>
     *
     * @return The double value at the buffer's current position
     * @throws BufferUnderflowException If there are fewer than eight bytes
     *                                  remaining in this buffer
     */
    double getDouble();

    /**
     * Relative <i>put</i> method for writing a double
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given double value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by eight.  </p>
     *
     * @param value The double value to be written
     * @return This buffer
     * @throws BufferOverflowException If there are fewer than eight bytes
     *                                 remaining in this buffer
     */
    Bytes putDouble(double value);

    /**
     * Absolute <i>get</i> method for reading a double value.
     * <p></p>
     * <p> Reads eight bytes at the given index, composing them into a
     * double value according to the current byte order.  </p>
     *
     * @param index The index from which the bytes will be read
     * @return The double value at the given index
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus seven
     */
    double getDouble(long index);

    /**
     * Absolute <i>put</i> method for writing a double
     * value.
     * <p></p>
     * <p> Writes eight bytes containing the given double value, in the
     * current byte order, into this buffer at the given index.  </p>
     *
     * @param index The index at which the bytes will be written
     * @param value The double value to be written
     * @return This buffer
     * @throws IndexOutOfBoundsException If <tt>index</tt> is negative
     *                                   or not smaller than the buffer's limit,
     *                                   minus seven
     */
    Bytes putDouble(long index, double value);

    /**
     * Relative bulk <i>get</i> method.
     *
     * <p> This method transfers doubles from this buffer into the given
     * destination array.  If there are fewer doubles remaining in the
     * buffer than are required to satisfy the request, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * doubles are transferred and a {@link BufferUnderflowException} is
     * thrown.
     *
     * <p> Otherwise, this method copies <tt>length</tt> doubles from this
     * buffer into the given array, starting at the current position of this
     * buffer and at the given offset in the array.  The position of this
     * buffer is then incremented by <tt>length</tt>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>src.get(dst,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     *
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst[i] = src.get(); </pre>
     *
     * except that it first checks that there are sufficient doubles in
     * this buffer and it is potentially much more efficient. </p>
     *
     * @param  dst
     *         The array into which doubles are to be written
     *
     * @param  offset
     *         The offset within the array of the first double to be
     *         written; must be non-negative and no larger than
     *         <tt>dst.length</tt>
     *
     * @param  length
     *         The maximum number of doubles to be written to the given
     *         array; must be non-negative and no larger than
     *         <tt>dst.length - offset</tt>
     *
     * @return  This buffer
     *
     * @throws  BufferUnderflowException
     *          If there are fewer than <tt>length</tt> doubles
     *          remaining in this buffer
     *
     * @throws  IndexOutOfBoundsException
     *          If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *          parameters do not hold
     */
    Bytes get(double[] dst, int offset, int length);

    /**
     * Relative bulk <i>get</i> method.
     *
     * <p> This method transfers doubles from this buffer into the given
     * destination array.  An invocation of this method of the form
     * <tt>src.get(a)</tt> behaves in exactly the same way as the invocation
     *
     * <pre>
     *     src.get(a, 0, a.length) </pre>
     *
     * @return  This buffer
     *
     * @throws  BufferUnderflowException
     *          If there are fewer than <tt>length</tt> doubles
     *          remaining in this buffer
     */
    Bytes get(double[] dst);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers the doubles remaining in the given source
     * buffer into this buffer.  If there are more doubles remaining in the
     * source buffer than in this buffer, that is, if
     * <tt>src.remaining()</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>,
     * then no doubles are transferred and a {@link
     * BufferOverflowException} is thrown.
     *
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;<tt>src.remaining()</tt> doubles from the given
     * buffer into this buffer, starting at each buffer's current position.
     * The positions of both buffers are then incremented by <i>n</i>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src)</tt> has exactly the same effect as the loop
     *
     * <pre>
     *     while (src.hasRemaining())
     *         dst.put(src.get()); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param  src
     *         The source buffer from which doubles are to be read;
     *         must not be this buffer
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *          for the remaining doubles in the source buffer
     *
     * @throws  IllegalArgumentException
     *          If the source buffer is this buffer
     *
     */
    Bytes put(DoubleBuffer src);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers doubles into this buffer from the given
     * source array.  If there are more doubles to be copied from the array
     * than remain in this buffer, that is, if
     * <tt>length</tt>&nbsp;<tt>&gt;</tt>&nbsp;<tt>remaining()</tt>, then no
     * doubles are transferred and a {@link BufferOverflowException} is
     * thrown.
     *
     * <p> Otherwise, this method copies <tt>length</tt> doubles from the
     * given array into this buffer, starting at the given offset in the array
     * and at the current position of this buffer.  The position of this buffer
     * is then incremented by <tt>length</tt>.
     *
     * <p> In other words, an invocation of this method of the form
     * <tt>dst.put(src,&nbsp;off,&nbsp;len)</tt> has exactly the same effect as
     * the loop
     *
     * <pre>
     *     for (int i = off; i < off + len; i++)
     *         dst.put(a[i]); </pre>
     *
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient. </p>
     *
     * @param  src
     *         The array from which doubles are to be read
     *
     * @param  offset
     *         The offset within the array of the first double to be read;
     *         must be non-negative and no larger than <tt>array.length</tt>
     *
     * @param  length
     *         The number of doubles to be read from the given array;
     *         must be non-negative and no larger than
     *         <tt>array.length - offset</tt>
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *
     * @throws  IndexOutOfBoundsException
     *          If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *          parameters do not hold
     *
     */
    Bytes put(double[] src, int offset, int length);

    /**
     * Relative bulk <i>put</i> method.
     *
     * <p> This method transfers the entire content of the given source
     * double array into this buffer.  An invocation of this method of the
     * form <tt>dst.put(a)</tt> behaves in exactly the same way as the
     * invocation
     *
     * <pre>
     *     dst.put(a, 0, a.length) </pre>
     *
     * @return  This buffer
     *
     * @throws  BufferOverflowException
     *          If there is insufficient space in this buffer
     *
     */
    Bytes put(double[] src);

    /**
     * Fill a region of the buffer with a value as a repeating byte
     */
    Bytes fillByte(long offset, int count, byte value);

    /**
     * Fill a region of the buffer with a value as a repeating char
     */
    Bytes fillChar(long offset, int count, char value);

    /**
     * Fill a region of the buffer with a value as a repeating short
     */
    Bytes fillShort(long offset, int count, short value);

    /**
     * Fill a region of the buffer with a value as a repeating int
     */
    Bytes fillInt(long offset, int count, int value);

    /**
     * Fill a region of the buffer with a value as a repeating long
     */
    Bytes fillLong(long offset, long count, long value);

    /**
     * Fill a region of the buffer with a value as a repeating float
     */
    Bytes fillLong(long offset, long count, float value);

    /**
     * Fill a region of the buffer with a value as a repeating double
     */
    Bytes fillLong(long offset, long count, double value);

    /**
     * Compare and swap a bit as an atomic operation
     *
     * @param bitOffset to swap
     * @param expected to find
     * @param value to set if the expected is found.
     * @return true, it the expected was replaced with the value
     */
    boolean compareAndSwapBit(long bitOffset, boolean expected, boolean value);

    /**
     * Compare and swap a boolean as an atomic operation
     *
     * @param offset to swap
     * @param expected to find
     * @param value to set if the expected is found.
     * @return true, it the expected was replaced with the value
     */
    boolean compareAndSwapBoolean(long offset, boolean expected, boolean value);

    /**
     * Compare and swap a byte as an atomic operation
     *
     * @param offset to swap
     * @param expected to find
     * @param value to set if the expected is found.
     * @return true, it the expected was replaced with the value
     */
    boolean compareAndSwapByte(long offset, byte expected, byte value);

    /**
     * Compare and swap a short as an atomic operation
     *
     * @param offset to swap
     * @param expected to find
     * @param value to set if the expected is found.
     * @return true, it the expected was replaced with the value
     */
    boolean compareAndSwapShort(long offset, short expected, short value);

    /**
     * Compare and swap an int as an atomic operation
     *
     * @param offset to swap
     * @param expected to find
     * @param value to set if the expected is found.
     * @return true, it the expected was replaced with the value
     */
    boolean compareAndSwapInt(long offset, int expected, int value);

    /**
     * Compare and swap a long as an atomic operation
     *
     * @param offset to swap
     * @param expected to find
     * @param value to set if the expected is found.
     * @return true, it the expected was replaced with the value
     */
    boolean compareAndSwapLong(long offset, long expected, long value);

    /**
     * Compare and swap a float as an atomic operation
     *
     * @param offset to swap
     * @param expected to find
     * @param value to set if the expected is found.
     * @return true, it the expected was replaced with the value
     */
    boolean compareAndSwapFloat(long offset, float expected, float value);

    /**
     * Compare and swap a double as an atomic operation
     *
     * @param offset to swap
     * @param expected to find
     * @param value to set if the expected is found.
     * @return true, it the expected was replaced with the value
     */
    boolean compareAndSwapDouble(long offset, double expected, double value);

    /**
     * add and get an int as an atomic operation
     *
     * @param offset to add to
     * @param toAdd value
     * @return the sum
     */
    int addAndGetInt(long offset, int toAdd);

    /**
     * get and add an int as an atomic operation
     *
     * @param offset to add to
     * @param toAdd value
     * @return the sum
     */
    int getAndAddInt(long offset, int toAdd);

    /**
     * add and get a long as an atomic operation
     *
     * @param offset to add to
     * @param toAdd value
     * @return the sum
     */
    long addAndGetLong(long offset, long toAdd);

    /**
     * get and add a long as an atomic operation
     *
     * @param offset to add to
     * @param toAdd value
     * @return the sum
     */
    long getAndAddLong(long offset, long toAdd);

    /**
     * add and get a long as an atomic operation
     *
     * @param offset to add to
     * @param toAdd value
     * @return the sum
     */
    float addAndGetFloat(long offset, float toAdd);

    /**
     * get and add a long as an atomic operation
     *
     * @param offset to add to
     * @param toAdd value
     * @return the sum
     */
    float getAndAddFloat(long offset, float toAdd);

    /**
     * add and get a long as an atomic operation
     *
     * @param offset to add to
     * @param toAdd value
     * @return the sum
     */
    double addAndGetDouble(long offset, double toAdd);

    /**
     * get and add a long as an atomic operation
     *
     * @param offset to add to
     * @param toAdd value
     * @return the sum
     */
    double getAndAddDouble(long offset, double toAdd);
}
