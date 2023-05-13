package atenda.admin.producto;

public class Producto {

    private int id;
    private String nome;
    private double prezo;
    private int desctonto;
    private double coste;
    private Iva iva;
    private int stock;
    private int baixa;

    public Producto() {
    }

    public Producto(String nome, double prezo, int desctonto, double coste, Iva iva, int stock) {
        this.nome = nome;
        this.prezo = prezo;
        this.desctonto = desctonto;
        this.coste = coste;
        this.iva = iva;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezo() {
        return prezo;
    }

    public void setPrezo(double prezo) {
        this.prezo = prezo;
    }

    public int getDesctonto() {
        return desctonto;
    }

    public void setDesctonto(int desctonto) {
        this.desctonto = desctonto;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public Iva getIva() {
        return iva;
    }

    public void setIva(Iva iva) {
        this.iva = iva;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBaixa() {
        return baixa;
    }

    public void setBaixa(int baixa) {
        this.baixa = baixa;
    }

    @Override
    public String toString() {
        return "Producto{" + "nome=" + nome + ", prezo=" + prezo + ", desctonto=" + desctonto + ", coste=" + coste + ", iva=" + iva + ", stock=" + stock + ", baixa=" + baixa + '}';
    }
    
}
