package br.com.sistema.dto;

public class MetricaDashboard {
    private Long totalClientes;
    private Double variacao;
    private Long novosHoje;
    private Double mediaDiaria;
    private Long clientesAtivos;
    private Double percentualAtivos;

    public MetricaDashboard() {
    	
    }

    public MetricaDashboard(Long totalClientes, Double variacao, Long novosHoje, Double mediaDiaria, Long clientesAtivos, Double percentualAtivos) {
        this.totalClientes = totalClientes;
        this.variacao = variacao;
        this.novosHoje = novosHoje;
        this.mediaDiaria = mediaDiaria;
        this.clientesAtivos = clientesAtivos;
        this.percentualAtivos = percentualAtivos;
    }

    // Getters e Setters
    public Long getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(Long totalClientes) {
        this.totalClientes = totalClientes;
    }

    public Double getVariacao() {
        return variacao;
    }

    public void setVariacao(Double variacao) {
        this.variacao = variacao;
    }

    public Long getNovosHoje() {
        return novosHoje;
    }

    public void setNovosHoje(Long novosHoje) {
        this.novosHoje = novosHoje;
    }

    public Double getMediaDiaria() {
        return mediaDiaria;
    }

    public void setMediaDiaria(Double mediaDiaria) {
        this.mediaDiaria = mediaDiaria;
    }

    public Long getClientesAtivos() {
        return clientesAtivos;
    }

    public void setClientesAtivos(Long clientesAtivos) {
        this.clientesAtivos = clientesAtivos;
    }

    public Double getPercentualAtivos() {
        return percentualAtivos;
    }

    public void setPercentualAtivos(Double percentualAtivos) {
        this.percentualAtivos = percentualAtivos;
    }
}
