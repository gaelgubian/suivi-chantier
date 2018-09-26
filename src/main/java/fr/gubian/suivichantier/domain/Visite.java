package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

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

    @Column(name = "jhi_label")
    private String label;

    @Column(name = "jhi_date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties("visites")
    private Bien bien;

    @OneToOne
    @JoinColumn(unique = true)
    private Document document;

    @OneToMany(mappedBy = "visite")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "visite")
    private Set<Signature> signatures = new HashSet<>();

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

    public Document getDocument() {
        return document;
    }

    public Visite document(Document document) {
        this.document = document;
        return this;
    }

    public void setDocument(Document document) {
        this.document = document;
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

    public Visite addSignature(Signature signature) {
        this.signatures.add(signature);
        signature.setVisite(this);
        return this;
    }

    public Visite removeSignature(Signature signature) {
        this.signatures.remove(signature);
        signature.setVisite(null);
        return this;
    }

    public void setSignatures(Set<Signature> signatures) {
        this.signatures = signatures;
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
            ", date='" + getDate() + "'" +
            "}";
    }
}
