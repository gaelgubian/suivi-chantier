package fr.gubian.suivichantier.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Adresse.
 */
@Entity
@Table(name = "adresse")
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "rue")
    private String rue;

    @Column(name = "complement")
    private String complement;

    @Column(name = "codepostal")
    private Integer codepostal;

    @Column(name = "ville")
    private String ville;

    @Column(name = "positionx")
    private String positionx;

    @Column(name = "positiony")
    private String positiony;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRue() {
        return rue;
    }

    public Adresse rue(String rue) {
        this.rue = rue;
        return this;
    }

    public void setRue(String rue) {
        this.rue = rue;
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

    public String getPositionx() {
        return positionx;
    }

    public Adresse positionx(String positionx) {
        this.positionx = positionx;
        return this;
    }

    public void setPositionx(String positionx) {
        this.positionx = positionx;
    }

    public String getPositiony() {
        return positiony;
    }

    public Adresse positiony(String positiony) {
        this.positiony = positiony;
        return this;
    }

    public void setPositiony(String positiony) {
        this.positiony = positiony;
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
            ", numero='" + getNumero() + "'" +
            ", rue='" + getRue() + "'" +
            ", complement='" + getComplement() + "'" +
            ", codepostal=" + getCodepostal() +
            ", ville='" + getVille() + "'" +
            ", positionx='" + getPositionx() + "'" +
            ", positiony='" + getPositiony() + "'" +
            "}";
    }
}
