package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object -> returns true
        assertEquals(remark, remark);

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertEquals(remarkCopy, remark);

        // different types -> returns false
        assertNotEquals(remark, 1);

        // null -> returns false
        assertNotEquals(remark, null);

        // different remark -> returns false
        Remark differentRemark = new Remark("Bye");
        assertNotEquals(differentRemark, remark);
    }
}
