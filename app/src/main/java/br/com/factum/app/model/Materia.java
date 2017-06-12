package br.com.factum.app.model;

/**
 * Created by 16165880 on 03/04/2017.
 */

public class Materia {

    private String materia;
    private String assunto;
    private String notas;

    public  Materia(String materia, String assunto, String notas ){

        this.materia = materia;
        this.assunto = assunto;
        this.notas = notas;

    }

    public Materia() {

    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}


