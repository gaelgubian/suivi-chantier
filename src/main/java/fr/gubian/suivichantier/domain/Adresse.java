package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Adresse postale d'un bien ou d'une personne
 * 
 * 6 lignes de 38 caractères maximum (éventuellement une 7ème ligne pour l’international, pour indiquer le pays).
 * Les lignes à blanc – non renseignées – sont supprimées pour rendre l’adresse plus esthétique.
 * L’adresse est alignée à gauche, Les lignes 4 à 6 sont mises en majuscule.
 * 
 * B to C :
 * L1 : Civilité - Titre ou Qualité - Prénom - Nom
 * L2 : Numéro App ou BAL - Etage - Couloir - Esc
 * L3 : Entrée - Bâtiment - Immeuble - Résidence
 * L4 : Numéro - Libellé de la voie
 * L5 : Lieu dit ou Service particulier de distribution
 * L6 : Code postal et Localité de destination ou Code cedex et Libellé cedex
 * 
 * B to B :
 * L1 : Raison sociale ou Dénomination
 * L2 : Identité du destinataire et/ou Service
 * L3 : Entrée - Bâtiment - Immeuble - Résidence - ZI
 * L4 : Numéro - Libellé de la voie
 * L5 : Mention spéciale et Commune géographique
 * L6 : Code postal et Localité de destination ou Code cedex et Libellé cedex
 */
@ApiModel(description = "Adresse postale d'un bien ou d'une personne 6 lignes de 38 caractères maximum (éventuellement une 7ème ligne pour l’international, pour indiquer le pays). Les lignes à blanc – non renseignées – sont supprimées pour rendre l’adresse plus esthétique. L’adresse est alignée à gauche, Les lignes 4 à 6 sont mises en majuscule. B to C : L1 : Civilité - Titre ou Qualité - Prénom - Nom L2 : Numéro App ou BAL - Etage - Couloir - Esc L3 : Entrée - Bâtiment - Immeuble - Résidence L4 : Numéro - Libellé de la voie L5 : Lieu dit ou Service particulier de distribution L6 : Code postal et Localité de destination ou Code cedex et Libellé cedex B to B : L1 : Raison sociale ou Dénomination L2 : Identité du destinataire et/ou Service L3 : Entrée - Bâtiment - Immeuble - Résidence - ZI L4 : Numéro - Libellé de la voie L5 : Mention spéciale et Commune géographique L6 : Code postal et Localité de destination ou Code cedex et Libellé cedex")
@Entity
@Table(name = "adresse")
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Numéro d'appartement ou Boîte aux lettres, étage, couloir, escalier
     */
    @Size(max = 256)
    @ApiModelProperty(value = "Numéro d'appartement ou Boîte aux lettres, étage, couloir, escalier")
    @Column(name = "appartement", length = 256)
    private String appartement;

    /**
     * Entrée, batiment, immeuble, résidence, ZI
     */
    @Size(max = 256)
    @ApiModelProperty(value = "Entrée, batiment, immeuble, résidence, ZI")
    @Column(name = "entree", length = 256)
    private String entree;

    /**
     * Numéro de rue, dont éventuellement le complément bis, ter...
     */
    @Size(max = 32)
    @ApiModelProperty(value = "Numéro de rue, dont éventuellement le complément bis, ter...")
    @Column(name = "numero", length = 32)
    private String numero;

    /**
     * libellé de la voie
     */
    @Size(max = 256)
    @ApiModelProperty(value = "libellé de la voie")
    @Column(name = "voie", length = 256)
    private String voie;

    /**
     * Complément d'adresse (lieu dit, service particulier de distribution, mention spéciale ou commune géographique)
     */
    @Size(max = 256)
    @ApiModelProperty(value = "Complément d'adresse (lieu dit, service particulier de distribution, mention spéciale ou commune géographique)")
    @Column(name = "complement", length = 256)
    private String complement;

    @Column(name = "codepostal")
    private Integer codepostal;

    @Size(max = 256)
    @Column(name = "ville", length = 256)
    private String ville;

    /**
     * position géographique (latitude)
     */
    @ApiModelProperty(value = "position géographique (latitude)")
    @Column(name = "latitude")
    private String latitude;

    /**
     * position géographique (longitude)
     */
    @ApiModelProperty(value = "position géographique (longitude)")
    @Column(name = "longitude")
    private String longitude;

    @OneToOne(mappedBy = "adresse")
    @JsonIgnore
    private Contact contact;

    @OneToOne(mappedBy = "adresseBien")
    @JsonIgnore
    private Bien bien;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppartement() {
        return appartement;
    }

    public Adresse appartement(String appartement) {
        this.appartement = appartement;
        return this;
    }

    public void setAppartement(String appartement) {
        this.appartement = appartement;
    }

    public String getEntree() {
        return entree;
    }

    public Adresse entree(String entree) {
        this.entree = entree;
        return this;
    }

    public void setEntree(String entree) {
        this.entree = entree;
    }

    public String getNumero() {
        return numero;
    }

    public Adresse numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getVoie() {
        return voie;
    }

    public Adresse voie(String voie) {
        this.voie = voie;
        return this;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public String getComplement() {
        return complement;
    }

    public Adresse complement(String complement) {
        this.complement = complement;
        return this;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Integer getCodepostal() {
        return codepostal;
    }

    public Adresse codepostal(Integer codepostal) {
        this.codepostal = codepostal;
        return this;
    }

    public void setCodepostal(Integer codepostal) {
        this.codepostal = codepostal;
    }

    public String getVille() {
        return ville;
    }

    public Adresse ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLatitude() {
        return latitude;
    }

    public Adresse latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Adresse longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Contact getContact() {
        return contact;
    }

    public Adresse contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Bien getBien() {
        return bien;
    }

    public Adresse bien(Bien bien) {
        this.bien = bien;
        return this;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
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
        Adresse adresse = (Adresse) o;
        if (adresse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adresse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Adresse{" +
            "id=" + getId() +
            ", appartement='" + getAppartement() + "'" +
            ", entree='" + getEntree() + "'" +
            ", numero='" + getNumero() + "'" +
            ", voie='" + getVoie() + "'" +
            ", complement='" + getComplement() + "'" +
            ", codepostal=" + getCodepostal() +
            ", ville='" + getVille() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            "}";
    }
}
