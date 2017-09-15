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

    private List<Job> cabeca;

    public List<Job> findCabeca(Tag xml) {
        this.cabeca = new ArrayList<>();
        for (Tag table : xml.getConteudo()) {
            List<Tag> jobsXml = table.getConteudo();
            for (Tag j : jobsXml) {
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
                    Job cab = new Job();
                    cab.setNome(j.getMapAttributes().get("JOBNAME"));
                    cab.setJob(j);
                    for (String o : outcond) {
                        cab.getDependentes().add(findJob(o, cab, table));
                    }
                    cabeca.add(cab);
                }
            }
        }
        return cabeca;
    }

    public Job findJob(String outName, Job ant, Tag table) {
        // encontro o nome do Job filho
        Job filho = new Job();
        //if externo
        if (!outName.contains("BUO")) {
            filho.setNome(outName);
        } else {
            //populo
            for (Tag job : table.getConteudo()) {

                if (job.getMapAttributes().get("JOBNAME").equals(outName)) {
                    filho.setJob(job);
                    filho.setNome(outName);
                    filho.getDependencias().add(ant);
                    //popula lista de outcond desse job
                    List<String> outcond = new ArrayList<>();
                    List<String> incond = new ArrayList<>();
                    for (Tag t : job.getConteudo()) {
                        if (t.getNome().equals("INCOND")) {
                            incond.add(t.getMapAttributes().get("NAME")
                                    .replace("1R_", "")
                                    .replace(job.getMapAttributes().get("JOBNAME"), "")
                                    .replace("-", ""));
                        }

                        if (t.getNome().equals("OUTCOND")) {
                            String out = t.getMapAttributes().get("NAME")
                                    .replace("1R_", "")
                                    .replace(job.getMapAttributes().get("JOBNAME"), "")
                                    .replace("-", "");
                            outcond.add(out);
                        }
                    }
                    //procura proximo se tiver
                    if (!outcond.isEmpty()) {
                        for (String o : outcond) {
                            if (!incond.contains(o)) {
                                filho.getDependentes().add(findJob(o, filho, table));

                            }
                        }
                    } else {
                        return new Job();
                    }
                }
            }
        }
        return filho;
    }
}
