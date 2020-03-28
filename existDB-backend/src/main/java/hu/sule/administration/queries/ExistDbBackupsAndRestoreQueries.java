package hu.sule.administration.queries;

import hu.sule.administration.model.CreateBackupEntity;
import hu.sule.administration.model.ExistDetails;
import hu.sule.administration.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.XMLDBException;

@Component
public class ExistDbBackupsAndRestoreQueries {

    @Autowired
    private Util util;

    public String getBackups(ExistDetails details, String url) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "declare variable $backupLocation := \"" + url + "\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        backups:list($backupLocation)\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String createBackup2(ExistDetails details, CreateBackupEntity createBackupEntity) throws XMLDBException {
        createBackupEntity.setZip(true);
        String query = "xquery version '3.1';\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        system:export(\"/exist/data/export\", " + createBackupEntity.isIncremental() + "(), " + createBackupEntity.isZip() + "())\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details,query);
    }

    public String restoreBackup(ExistDetails details, String path) throws XMLDBException {
        String query = "xquery version \"3.1\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" ,\"" + details.getUsername() + "\", \"" + details.getPassword()+ "\")) then\n" +
                "    (\n" +
                "        system:import(\"" + path + "\",\"" + details.getPassword() + "\",\"" + details.getPassword() + "\")\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details,query);
    }

//    public String createBackup(ExistDetails details, CreateBackupEntity createBackupEntity) throws XMLDBException {
//        System.out.println(createBackupEntity.toString());
//        String query = "xquery version \"3.1\";\n" +
//                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
//                "    (\n" +
//                "        let $zip := " + createBackupEntity.isZip() + "()\n" +
//                "        let $incremental := " + createBackupEntity.isIncremental() + "()\n" +
//                "        let $params :=\n" +
//                "            <parameters>\n" +
//                "                <param name=\"output\" value=\"export\"/>\n" +
//                "                <param name=\"backup\" value=\"yes\"/>\n" +
//                "                <param name=\"incremental\" value=\"{if ($incremental) then 'yes' else 'no'}\"/>\n" +
//                "                <param name=\"zip\" value=\"{if ($zip) then 'yes' else 'no'}\"/>\n" +
//                "            </parameters>\n" +
//                "        return (\n" +
//                "            system:trigger-system-task(\"org.exist.storage.ConsistencyCheckTask\", $params),\n" +
//                "            <response status=\"ok\"/>\n" +
//                "    )\n" +
//                "    )\n" +
//                "else\n" +
//                "false()";
//        return util.stringResultQuery(details, query);
//    }

}
