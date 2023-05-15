package atenda.usuario.negocio;

public class Usuario {

    int idUsuario;
    String username;
    String password;
    String nome;
    Rol rol;
    String avatar;

    public Usuario(int idUsuario, String username, String password, String nome, Rol rol, String avatar) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.rol = rol;
        this.avatar = avatar;
    }

    public Usuario() {
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    } 
    
}
