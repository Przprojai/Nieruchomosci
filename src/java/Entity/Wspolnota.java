/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Waldek
 */
@Entity
@Table(name = "wspolnota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wspolnota.findAll", query = "SELECT w FROM Wspolnota w")
    , @NamedQuery(name = "Wspolnota.findById", query = "SELECT w FROM Wspolnota w WHERE w.id = :id")
    , @NamedQuery(name = "Wspolnota.findByNazwa", query = "SELECT w FROM Wspolnota w WHERE w.nazwa = :nazwa")})
public class Wspolnota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idWspolnota")
    private Collection<Budynek> budynekCollection;

    public Wspolnota() {
    }

    public Wspolnota(Integer id) {
        this.id = id;
    }

    public Wspolnota(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @XmlTransient
    public Collection<Budynek> getBudynekCollection() {
        return budynekCollection;
    }

    public void setBudynekCollection(Collection<Budynek> budynekCollection) {
        this.budynekCollection = budynekCollection;
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
        if (!(object instanceof Wspolnota)) {
            return false;
        }
        Wspolnota other = (Wspolnota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Wspolnota[ id=" + id + " ]";
    }
    
}
