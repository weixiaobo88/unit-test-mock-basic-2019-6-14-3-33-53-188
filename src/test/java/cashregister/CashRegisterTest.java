package cashregister;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CashRegisterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {
        //given
        CashRegister cashRegister = new CashRegister(new Printer());
        Item apple = new Item("Apple", 10);
        Item banana = new Item("Banana", 8);
        Item[] items = new Item[]{apple, banana};
        //when
        Purchase purchase = new Purchase(items);
        cashRegister.process(purchase);

        //then
        assertEquals(purchase.asString().trim(), outContent.toString().trim());
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {
        //given
        CashRegister cashRegister = new CashRegister(new Printer());
        Item apple = new Item("Apple", 10);
        Item banana = new Item("Banana", 8);
        Item[] items = new Item[]{apple, banana};

        //when
        Purchase purchase = new Purchase(items);
        cashRegister.process(purchase);

        //then
        assertEquals("Apple\t10.0\nBanana\t8.0", outContent.toString().trim());
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {
        //given
        Printer printer = mock(Printer.class);
        CashRegister cashRegister = new CashRegister(printer);
        Purchase purchase = new Purchase(new Item[]{});

        //when
        cashRegister.process(purchase);

        //then
        verify(printer).print(purchase.asString());
    }

}
