/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Hermes
 */
public class Agendas implements Serializable {

    String codigo;
    String titular;
    String color;
    ArrayList<Eventos> eventos;

    public Agendas() {
    }

    public Agendas(String codigo, String titular, String color) {
        this.codigo = codigo;
        this.titular = titular;
        this.color = color;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<Eventos> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<Eventos> eventos) {
        this.eventos = eventos;
    }

    @Override
    public String toString() {
        return "Agendas{" + "codigo=" + codigo + ", titular=" + titular + ", color=" + color + '}';
    }

}
