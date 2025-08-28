package com.example.weather.resilience;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=test")
public class CircuitBreakerTest {
    @Autowired
    CircuitBreakerRegistry registry;

    @Test
    @DisplayName("CB 'accuWeather' abre tras fallas y permite recuperar")
    void circuitBehavior() throws Exception {
        var cb = registry.circuitBreaker("accuWeather"); // <— usa tu nombre real
        assertNotNull(cb, "No existe CB 'accuWeather' en el Registry");

        Callable<String> failing = CircuitBreaker.decorateCallable(cb,
                () -> { throw new RuntimeException("upstream timeout"); });

        for (int i = 0; i < 10; i++) {
            try { failing.call(); } catch (Exception ignored) {}
            if (cb.getState() == CircuitBreaker.State.OPEN) break;
        }
        assertEquals(CircuitBreaker.State.OPEN, cb.getState(), "Debe abrirse tras umbral de fallas");

        Thread.sleep(Duration.ofSeconds(3).toMillis()); // >= waitDurationInOpenState (ver yml test)

        Callable<String> ok = CircuitBreaker.decorateCallable(cb, () -> "OK");
        String res = ok.call();
        assertEquals("OK", res);

        assertTrue(cb.getState() == CircuitBreaker.State.HALF_OPEN
                || cb.getState() == CircuitBreaker.State.CLOSED);
    }
}
