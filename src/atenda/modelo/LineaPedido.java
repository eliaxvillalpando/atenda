/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atenda.modelo;

/**
 *
 * @author elias
 */
public class LineaPedido {
    
    private int id;
    
    private int id_pedido;
    private int id_producto;
    private int desconto;
    private int unidades;
    private Double prezo;
    private Double coste;
    

    public LineaPedido() {
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getDesconto() {
        return desconto;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
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
    
    
    
    
    
}
