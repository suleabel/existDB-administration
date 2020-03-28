package hu.sule.administration.service;

import hu.sule.administration.model.ExistCollectionManagerModel;
import hu.sule.administration.model.ForStoreResourceAndColl;
import hu.sule.administration.model.ResourceReadModel;
import hu.sule.administration.queries.ExistDbCollectionManagerQueries;
import org.eclipse.jetty.util.IO;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


@Service
public class CollectionService {

    @Autowired
    private ExistDbCollectionManagerQueries existDbCollectionManagerQueries;

    private static final Logger logger = LoggerFactory.getLogger(CollectionService.class);

    public ArrayList<ExistCollectionManagerModel> getFileManagerCollectionsByCollection(String collection) throws XMLDBException, JDOMException, IOException {
            ArrayList<ExistCollectionManagerModel> collections = new ArrayList<>();
        for (ExistCollectionManagerModel col: mapCollectionQueryResult(existDbCollectionManagerQueries.getCollectionContent2(ExistDbCredentialsService.getDetails(), collection))) {
            if(!col.isResource())
                collections.add(col);
        }
        return collections;
    }

    public ArrayList<ExistCollectionManagerModel> getFileManagerContentByCollection(String collection) throws XMLDBException, JDOMException, IOException {
        return mapCollectionQueryResult(existDbCollectionManagerQueries.getCollectionContent2(ExistDbCredentialsService.getDetails(), collection));
    }

    public String createDir(ForStoreResourceAndColl storeResource) throws XMLDBException {
        return existDbCollectionManagerQueries.createCollection(ExistDbCredentialsService.getDetails(), storeResource);
    }

    public String Store(ForStoreResourceAndColl forStoreResourceAndColl) throws XMLDBException {
        return existDbCollectionManagerQueries.saveResource(ExistDbCredentialsService.getDetails(), forStoreResourceAndColl);
    }

    public String saveEditedRes(ForStoreResourceAndColl forStoreResourceAndColl) throws XMLDBException {
        return existDbCollectionManagerQueries.saveEditedRes(ExistDbCredentialsService.getDetails(), forStoreResourceAndColl);
    }

    public String deleteFile(ExistCollectionManagerModel existCollectionManagerModel) throws XMLDBException {
        return existDbCollectionManagerQueries.deleteResource(ExistDbCredentialsService.getDetails(), existCollectionManagerModel);
    }

    public String deleteCollection(ExistCollectionManagerModel existCollectionManagerModel) throws XMLDBException {
        return existDbCollectionManagerQueries.removeCollection(ExistDbCredentialsService.getDetails(), existCollectionManagerModel);
    }

    public String editResCred(ExistCollectionManagerModel existCollectionManagerModel) throws XMLDBException {
        return existDbCollectionManagerQueries.editResCred(ExistDbCredentialsService.getDetails(), existCollectionManagerModel);
    }

    public String evalXqueryasString(String query) throws XMLDBException {
        return existDbCollectionManagerQueries.evalXqueryasString(ExistDbCredentialsService.getDetails(),query);
    }

    public String evalXqueryasPath(String query) throws XMLDBException, IOException, JDOMException{
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbCollectionManagerQueries.evalXqueryasPath(ExistDbCredentialsService.getDetails(),query))));
    }

    public String unlockResource(String url) throws XMLDBException{
        return existDbCollectionManagerQueries.unlockResource(ExistDbCredentialsService.getDetails(), url);
    }

    public ResourceReadModel readFile(String url) throws XMLDBException {
        ResourceReadModel resourceReadModel = new ResourceReadModel();
        resourceReadModel.setContent(existDbCollectionManagerQueries.readFile(ExistDbCredentialsService.getDetails(), url));
        resourceReadModel.setIsBinary(existDbCollectionManagerQueries.isBinary(ExistDbCredentialsService.getDetails(), url));
        return resourceReadModel;
    }

    private ArrayList<ExistCollectionManagerModel> mapCollectionQueryResult(String input) throws IOException, JDOMException{
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
