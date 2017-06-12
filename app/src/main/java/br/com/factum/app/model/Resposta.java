package br.com.factum.app.model;

/**
 * Created by 16165877 on 17/04/2017.
 */

public class Resposta {
    private String texto;
    private int fal_ver;
    private boolean selecionado;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getFal_ver() {
        return fal_ver;
    }

    public void setFal_ver(int fal_ver) {
        this.fal_ver = fal_ver;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
}
