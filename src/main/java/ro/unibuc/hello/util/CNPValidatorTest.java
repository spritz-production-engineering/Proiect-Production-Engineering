package ro.unibuc.hello.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CNPValidatorTest {

    @Test
    public void testValidCNP() {
        String validCnp = "1980917400014"; // CNP valid
        assertTrue(CNPValidator.isValidCNP(validCnp));
    }

    @Test
    public void testInvalidCNP_Length() {
        String invalidCnp = "19809174000"; // doar 11 cifre
        assertFalse(CNPValidator.isValidCNP(invalidCnp));
    }

    @Test
    public void testInvalidCNP_NonNumeric() {
        String invalidCnp = "19809A7400014";
        assertFalse(CNPValidator.isValidCNP(invalidCnp));
    }

    @Test
    public void testInvalidCNP_ControlDigit() {
        String invalidCnp = "1980917400010"; // cf de control gresita
        assertFalse(CNPValidator.isValidCNP(invalidCnp));
    }

    @Test
    public void testInvalidCNP_InvalidDate() {
        String invalidCnp = "1980232400014"; // 32 februarie
        assertFalse(CNPValidator.isValidCNP(invalidCnp));
    }

    @Test
    public void testInvalidCNP_InvalidCounty() {
        String invalidCnp = "1980917990014"; // judet > 52
        assertFalse(CNPValidator.isValidCNP(invalidCnp));
    }
}
