package atenda.admin.negocio;

public class Devolucion {
    
    private int id;
    private int id_pedido;
    private String dependente;
    private int unidades;
    private int id_producto;
    private String fecha;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getDependente() {
        return dependente;
    }

    public void setDependente(String dependente) {
        this.dependente = dependente;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }
    
    
    
    
    
}
