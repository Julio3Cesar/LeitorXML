package readjobsxml.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bob-c
 */
public class Tag {
    private String Nome;
    private List<String> atributos;
    private List<String> valores;
    private List<Tag> conteudo;

    public Tag() {
        this.atributos = new ArrayList<>();
        this.valores = new ArrayList<>();
        this.conteudo = new ArrayList<>();
    }

    public List<String> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<String> atributos) {
        this.atributos = atributos;
    }

    public List<String> getValores() {
        return valores;
    }

    public void setValores(List<String> valores) {
        this.valores = valores;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public List<Tag> getConteudo() {
        return conteudo;
    }

    public void setConteudo(List<Tag> conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public String toString() {
        String att = "";
        String val = "";
        String cont = "";
        for(String a : getAtributos()){
            att += "\n"+a;
        }
        for(String v : getValores()){
            val += "\n"+v;
        }
        for(Tag c : getConteudo()){
            cont += "\n"+c.toString();
        }
        return "Nome: "+ getNome()+"\nNome Atributos:"+att+"\nValores:"+val+"\nConteudo:"+cont;
    }
}
