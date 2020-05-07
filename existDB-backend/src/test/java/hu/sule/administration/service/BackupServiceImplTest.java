package hu.sule.administration.service;

import hu.sule.administration.model.BackupEntity;
import hu.sule.administration.queries.ExistDbBackupsAndRestoreQueries;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackupServiceImplTest {

    @Autowired
    private BackupService backupService;

    @MockBean
    private ExistDbBackupsAndRestoreQueries existDbBackupsAndRestoreQueries;

    @Test
    public void getBackups() throws JDOMException, IOException {
        ArrayList<BackupEntity> testList = new ArrayList<>();
        testList.add(new BackupEntity("full20200415-1339.zip","1","2020-04-15T13:39:15.219Z","no",0));
        when(existDbBackupsAndRestoreQueries.getBackups(any(),any())).thenReturn("<directory xmlns=\"http://exist.sourceforge.net/NS/exist\">\n" +
                "<backup file=\"full20200415-1339.zip\">\n" +
                "<nr-in-sequence>1</nr-in-sequence>\n" +
                "<date>2020-04-15T13:39:15.219Z</date>\n" +
                "<incremental>no</incremental>\n" +
                "</backup>\n" +
                "</directory>\n");
        assertEquals(backupService.getBackups(""),testList);
    }

    @Test
    public void getBackupsQueryNoReturnValue(){
        when(existDbBackupsAndRestoreQueries.getBackups(any(),any())).thenReturn("");
        Exception exception = assertThrows(JDOMParseException.class, () -> {
            backupService.getBackups("");
        });
        String expectedMessage = "Error on line -1: Premature end of file.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createBackup() {
    }

    @Test
    public void restoreBackup() {
    }
}