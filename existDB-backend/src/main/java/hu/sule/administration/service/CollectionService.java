package hu.sule.administration.service;

import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ResourceReadModel;
import hu.sule.administration.queries.ExistDbCollectionManagerQueries;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


@Service
public class CollectionService {

    @Autowired
    private ExistDbCollectionManagerQueries existDbCollectionManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(CollectionService.class);

    public ArrayList<ExistCollectionManagerModel> getFileManagerCollectionsByCollection(String collection) {
        String[] collections = existDbCollectionManagerQueries.getCollectionContent(ExistDbCredentialsService.getDetails(), collection).split("\n");
        return getCollections(collection, collections);
    }

    public ArrayList<ExistCollectionManagerModel> getFileManagerContentByCollection(String collection) {
//        String[] collections = existDbCollectionManagerQueries.getCollectionContent(ExistDbCredentialsService.getDetails(), collection).split("\n");
//        String[] resources = existDbCollectionManagerQueries.getCollectionResources(ExistDbCredentialsService.getDetails(), collection).split("\n");
//        ArrayList<ExistCollectionManagerModel> existCollectionManagerModels = new ArrayList<>(getCollections(collection, collections));
//        existCollectionManagerModels.addAll(getResources(collection, resources));
        return mapCollectionQueryResult(existDbCollectionManagerQueries.getCollectionContent2(ExistDbCredentialsService.getDetails(), collection));
    }

    private ArrayList<ExistCollectionManagerModel> getResources(String collection, String[] resources) {
        ArrayList<ExistCollectionManagerModel> existCollectionManagerModels = new ArrayList<>();
        if (!resources[0].equals("")) {
            for (String res : resources) {
                String[] ResData = existDbCollectionManagerQueries.getResourceData(ExistDbCredentialsService.getDetails(), collection, res).split("\n");
                ExistCollectionManagerModel existCollectionManagerModel = new ExistCollectionManagerModel(res, collection, ResData[0], ResData[1], ResData[2].equals("true"), ResData[3], ResData[4], true, false);
                existCollectionManagerModels.add(existCollectionManagerModel);
            }
        }
        return existCollectionManagerModels;
    }

    private ArrayList<ExistCollectionManagerModel> getCollections(String collection, String[] collections) {
        ArrayList<ExistCollectionManagerModel> existCollectionManagerModels = new ArrayList<>();
        if (!collections[0].equals("")) {
            for (String col : collections) {
                String[] ResData = existDbCollectionManagerQueries.getResourceData(ExistDbCredentialsService.getDetails(), collection, col).split("\n");
                ExistCollectionManagerModel existCollectionManagerModel = new ExistCollectionManagerModel(col, collection, ResData[0], ResData[1], ResData[2].equals("true"), ResData[3], "", false, false);
                existCollectionManagerModels.add(existCollectionManagerModel);
            }
        }
        return existCollectionManagerModels;
    }

    public String createDir(ForStoreResourceAndColl storeResource) {
        return existDbCollectionManagerQueries.createCollection(ExistDbCredentialsService.getDetails(), storeResource);
    }

    public String Store(ForStoreResourceAndColl forStoreResourceAndColl){
        return existDbCollectionManagerQueries.saveResource(ExistDbCredentialsService.getDetails(), forStoreResourceAndColl);
    }

    public String saveEditedRes(ForStoreResourceAndColl forStoreResourceAndColl){
        return existDbCollectionManagerQueries.saveEditedRes(ExistDbCredentialsService.getDetails(), forStoreResourceAndColl);
    }

    public String deleteFile(ExistCollectionManagerModel existCollectionManagerModel) {
        return existDbCollectionManagerQueries.deleteResource(ExistDbCredentialsService.getDetails(), existCollectionManagerModel);
    }

    public String deleteCollection(ExistCollectionManagerModel existCollectionManagerModel){
        return existDbCollectionManagerQueries.removeCollection(ExistDbCredentialsService.getDetails(), existCollectionManagerModel);
    }

    public String editResCred(ExistCollectionManagerModel existCollectionManagerModel) {
        return existDbCollectionManagerQueries.editResCred(ExistDbCredentialsService.getDetails(), existCollectionManagerModel);
    }

    public ResourceReadModel readFile(String url) {
        ResourceReadModel resourceReadModel = new ResourceReadModel();
        resourceReadModel.setContent(existDbCollectionManagerQueries.readFile(ExistDbCredentialsService.getDetails(), url));
        resourceReadModel.setIsBinary(existDbCollectionManagerQueries.isBinary(ExistDbCredentialsService.getDetails(), url));
        return resourceReadModel;
    }

    private ArrayList<ExistCollectionManagerModel> mapCollectionQueryResult(String input){
        ArrayList<ExistCollectionManagerModel> collectionManagerModels = new ArrayList<>();
        ExistCollectionManagerModel model = null;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(input)));
        } catch (JDOMException | IOException e){
            logger.error("SAXBuilder exception: " + e.getMessage()) ;
        }
        if(doc != null) {
            Element result = doc.getRootElement();
            List<Element> exist = result.getChildren();
            for (Element element: exist) {
                if(element.getName().equals("collection")){
                    model = new ExistCollectionManagerModel(element.getAttributeValue("name"),element.getAttributeValue("path"),element.getAttributeValue("owner"),element.getAttributeValue("group"),element.getAttributeValue("writable").equals("true"), element.getAttributeValue("mode"),element.getAttributeValue("date"),element.getAttributeValue("resource").equals("true"), false);
                }else if(element.getName().equals("resource")){
                    model = new ExistCollectionManagerModel(element.getAttributeValue("name"),element.getAttributeValue("path"),element.getAttributeValue("owner"),element.getAttributeValue("group"),element.getAttributeValue("writable").equals("true"), element.getAttributeValue("mode"),element.getAttributeValue("date"),element.getAttributeValue("resource").equals("true"), false);
                }
                collectionManagerModels.add(model);
            }
        }
        return collectionManagerModels;
    }




//    public List<String> getCollectionFree(String collection) {
//        List<String> collectionTree = new ArrayList<>();
//        collectionTree.add(collection);
//        for (String col : getCollectionChilds(collection)) {
//            collectionTree.addAll(getCollectionFree(col));
//        }
//        return collectionTree;
//    }
//
//    public List<String> getCollectionChilds(String col) {
//        List<String> result = new ArrayList<>();
//        for (String child : Arrays.asList(existDbCollectionManagerQueries.getCollectionContent(ExistDbCredentialsService.getDetails(), col).split("\n"))) {
//            result.add(col + "/" + child);
//        }
//        return result;
//    }
}
