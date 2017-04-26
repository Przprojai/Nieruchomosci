/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Waldek
 */
@Entity
@Table(name = "oplaty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oplaty.findAll", query = "SELECT o FROM Oplaty o")
    , @NamedQuery(name = "Oplaty.findById", query = "SELECT o FROM Oplaty o WHERE o.id = :id")
    , @NamedQuery(name = "Oplaty.findByMiesiac", query = "SELECT o FROM Oplaty o WHERE o.miesiac = :miesiac")
    , @NamedQuery(name = "Oplaty.findByRok", query = "SELECT o FROM Oplaty o WHERE o.rok = :rok")
    , @NamedQuery(name = "Oplaty.findBySumaOplat", query = "SELECT o FROM Oplaty o WHERE o.sumaOplat = :sumaOplat")
    , @NamedQuery(name = "Oplaty.findByZaplacono", query = "SELECT o FROM Oplaty o WHERE o.zaplacono = :zaplacono")
    , @NamedQuery(name = "Oplaty.findByZaleglosci", query = "SELECT o FROM Oplaty o WHERE o.zaleglosci = :zaleglosci")
    , @NamedQuery(name = "Oplaty.findByPodsumowanie", query = "SELECT o FROM Oplaty o WHERE o.podsumowanie = :podsumowanie")})
public class Oplaty implements Serializable {

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
    @Column(name = "suma_oplat")
    private BigDecimal sumaOplat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zaplacono")
    private BigDecimal zaplacono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zaleglosci")
    private BigDecimal zaleglosci;
    @Basic(optional = false)
    @NotNull
    @Column(name = "podsumowanie")
    private BigDecimal podsumowanie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOplaty")
    private Collection<DodatkoweOplaty> dodatkoweOplatyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOplaty")
    private Collection<Szczegoly> szczegolyCollection;
    @JoinColumn(name = "id_mieszkania", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Mieszkanie idMieszkania;
    @JoinColumn(name = "id_stawki", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stawki idStawki;

    public Oplaty() {
    }

    public Oplaty(Integer id) {
        this.id = id;
    }

    public Oplaty(Integer id, int miesiac, int rok, BigDecimal sumaOplat, BigDecimal zaplacono, BigDecimal zaleglosci, BigDecimal podsumowanie) {
        this.id = id;
        this.miesiac = miesiac;
        this.rok = rok;
        this.sumaOplat = sumaOplat;
        this.zaplacono = zaplacono;
        this.podsumowanie = podsumowanie;
        this.zaleglosci = zaleglosci;
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

    public BigDecimal getSumaOplat() {
        return sumaOplat;
    }

    public void setSumaOplat(BigDecimal sumaOplat) {
        this.sumaOplat = sumaOplat;
    }

    public BigDecimal getZaplacono() {
        return zaplacono;
    }

    public void setZaplacono(BigDecimal zaplacono) {
        this.zaplacono = zaplacono;
    }

    public BigDecimal getZaleglosci() {
        return zaleglosci;
    }

    public void setZaleglosci(BigDecimal zaleglosci) {
        this.zaleglosci = zaleglosci;
    }
    public BigDecimal getPodsumowanie() {
        return podsumowanie;
    }

    public void setPodsumowanie(BigDecimal podsumowanie) {
        this.podsumowanie = podsumowanie;
    }

    @XmlTransient
    public Collection<DodatkoweOplaty> getDodatkoweOplatyCollection() {
        return dodatkoweOplatyCollection;
    }

    public void setDodatkoweOplatyCollection(Collection<DodatkoweOplaty> dodatkoweOplatyCollection) {
        this.dodatkoweOplatyCollection = dodatkoweOplatyCollection;
    }

    @XmlTransient
    public Collection<Szczegoly> getSzczegolyCollection() {
        return szczegolyCollection;
    }

    public void setSzczegolyCollection(Collection<Szczegoly> szczegolyCollection) {
        this.szczegolyCollection = szczegolyCollection;
    }

    public Mieszkanie getIdMieszkania() {
        return idMieszkania;
    }

    public void setIdMieszkania(Mieszkanie idMieszkania) {
        this.idMieszkania = idMieszkania;
    }

    public Stawki getIdStawki() {
        return idStawki;
    }

    public void setIdStawki(Stawki idStawki) {
        this.idStawki = idStawki;
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
        if (!(object instanceof Oplaty)) {
            return false;
        }
        Oplaty other = (Oplaty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Oplaty[ id=" + id + " ]";
    }
    
}
