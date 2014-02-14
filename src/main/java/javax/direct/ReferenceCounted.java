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

/**
 * Signifies this resource is reference counted and can be cleaned up
 * deterministically when the reference count reached zero.  The reference count starts with 1.
 * <p>It is illegal to access this resource after the reference count has reached zero,
 * even if the underlying resource has not been freed</p>
 */
public interface ReferenceCounted {
    /**
     * Increment the reference count by one.
     *
     * @throws java.lang.IllegalStateException if the reference count has reached zero.
     */
    void reserve();

    /**
     * Decrements the reference count, unless it is already zero, in which case it throws an IllegalStateException.
     * <p>When the reference count reaches zero, the implementation may release underlying resources deterministically</p>
     *
     * @throws java.lang.IllegalStateException if the reference count was reached zero.
     */
    void release();

    /**
     * This returns the reference count.  Note this can be safely called even after the resource has been freed.
     *
     * @return the reference count.
     */
    int referenceCount();
}
