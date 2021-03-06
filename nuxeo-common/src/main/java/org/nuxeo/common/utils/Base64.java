/*
 * (C) Copyright 2006-2012 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     bstefanescu
 */
package org.nuxeo.common.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simple wrapper around codec library to preserve backward compatibility
 *
 * @author bstefanescu
 * @deprecated Since 5.6. Use {@link org.apache.commons.codec.binary.Base64} instead.
 */
@Deprecated
public class Base64 {

    private static final Log log = LogFactory.getLog(Base64.class);

    public final static String encodeBytes(byte[] source) {
        try {
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(source), "US-ASCII").trim();
        } catch (UnsupportedEncodingException e) {
            log.error(e);
            return "";
        }
    }

    public final static byte[] decode(String s) {
        return org.apache.commons.codec.binary.Base64.decodeBase64(s);
    }

}
