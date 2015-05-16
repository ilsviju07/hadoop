/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.io.erasurecode.rawcoder;

import org.junit.Before;
import org.junit.Test;

/**
 * Test XOR encoding and decoding.
 */
public class TestXORRawCoder extends TestRawCoderBase {

  @Before
  public void setup() {
    this.encoderClass = XORRawEncoder.class;
    this.decoderClass = XORRawDecoder.class;

    this.numDataUnits = 10;
    this.numParityUnits = 1;
  }

  @Test
  public void testCodingNoDirectBuffer_erasing_d0() {
    prepare(null, 10, 1, new int[] {0}, new int[0]);

    /**
     * Doing twice to test if the coders can be repeatedly reused. This matters
     * as the underlying coding buffers are shared, which may have bugs.
     */
    testCoding(false);
    testCoding(false);
  }

  @Test
  public void testCodingDirectBuffer_erasing_p0() {
    prepare(null, 10, 1, new int[0], new int[] {0});

    testCoding(true);
    testCoding(true);
  }

  @Test
  public void testCodingDirectBuffer_erasing_d0() {
    prepare(null, 10, 1, new int[] {0}, new int[0]);

    testCoding(true);
    testCoding(true);
  }

  @Test
  public void testCodingBothBuffers_erasing_d5() {
    prepare(null, 10, 1, new int[]{5}, new int[0]);

    /**
     * Doing in mixed buffer usage model to test if the coders can be repeatedly
     * reused with different buffer usage model. This matters as the underlying
     * coding buffers are shared, which may have bugs.
     */
    testCoding(true);
    testCoding(false);
    testCoding(true);
    testCoding(false);
  }

  @Test
  public void testCodingNegative_erasing_d5() {
    prepare(null, 10, 1, new int[]{5}, new int[0]);

    testCodingWithBadInput(true);
    testCodingWithBadOutput(false);
    testCodingWithBadInput(true);
    testCodingWithBadOutput(false);
  }
}
