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
        String dentes = "";
        String dencias = "";

        for (Job j : l) {
            //criar shape
            
            //
            
            
            
            
            if (j.getJob() != null) {
                String group = j.getJob().getMapAttributes().get("GROUP");
                String periodo = j.getJob().getMapAttributes().get("PARENT_TABLE")
                        .replace("BUexport(j.getDependentes());O_", "");
                if (!result.contains("                                                    GRUPO: " + group
                        + "\n                                                         PERIODO: " + periodo)) {
                    result.add("-----------------------------------------------"
                            + "------------------------------------------------"
                            + "----------------------------------");
                    result.add("                                                    GRUPO: " + group
                            + "\n                                                         PERIODO: " + periodo);
                }

            }
            if (j.getJob() == null) {
                String pai = result.get(result.size() -1);
                dentes += "           -JOB EXTERNO, verificar sua entrada nas saidas dos jobs acima. ";
                p += "\nO JOB de nome: " + j.getNome();              
                p += ".\nPossui os seguintes JOBs como ENTRADAS: \n"+dentes +"\nE os seguintes como SAIDAS: "
                        +"\n           -NÃO POSSUI SAIDAS" + "\n\nDESCRIÇÃO:\n" + getDescricao(j) 
                        + "\n\n"
                        + "                                                                         ----";
                result.add(p);
                p = "";
                dencias = "";
                dentes = "";
            } else {
                if (j.getDependencias().isEmpty() && j.getDependentes().isEmpty()) {
                    p += "\nO JOB de nome: " + j.getNome() + ".\n           -NÃO POSSUI ENTRADAS "
                            + "\n           -NÃO POSSUI SAIDAS";
                    p += "\n\nDESCRIÇÃO:\n" + getDescricao(j) + "\n\n"
                            + "                                                                         ----";
                    result.add(p);
                    p = "";
                    dencias = "";
                    dentes = "";                
                } else {
                    if (j.getDependencias().isEmpty()) {
                        dencias = "\n           -SEM ENTRADAS";
                    } else {
                        for (Job d : j.getDependencias()) {
                            dencias += "\n           -" + d.getNome() + ",";
                        }
                    }

                    if (j.getDependentes().isEmpty()) {
                        p += "\nO JOB de nome: " + j.getNome() + ".\nPossui os seguintes JOBs como "
                                + "ENTRADAS:" + dencias
                                + "\n           -NÃO POSSUI SAIDAS";
                        p += "\n\nDESCRIÇÃO:\n" + getDescricao(j) + "\n\n"
                                + "                                                                         ----";
                        result.add(p);
                        p = "";
                        dencias = "";
                        dentes = "";
                        return null;
                    } else {
                        for (Job d : j.getDependentes()) {
                            dentes += "\n           -" + d.getNome() + ",";
                        }
                    }

                    p += "\nO JOB de nome: " + j.getNome() + ".\nPossui os seguintes JOBs como "
                            + "ENTRADAS:" + dencias
                            + "\nE os seguintes como SAIDAS:" + dentes;
                    p += "\n\nDESCRIÇÃO:\n" + getDescricao(j) + "\n\n"
                            + "                                                                         ----";

                    result.add(p);
                    p = "";
                    dencias = "";
                    dentes = "";
                }
                export(j.getDependentes());
            }
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
            return "O Job é pertecente a um sistema externo.";
        } else {
            final String MEMNAME = j.getJob().getMapAttributes().get("MEMNAME");
            if (MEMNAME.contains("FW - ")) {
                //é um FILE WATCH
                result += "-É um processo FILE WATCH, responsável por verificar o ARQUIVO: \"  ";
                for (Tag t : j.getJob().getConteudo()) {
                    String name = t.getMapAttributes().get("NAME").toUpperCase();
                    if (name.contains("FILE_PATH")) {
                        result += t.getMapAttributes().get("VALUE") + " \".";
                        break;
                    }
                }
            } else if (MEMNAME.toLowerCase().contains("transfer")) {
                //é um transfer
                result += "-É um FILE TRANSFER, responsável por transferirir o seguinte ARQUIVO: \n \" ";

                for (Tag t : j.getJob().getConteudo()) {
                    String name = t.getMapAttributes().get("NAME").toUpperCase();
                    if (name.contains("%%PARM3")) {
                        result += t.getMapAttributes().get("VALUE") + "\"\n Do Caminho:  ";
                        break;
                    }
                }
                for (Tag t : j.getJob().getConteudo()) {
                    String name = t.getMapAttributes().get("NAME").toUpperCase();
                    if (name.contains("%%PARM1")) {
                        result += t.getMapAttributes().get("VALUE") + " \n Para o Servidor: ";
                        break;
                    }
                }
                for (Tag t : j.getJob().getConteudo()) {
                    String name = t.getMapAttributes().get("NAME").toUpperCase();
                    if (name.contains("%%PARM4")) {
                        result += t.getMapAttributes().get("VALUE") + "\n Para o caminho: ";
                        break;
                    }
                }
                for (Tag t : j.getJob().getConteudo()) {
                    String name = t.getMapAttributes().get("NAME").toUpperCase();
                    if (name.contains("%%PARM2")) {
                        result += t.getMapAttributes().get("VALUE") + "";
                        break;
                    }
                    
                }
                for (Tag t : j.getJob().getConteudo()) {
                    String name = t.getMapAttributes().get("NAME").toUpperCase();
                    if (name.contains("%%PARM5")) {
                        result += "\n E precisa do Bastão: "+t.getMapAttributes().get("VALUE") + "\n Que está no caminho: ";
                        break;
                    }
                    
                }
                for (Tag t : j.getJob().getConteudo()) {
                    String name = t.getMapAttributes().get("NAME").toUpperCase();
                    if (name.contains("%%PARM6")) {
                        result += t.getMapAttributes().get("VALUE");
                        break;
                    }
                    
                }
                
            } else {
                // é um bat
                result += "-É um executavel, com o NOME: \"" + j.getJob().getMapAttributes().get("MEMNAME")
                        + "\", RESPONSÁVEL por: \"" + j.getJob().getMapAttributes().get("DESCRIPTION")
                        + "\" \nPassando como parametros: \n(";
                for (Tag t : j.getJob().getConteudo()) {
                    if (t.getMapAttributes().get("NAME") != null && t.getMapAttributes().get("NAME").toUpperCase().contains("%%PARM")) {
                        result += t.getMapAttributes().get("VALUE") + ", ";

                    }
                }
                result += ").";
            }
        }
        //pegar quando executa
        //pegar o que faz
        //

        return result;
    }
}
