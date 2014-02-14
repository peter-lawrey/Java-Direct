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

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * A ByteFactory creates a new Bytes buffer.  Some options are provided as performance hints and may be ignored in some environments.
 */
public interface BytesFactory {
    /**
     * <p>This <i>hints</i> that bounds checking should be kept to a minimum for performance reasons.</p>
     * <p>The value is always treated as true when assertions are enabled</p>
     *
     * @param boundsChecking perform bounds checking if true
     * @return this BytesFactory
     */
    BytesFactory boundsChecking(boolean boundsChecking);

    boolean boundsChecking();

    /**
     * <p>This <i>hints</i> that deallocation checking should be kept to a minimum for performance reasons.</p>
     * <p>The value is always treated as true when assertions are enabled</p>
     *
     * @param deallocationChecks perform deallocation checking if true
     * @return this BytesFactory
     */
    BytesFactory deallocationChecks(boolean deallocationChecks);

    boolean deallocationChecks();

    /**
     * When true, a Bytes is only released by the GC, when false a Byte can be released deterministically when there is no more references.
     * <p>
     * Whether this is true, or false, is it illegal to access a Bytes once it's reference count reaches zero.
     * </p>
     *
     * @param freeOnGC if true, the underlying storage for these bytes is only released by the GC.
     * @return this. BytesFactory.
     */
    BytesFactory freeOnGC(boolean freeOnGC);

    boolean freeOnGC();

    /**
     * Create a Bytes instance which wraps this byte array
     *
     * @param byteOrder to used, ByteOrder.nativeOrder() is the default order of your system.
     * @param bytes     to wrap.
     * @return Bytes instance
     */
    Bytes wrap(ByteOrder byteOrder, byte[] bytes);

    /**
     * Create a Bytes instance which wraps this ByteBuffer
     *
     * @param byteOrder  to used, ByteOrder.nativeOrder() is the default order of your system.
     * @param byteBuffer to wrap.
     * @return Bytes instance
     */
    Bytes wrap(ByteOrder byteOrder, ByteBuffer byteBuffer);

    /**
     * Create a Bytes instance which wraps this byte array
     *
     * @param byteOrder to used, ByteOrder.nativeOrder() is the default order of your system.
     * @param size      of Bytes to create.
     * @return Bytes instance
     */
    Bytes create(ByteOrder byteOrder, long size);

    /**
     * Memory Map a file channel into memory
     *
     * @param fileChannel to map into memory
     * @param offset within the file
     * @param size of mapping.
     * @return the Bytes representing this mapping.
     */
    Bytes map(FileChannel fileChannel, long offset, long size);

    /**
     * Memory Map a file into memory
     *
     * @param file to map into memory
     * @param size of mapping.
     * @return the Bytes representing this mapping.
     */
    Bytes map(File file, long size);
}
