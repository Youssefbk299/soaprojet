import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Standalone launcher for the Gestion_Options_Etudiants REST API.
 *
 * Run with Maven, then test the API at http://localhost:8080/rest/.
 * The existing RestActivator remains available for WAR/servlet deployment.
 */
public final class Main {
    private static final URI BASE_URI = URI.create("http://localhost:8080/rest/");

    private Main() {
        // Utility class; do not instantiate.
    }

    public static void main(String[] args) throws IOException {
        ResourceConfig config = new ResourceConfig()
                .packages("Ressources")
                .register(JacksonFeature.class);

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config, false);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow, "rest-api-shutdown"));

        try {
            server.start();
            System.out.println("Gestion Options Etudiants REST API started.");
            System.out.println("Base URL: " + BASE_URI);
            System.out.println("GET " + BASE_URI.resolve("etudiants"));
            System.out.println("GET " + BASE_URI.resolve("options"));
            System.out.println("Press Ctrl+C to stop the server.");
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            server.shutdownNow();
        } catch (IOException | RuntimeException e) {
            server.shutdownNow();
            throw e;
        }
    }
}
