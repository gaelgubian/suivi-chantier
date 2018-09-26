package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Signature.
 */
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
    private Document document;

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

    public Document getDocument() {
        return document;
    }

    public Signature document(Document document) {
        this.document = document;
        return this;
    }

    public void setDocument(Document document) {
        this.document = document;
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
