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

    public ArrayList<ExistCollectionManagerModel> getFileManagerCollectionsByCollection(String collection) throws IOException {
            ArrayList<ExistCollectionManagerModel> collections = new ArrayList<>();
        for (ExistCollectionManagerModel col: mapCollectionQueryResult(existDbCollectionManagerQueries.getCollectionContent2(ExistDbCredentialsService.getDetails(), collection))) {
            if(!col.isResource())
                collections.add(col);
        }
        return collections;
    }

    public ArrayList<ExistCollectionManagerModel> getFileManagerContentByCollection(String collection) throws IOException {
        return mapCollectionQueryResult(existDbCollectionManagerQueries.getCollectionContent2(ExistDbCredentialsService.getDetails(), collection));
    }

    public String createDir(ForStoreResourceAndColl storeResource) {
        return existDbCollectionManagerQueries.createCollection(ExistDbCredentialsService.getDetails(), storeResource);
    }

    public String Store(ForStoreResourceAndColl forStoreResourceAndColl) {
        return existDbCollectionManagerQueries.saveResource(ExistDbCredentialsService.getDetails(), forStoreResourceAndColl);
    }

    public String saveEditedRes(ForStoreResourceAndColl forStoreResourceAndColl) {
        return existDbCollectionManagerQueries.saveEditedRes(ExistDbCredentialsService.getDetails(), forStoreResourceAndColl);
    }

    public String deleteFile(ExistCollectionManagerModel existCollectionManagerModel) {
        return existDbCollectionManagerQueries.deleteResource(ExistDbCredentialsService.getDetails(), existCollectionManagerModel);
    }

    public String deleteCollection(ExistCollectionManagerModel existCollectionManagerModel) {
        return existDbCollectionManagerQueries.removeCollection(ExistDbCredentialsService.getDetails(), existCollectionManagerModel);
    }

    public String editResCred(ExistCollectionManagerModel existCollectionManagerModel) {
        return existDbCollectionManagerQueries.editResCred(ExistDbCredentialsService.getDetails(), existCollectionManagerModel);
    }

    public String evalXqueryasString(String query) {
        return existDbCollectionManagerQueries.evalXqueryasString(ExistDbCredentialsService.getDetails(),query.replaceAll("\"","'"));
    }

    public String evalXqueryasPath(String query) throws IOException, JDOMException{
        return new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbCollectionManagerQueries.evalXqueryasPath(ExistDbCredentialsService.getDetails(),query))));
    }

    public String unlockResource(String url){
        return existDbCollectionManagerQueries.unlockResource(ExistDbCredentialsService.getDetails(), url);
    }

    public ResourceReadModel readFile(String url) throws JDOMException, IOException {
        ResourceReadModel resourceReadModel = new ResourceReadModel();
        if(url.endsWith(".xml"))
            resourceReadModel.setContent(new XMLOutputter(Format.getPrettyFormat()).outputString(new SAXBuilder().build(new StringReader(existDbCollectionManagerQueries.readFile(ExistDbCredentialsService.getDetails(), url)))));
        else
            resourceReadModel.setContent(existDbCollectionManagerQueries.readFile(ExistDbCredentialsService.getDetails(), url));
        resourceReadModel.setBinary(existDbCollectionManagerQueries.isBinary(ExistDbCredentialsService.getDetails(), url));
        return resourceReadModel;
    }

    private ArrayList<ExistCollectionManagerModel> mapCollectionQueryResult(String input) throws IOException{
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
}
