package com.poc.thorntail;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.servers.ServerVariable;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Resource entry point
 */
@ApplicationPath("/api")

@OpenAPIDefinition(
        info = @Info(
                title = "Foo Application",
                version = "1.0.0",
                description = "Bar API",
                license = @License(
                        name = "Foo License",
                        url = "https://foo.bar/license"),
                contact = @Contact(
                        email = "foobar@foo.bar",
                        name = "Foo",
                        url = "https://foo.bar"),
                termsOfService = "https://foo.bar/terms"),
        security = @SecurityRequirement(
                name = "oauth2",
                scopes = "read:foobar"),
        externalDocs = @ExternalDocumentation(
                description = "Additional information",
                url = "https://bar.foo/info"),
        servers = @Server(
                description = "Foo description",
                url = "http://localhost:8080/{env}",
                variables = @ServerVariable(
                        name = "env",
                        description = "Server variable",
                        enumeration = {"dev", "prod"},
                        defaultValue = "dev")
                ),
        tags = @Tag(
                name = "Type",
                description = "API type")
        )
public class ApplicationConfig extends Application {
    public static final AtomicBoolean IS_ALIVE = new AtomicBoolean(true);
}


