package hu.sule.administration.service;

import hu.sule.administration.model.ExistFileManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ResourceReadModel;
import hu.sule.administration.queries.ExistDbCollectionManagerQueries;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CollectionService {

    @Autowired
    private ExistDbCollectionManagerQueries existDbCollectionManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(CollectionService.class);

    public ArrayList<ExistFileManagerModel> getCollectionContainedCollections(String collection) {
        String[] collections = existDbCollectionManagerQueries.getCollectionContent(ExistDbCredentialsService.getDetails(), collection).split("\n");
        return getCollections(collection, collections);
    }

    public ArrayList<ExistFileManagerModel> getFileManagerContentByCollection(String collection) {
        String[] collections = existDbCollectionManagerQueries.getCollectionContent(ExistDbCredentialsService.getDetails(), collection).split("\n");
        String[] resources = existDbCollectionManagerQueries.getCollectionResources(ExistDbCredentialsService.getDetails(), collection).split("\n");
        ArrayList<ExistFileManagerModel> existFileManagerModels = new ArrayList<>(getCollections(collection, collections));
        existFileManagerModels.addAll(getResources(collection, resources));
        return existFileManagerModels;
    }

    private ArrayList<ExistFileManagerModel> getResources(String collection, String[] resources) {
        ArrayList<ExistFileManagerModel> existFileManagerModels = new ArrayList<>();
        if (!resources[0].equals("")) {
            for (String res : resources) {
                String[] ResData = existDbCollectionManagerQueries.getResourceData(ExistDbCredentialsService.getDetails(), collection, res).split("\n");
                ExistFileManagerModel existFileManagerModel = new ExistFileManagerModel(res, collection, ResData[0], ResData[1], ResData[2].equals("true"), ResData[3], ResData[4], true, false);
                existFileManagerModels.add(existFileManagerModel);
            }
        }
        return existFileManagerModels;
    }

    private ArrayList<ExistFileManagerModel> getCollections(String collection, String[] collections) {
        ArrayList<ExistFileManagerModel> existFileManagerModels = new ArrayList<>();
        if (!collections[0].equals("")) {
            for (String col : collections) {
                String[] ResData = existDbCollectionManagerQueries.getResourceData(ExistDbCredentialsService.getDetails(), collection, col).split("\n");
                ExistFileManagerModel existFileManagerModel = new ExistFileManagerModel(col, collection, ResData[0], ResData[1], ResData[2].equals("true"), ResData[3], "", false, false);
                existFileManagerModels.add(existFileManagerModel);
            }
        }
        return existFileManagerModels;
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

    public String deleteFile(ExistFileManagerModel existFileManagerModel) {
        return existDbCollectionManagerQueries.deleteResource(ExistDbCredentialsService.getDetails(), existFileManagerModel);
    }

    public String deleteCollection(ExistFileManagerModel existFileManagerModel){
        return existDbCollectionManagerQueries.removeCollection(ExistDbCredentialsService.getDetails(), existFileManagerModel);
    }

    public String editResCred(ExistFileManagerModel existFileManagerModel) {
        return existDbCollectionManagerQueries.editResCred(ExistDbCredentialsService.getDetails(), existFileManagerModel);
    }

    public ResourceReadModel readFile(String url) {
        ResourceReadModel resourceReadModel = new ResourceReadModel();
        resourceReadModel.setContent(existDbCollectionManagerQueries.readFile(ExistDbCredentialsService.getDetails(), url));
        resourceReadModel.setIsBinary(existDbCollectionManagerQueries.isBinary(ExistDbCredentialsService.getDetails(), url));
        return resourceReadModel;
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
