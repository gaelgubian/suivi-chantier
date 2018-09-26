package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * application {
 * config {
 * baseName suivichantier,
 * nativeLanguage fr,
 * packageName fr.gubian.suivichantier,
 * prodDatabaseType postgresql
 * }
 * }
 */
@ApiModel(description = "application { config { baseName suivichantier, nativeLanguage fr, packageName fr.gubian.suivichantier, prodDatabaseType postgresql } }")
@Entity
@Table(name = "chantier")
public class Chantier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 64)
    @Column(name = "jhi_label", length = 64, nullable = false)
    private String label;

    
    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @OneToMany(mappedBy = "chantier")
    private Set<Bien> biens = new HashSet<>();

    @OneToMany(mappedBy = "chantier")
    private Set<Document> documents = new HashSet<>();

    @OneToMany(mappedBy = "chantier")
    private Set<ChantierIntervenant> chantierIntervenants = new HashSet<>();

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

    public Chantier label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public byte[] getImage() {
        return image;
    }

    public Chantier image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Chantier imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getDescription() {
        return description;
    }

    public Chantier description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Chantier dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Chantier dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Set<Bien> getBiens() {
        return biens;
    }

    public Chantier biens(Set<Bien> biens) {
        this.biens = biens;
        return this;
    }

    public Chantier addBiens(Bien bien) {
        this.biens.add(bien);
        bien.setChantier(this);
        return this;
    }

    public Chantier removeBiens(Bien bien) {
        this.biens.remove(bien);
        bien.setChantier(null);
        return this;
    }

    public void setBiens(Set<Bien> biens) {
        this.biens = biens;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public Chantier documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public Chantier addDocuments(Document document) {
        this.documents.add(document);
        document.setChantier(this);
        return this;
    }

    public Chantier removeDocuments(Document document) {
        this.documents.remove(document);
        document.setChantier(null);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Set<ChantierIntervenant> getChantierIntervenants() {
        return chantierIntervenants;
    }

    public Chantier chantierIntervenants(Set<ChantierIntervenant> chantierIntervenants) {
        this.chantierIntervenants = chantierIntervenants;
        return this;
    }

    public Chantier addChantierIntervenant(ChantierIntervenant chantierIntervenant) {
        this.chantierIntervenants.add(chantierIntervenant);
        chantierIntervenant.setChantier(this);
        return this;
    }

    public Chantier removeChantierIntervenant(ChantierIntervenant chantierIntervenant) {
        this.chantierIntervenants.remove(chantierIntervenant);
        chantierIntervenant.setChantier(null);
        return this;
    }

    public void setChantierIntervenants(Set<ChantierIntervenant> chantierIntervenants) {
        this.chantierIntervenants = chantierIntervenants;
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
        Chantier chantier = (Chantier) o;
        if (chantier.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chantier.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Chantier{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
