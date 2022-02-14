package es.jaime.gateway._shared.domain;

import java.io.InputStream;

public interface FileResolver {
    InputStream resolve(String file);
}
