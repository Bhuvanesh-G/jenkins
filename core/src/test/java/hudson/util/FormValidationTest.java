/*
 * The MIT License
 *
 * Copyright (c) 2010, Seiji Sogabe
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author sogabe
 */
public class FormValidationTest {

    @Test
    public void testValidateRequired_OK() {
        FormValidation actual = FormValidation.validateRequired("Name");
        assertEquals(FormValidation.ok(), actual);
    }

    @Test
    public void testValidateRequired_Null() {
        FormValidation actual = FormValidation.validateRequired(null);
        assertNotNull(actual);
        assertEquals(FormValidation.Kind.ERROR, actual.kind);
    }

    @Test
    public void testValidateRequired_Empty() {
        FormValidation actual = FormValidation.validateRequired("  ");
        assertNotNull(actual);
        assertEquals(FormValidation.Kind.ERROR, actual.kind);
    }

    // @Bug(7438)
    @Test
    public void testMessage() {
        assertEquals("test msg", FormValidation.errorWithMarkup("test msg").getMessage());
    }
}
