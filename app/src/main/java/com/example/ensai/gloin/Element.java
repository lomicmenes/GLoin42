package com.example.ensai.gloin;

/**
 * Created by ensai on 13/05/16.
 */
public class Element {

private String pseudo ;
    private String motDePasse ;
    private String mail ;
    private String numero ;
    private int  gloin ;

    public  Element()
    {}

    public Element(String pseudo, String motDePasse, String mail , String numero , int gloin) {
        super();
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.mail =mail ;
        this.numero = numero;
        this.gloin = gloin ;
    }

    public String getPseudo () {
        return pseudo ;
    }

    public void setPseudo(String pseudo ) {
        this.pseudo  = pseudo ;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getGloin() {
        return gloin;
    }

    public void setGloin(int gloin) {
        this.gloin = gloin;
    }

    @Override
    public String toString() {
        return pseudo ;
    }
}
