package devoxx.microframeworks.exos;

import devoxx.microframeworks.exos.services.AuthenticationService;
import devoxx.microframeworks.exos.services.Services;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.awaitInitialization;
import static spark.Spark.getInstance;

public interface TestUtils {

    /**
     * Meant to be called from a different thread, once the spark app is running
     * This is probably only going to be used during the integration testing process, not ever in prod!
     *
     * @return the port it's running on
     */
    static int awaitRunningPort() throws Exception {
        awaitInitialization();
        //I have to get the port via reflection, which is fugly, but the API doesn't exist :(
        //Since we'll only use this in testing, it's not going to kill us
        Object instance = getInstance();
        Class theClass = instance.getClass();
        Field serverField = theClass.getDeclaredField("server");
        serverField.setAccessible(true);
        Object oneLevelDeepServer = serverField.get(instance);

        Class jettyServerClass = oneLevelDeepServer.getClass();
        Field jettyServerField = jettyServerClass.getDeclaredField("server");
        jettyServerField.setAccessible(true);
        //Have to pull in the jetty server stuff to do this mess
        Server jettyServer = (Server) jettyServerField.get(oneLevelDeepServer);

        return ((ServerConnector) jettyServer.getConnectors()[0]).getLocalPort();
    }

    static String createToken(String email, String password) {
        AuthenticationService service = Services.INSTANCE.get(AuthenticationService.class);
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        Map<String, String> result = service.login(map);
        return result.get("token");
    }

    static byte[] getBytes(Object object) {
        String string = Configuration.INSTANCE.getGson().toJson(object);
        return string.getBytes();
    }
}
