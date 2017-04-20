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
@Table(name = "ksiegowosc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ksiegowosc.findAll", query = "SELECT k FROM Ksiegowosc k")
    , @NamedQuery(name = "Ksiegowosc.findById", query = "SELECT k FROM Ksiegowosc k WHERE k.id = :id")
    , @NamedQuery(name = "Ksiegowosc.findByLogin", query = "SELECT k FROM Ksiegowosc k WHERE k.login = :login")
    , @NamedQuery(name = "Ksiegowosc.findByHaslo", query = "SELECT k FROM Ksiegowosc k WHERE k.haslo = :haslo")
    , @NamedQuery(name = "Ksiegowosc.findByImie", query = "SELECT k FROM Ksiegowosc k WHERE k.imie = :imie")
    , @NamedQuery(name = "Ksiegowosc.findByNazwisko", query = "SELECT k FROM Ksiegowosc k WHERE k.nazwisko = :nazwisko")
    , @NamedQuery(name = "Ksiegowosc.findByKontakt", query = "SELECT k FROM Ksiegowosc k WHERE k.kontakt = :kontakt")})
public class Ksiegowosc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "haslo")
    private String haslo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "imie")
    private String imie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nazwisko")
    private String nazwisko;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "kontakt")
    private String kontakt;

    public Ksiegowosc() {
    }

    public Ksiegowosc(Integer id) {
        this.id = id;
    }

    public Ksiegowosc(Integer id, String login, String haslo, String imie, String nazwisko, String kontakt) {
        this.id = id;
        this.login = login;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.kontakt = kontakt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
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
        if (!(object instanceof Ksiegowosc)) {
            return false;
        }
        Ksiegowosc other = (Ksiegowosc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Ksiegowosc[ id=" + id + " ]";
    }
    
}
