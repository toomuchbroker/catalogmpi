package backend;


public class User {
    
    // Atribute (din UML)
    private int userId;
    private String name;
    private String email;
    private String password;
    
    // Constructor fără parametri (opțional, dar util)
    public User() {
    }
    
    // Constructor cu toți parametrii
    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Metoda de login.
     * @param email    email‐ul introdus de utilizator
     * @param password parola introdusă de utilizator
     * @return true dacă autentificarea reușește, false altfel
     */
    public boolean login(String email, String password) {
        // Exemplu simplu de validare (ajustează după logica ta reală)
        if (this.email.equals(email) && this.password.equals(password)) {
            // Autentificare reușită
            return true;
        }
        // Autentificare eșuată
        return false;
    }
    
    /**
     * Asociază utilizatorul la un curs anume.
     * @param course referință la un obiect de tip Course
     * @return true dacă asocierea a reușit, false altfel
     */
    public boolean assignToCourse(Course course) {
        // Aici pui logica de înscriere la curs, de ex.:
        // course.addStudent(this); // dacă this e Student, etc.
        // Pentru moment întoarcem true direct, ca exemplu
        return true;
    }
    
    /**
     * Permite utilizatorului să își gestioneze profilul.
     * De exemplu, să își modifice numele, emailul sau parola.
     */
    public void manageProfile() {
        // Aici pui logica de actualizare profil 
        // (ex. schimbare email, schimbare parolă, etc.)
    }
    
    // Getters și setters pentru atribute

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

