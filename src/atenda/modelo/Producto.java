package atenda.modelo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;


public class Producto {

    private Integer id;
    private String nome;
    private Double prezo;
    private Integer desconto;
    private Double coste;
    
    @XmlTransient
    private Iva iva;


    private Integer stock;
    private boolean baixa;
    
    
    private Iva ivaProducto;
    private double ivaAsignado;
    private String ivaString;
    
    
    public Producto() {
    }

    public Producto(String nome, Double prezo, Integer desconto, Double coste, String iva, Integer stock) {
        this.nome = nome;
        this.prezo = prezo;
        this.desconto = desconto;
        this.coste = coste;
        
        this.stock = stock;
        
        
        this.ivaProducto = Iva.fromString(iva);

        if (ivaProducto == Iva.TIPO_REDUCIDO) {
            ivaAsignado = 10.0 / 100.0;
        } else if (ivaProducto == Iva.TIPO_XERAL) {
            ivaAsignado = 21.0 / 100.0;
        } else if (ivaProducto == Iva.TIPO_SUPERREDUCIDO) {
            ivaAsignado = 4.0 / 100.0;
        }
        
        
    }
    
    public String getIvaString() {
        if (iva != null) {
            return iva.toString();
        } else {
            return null;
        }
    }
    
    public void setIvaAsignado(double ivaAsignado) {
        this.ivaAsignado = ivaAsignado;
    }
    
    @XmlElement
    public double getIvaAsignado() {
        return ivaAsignado;
    }

    @XmlElement
public void setIvaString(String ivaString) {
    this.ivaString = ivaString;
    if (ivaString != null) {
        switch (ivaString) {
            case "TIPO_REDUCIDO":
                this.ivaAsignado = 10.0 / 100.0;
                break;
            case "TIPO_XERAL":
                this.ivaAsignado = 21.0 / 100.0;
                break;
            case "TIPO_SUPERREDUCIDO":
                this.ivaAsignado = 4.0 / 100.0;
                break;
            default:
                break;
        }
    }
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
