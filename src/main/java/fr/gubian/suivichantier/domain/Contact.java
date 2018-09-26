package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fr.gubian.suivichantier.domain.enumeration.Corpsmetier;

/**
 * Login calculé à partir du nom et prénom.
 */
@ApiModel(description = "Login calculé à partir du nom et prénom.")
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_label")
    private String label;

    @Column(name = "login")
    private String login;

    @Size(max = 256)
    @Column(name = "prenom", length = 256)
    private String prenom;

    @Size(max = 256)
    @Column(name = "nom", length = 256)
    private String nom;

    @Size(max = 256)
    @Column(name = "email", length = 256)
    private String email;

    @Column(name = "telephone_mobile")
    private String telephoneMobile;

    @Column(name = "telephone_2")
    private String telephone2;

    @Column(name = "fax")
    private String fax;

    @Column(name = "description")
    private String description;

    @Column(name = "poste")
    private String poste;

    @Enumerated(EnumType.STRING)
    @Column(name = "corpmetier")
    private Corpsmetier corpmetier;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adressecontact;

    @OneToOne(mappedBy = "contact")
    @JsonIgnore
    private ChantierIntervenant chantierIntervenant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Contact label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLogin() {
        return login;
    }

    public Contact login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPrenom() {
        return prenom;
    }

    public Contact prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Contact nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public Contact email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneMobile() {
        return telephoneMobile;
    }

    public Contact telephoneMobile(String telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
        return this;
    }

    public void setTelephoneMobile(String telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public Contact telephone2(String telephone2) {
        this.telephone2 = telephone2;
        return this;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getFax() {
        return fax;
    }

    public Contact fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDescription() {
        return description;
    }

    public Contact description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoste() {
        return poste;
    }

    public Contact poste(String poste) {
        this.poste = poste;
        return this;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Corpsmetier getCorpmetier() {
        return corpmetier;
    }

    public Contact corpmetier(Corpsmetier corpmetier) {
        this.corpmetier = corpmetier;
        return this;
    }

    public void setCorpmetier(Corpsmetier corpmetier) {
        this.corpmetier = corpmetier;
    }

    public Adresse getAdressecontact() {
        return adressecontact;
    }

    public Contact adressecontact(Adresse adresse) {
        this.adressecontact = adresse;
        return this;
    }

    public void setAdressecontact(Adresse adresse) {
        this.adressecontact = adresse;
    }

    public ChantierIntervenant getChantierIntervenant() {
        return chantierIntervenant;
    }

    public Contact chantierIntervenant(ChantierIntervenant chantierIntervenant) {
        this.chantierIntervenant = chantierIntervenant;
        return this;
    }

    public void setChantierIntervenant(ChantierIntervenant chantierIntervenant) {
        this.chantierIntervenant = chantierIntervenant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", login='" + getLogin() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephoneMobile='" + getTelephoneMobile() + "'" +
            ", telephone2='" + getTelephone2() + "'" +
            ", fax='" + getFax() + "'" +
            ", description='" + getDescription() + "'" +
            ", poste='" + getPoste() + "'" +
            ", corpmetier='" + getCorpmetier() + "'" +
            "}";
    }
}
