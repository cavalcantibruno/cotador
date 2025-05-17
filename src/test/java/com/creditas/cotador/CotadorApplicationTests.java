package com.creditas.cotador;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {"app.timezone=America/Sao_Paulo"})
class CotadorApplicationTests {

    @Test
    void testStartedSetsDefaultTimeZone() {
        assertEquals("America/Sao_Paulo", TimeZone.getDefault().getID());
    }

}
