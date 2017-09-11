package readjobsxml.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bob-c
 */
public class Tag {

    private String Nome;
    private Map<String,String> mapAttributes;
    private List<Tag> conteudo;

    public Tag() {
        this.mapAttributes = new HashMap<>();
        this.conteudo = new ArrayList<>();
    }

    public Map<String, String> getMapAttributes() {
        return mapAttributes;
    }

    public void setMapAttributes(Map<String, String> mapAttributes) {
        this.mapAttributes = mapAttributes;
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
        String cont = "\nConteudo: \n";
        att = getMapAttributes().keySet().stream().map((a) -> "\n   " + a).reduce(att, String::concat);
        val = getMapAttributes().values().stream().map((v) -> "\n   " + v).reduce(val, String::concat);
        cont = getConteudo().stream().map((c) -> "\n" + c.toString()).reduce(cont, String::concat);
        if(cont.equals("\nConteudo: \n")){
            cont = "\nSem conteudo";
        }
        return "Nome: " + getNome() + "\nAtributos:" + att + "\nValores:" + val + cont;
    }
}