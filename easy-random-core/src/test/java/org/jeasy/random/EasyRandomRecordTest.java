/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.nio.charset.StandardCharsets;

import org.jeasy.random.beans.MyClass;
import org.jeasy.random.beans.MyRecord;
import org.jeasy.random.beans.SimpleClass;
import org.jeasy.random.beans.SimpleRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class EasyRandomRecordTest {

  protected EasyRandom easyRandom;

  @BeforeEach
  void setupEasyRandomInstance() {
    EasyRandomParameters randomParameters = new EasyRandomParameters()
        .seed(System.nanoTime())
        .objectPoolSize(100)
        .charset(StandardCharsets.UTF_8)
        .stringLengthRange(5, 50)
        .collectionSizeRange(1, 10)
        .scanClasspathForConcreteTypes(true)
        .overrideDefaultInitialization(true)
        .ignoreRandomizationErrors(false)        ;
    this.easyRandom = new EasyRandom(randomParameters);
  }

  @RepeatedTest(10)
  void usingEasyRandomWithRecords() {
    var myRecord = this.easyRandom.nextObject(MyRecord.class);
    assertAll(
        () -> assertThat(myRecord).isEqualTo(new MyRecord(myRecord)),
        () -> assertThat(myRecord.description()).isPresent(),
        () -> assertThat(myRecord.listString()).isNotEmpty()
    );
  }

  @RepeatedTest(1)
  void usingEasyRandomWithClasses() {
    var myClass = this.easyRandom.nextObject(MyClass.class);
    assertAll(
        () -> assertThat(myClass).isEqualTo(new MyClass(myClass)),
        () -> assertThat(myClass.getOptInteger()).isPresent(),
        () -> assertThat(myClass.getListString()).isNotEmpty()
    );
  }

  @RepeatedTest(10)
  void usingEasyRandomWithSimpleRecord() {
    var simpleRecord = this.easyRandom.nextObject(SimpleRecord.class);
    assertAll(
        () -> assertThat(simpleRecord).isEqualTo(new SimpleRecord(simpleRecord)),
        () -> assertThat(simpleRecord.id()).isNotNull(),
        () -> assertThat(simpleRecord.listString()).isNotEmpty()
    );
  }

  @RepeatedTest(1)
  void usingEasyRandomWithSimpleClass() {
    var simpleClass = this.easyRandom.nextObject(SimpleClass.class);
    assertAll(
        () -> assertThat(simpleClass).isEqualTo(new SimpleClass(simpleClass)),
        () -> assertThat(simpleClass.getListString()).isNotEmpty()
    );
  }
}