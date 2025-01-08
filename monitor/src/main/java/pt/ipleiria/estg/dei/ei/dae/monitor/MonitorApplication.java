package pt.ipleiria.estg.dei.ei.dae.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

@ApplicationPath("/api")
public class MonitorApplication extends Application {
    @Provider
    public static class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
        private final ObjectMapper objectMapper;

        public ObjectMapperProvider() {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
        }

        @Override
        public ObjectMapper getContext(Class<?> type) {
            return objectMapper;
        }
    }
}