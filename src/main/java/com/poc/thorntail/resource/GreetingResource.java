package com.poc.thorntail.resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.poc.thorntail.ApplicationConfig;
//import com.poc.thorntail.dbentities.GreetingEntity;
import com.poc.thorntail.model.Greeting;
import com.poc.thorntail.service.GreetingService;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.Content;

@Path("/")
public class GreetingResource {

	@Inject
	private JPAFactory jpaFactory;

	private static final String template = "Hello, %s!";


//
	@Inject
	@ConfigProperty(name = "office")
	private Optional<String> office;
//
	@Inject
	@ConfigProperty(name = "profession")
	private Optional<String> profession;
//
//
	@Inject
	@ConfigProperty(name = "db.username")
	private String dbUserName;
//
	@Inject
	@ConfigProperty(name = "db.password")
	private String dbPassword;

	@Inject
	private GreetingService service;

	@GET @Path("/greeting")@Produces("application/json")
	 @Retry(delay = 200, maxRetries = 2,retryOn =Exception.class)
	 @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.75, delay =
	 1000, successThreshold = 10)
	 @Fallback(fallbackMethod = "greetingFallback")
	
	@Operation(summary = "Get Greeting", description = "This will just return greeting")
	@APIResponse(responseCode = "200", description = "All Greetings", content = @Content(schema = @Schema(implementation = Greeting.class)))

	public List<Greeting> getGreetings(
			@Parameter(name = "name", description = "id of the person that should be retrieved", schema = @Schema(type = SchemaType.OBJECT), required = true)

			@QueryParam("name") String name) throws SQLException {

		System.out.println(
				"Printing configmap values---------------office="  + office + " profession=" + profession);
		
//		System.out.println("POSTGRESQL_DATABASE=" + System.getenv("POSTGRESQL_DATABASE"));
//		System.out.println("POSTGRESQL_SERVICE_HOST=" + System.getenv("POSTGRESQL_SERVICE_HOST"));
//		System.out.println("POSTGRESQL_SERVICE_PORT=" + System.getenv("POSTGRESQL_SERVICE_PORT"));
//		System.out.println("POSTGRESQL_USER=" + System.getenv("POSTGRESQL_USER"));
		if (!ApplicationConfig.IS_ALIVE.get()) {
			throw new WebApplicationException(Response.Status.SERVICE_UNAVAILABLE);
		}
		if ("deb".equalsIgnoreCase(name)) {
			throw new WebApplicationException("Invalid Name");
		}
		System.out.println("dbUserName="+dbUserName+"dbPassword="+dbPassword);
		return service.getGreetings(dbUserName, dbPassword);

	}

	public List<Greeting> greetingFallback(String name) {
		System.out.println("Fallback invoked---------------------------------");
		Greeting greeting = new Greeting("FallBackContent");
		List<Greeting> list = new ArrayList<Greeting>();
		list.add(greeting);		
		return list;
	}

}
