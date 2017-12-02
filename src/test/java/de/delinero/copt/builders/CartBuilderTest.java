package de.delinero.copt.builders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.exceptions.DeserializationException;
import de.delinero.copt.models.carts.Cart;
import de.delinero.copt.utils.FixtureLoader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CartBuilderTest {

    private ObjectMapper objectMapper;
    private CartBuilder cartBuilder;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        cartBuilder = new CartBuilder(objectMapper);
    }

    @Test
    public void testCartBuilderIsBuildingCarts() throws JsonProcessingException {
        String payload = FixtureLoader.loadAsString("/builders/cart.json");
        Cart testCart = cartBuilder.build(payload);

        assertEquals(new Integer(100), testCart.getValue());
        assertEquals("ABC", testCart.getCode());

        assertEquals(payload, objectMapper.writeValueAsString(testCart));
    }

    @Test
    public void testCartBuilderIsThrowingDeserializationException() {
        String invalidPayload = "{\"someField\":\"someValue\"}";

        exception.expect(DeserializationException.class);
        cartBuilder.build(invalidPayload);
    }
}
