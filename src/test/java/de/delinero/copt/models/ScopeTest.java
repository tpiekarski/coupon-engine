package de.delinero.copt.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScopeTest {

    @Test
    public void testScopes() {
        assertEquals("application", Scope.APPLICATION);
        assertEquals("validation", Scope.VALIDATION);
    }

}
