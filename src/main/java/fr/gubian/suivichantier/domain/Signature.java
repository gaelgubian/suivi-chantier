package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * image de la signature d'un document (saisie par stylet) par une personne
 */
@ApiModel(description = "image de la signature d'un document (saisie par stylet) par une personne")
@Entity
@Table(name = "signature")
public class Signature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private ChantierIntervenant chantierIntervenant;

    @ManyToOne
    @JsonIgnoreProperties("signatures")
    private Document documents;

    @ManyToOne
    @JsonIgnoreProperties("signatures")
    private Visite visite;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public Signature image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Signature imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public ChantierIntervenant getChantierIntervenant() {
        return chantierIntervenant;
    }

    public Signature chantierIntervenant(ChantierIntervenant chantierIntervenant) {
        this.chantierIntervenant = chantierIntervenant;
        return this;
    }

    public void setChantierIntervenant(ChantierIntervenant chantierIntervenant) {
        this.chantierIntervenant = chantierIntervenant;
    }

    public Document getDocuments() {
        return documents;
    }

    public Signature documents(Document document) {
        this.documents = document;
        return this;
    }

    public void setDocuments(Document document) {
        this.documents = document;
    }

    public Visite getVisite() {
        return visite;
    }

    public Signature visite(Visite visite) {
        this.visite = visite;
        return this;
    }

    public void setVisite(Visite visite) {
        this.visite = visite;
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
        Signature signature = (Signature) o;
        if (signature.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signature.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Signature{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
