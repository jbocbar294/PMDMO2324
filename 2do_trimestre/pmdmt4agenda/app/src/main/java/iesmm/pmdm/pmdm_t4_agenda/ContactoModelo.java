package iesmm.pmdm.pmdm_t4_agenda;

public class ContactoModelo {
    private String nombre, email, telefono;

    public ContactoModelo(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }
}
