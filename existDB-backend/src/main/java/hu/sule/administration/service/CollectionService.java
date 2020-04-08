package hu.sule.administration.service;

import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ResourceReadModel;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
@Service
public interface CollectionService {

    ArrayList<ExistCollectionManagerModel> getFileManagerCollectionsByCollection(String collection) throws IOException;
    ArrayList<ExistCollectionManagerModel> getFileManagerContentByCollection(String collection) throws IOException;
    String createDir(ForStoreResourceAndColl storeResource);
    String Store(ForStoreResourceAndColl forStoreResourceAndColl);
    String saveEditedRes(ForStoreResourceAndColl forStoreResourceAndColl);
    String deleteFile(ExistCollectionManagerModel existCollectionManagerModel);
    String deleteCollection(ExistCollectionManagerModel existCollectionManagerModel);
    String editResCred(ExistCollectionManagerModel existCollectionManagerModel);
    String unlockResource(String url);
    ResourceReadModel readFile(String url) throws JDOMException, IOException;
    ArrayList<ExistCollectionManagerModel> mapCollectionQueryResult(String input) throws IOException;
    boolean resourceIsAvailable(String path);
    boolean collectionIsAvailable(String path);

}
