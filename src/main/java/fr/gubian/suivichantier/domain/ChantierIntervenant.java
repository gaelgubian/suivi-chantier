package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import fr.gubian.suivichantier.domain.enumeration.Corpsmetier;

import fr.gubian.suivichantier.domain.enumeration.Role;

/**
 * Un intervenant sur un chantier particulier.
 * Chaque chantier devrait au moins comporter une MOA et une MOE.
 */
@ApiModel(description = "Un intervenant sur un chantier particulier. Chaque chantier devrait au moins comporter une MOA et une MOE.")
@Entity
@Table(name = "chantier_intervenant")
public class ChantierIntervenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "corpmetier")
    private Corpsmetier corpmetier;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_role")
    private Role role;

    @OneToOne
    @JoinColumn(unique = true)
    private Contact contact;

    @OneToOne(mappedBy = "chantierIntervenant")
    @JsonIgnore
    private Signature signature;

    @ManyToOne
    @JsonIgnoreProperties("chantierIntervenants")
    private Chantier chantier;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Corpsmetier getCorpmetier() {
        return corpmetier;
    }

    public ChantierIntervenant corpmetier(Corpsmetier corpmetier) {
        this.corpmetier = corpmetier;
        return this;
    }

    public void setCorpmetier(Corpsmetier corpmetier) {
        this.corpmetier = corpmetier;
    }

    public Role getRole() {
        return role;
    }

    public ChantierIntervenant role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Contact getContact() {
        return contact;
    }

    public ChantierIntervenant contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Signature getSignature() {
        return signature;
    }

    public ChantierIntervenant signature(Signature signature) {
        this.signature = signature;
        return this;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public Chantier getChantier() {
        return chantier;
    }

    public ChantierIntervenant chantier(Chantier chantier) {
        this.chantier = chantier;
        return this;
    }

    public void setChantier(Chantier chantier) {
        this.chantier = chantier;
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
        ChantierIntervenant chantierIntervenant = (ChantierIntervenant) o;
        if (chantierIntervenant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chantierIntervenant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChantierIntervenant{" +
            "id=" + getId() +
            ", corpmetier='" + getCorpmetier() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
