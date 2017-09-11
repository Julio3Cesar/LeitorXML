package readjobsxml.service;

import java.util.ArrayList;
import java.util.List;
import readjobsxml.model.Job;
import readjobsxml.model.Tag;

/**
 *
 * @author bob-c
 */
public class HierarquiaJobService {

//    private final List<Job> jobs;
    private List<Job> cabeca;
    private List<Job> dependentes;

    public HierarquiaJobService() {
//        this.jobs = new ArrayList<>();
    }

    public List<Job> findCabeca(Tag xml) {
        this.cabeca = new ArrayList<>();
        for (Tag table : xml.getConteudo()) {
            List<Tag> jobsXml = table.getConteudo();
            for (Tag j : jobsXml) {
                this.dependentes = new ArrayList<>();
                List<String> outcond = new ArrayList<>();
                int cont = 0;
                for (Tag t : j.getConteudo()) {
                    if (t.getNome().equals("INCOND")) {
                        cont -= 1;
                        break;
                    }
                }
                if (cont == 0) {
                    for (Tag t : j.getConteudo()) {
                        if (t.getNome().equals("OUTCOND")) {
                            outcond.add(t.getMapAttributes().get("NAME")
                                    .replace("1R_", "")
                                    .replace(j.getMapAttributes().get("JOBNAME"), "")
                                    .replace("-", ""));
                        }
                    }
//                    System.out.println(outcond.toString());
//                    System.out.println("JOB NAME: " + j.getMapAttributes().get("JOBNAME") + " INCOND: " + cont + "\n");
                    Job cab = new Job();
                    cab.setNome(j.getMapAttributes().get("JOBNAME"));
                    cab.setJob(j);
                    cab.setDependencias(null);
//                    System.out.println(cab.toString());
                    cab.setDependentes(findJob(outcond, cab, table));
                    cabeca.add(cab);
//                    System.out.println(cabeca.toString());
                }
            }
        }
        return cabeca;
    }

    public List<Job> findJob(List<String> listName, Job ant, Tag table) {
        // encontro o nome do Job filho
        //populo
        for (Tag job : table.getConteudo()) {
            for (String name : listName) {
                if (job.getMapAttributes().get("JOBNAME").equals(name)) {
                    Job filho = new Job();
                    filho.setJob(job);
                    filho.setNome(name);
                    filho.getDependencias().add(ant);
                    //procura proximo se tiver
//                    System.out.println(!listName.isEmpty() && !outExterno(filho, table));
                    if (!listName.isEmpty() && !outExterno(filho, table)) {
                        List<String> listOutFilho = new ArrayList<>();
                        for (Tag conteudo : filho.getJob().getConteudo()) {
                            if (conteudo.getNome().equals("OUTCOND")) {
                                listOutFilho.add(conteudo.getMapAttributes().get("NAME"));
                            }
                        }
                        filho.setDependentes(findJob(listOutFilho, filho, table));
                    } else {
                        filho.setDependentes(null);
                    }
                    dependentes.add(filho);
                }
            }
        }

        return dependentes;
    }

    private boolean outExterno(Job filho, Tag table) {
        for (Tag cont : filho.getJob().getConteudo()) {
            if (cont.getNome().equals("OUTCOND")) {
                return isOutExist(cont.getMapAttributes().get("NAME").replace(filho.getNome(), "").replace("-", ""), table);
            }
        }
        return false;
    }

    private boolean isOutExist(String outName, Tag table) {
        for (Tag job : table.getConteudo()) {
            if (job.getMapAttributes().get("JOBNAME").equals(outName)) {
                return true;
            }
        }
        return false;
    }
}
