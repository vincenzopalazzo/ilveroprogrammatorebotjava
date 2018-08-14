package top.gigabox.persistenza;

import junit.framework.TestCase;
import top.gigabox.ilveroprogrammatore.persistenza.DAOFrasi;

public class DAOFrasiTest extends TestCase {

    public void testParsingDate(){
        DAOFrasi daoFrasi = new DAOFrasi();
        assertNotNull(daoFrasi.load(""));
    }
}
