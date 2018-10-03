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
 * A Bien.
 */
@Entity
@Table(name = "bien")
public class Bien implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("biens")
    private Chantier chantier;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adressechantier;

    @OneToMany(mappedBy = "bien")
    private Set<Document> documents = new HashSet<>();

    @OneToMany(mappedBy = "bien")
    private Set<Visite> visites = new HashSet<>();

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

    public Bien label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public Bien description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public Bien chantier(Chantier chantier) {
        this.chantier = chantier;
        return this;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
    }

    public Adresse getAdressechantier() {
        return adressechantier;
    }

    public Bien adressechantier(Adresse adresse) {
        this.adressechantier = adresse;
        return this;
    }

    public void setAdressechantier(Adresse adresse) {
        this.adressechantier = adresse;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Bien documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Bien addDocuments(Document document) {
        this.documents.add(document);
        document.setBien(this);
        return this;
    }

    public Bien removeDocuments(Document document) {
        this.documents.remove(document);
        document.setBien(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Set<Visite> getVisites() {
        return visites;
    }

    public Bien visites(Set<Visite> visites) {
        this.visites = visites;
        return this;
    }

    public Bien addVisites(Visite visite) {
        this.visites.add(visite);
        visite.setBien(this);
        return this;
    }

    public Bien removeVisites(Visite visite) {
        this.visites.remove(visite);
        visite.setBien(null);
        return this;
    }

    public void setVisites(Set<Visite> visites) {
        this.visites = visites;
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
        Bien bien = (Bien) o;
        if (bien.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bien.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bien{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
