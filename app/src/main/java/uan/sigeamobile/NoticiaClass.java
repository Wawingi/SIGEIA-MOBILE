package uan.sigeamobile;

/**
 * Created by Wawingi Sebastiao on 02/01/2019.
 */

public class NoticiaClass {
    private String titulo;
    private String descricao;
    private String data;

    public NoticiaClass() {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setData(String data) {
        this.data = data;
    }
}
