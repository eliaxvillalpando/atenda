package atenda.test;

import atenda.usuarios.Rol;
import atenda.usuarios.Usuario;
import atenda.usuarios.UsuarioDAO;


public class ClaseMainPruebas {


    public static void main(String[] args) {
        
        
        Usuario userTested = new Usuario();
        userTested.setUsername("Anakin");
        userTested.setNome("Anakin Skywalker");
        userTested.setPassword("anakin");
        userTested.setAvatar("/path/to/avatar");
        userTested.setRol(Rol.DEPENDENTE);
        
        UsuarioDAO metodoBD = new UsuarioDAO();
        metodoBD.save(userTested);
        
        System.out.println("Usuario creado exitosamente");
        
    }


    
}
