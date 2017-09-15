package readjobsxml;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import readjobsxml.model.Job;
import readjobsxml.model.Tag;
import readjobsxml.service.ExportPDFService;
import readjobsxml.service.ExportVIsioService;
import readjobsxml.service.HierarquiaJobService;

/**
 *
 * @author bob-c
 */
public class ReadJobsXml {

    public static Tag xml = new Tag();
    public static HierarquiaJobService hierarquiaJobService = new HierarquiaJobService();

    public static void main(String[] args) throws MalformedURLException, DocumentException, Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new File("BUO_PROD_V7.xml"));
        leitorTagRecursivo(doc.getRootElement(), xml);

        List<Job> l = hierarquiaJobService.findCabeca(xml);
        System.out.println("ok");
        ExportPDFService.export(l);
        new ExportVIsioService().export(l);
    }

    public static Element leitorTagRecursivo(Element e, Tag tag) {
        tag.setNome(e.getName());

        leitorAttr(e, tag);

        if (e.elementIterator() != null) {
            e.elementIterator().forEachRemaining((t) -> {
                Tag aux = new Tag();
                tag.getConteudo().add(aux);
                leitorTagRecursivo(t, aux);
            });
        }
        return null;
    }

    public static void leitorAttr(Element e, Tag tag) {

        if (e.attributeIterator() != null) {
            e.attributeIterator().forEachRemaining((a) -> {
                tag.getMapAttributes().put(a.getName(), a.getValue());
            });
        }
    }
}
