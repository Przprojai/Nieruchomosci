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
@Table(name = "budynek")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Budynek.findAll", query = "SELECT b FROM Budynek b")
    , @NamedQuery(name = "Budynek.findById", query = "SELECT b FROM Budynek b WHERE b.id = :id")
    , @NamedQuery(name = "Budynek.findByAdres", query = "SELECT b FROM Budynek b WHERE b.adres = :adres")
    , @NamedQuery(name = "Budynek.findByLiczbaMieszkan", query = "SELECT b FROM Budynek b WHERE b.liczbaMieszkan = :liczbaMieszkan")
    , @NamedQuery(name = "Budynek.findByLiczbaKlatek", query = "SELECT b FROM Budynek b WHERE b.liczbaKlatek = :liczbaKlatek")
    , @NamedQuery(name = "Budynek.findByKontakt", query = "SELECT b FROM Budynek b WHERE b.kontakt = :kontakt")
    , @NamedQuery(name = "Budynek.findByWspolnyLicznik", query = "SELECT b FROM Budynek b WHERE b.wspolnyLicznik = :wspolnyLicznik")})
public class Budynek implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "adres")
    private String adres;
    @Basic(optional = false)
    @NotNull
    @Column(name = "liczba_mieszkan")
    private int liczbaMieszkan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "liczba_klatek")
    private int liczbaKlatek;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "kontakt")
    private String kontakt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wspolny_licznik")
    private boolean wspolnyLicznik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wspolny_licznik_gazu")
    private boolean wspolnyLicznikGazu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudynku")
    private Collection<PodsumowanieBudynku> podsumowanieBudynkuCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudynku")
    private Collection<LicznikiBudynku> licznikiBudynkuCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudynku")
    private Collection<DodatkoweOplaty> dodatkoweOplatyCollection;
    @JoinColumn(name = "id_wspolnota", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Wspolnota idWspolnota;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudynku")
    private Collection<Stawki> stawkiCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudynku")
    private Collection<Mieszkanie> mieszkanieCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBudynku")
    private Collection<Awaria> awariaCollection;

    public Budynek() {
    }

    public Budynek(Integer id) {
        this.id = id;
    }

    public Budynek(Integer id, String adres, int liczbaMieszkan, int liczbaKlatek, String kontakt, boolean wspolnyLicznik,boolean wspolnyLicznikGazu) {
        this.id = id;
        this.adres = adres;
        this.liczbaMieszkan = liczbaMieszkan;
        this.liczbaKlatek = liczbaKlatek;
        this.kontakt = kontakt;
        this.wspolnyLicznik = wspolnyLicznik;
        this.wspolnyLicznikGazu = wspolnyLicznikGazu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public int getLiczbaMieszkan() {
        return liczbaMieszkan;
    }

    public void setLiczbaMieszkan(int liczbaMieszkan) {
        this.liczbaMieszkan = liczbaMieszkan;
    }

    public int getLiczbaKlatek() {
        return liczbaKlatek;
    }

    public void setLiczbaKlatek(int liczbaKlatek) {
        this.liczbaKlatek = liczbaKlatek;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public boolean getWspolnyLicznik() {
        return wspolnyLicznik;
    }

    public void setWspolnyLicznik(boolean wspolnyLicznik) {
        this.wspolnyLicznik = wspolnyLicznik;
    }

    public boolean getWspolnyLicznikGazu() {
        return wspolnyLicznikGazu;
    }

    public void setWspolnyLicznikGazu(boolean wspolnyLicznikGazu) {
        this.wspolnyLicznikGazu = wspolnyLicznikGazu;
    }

    @XmlTransient
    public Collection<PodsumowanieBudynku> getPodsumowanieBudynkuCollection() {
        return podsumowanieBudynkuCollection;
    }

    public void setPodsumowanieBudynkuCollection(Collection<PodsumowanieBudynku> podsumowanieBudynkuCollection) {
        this.podsumowanieBudynkuCollection = podsumowanieBudynkuCollection;
    }

    @XmlTransient
    public Collection<LicznikiBudynku> getLicznikiBudynkuCollection() {
        return licznikiBudynkuCollection;
    }

    public void setLicznikiBudynkuCollection(Collection<LicznikiBudynku> licznikiBudynkuCollection) {
        this.licznikiBudynkuCollection = licznikiBudynkuCollection;
    }

    @XmlTransient
    public Collection<DodatkoweOplaty> getDodatkoweOplatyCollection() {
        return dodatkoweOplatyCollection;
    }

    public void setDodatkoweOplatyCollection(Collection<DodatkoweOplaty> dodatkoweOplatyCollection) {
        this.dodatkoweOplatyCollection = dodatkoweOplatyCollection;
    }

    public Wspolnota getIdWspolnota() {
        return idWspolnota;
    }

    public void setIdWspolnota(Wspolnota idWspolnota) {
        this.idWspolnota = idWspolnota;
    }

    @XmlTransient
    public Collection<Stawki> getStawkiCollection() {
        return stawkiCollection;
    }

    public void setStawkiCollection(Collection<Stawki> stawkiCollection) {
        this.stawkiCollection = stawkiCollection;
    }

    @XmlTransient
    public Collection<Mieszkanie> getMieszkanieCollection() {
        return mieszkanieCollection;
    }

    public void setMieszkanieCollection(Collection<Mieszkanie> mieszkanieCollection) {
        this.mieszkanieCollection = mieszkanieCollection;
    }

    @XmlTransient
    public Collection<Awaria> getAwariaCollection() {
        return awariaCollection;
    }

    public void setAwariaCollection(Collection<Awaria> awariaCollection) {
        this.awariaCollection = awariaCollection;
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
        if (!(object instanceof Budynek)) {
            return false;
        }
        Budynek other = (Budynek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Budynek[ id=" + id + " ]";
    }
    
}
