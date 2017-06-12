package br.com.factum.app.model;

/**
 * Created by 16165880 on 22/05/2017.
 */

public class Estudo {
    private String data;
    private String materia;
    private String assunto;
    private Boolean pendencia;

    //CONSTRUTORES
    public Estudo(String data, String materia, String assunto, Boolean pendencia ){

        this.data= data;
        this.materia= materia;
        this.assunto= assunto;
        this.pendencia = pendencia;

    }
    public  Estudo(){

    }

    //GETTERS AND SETTERS
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public Boolean getPendencia() {
        return pendencia;
    }

    public void setPendencia(Boolean pendencia) {
        this.pendencia = pendencia;
    }
}
