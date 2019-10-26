package uan.sigeamobile;

/**
 * Created by Wawingi Sebastiao on 30/12/2018.
 */

public class CalendarizacaoClass {
    private String evento;
    private String datainicio;
    private String datafim;

    public CalendarizacaoClass() {
        this.evento = evento;
        this.datainicio = datainicio;
        this.datafim = datafim;
    }

    public String getEvento() {
        return evento;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public String getDatafim() {
        return datafim;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public void setDatafim(String datafim) {
        this.datafim = datafim;
    }
}

