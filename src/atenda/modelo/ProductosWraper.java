package atenda.modelo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "Productos")
public class ProductosWraper {
    
    List<Producto> productos;

    public List<Producto> getProductos() {
        return productos;
    }

    @XmlElement(name = "Producto")
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

}
