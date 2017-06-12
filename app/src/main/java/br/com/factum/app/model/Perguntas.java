package br.com.factum.app.model;

/**
 * Created by 16165877 on 05/04/2017.
 */

public class Perguntas {

    private int idPergunta;
    private String pergunta;
    private Resposta[] respostas;

    public Perguntas(int idPergunta, String pergunta){

        this.setIdPergunta(idPergunta);
        this.setPergunta(pergunta);

    }

    public Perguntas(){

    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }


    public int getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(int idPergunta) {
        this.idPergunta = idPergunta;
    }

    public Resposta[] getRespostas() {
        return respostas;
    }

    public void setRespostas(Resposta[] respostas) {
        this.respostas = respostas;
    }
}
