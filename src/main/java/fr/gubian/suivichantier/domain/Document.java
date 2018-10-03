package fr.gubian.suivichantier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Document
 */
@ApiModel(description = "Document")
@Entity
@Table(name = "document")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Nom du document
     */
    @NotNull
    @Size(max = 256)
    @ApiModelProperty(value = "Nom du document", required = true)
    @Column(name = "jhi_label", length = 256, nullable = false)
    private String label;

    /**
     * Détail sur le document, résumé, commentaires...
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "Détail sur le document, résumé, commentaires...")
    @Column(name = "description", length = 4000)
    private String description;

    /**
     * Chemin d'accès complet au fichier sur le serveur. Dont le nom du fichier après renommage de sécurité
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "Chemin d'accès complet au fichier sur le serveur. Dont le nom du fichier après renommage de sécurité")
    @Column(name = "path", length = 4000)
    private String path;

    /**
     * nom du fichier original
     */
    @Size(max = 512)
    @ApiModelProperty(value = "nom du fichier original")
    @Column(name = "filename", length = 512)
    private String filename;

    /**
     * TODO : attribut à supprimer. Stocker le fichier sur le système, le convertir en images (tuiles)
     */
    
    @ApiModelProperty(value = "TODO : attribut à supprimer. Stocker le fichier sur le système, le convertir en images (tuiles)")
    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "content_content_type")
    private String contentContentType;

    @OneToMany(mappedBy = "documents")
    private Set<Signature> signatures = new HashSet<>();

    @OneToMany(mappedBy = "documents")
    private Set<DocumentTuile> tuiles = new HashSet<>();

    @OneToMany(mappedBy = "documents")
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("documents")
    private Chantier chantier;

    @ManyToOne
    @JsonIgnoreProperties("documents")
    private Bien bien;

    @ManyToOne
    @JsonIgnoreProperties("supportsVisites")
    private Visite visite;

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

    public String getFilename() {
        return filename;
    }

    public Document filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getContent() {
        return content;
    }

    public Document content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public Document contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public Set<Signature> getSignatures() {
        return signatures;
    }

    public Document signatures(Set<Signature> signatures) {
        this.signatures = signatures;
        return this;
    }

    public Document addSignatures(Signature signature) {
        this.signatures.add(signature);
        signature.setDocuments(this);
        return this;
    }

    public Document removeSignatures(Signature signature) {
        this.signatures.remove(signature);
        signature.setDocuments(null);
        return this;
    }

    public void setSignatures(Set<Signature> signatures) {
        this.signatures = signatures;
    }

    public Set<DocumentTuile> getTuiles() {
        return tuiles;
    }

    public Document tuiles(Set<DocumentTuile> documentTuiles) {
        this.tuiles = documentTuiles;
        return this;
    }

    public Document addTuiles(DocumentTuile documentTuile) {
        this.tuiles.add(documentTuile);
        documentTuile.setDocuments(this);
        return this;
    }

    public Document removeTuiles(DocumentTuile documentTuile) {
        this.tuiles.remove(documentTuile);
        documentTuile.setDocuments(null);
        return this;
    }

    public void setTuiles(Set<DocumentTuile> documentTuiles) {
        this.tuiles = documentTuiles;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Document comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Document addComments(Comment comment) {
        this.comments.add(comment);
        comment.setDocuments(this);
        return this;
    }

    public Document removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setDocuments(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
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

    public Bien getBien() {
        return bien;
    }

    public Document bien(Bien bien) {
        this.bien = bien;
        return this;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
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
            ", filename='" + getFilename() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            "}";
    }
}
