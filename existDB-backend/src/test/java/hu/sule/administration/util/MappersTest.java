package hu.sule.administration.util;

import hu.sule.administration.model.*;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MappersTest {

    @Test
    public void mapCreatedViewModel() {
    }

    @Test
    public void mapBackups() throws IOException, JDOMException {
        ArrayList<BackupEntity> testList = new ArrayList<>();
        testList.add(new BackupEntity("full20200415-1339.zip","1","2020-04-15T13:39:15.219Z","no",0));
        assertEquals(Mappers.mapBackups(
                "<directory xmlns=\"http://exist.sourceforge.net/NS/exist\">\n" +
                        "<backup file=\"full20200415-1339.zip\">\n" +
                        "<nr-in-sequence>1</nr-in-sequence>\n" +
                        "<date>2020-04-15T13:39:15.219Z</date>\n" +
                        "<incremental>no</incremental>\n" +
                        "</backup>\n" +
                        "</directory>"), testList);
    }

    @Test
    public void mapBackupsNoInput(){
        Exception exception = assertThrows(JDOMParseException.class, () -> {
            Mappers.mapBackups("");
        });
        String expectedMessage = "Error on line -1: Premature end of file.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void mapCollectionQueryResult() throws IOException, JDOMException {
        ArrayList<ExistCollectionManagerModel> testList = new ArrayList<>();
        testList.add(new ExistCollectionManagerModel("test","/db","admin","dba","rwxr-xr-x","","",false,false));
        assertEquals(Mappers.mapCollectionQueryResult(
                "<result>\n" +
                "<exist:collection xmlns:exist=\"http://exist.sourceforge.net/NS/exist\" name=\"test\" path=\"/db\" owner=\"admin\" group=\"dba\" writeable=\"true\" mode=\"rwxr-xr-x\" mime=\"\" date=\"\" resource=\"false\"/>\n" +
                "</result>"),testList);
    }

    @Test
    public void mapCollectionQueryResultNoInput(){
        Exception exception = assertThrows(JDOMParseException.class, () -> {
            Mappers.mapCollectionQueryResult("");
        });
        String expectedMessage = "Error on line -1: Premature end of file.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void mapGroupsQueryResult() throws IOException, JDOMException{
        ArrayList<ExistDBGroup> testList = new ArrayList<>();
        List<String> members = new ArrayList<>();
        members.add("SYSTEM");
        members.add("admin");
        testList.add(new ExistDBGroup("dba","admin","Database Administrators",members,false));
        assertEquals(Mappers.mapGroupsQueryResult("<groups>\n" +
                "<group>\n" +
                "<name>dba</name>\n" +
                "<manager>admin</manager>\n" +
                "<desc>Database Administrators</desc>\n" +
                "<groupMembers><member>SYSTEM</member>\n" +
                "<member>admin</member></groupMembers>\n" +
                "</group>\n" +
                "</groups>"),testList);
    }

    @Test
    public void mapGroupsQueryResultNoInput(){
        Exception exception = assertThrows(JDOMParseException.class, () -> {
            Mappers.mapGroupsQueryResult("");
        });
        String expectedMessage = "Error on line -1: Premature end of file.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void mapUsersQueryResult() throws IOException, JDOMException {
        ArrayList<ExistDBUser> testList = new ArrayList<>();
        List<String> groups= new ArrayList<>();
        groups.add("dba");
        testList.add(new ExistDBUser("SYSTEM",groups,"18","dba","SYSTEM","System Internals","nan",false,true));
        assertEquals(Mappers.mapUsersQueryResult("<users><user><username>SYSTEM</username><primaryGroups>dba</primaryGroups><fullName>SYSTEM</fullName><groups><group>dba</group></groups><umask>18</umask><desc>System Internals</desc><isEnabled>true</isEnabled></user></users>"),testList);
    }
    @Test
    public void mapUsersQueryResultNoInput(){
        Exception exception = assertThrows(JDOMParseException.class, () -> {
            Mappers.mapUsersQueryResult("");
        });
        String expectedMessage = "Error on line -1: Premature end of file.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void mapVersions() throws IOException, JDOMException {
        ArrayList<ReversionsModel> reversionsModels = new ArrayList<>();
        reversionsModels.add(new ReversionsModel("3","2020-04-15T14:52:48.926Z","admin"));
        VersionsModel versionsModel = new VersionsModel("/db/createdViews.xml",reversionsModels);
        assertEquals(Mappers.mapVersions("<v:history xmlns:v=\"http://exist-db.org/versioning\">\n" +
                "<v:document>/db/createdViews.xml</v:document>\n" +
                "<v:revisions>\n" +
                "<v:revision rev=\"3\">\n" +
                "<v:date>2020-04-15T14:52:48.926Z</v:date>\n" +
                "<v:user>admin</v:user>\n" +
                "</v:revision>\n" +
                "</v:revisions>\n" +
                "</v:history>"), versionsModel);
    }

    @Test
    public void mapVersionsNoInput(){
        Exception exception = assertThrows(JDOMParseException.class, () -> {
            Mappers.mapVersions("");
        });
        String expectedMessage = "Error on line -1: Premature end of file.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void mapFileManagerContent() throws IOException, JDOMException {
        ArrayList<FileManagerEntity> testList = new ArrayList<>();
        testList.add(new FileManagerEntity(true, "start.jar","19617","19KB","2019-03-02T11:20:13Z",false,true,true));
        assertEquals(Mappers.mapFileManagerContant("<list>\n" +
                "<file:file xmlns:file=\"http://exist-db.org/xquery/file\" name=\"start.jar\" size=\"19617\" human-size=\"19KB\" modified=\"2019-03-02T11:20:13Z\" hidden=\"false\" canRead=\"true\" canWrite=\"true\"/>\n" +
                "</list>\n"),testList);
    }

    @Test
    public void mapFileManagerContentNoInput(){
        Exception exception = assertThrows(JDOMParseException.class, () -> {
            Mappers.mapFileManagerContant("");
        });
        String expectedMessage = "Error on line -1: Premature end of file.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}