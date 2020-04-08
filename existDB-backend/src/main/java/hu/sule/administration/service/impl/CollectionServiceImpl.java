package hu.sule.administration.service.impl;

import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ResourceReadModel;
import hu.sule.administration.queries.ExistDbCollectionManagerQueries;
import hu.sule.administration.service.CollectionService;
import hu.sule.administration.service.ExistDbCredentialsService;
import hu.sule.administration.util.Mappers;
import hu.sule.administration.util.XmlValidator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private ExistDbCollectionManagerQueries existDbCollectionManagerQueries;

    public ArrayList<ExistCollectionManagerModel> getFileManagerCollectionsByCollection(String collection) throws IOException, JDOMException {
            ArrayList<ExistCollectionManagerModel> collections = new ArrayList<>();
        for (ExistCollectionManagerModel col: Mappers.mapCollectionQueryResult(existDbCollectionManagerQueries.getCollectionContent2(ExistDbCredentialsServiceImpl.getDetails(), collection))) {
            if(!col.isResource())
                collections.add(col);
        }
        return collections;
    }

    public ArrayList<ExistCollectionManagerModel> getFileManagerContentByCollection(String collection) throws IOException, JDOMException {
        return Mappers.mapCollectionQueryResult(existDbCollectionManagerQueries.getCollectionContent2(ExistDbCredentialsServiceImpl.getDetails(), collection));
    }

    public String createDir(ForStoreResourceAndColl storeResource) {
        return existDbCollectionManagerQueries.createCollection(ExistDbCredentialsServiceImpl.getDetails(), storeResource);
    }

    public String Store(ForStoreResourceAndColl forStoreResourceAndColl) {
        return existDbCollectionManagerQueries.saveResource(ExistDbCredentialsServiceImpl.getDetails(), forStoreResourceAndColl);
    }

    public String saveEditedRes(ForStoreResourceAndColl forStoreResourceAndColl) {
        return existDbCollectionManagerQueries.saveEditedRes(ExistDbCredentialsServiceImpl.getDetails(), forStoreResourceAndColl);
    }

    public String deleteFile(ExistCollectionManagerModel existCollectionManagerModel) {
        return existDbCollectionManagerQueries.deleteResource(ExistDbCredentialsServiceImpl.getDetails(), existCollectionManagerModel);
    }

    public String deleteCollection(ExistCollectionManagerModel existCollectionManagerModel) {
        return existDbCollectionManagerQueries.removeCollection(ExistDbCredentialsServiceImpl.getDetails(), existCollectionManagerModel);
    }

    public String editResCred(ExistCollectionManagerModel existCollectionManagerModel) {
        return existDbCollectionManagerQueries.editResCred(ExistDbCredentialsServiceImpl.getDetails(), existCollectionManagerModel);
    }

    public String unlockResource(String url){
        return existDbCollectionManagerQueries.unlockResource(ExistDbCredentialsServiceImpl.getDetails(), url);
    }

    public ResourceReadModel readFile(String url) throws JDOMException, IOException {
        String content = existDbCollectionManagerQueries.readFile(ExistDbCredentialsServiceImpl.getDetails(), url);
        ResourceReadModel resourceReadModel = new ResourceReadModel();
        if(url.endsWith(".xml") && XmlValidator.validateXML(content))
            resourceReadModel.setContent(new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(content))));
        else
            resourceReadModel.setContent(content);
        resourceReadModel.setBinary(existDbCollectionManagerQueries.isBinary(ExistDbCredentialsServiceImpl.getDetails(), url));
        return resourceReadModel;
    }

    @Override
    public boolean resourceIsAvailable(String path) {
        return existDbCollectionManagerQueries.resourceIsAvailable(ExistDbCredentialsServiceImpl.getDetails(), path);
    }

    @Override
    public boolean collectionIsAvailable(String path) {
        return existDbCollectionManagerQueries.collectionIsAvailable(ExistDbCredentialsServiceImpl.getDetails(), path);
    }
}
