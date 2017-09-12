package readjobsxml.service;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import readjobsxml.model.Job;

/**
 *
 * @author bob-c
 */
public class ExportPDFService {

    private static List<String> result = new ArrayList<>();

    public static List<String> export(List<Job> l) {

        String p = "";
        String dentes = "[";
        String dencias = "[";

        for (Job j : l) {
            if (j.getDependencias().isEmpty()) {
                dencias = "[SEM DEPENDENTES";
            } else {
                for (Job d : j.getDependencias()) {
                    dencias += d.getNome() + ",";
                }
            }

            if (j.getDependentes().isEmpty()) {
                p += "\nJOB NAME: " + j.getNome() + "\nDEPENDENCIAS:\n" + dencias
                        + "]\nDEPENDENTES:\n[SEM DEPENDENTES]\n";
                result.add(p);
                p = "";
                dencias = "[";
                dentes = "[";
                return null;
            } else {
                for (Job d : j.getDependentes()) {
                    dentes += d.getNome() + ",";
                }
            }

            p += "\nJOB NAME: " + j.getNome() + "\nDEPENDENCIAS:\n" + dencias
                    + "]\nDEPENDENTES:\n" + dentes + "]\n";

            result.add(p);
            p = "";
            dencias = "[";
            dentes = "[";

            export(j.getDependentes());
        }
        salvarPDF(result);
        return null;
    }

    private static void salvarPDF(List<String> p) {
        // criação do documento
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Malha.pdf"));
            document.open();
            // adicionando um parágrafo no documento
            for (String i : p) {
                document.add(new Paragraph(i));
            }
        } catch (com.itextpdf.text.DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
        document.close();
    }

//    public static void export(List<Job> l) {
//        String p = "";
//        String dentes = "[";
//        String dencias = "[";
//        
//        for (Job j :l) {
//            for(Job f : j.getDependentes()){
//                
//            }
//            for(Job d : j.getDependencias()){
//              dencias += d.getNome()+",";
//            }
//            for(Job d : j.getDependentes()){
//              dentes += d.getNome()+",";
//            }
//            p += "\nJOB NAME: "+j.getNome()+"\nDEPENDENCIAS:\n"+dencias
//                    +"]\nDEPENDENTES:\n"+dentes+"]\n";
//            dencias = "[";
//            dentes = "[";
//        }
//        salvarPDF(p);
//    }
}
