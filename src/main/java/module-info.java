module org.example.kompletteringsuppgiftajp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens org.example.kompletteringsuppgiftajp to javafx.fxml;
    exports org.example.kompletteringsuppgiftajp;
    exports org.example.kompletteringsuppgiftajp.Entities;
    opens org.example.kompletteringsuppgiftajp.Entities to javafx.fxml;
}