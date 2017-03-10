package br.com.ggsoftware.avisoperiodico;

/**
 * Created by f3861879 on 09/03/17.
 */

public class LembreteVO {

private Integer id;
    private String lembrete;
    private String dataHora;
    private Integer tipo;
    private Integer tipoPeriodicidade;
    private Integer periodicidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLembrete() {
        return lembrete;
    }

    public void setLembrete(String lembrete) {
        this.lembrete = lembrete;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipoPeriodicidade() {
        return tipoPeriodicidade;
    }

    public void setTipoPeriodicidade(Integer tipoPeriodicidade) {
        this.tipoPeriodicidade = tipoPeriodicidade;
    }

    public Integer getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(Integer periodicidade) {
        this.periodicidade = periodicidade;
    }
}
