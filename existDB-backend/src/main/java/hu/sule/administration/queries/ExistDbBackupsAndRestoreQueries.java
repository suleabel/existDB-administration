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

    public String getBackups(ExistDetails details, String url) {
        String query = "xquery version \"3.1\";\n" +
                "import module namespace backups=\"http://exist-db.org/xquery/backups\" at \"java:org.exist.backup.xquery.BackupModule\";\n"+
                "declare variable $backupLocation := \"" + url + "\";\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        backups:list($backupLocation)\n" +
                "    )\n" +
                "else\n" +
                "false()";
        return util.stringResultQuery(details, query);
    }

    public String createBackup2(ExistDetails details, CreateBackupEntity createBackupEntity) {
        String query = "xquery version '3.1';\n" +
                "if(xmldb:login(\"" + details.getCollection() + "\" , \"" + details.getUsername() + "\", \"" + details.getPassword() + "\")) then\n" +
                "    (\n" +
                "        system:export(\"/exist/data/export\", " + createBackupEntity.getIsIncremental() + "(), " + createBackupEntity.getIsZip() + "())\n" +
                "    )\n" +
                "else\n" +
                "false()";
        System.out.println(query);
        return util.stringResultQuery(details,query);
    }

    public String restoreBackup(ExistDetails details, String path) {
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
}
