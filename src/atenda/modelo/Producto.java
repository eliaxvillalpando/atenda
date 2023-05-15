package atenda.modelo;

public class Producto {

    private Integer id;
    private String nome;
    private Double prezo;
    private Integer desconto;
    private Double coste;
    
    private Iva iva;

    private Integer stock;
    private boolean baixa;

    public Producto() {
    }

    public Producto(String nome, Double prezo, Integer desconto, Double coste, Iva iva, Integer stock) {
        this.nome = nome;
        this.prezo = prezo;
        this.desconto = desconto;
        this.coste = coste;
        this.iva = iva;
        this.stock = stock;
    }

    public Integer getDesconto() {
        return desconto;
    }

    public void setDesconto(Integer desconto) {
        this.desconto = desconto;
    }

    public Iva getIva() {
        return iva;
    }

    public void setIva(Iva iva) {
        this.iva = iva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrezo() {
        return prezo;
    }

    public void setPrezo(Double prezo) {
        this.prezo = prezo;
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public boolean isBaixa() {
        return baixa;
    }

    public void setBaixa(boolean baixa) {
        this.baixa = baixa;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

}
