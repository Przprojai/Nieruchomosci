/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Waldek
 */
@Entity
@Table(name = "informacja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Informacja.findAll", query = "SELECT i FROM Informacja i")
    , @NamedQuery(name = "Informacja.findById", query = "SELECT i FROM Informacja i WHERE i.id = :id")
    , @NamedQuery(name = "Informacja.findByOpis", query = "SELECT i FROM Informacja i WHERE i.opis = :opis")
    , @NamedQuery(name = "Informacja.findByNumer", query = "SELECT i FROM Informacja i WHERE i.numer = :numer")
    , @NamedQuery(name = "Informacja.findByPotwierdzenie", query = "SELECT i FROM Informacja i WHERE i.potwierdzenie = :potwierdzenie")})
public class Informacja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numer")
    private int numer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "potwierdzenie")
    private boolean potwierdzenie;
    @JoinColumn(name = "id_mieszkania", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Mieszkanie idMieszkania;

    public Informacja() {
    }

    public Informacja(Integer id) {
        this.id = id;
    }

    public Informacja(Integer id, String opis, int numer, boolean potwierdzenie) {
        this.id = id;
        this.opis = opis;
        this.numer = numer;
        this.potwierdzenie = potwierdzenie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public boolean getPotwierdzenie() {
        return potwierdzenie;
    }

    public void setPotwierdzenie(boolean potwierdzenie) {
        this.potwierdzenie = potwierdzenie;
    }

    public Mieszkanie getIdMieszkania() {
        return idMieszkania;
    }

    public void setIdMieszkania(Mieszkanie idMieszkania) {
        this.idMieszkania = idMieszkania;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Informacja)) {
            return false;
        }
        Informacja other = (Informacja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Informacja[ id=" + id + " ]";
    }
    
}
