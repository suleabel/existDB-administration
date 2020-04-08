package hu.sule.administration.service.impl;

import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ResourceReadModel;
import hu.sule.administration.queries.ExistDbCollectionManagerQueries;
import hu.sule.administration.service.CollectionService;
import hu.sule.administration.service.ExistDbCredentialsService;
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

    public ArrayList<ExistCollectionManagerModel> getFileManagerCollectionsByCollection(String collection) throws IOException {
            ArrayList<ExistCollectionManagerModel> collections = new ArrayList<>();
        for (ExistCollectionManagerModel col: mapCollectionQueryResult(existDbCollectionManagerQueries.getCollectionContent2(ExistDbCredentialsServiceImpl.getDetails(), collection))) {
            if(!col.isResource())
                collections.add(col);
        }
        return collections;
    }

    public ArrayList<ExistCollectionManagerModel> getFileManagerContentByCollection(String collection) throws IOException {
        return mapCollectionQueryResult(existDbCollectionManagerQueries.getCollectionContent2(ExistDbCredentialsServiceImpl.getDetails(), collection));
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
        ResourceReadModel resourceReadModel = new ResourceReadModel();
        if(url.endsWith(".xml"))
            resourceReadModel.setContent(new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbCollectionManagerQueries.readFile(ExistDbCredentialsServiceImpl.getDetails(), url)))));
        else
            resourceReadModel.setContent(existDbCollectionManagerQueries.readFile(ExistDbCredentialsServiceImpl.getDetails(), url));
        resourceReadModel.setBinary(existDbCollectionManagerQueries.isBinary(ExistDbCredentialsServiceImpl.getDetails(), url));
        return resourceReadModel;
    }

    public ArrayList<ExistCollectionManagerModel> mapCollectionQueryResult(String input) throws IOException{
        ArrayList<ExistCollectionManagerModel> collectionManagerModels = new ArrayList<>();
        ExistCollectionManagerModel model = null;
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;
        try {
            doc = saxBuilder.build(new InputSource(new StringReader(input)));
        }catch (JDOMException e){
            e.printStackTrace();
        }
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

    @Override
    public boolean resourceIsAvailable(String path) {
        return existDbCollectionManagerQueries.resourceIsAvailable(ExistDbCredentialsServiceImpl.getDetails(), path);
    }

    @Override
    public boolean collectionIsAvailable(String path) {
        return existDbCollectionManagerQueries.collectionIsAvailable(ExistDbCredentialsServiceImpl.getDetails(), path);
    }
}
