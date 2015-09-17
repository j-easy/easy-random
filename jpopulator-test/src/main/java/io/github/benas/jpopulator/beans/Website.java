package io.github.benas.jpopulator.beans;

import java.net.URI;
import java.net.URL;

public class Website {

    private String name;

    private URL url;

    private URI uri;

    public Website() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
