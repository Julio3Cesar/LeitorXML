package readjobsxml;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import readjobsxml.model.Tag;

/**
 *
 * @author bob-c
 */
public class ReadJobsXml {
    public static Tag xml = new Tag();

    public static void main(String[] args) throws MalformedURLException, DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new File("BUO_PROD_V7.xml"));
        leitorTagRecursivo(doc.getRootElement(), xml);
        System.out.println(xml.toString());

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
//        salvarPDF(p);
        return null;
    }

    public static void leitorAttr(Element e, Tag tag) {

        if (e.attributeIterator() != null) {
            e.attributeIterator().forEachRemaining((a) -> {
                tag.getAtributos().add("    "+a.getName());
                tag.getValores().add("    "+a.getValue());
            });
        }
    }

    public static void salvarPDF(String p) {
        // criação do documento
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Malha.pdf"));
            document.open();
            // adicionando um parágrafo no documento
            document.add(new Paragraph(p));
        } catch (com.itextpdf.text.DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
        document.close();
    }
}
