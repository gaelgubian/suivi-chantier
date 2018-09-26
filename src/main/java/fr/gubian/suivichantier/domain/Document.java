package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_label", nullable = false)
    private String label;

    @Column(name = "description")
    private String description;

    @Column(name = "path")
    private String path;

    @OneToMany(mappedBy = "document")
    private Set<Signature> signatures = new HashSet<>();

    @OneToMany(mappedBy = "document")
    private Set<DocumentTuile> documentTuiles = new HashSet<>();

    @OneToOne(mappedBy = "document")
    @JsonIgnore
    private Visite visite;

    @ManyToOne
    @JsonIgnoreProperties("documents")
    private Chantier chantier;

    @ManyToOne
    @JsonIgnoreProperties("documents")
    private Bien chantier;

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

    public Document label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public Document description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public Document path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Signature> getSignatures() {
        return signatures;
    }

    public Document signatures(Set<Signature> signatures) {
        this.signatures = signatures;
        return this;
    }

    public Document addSignature(Signature signature) {
        this.signatures.add(signature);
        signature.setDocument(this);
        return this;
    }

    public Document removeSignature(Signature signature) {
        this.signatures.remove(signature);
        signature.setDocument(null);
        return this;
    }

    public void setSignatures(Set<Signature> signatures) {
        this.signatures = signatures;
    }

    public Set<DocumentTuile> getDocumentTuiles() {
        return documentTuiles;
    }

    public Document documentTuiles(Set<DocumentTuile> documentTuiles) {
        this.documentTuiles = documentTuiles;
        return this;
    }

    public Document addDocumentTuile(DocumentTuile documentTuile) {
        this.documentTuiles.add(documentTuile);
        documentTuile.setDocument(this);
        return this;
    }

    public Document removeDocumentTuile(DocumentTuile documentTuile) {
        this.documentTuiles.remove(documentTuile);
        documentTuile.setDocument(null);
        return this;
    }

    public void setDocumentTuiles(Set<DocumentTuile> documentTuiles) {
        this.documentTuiles = documentTuiles;
    }

    public Visite getVisite() {
        return visite;
    }

    public Document visite(Visite visite) {
        this.visite = visite;
        return this;
    }

    public void setVisite(Visite visite) {
        this.visite = visite;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public Document chantier(Chantier chantier) {
        this.chantier = chantier;
        return this;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Bien getChantier() {
        return chantier;
    }

    public Document chantier(Bien bien) {
        this.chantier = bien;
        return this;
    }

    public void setChantier(Bien bien) {
        this.chantier = bien;
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
        Document document = (Document) o;
        if (document.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), document.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
