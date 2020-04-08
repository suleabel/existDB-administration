package hu.sule.administration.util;

import hu.sule.administration.model.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class Mappers {

    public static ArrayList<CreatedViewModel> mapCreatedViewModel(String input) throws IOException, JDOMException {
        ArrayList<CreatedViewModel> createdViewModels = new ArrayList<>();
        CreatedViewModel createdViewModel;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc  = saxBuilder.build(new InputSource(new StringReader(input)));
        if(doc != null) {
            Element result = doc.getRootElement();
            List<Element> views = result.getChildren();
            for (Element view: views) {
                createdViewModel = new CreatedViewModel(
                        view.getAttributeValue("user"),
                        view.getAttributeValue("date"),
                        view.getAttributeValue("config-location"),
                        view.getAttributeValue("query-name"),
                        view.getAttributeValue("view-location"));
                createdViewModels.add(createdViewModel);
            }
        }
        return createdViewModels;
    }

    public static ArrayList<BackupEntity> mapBackups(String input) throws JDOMException, IOException {
        ArrayList<BackupEntity> backupEntities = new ArrayList<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        doc = saxBuilder.build(new InputSource(new StringReader(input)));
        if(doc != null) {
            Element directory = doc.getRootElement();
            List<Element> backups = directory.getChildren();
            for (Element backup: backups) {
                List<Element> details = backup.getChildren();
                BackupEntity backupEntity = new BackupEntity();
                backupEntity.setFileName(backup.getAttributeValue("file"));
                backupEntity.setDownloadable(backupEntity.getFileName().contains(".zip"));
                for (Element detail: details) {
                    switch(detail.getName()){
                        case "nr-in-sequence":
                            backupEntity.setNrInSequence(detail.getValue());
                            break;
                        case "date":
                            backupEntity.setDate(detail.getValue());
                            break;
                        case "previous":
                            backupEntity.setPrevious(detail.getValue());
                            break;
                        case "incremental":
                            backupEntity.setIncremental(detail.getValue());
                            break;
                    }
                }
                backupEntities.add(backupEntity);
            }
        }
        return backupEntities;
    }

    public static ArrayList<ExistCollectionManagerModel> mapCollectionQueryResult(String input) throws IOException, JDOMException{
        ArrayList<ExistCollectionManagerModel> collectionManagerModels = new ArrayList<>();
        ExistCollectionManagerModel model = null;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(new InputSource(new StringReader(input)));
        if(doc != null) {
            Element result = doc.getRootElement();
            List<Element> exist = result.getChildren();
            for (Element element: exist) {
                if(element.getName().equals("collection")){
                    model = new ExistCollectionManagerModel(element.getAttributeValue("name"),element.getAttributeValue("path"),element.getAttributeValue("owner"),element.getAttributeValue("group"), element.getAttributeValue("mode"),element.getAttributeValue("date"),element.getAttributeValue("mime"),null,element.getAttributeValue("resource").equals("true"), false);
                }else if(element.getName().equals("resource")){
                    model = new ExistCollectionManagerModel(element.getAttributeValue("name"),element.getAttributeValue("path"),element.getAttributeValue("owner"),element.getAttributeValue("group"), element.getAttributeValue("mode"),element.getAttributeValue("date"),element.getAttributeValue("mime"),element.getAttributeValue("locked"),element.getAttributeValue("resource").equals("true"), false);
                }
                collectionManagerModels.add(model);
            }
        }
        return collectionManagerModels;
    }

    public static ArrayList<ExistDBGroup> mapGroupsQueryResult(String input) throws JDOMException, IOException {
        ArrayList<ExistDBGroup> existDBGroups = new ArrayList<>();
        ExistDBGroup existDBGroup;
        ArrayList<String> groupMembers;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        doc = saxBuilder.build(new InputSource(new StringReader(input)));
        if (doc != null) {
            Element result = doc.getRootElement();
            List<Element> groups = result.getChildren();
            for (Element group : groups) {
                groupMembers = new ArrayList<>();
                existDBGroup = new ExistDBGroup(group.getChildText("name"), group.getChildText("manager"), group.getChildText("desc"), false);
                Element members = group.getChild("groupMembers");
                List<Element> membersList = members.getChildren();
                for (Element member : membersList) {
                    groupMembers.add(member.getValue());
                }
                existDBGroup.setGroupMembers(groupMembers);
                existDBGroups.add(existDBGroup);
            }
        }
        return existDBGroups;
    }

    public static ArrayList<ExistDBUser> mapUsersQueryResult(String input) throws JDOMException, IOException {
        ArrayList<ExistDBUser> existDBUsers = new ArrayList<>();
        ExistDBUser existDBUser;
        ArrayList<String> userGroups;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        doc = saxBuilder.build(new InputSource(new StringReader(input)));
        if (doc != null) {
            Element result = doc.getRootElement();
            List<Element> users = result.getChildren();
            for (Element user : users) {
                userGroups = new ArrayList<>();
                existDBUser = new ExistDBUser(
                        user.getChildText("username"), user.getChildText("umask"), user.getChildText("primaryGroups"),
                        user.getChildText("fullName"), user.getChildText("desc"), false, true);
                Element groups = user.getChild("groups");
                List<Element> groupList = groups.getChildren();
                for (Element group : groupList) {
                    userGroups.add(group.getValue());
                }
                existDBUser.setGroups(userGroups);
                existDBUsers.add(existDBUser);
            }
        }
        return existDBUsers;
    }

    public static VersionsModel mapVersions(String input) throws JDOMException, IOException {
        VersionsModel versionsModel = new VersionsModel();
        ArrayList<ReversionsModel> reversionsModels = new ArrayList<>();
        ReversionsModel reversionsModel;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        doc = saxBuilder.build(new InputSource(new StringReader(input)));
        if (doc != null) {
            Element root = doc.getRootElement();
            List<Element> data = root.getChildren();
            for (Element node : data) {
                if("document".equals(node.getName())){
                    versionsModel.setDoc(node.getValue());
                }
                if("revisions".equals(node.getName())){
                    List<Element> reversions = node.getChildren();
                    for (Element reversion : reversions) {
                        reversionsModel = new ReversionsModel(reversion.getAttributeValue("rev"),reversion.getChildren().get(0).getValue(),reversion.getChildren().get(1).getValue());
                        reversionsModels.add(reversionsModel);
                    }
                }
            }
            versionsModel.setReversions(reversionsModels);
        }
        return versionsModel;
    }

    public static List<FileManagerEntity> mapFileManagerContant(String input) throws IOException, JDOMException{
        List<FileManagerEntity> fileManagerEntities = new ArrayList<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        Namespace ns = Namespace.getNamespace("http://exist-db.org/collection-config/1.0");
        doc = saxBuilder.build(new InputSource(new StringReader(input)));
        if (doc != null) {
            Element list = doc.getRootElement();
            List<Element> content = list.getChildren();
            for (Element file : content) {
                FileManagerEntity fme = null;
                if (file.getName().equals("file")) {
                    fme = new FileManagerEntity(true, file.getAttributeValue("name"), file.getAttributeValue("size"), file.getAttributeValue("human-size"), file.getAttributeValue("modified"), file.getAttributeValue("hidden").equals("true"), file.getAttributeValue("canRead").equals("true"), file.getAttributeValue("canWrite").equals("true"));
                } else if (file.getName().equals("directory")) {
                    fme = new FileManagerEntity(false, file.getAttributeValue("name"), file.getAttributeValue("size"), file.getAttributeValue("human-size"), file.getAttributeValue("modified"), file.getAttributeValue("hidden").equals("true"), file.getAttributeValue("canRead").equals("true"), file.getAttributeValue("canWrite").equals("true"));
                }
                fileManagerEntities.add(fme);
            }
        }
        return fileManagerEntities;
    }

}
