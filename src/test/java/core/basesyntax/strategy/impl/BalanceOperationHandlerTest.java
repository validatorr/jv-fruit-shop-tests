package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final int ZERO_QUANTITY = 0;
    private static final int VALID_QUANTITY = 10;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(FRUIT);
        fruitTransaction.setQuantity(ZERO_QUANTITY);
    }

    @Test
    void quantityZeroOrLess_notOk() {
        assertThrows(FruitShopException.class, () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void correctTrasaction_Ok() {
        fruitTransaction.setQuantity(VALID_QUANTITY);
        operationHandler.handle(fruitTransaction);
        int actual = Storage.STORAGE.get(FRUIT);
        assertEquals(VALID_QUANTITY, actual);
    }
}