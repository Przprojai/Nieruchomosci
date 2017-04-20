/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Waldek
 */
@Entity
@Table(name = "mieszkanie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mieszkanie.findAll", query = "SELECT m FROM Mieszkanie m")
    , @NamedQuery(name = "Mieszkanie.findById", query = "SELECT m FROM Mieszkanie m WHERE m.id = :id")
    , @NamedQuery(name = "Mieszkanie.findByNrMieszkania", query = "SELECT m FROM Mieszkanie m WHERE m.nrMieszkania = :nrMieszkania")
    , @NamedQuery(name = "Mieszkanie.findByUlica", query = "SELECT m FROM Mieszkanie m WHERE m.ulica = :ulica")
    , @NamedQuery(name = "Mieszkanie.findByNrBloku", query = "SELECT m FROM Mieszkanie m WHERE m.nrBloku = :nrBloku")
    , @NamedQuery(name = "Mieszkanie.findByKlatka", query = "SELECT m FROM Mieszkanie m WHERE m.klatka = :klatka")
    , @NamedQuery(name = "Mieszkanie.findByLiczbaOsob", query = "SELECT m FROM Mieszkanie m WHERE m.liczbaOsob = :liczbaOsob")
    , @NamedQuery(name = "Mieszkanie.findByPowierzchnia", query = "SELECT m FROM Mieszkanie m WHERE m.powierzchnia = :powierzchnia")
    , @NamedQuery(name = "Mieszkanie.findByPietro", query = "SELECT m FROM Mieszkanie m WHERE m.pietro = :pietro")
    , @NamedQuery(name = "Mieszkanie.findByZajetosc", query = "SELECT m FROM Mieszkanie m WHERE m.zajetosc = :zajetosc ORDER BY m.id")
    , @NamedQuery(name = "Mieszkanie.findByNrKonta", query = "SELECT m FROM Mieszkanie m WHERE m.nrKonta = :nrKonta")
    , @NamedQuery(name = "Mieszkanie.findByNadplata", query = "SELECT m FROM Mieszkanie m WHERE m.nadplata = :nadplata")
    , @NamedQuery(name = "Mieszkanie.findByAdresKorespondencyjny", query = "SELECT m FROM Mieszkanie m WHERE m.adresKorespondencyjny = :adresKorespondencyjny")
    , @NamedQuery(name = "Mieszkanie.findByStanKonta", query = "SELECT m FROM Mieszkanie m WHERE m.stanKonta = :stanKonta")
    , @NamedQuery(name = "Mieszkanie.findByPodsumowanie", query = "SELECT m FROM Mieszkanie m WHERE m.podsumowanie = :podsumowanie")})
public class Mieszkanie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nr_mieszkania")
    private int nrMieszkania;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ulica")
    private String ulica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nr_bloku")
    private int nrBloku;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "klatka")
    private String klatka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "liczba_osob")
    private int liczbaOsob;
    @Basic(optional = false)
    @NotNull
    @Column(name = "powierzchnia")
    private float powierzchnia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pietro")
    private int pietro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zajetosc")
    private boolean zajetosc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nr_konta")
    private BigInteger nrKonta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nadplata")
    private float nadplata;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "adres_korespondencyjny")
    private String adresKorespondencyjny;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stan_konta")
    private float stanKonta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "podsumowanie")
    private float podsumowanie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMieszkania")
    private Collection<Lokator> lokatorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMieszkania")
    private Collection<Informacja> informacjaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMieszkania")
    private Collection<Liczniki> licznikiCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMieszkania")
    private Collection<Oplaty> oplatyCollection;
    @JoinColumn(name = "id_budynku", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Budynek idBudynku;

    public Mieszkanie() {
    }

    public Mieszkanie(Integer id) {
        this.id = id;
    }

    public Mieszkanie(Integer id, int nrMieszkania, String ulica, int nrBloku, String klatka, int liczbaOsob, float powierzchnia, int pietro, boolean zajetosc, BigInteger nrKonta, float nadplata, String adresKorespondencyjny, float stanKonta, float podsumowanie) {
        this.id = id;
        this.nrMieszkania = nrMieszkania;
        this.ulica = ulica;
        this.nrBloku = nrBloku;
        this.klatka = klatka;
        this.liczbaOsob = liczbaOsob;
        this.powierzchnia = powierzchnia;
        this.pietro = pietro;
        this.zajetosc = zajetosc;
        this.nrKonta = nrKonta;
        this.nadplata = nadplata;
        this.adresKorespondencyjny = adresKorespondencyjny;
        this.stanKonta = stanKonta;
        this.podsumowanie = podsumowanie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNrMieszkania() {
        return nrMieszkania;
    }

    public void setNrMieszkania(int nrMieszkania) {
        this.nrMieszkania = nrMieszkania;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getNrBloku() {
        return nrBloku;
    }

    public void setNrBloku(int nrBloku) {
        this.nrBloku = nrBloku;
    }

    public String getKlatka() {
        return klatka;
    }

    public void setKlatka(String klatka) {
        this.klatka = klatka;
    }

    public int getLiczbaOsob() {
        return liczbaOsob;
    }

    public void setLiczbaOsob(int liczbaOsob) {
        this.liczbaOsob = liczbaOsob;
    }

    public float getPowierzchnia() {
        return powierzchnia;
    }

    public void setPowierzchnia(float powierzchnia) {
        this.powierzchnia = powierzchnia;
    }

    public int getPietro() {
        return pietro;
    }

    public void setPietro(int pietro) {
        this.pietro = pietro;
    }

    public boolean getZajetosc() {
        return zajetosc;
    }

    public void setZajetosc(boolean zajetosc) {
        this.zajetosc = zajetosc;
    }

    public BigInteger getNrKonta() {
        return nrKonta;
    }

    public void setNrKonta(BigInteger nrKonta) {
        this.nrKonta = nrKonta;
    }

    public float getNadplata() {
        return nadplata;
    }

    public void setNadplata(float nadplata) {
        this.nadplata = nadplata;
    }

    public String getAdresKorespondencyjny() {
        return adresKorespondencyjny;
    }

    public void setAdresKorespondencyjny(String adresKorespondencyjny) {
        this.adresKorespondencyjny = adresKorespondencyjny;
    }

    public float getStanKonta() {
        return stanKonta;
    }

    public void setStanKonta(float stanKonta) {
        this.stanKonta = stanKonta;
    }

    public float getPodsumowanie() {
        return podsumowanie;
    }

    public void setPodsumowanie(float podsumowanie) {
        this.podsumowanie = podsumowanie;
    }

    @XmlTransient
    public Collection<Lokator> getLokatorCollection() {
        return lokatorCollection;
    }

    public void setLokatorCollection(Collection<Lokator> lokatorCollection) {
        this.lokatorCollection = lokatorCollection;
    }

    @XmlTransient
    public Collection<Informacja> getInformacjaCollection() {
        return informacjaCollection;
    }

    public void setInformacjaCollection(Collection<Informacja> informacjaCollection) {
        this.informacjaCollection = informacjaCollection;
    }

    @XmlTransient
    public Collection<Liczniki> getLicznikiCollection() {
        return licznikiCollection;
    }

    public void setLicznikiCollection(Collection<Liczniki> licznikiCollection) {
        this.licznikiCollection = licznikiCollection;
    }

    @XmlTransient
    public Collection<Oplaty> getOplatyCollection() {
        return oplatyCollection;
    }

    public void setOplatyCollection(Collection<Oplaty> oplatyCollection) {
        this.oplatyCollection = oplatyCollection;
    }

    public Budynek getIdBudynku() {
        return idBudynku;
    }

    public void setIdBudynku(Budynek idBudynku) {
        this.idBudynku = idBudynku;
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
        if (!(object instanceof Mieszkanie)) {
            return false;
        }
        Mieszkanie other = (Mieszkanie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Mieszkanie[ id=" + id + " ]";
    }
    
}
