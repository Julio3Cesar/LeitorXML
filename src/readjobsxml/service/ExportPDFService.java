package readjobsxml.service;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import readjobsxml.model.Job;
import readjobsxml.model.Tag;

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
            if (j.getJob() != null) {
                String group = j.getJob().getMapAttributes().get("GROUP");
                String periodo = j.getJob().getMapAttributes().get("PARENT_TABLE")
                        .replace("BUO_", "");
                if (!result.contains("GRUPO: " + group + "\nPERIODO: "+periodo)) {
                    result.add("-----------------------------------------------"
                            + "------------------------------------------------"
                            + "----------------------------------");
                    result.add("GRUPO: " + group + "\nPERIODO: "+periodo);
                }

            }

            if (j.getDependencias().isEmpty()) {
                dencias = "[SEM DEPENDENCIAS";
            } else {
                for (Job d : j.getDependencias()) {
                    dencias += d.getNome() + ",";
                }
            }

            if (j.getDependentes().isEmpty()) {
                p += "\nO JOB DE NOME: " + j.getNome() + "\nPOSSUI AS SEGUINTES "
                        + "ENTRADAS:\n" + dencias
                        + "]\nE NÃO POSSUI SAIDAS\n";
                p += "\nDESCRIÇÃO:\n" + getDescricao(j) + "\n\n";
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

            p += "\nO JOB DE NOME: " + j.getNome() + "\nPOSSUI AS SEGUINTES "
                    + "ENTRADAS:\n" + dencias
                    + "]\nE AS SEGUINTES SAIDAS:\n" + dentes + "]\n";
            p += "\nDESCRIÇÃO:\n" + getDescricao(j) + "\n\n";

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

    private static String getDescricao(Job j) {
        String result = "";
        if (j.getJob() == null) {
            return "Job de outro sistema, externo.";
        } else {
            final String MEMNAME = j.getJob().getMapAttributes().get("MEMNAME");
            if (MEMNAME.contains("FW - ")) {
                //é um FILE WATCH
                result += "é um processo FILE WATCH, responsável por verificar o arquivo ";
                for (Tag t : j.getJob().getConteudo()) {
                    String name = t.getMapAttributes().get("NAME").toUpperCase();
                    if (name.contains("FILE_PATH")) {
                        result += t.getMapAttributes().get("VALUE");
                        break;
                    }
                }
            } else {
                if (MEMNAME.toLowerCase().contains("transfer")) {
                    //é um transfer
                    result += "é um FILE TRANSFER, responsável por transferirir o arquivo ";

                    for (Tag t : j.getJob().getConteudo()) {
                        String name = t.getMapAttributes().get("NAME").toUpperCase();
                        if (name.contains("%%PARM3")) {
                            result += t.getMapAttributes().get("VALUE") + " \ndo caminho ";
                            break;
                        }
                    }
                    for (Tag t : j.getJob().getConteudo()) {
                        String name = t.getMapAttributes().get("NAME").toUpperCase();
                        if (name.contains("%%PARM1")) {
                            result += t.getMapAttributes().get("VALUE") + " \npara o caminho ";
                            break;
                        }
                    }
                    for (Tag t : j.getJob().getConteudo()) {
                        String name = t.getMapAttributes().get("NAME").toUpperCase();
                        if (name.contains("%%PARM2")) {
                            result += t.getMapAttributes().get("VALUE");
                            break;
                        }
                    }
                } else {
                    // é um bat
                    result += "é um executavel, com o nome \"" +j.getJob().getMapAttributes().get("MEMNAME")
                            +"\", responsável por \""+j.getJob().getMapAttributes().get("DESCRIPTION")
                            +"\" passando os seguintes parametros: \n";
                    for(Tag t : j.getJob().getConteudo()){
                        if(t.getMapAttributes().get("NAME")!= null && t.getMapAttributes().get("NAME").toUpperCase().contains("%%PARM")){
                            result += t.getMapAttributes().get("VALUE")+"\n";
                            
                        }
                    }
                }
            }
        }
        //pegar quando executa
        //pegar o que faz
        //

        return result;
    }
}
