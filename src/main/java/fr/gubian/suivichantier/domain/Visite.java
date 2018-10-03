package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Réunion, visite
 */
@ApiModel(description = "Réunion, visite")
@Entity
@Table(name = "visite")
public class Visite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "jhi_label", length = 256, nullable = false)
    private String label;

    @Size(max = 4000)
    @Column(name = "resume", length = 4000)
    private String resume;

    @Column(name = "jhi_date")
    private LocalDate date;

    @OneToMany(mappedBy = "visite")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "visite")
    private Set<Signature> signatures = new HashSet<>();

    @OneToMany(mappedBy = "visite")
    private Set<Document> supportsVisites = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("visites")
    private Bien bien;

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

    public Visite label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getResume() {
        return resume;
    }

    public Visite resume(String resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public LocalDate getDate() {
        return date;
    }

    public Visite date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Visite comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Visite addComments(Comment comment) {
        this.comments.add(comment);
        comment.setVisite(this);
        return this;
    }

    public Visite removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setVisite(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Signature> getSignatures() {
        return signatures;
    }

    public Visite signatures(Set<Signature> signatures) {
        this.signatures = signatures;
        return this;
    }

    public Visite addSignatures(Signature signature) {
        this.signatures.add(signature);
        signature.setVisite(this);
        return this;
    }

    public Visite removeSignatures(Signature signature) {
        this.signatures.remove(signature);
        signature.setVisite(null);
        return this;
    }

    public void setSignatures(Set<Signature> signatures) {
        this.signatures = signatures;
    }

    public Set<Document> getSupportsVisites() {
        return supportsVisites;
    }

    public Visite supportsVisites(Set<Document> documents) {
        this.supportsVisites = documents;
        return this;
    }

    public Visite addSupportsVisite(Document document) {
        this.supportsVisites.add(document);
        document.setVisite(this);
        return this;
    }

    public Visite removeSupportsVisite(Document document) {
        this.supportsVisites.remove(document);
        document.setVisite(null);
        return this;
    }

    public void setSupportsVisites(Set<Document> documents) {
        this.supportsVisites = documents;
    }

    public Bien getBien() {
        return bien;
    }

    public Visite bien(Bien bien) {
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
        Visite visite = (Visite) o;
        if (visite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), visite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Visite{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", resume='" + getResume() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
