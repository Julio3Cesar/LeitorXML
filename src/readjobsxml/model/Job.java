package readjobsxml.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bob-c
 */
public class Job {
    private String nome;
    private Tag job;
    private List<Job> dependencias;
    private List<Job> dependentes;

    public Job() {
        this.dependencias = new ArrayList<>();
        this.dependentes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tag getJob() {
        return job;
    }

    public void setJob(Tag job) {
        this.job = job;
    }

    public List<Job> getDependencias() {
        return dependencias;
    }

    public void setDependencias(List<Job> dependencias) {
        this.dependencias = dependencias;
    }

    public List<Job> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<Job> dependentes) {
        this.dependentes = dependentes;
    }

    @Override
    public String toString() {
        return "\nJOB NOME: "+getNome()+"\nDEPENDENCIAS"+getDependencias().toString()+
                "\nDEPENDENTES"+getDependentes().toString();
    }
    
    
}
