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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Waldek
 */
@Entity
@Table(name = "liczniki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liczniki.findAll", query = "SELECT l FROM Liczniki l")
    , @NamedQuery(name = "Liczniki.findById", query = "SELECT l FROM Liczniki l WHERE l.id = :id")
    , @NamedQuery(name = "Liczniki.findByMiesiac", query = "SELECT l FROM Liczniki l WHERE l.miesiac = :miesiac")
    , @NamedQuery(name = "Liczniki.findByRok", query = "SELECT l FROM Liczniki l WHERE l.rok = :rok")
    , @NamedQuery(name = "Liczniki.findByLicznikWodyCieplej", query = "SELECT l FROM Liczniki l WHERE l.licznikWodyCieplej = :licznikWodyCieplej")
    , @NamedQuery(name = "Liczniki.findByLicznikWodyZimnej", query = "SELECT l FROM Liczniki l WHERE l.licznikWodyZimnej = :licznikWodyZimnej")
    , @NamedQuery(name = "Liczniki.findByLicznikCiepla", query = "SELECT l FROM Liczniki l WHERE l.licznikCiepla = :licznikCiepla")})
public class Liczniki implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "miesiac")
    private int miesiac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rok")
    private int rok;
    @Basic(optional = false)
    @NotNull
    @Column(name = "licznik_wody_cieplej")
    private int licznikWodyCieplej;
    @Basic(optional = false)
    @NotNull
    @Column(name = "licznik_wody_zimnej")
    private int licznikWodyZimnej;
    @Basic(optional = false)
    @NotNull
    @Column(name = "licznik_ciepla")
    private int licznikCiepla;
    @JoinColumn(name = "id_mieszkania", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Mieszkanie idMieszkania;

    public Liczniki() {
    }

    public Liczniki(Integer id) {
        this.id = id;
    }

    public Liczniki(Integer id, int miesiac, int rok, int licznikWodyCieplej, int licznikWodyZimnej, int licznikCiepla) {
        this.id = id;
        this.miesiac = miesiac;
        this.rok = rok;
        this.licznikWodyCieplej = licznikWodyCieplej;
        this.licznikWodyZimnej = licznikWodyZimnej;
        this.licznikCiepla = licznikCiepla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(int miesiac) {
        this.miesiac = miesiac;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public int getLicznikWodyCieplej() {
        return licznikWodyCieplej;
    }

    public void setLicznikWodyCieplej(int licznikWodyCieplej) {
        this.licznikWodyCieplej = licznikWodyCieplej;
    }

    public int getLicznikWodyZimnej() {
        return licznikWodyZimnej;
    }

    public void setLicznikWodyZimnej(int licznikWodyZimnej) {
        this.licznikWodyZimnej = licznikWodyZimnej;
    }

    public int getLicznikCiepla() {
        return licznikCiepla;
    }

    public void setLicznikCiepla(int licznikCiepla) {
        this.licznikCiepla = licznikCiepla;
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
        if (!(object instanceof Liczniki)) {
            return false;
        }
        Liczniki other = (Liczniki) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Liczniki[ id=" + id + " ]";
    }
    
}
