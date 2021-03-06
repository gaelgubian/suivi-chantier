package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import fr.gubian.suivichantier.domain.enumeration.CommentType;

import fr.gubian.suivichantier.domain.enumeration.CommentState;

/**
 * Commentaire effectué lors d'une visite
 */
@ApiModel(description = "Commentaire effectué lors d'une visite")
@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Objet du commentaire
     */
    @NotNull
    @Size(max = 256)
    @ApiModelProperty(value = "Objet du commentaire", required = true)
    @Column(name = "jhi_label", length = 256, nullable = false)
    private String label;

    /**
     * Détail du commentaire
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "Détail du commentaire")
    @Column(name = "jhi_comment", length = 4000)
    private String comment;

    /**
     * Position (X) du commentaire lorsqu'il est lié à un document
     */
    @ApiModelProperty(value = "Position (X) du commentaire lorsqu'il est lié à un document")
    @Column(name = "positionx")
    private Integer positionx;

    /**
     * Position (Y) du commentaire lorsqu'il est lié à un document
     */
    @ApiModelProperty(value = "Position (Y) du commentaire lorsqu'il est lié à un document")
    @Column(name = "positiony")
    private Integer positiony;

    /**
     * Largeur du commentaire lorsqu'il est lié à un document
     */
    @ApiModelProperty(value = "Largeur du commentaire lorsqu'il est lié à un document")
    @Column(name = "width")
    private Integer width;

    /**
     * Hauteur du commentaire lorsqu'il est lié à un document
     */
    @ApiModelProperty(value = "Hauteur du commentaire lorsqu'il est lié à un document")
    @Column(name = "heigth")
    private Integer heigth;

    /**
     * Date de dernière modification
     */
    @ApiModelProperty(value = "Date de dernière modification")
    @Column(name = "modified")
    private ZonedDateTime modified;

    /**
     * Echéance de résolution
     */
    @ApiModelProperty(value = "Echéance de résolution")
    @Column(name = "echeance")
    private LocalDate echeance;

    /**
     * Image illustrant le commentaire effectué. Par exemple une photo de l'anomalie évoquée dans le commentaire.
     */
    
    @ApiModelProperty(value = "Image illustrant le commentaire effectué. Par exemple une photo de l'anomalie évoquée dans le commentaire.")
    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    /**
     * Type de commentaire
     */
    @ApiModelProperty(value = "Type de commentaire")
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private CommentType type;

    /**
     * Statut de l'avancement de la prise en compte du commentaire
     */
    @ApiModelProperty(value = "Statut de l'avancement de la prise en compte du commentaire")
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private CommentState state;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Icon icon;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Visite visite;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Document documents;

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

    public Comment label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComment() {
        return comment;
    }

    public Comment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPositionx() {
        return positionx;
    }

    public Comment positionx(Integer positionx) {
        this.positionx = positionx;
        return this;
    }

    public void setPositionx(Integer positionx) {
        this.positionx = positionx;
    }

    public Integer getPositiony() {
        return positiony;
    }

    public Comment positiony(Integer positiony) {
        this.positiony = positiony;
        return this;
    }

    public void setPositiony(Integer positiony) {
        this.positiony = positiony;
    }

    public Integer getWidth() {
        return width;
    }

    public Comment width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeigth() {
        return heigth;
    }

    public Comment heigth(Integer heigth) {
        this.heigth = heigth;
        return this;
    }

    public void setHeigth(Integer heigth) {
        this.heigth = heigth;
    }

    public ZonedDateTime getModified() {
        return modified;
    }

    public Comment modified(ZonedDateTime modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(ZonedDateTime modified) {
        this.modified = modified;
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public Comment echeance(LocalDate echeance) {
        this.echeance = echeance;
        return this;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }

    public byte[] getImage() {
        return image;
    }

    public Comment image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Comment imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public CommentType getType() {
        return type;
    }

    public Comment type(CommentType type) {
        this.type = type;
        return this;
    }

    public void setType(CommentType type) {
        this.type = type;
    }

    public CommentState getState() {
        return state;
    }

    public Comment state(CommentState state) {
        this.state = state;
        return this;
    }

    public void setState(CommentState state) {
        this.state = state;
    }

    public Icon getIcon() {
        return icon;
    }

    public Comment icon(Icon icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Visite getVisite() {
        return visite;
    }

    public Comment visite(Visite visite) {
        this.visite = visite;
        return this;
    }

    public void setVisite(Visite visite) {
        this.visite = visite;
    }

    public Document getDocuments() {
        return documents;
    }

    public Comment documents(Document document) {
        this.documents = document;
        return this;
    }

    public void setDocuments(Document document) {
        this.documents = document;
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
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", comment='" + getComment() + "'" +
            ", positionx=" + getPositionx() +
            ", positiony=" + getPositiony() +
            ", width=" + getWidth() +
            ", heigth=" + getHeigth() +
            ", modified='" + getModified() + "'" +
            ", echeance='" + getEcheance() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", type='" + getType() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
