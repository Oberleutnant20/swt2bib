package de.swt2bib.datenlogik;

import de.swt2bib.datenlogik.dao.AccountDAO;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author root
 */
public class AccountDAOTest {

    AccountDAO sut;

    public AccountDAOTest() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new AccountDAO();
    }

    @Test
    public void testLadeDb() throws Exception {
        //GIVEN
        //WHEN
        //THEN
        assertTrue(sut.laden() != null);
    }
}
