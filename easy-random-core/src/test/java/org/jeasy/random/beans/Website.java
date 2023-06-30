/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.beans;

import java.net.URI;
import java.net.URL;

public class Website {

    private String name;

    private URL url;

    private URI uri;

    @Deprecated
    private String provider;

	public Website() {
	}

	public String getName() {
		return this.name;
	}

	public URL getUrl() {
		return this.url;
	}

	public URI getUri() {
		return this.uri;
	}

	@Deprecated
	public String getProvider() {
		return this.provider;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	@Deprecated
	public void setProvider(String provider) {
		this.provider = provider;
	}
}
