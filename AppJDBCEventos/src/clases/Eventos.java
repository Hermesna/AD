/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;

/**
 *
 * @author Hermes
 */
public class Eventos implements Serializable{
    
    int idreg;
    String fecha;
    String hora;
    String texto;
    String estado;

    public Eventos() {
    }

    public Eventos(int idreg, String fecha, String hora, String texto, String estado) {
        this.idreg = idreg;
        this.fecha = fecha;
        this.hora = hora;
        this.texto = texto;
        this.estado = estado;
    }

    public int getIdreg() {
        return idreg;
    }

    public void setIdreg(int idreg) {
        this.idreg = idreg;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Eventos{" + "idreg=" + idreg + ", fecha=" + fecha + ", hora=" + hora + ", texto=" + texto + ", estado=" + estado + '}';
    }
    
}
