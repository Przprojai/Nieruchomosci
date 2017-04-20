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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Waldek
 */
@Entity
@Table(name = "stawki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stawki.findAll", query = "SELECT s FROM Stawki s")
    , @NamedQuery(name = "Stawki.findById", query = "SELECT s FROM Stawki s WHERE s.id = :id")
    , @NamedQuery(name = "Stawki.findByEksploatacjaPodstawowa", query = "SELECT s FROM Stawki s WHERE s.eksploatacjaPodstawowa = :eksploatacjaPodstawowa")
    , @NamedQuery(name = "Stawki.findByFunduszRemontowy", query = "SELECT s FROM Stawki s WHERE s.funduszRemontowy = :funduszRemontowy")
    , @NamedQuery(name = "Stawki.findByLegalizacjaWodomierza", query = "SELECT s FROM Stawki s WHERE s.legalizacjaWodomierza = :legalizacjaWodomierza")
    , @NamedQuery(name = "Stawki.findByKonserwacjaDomofonu", query = "SELECT s FROM Stawki s WHERE s.konserwacjaDomofonu = :konserwacjaDomofonu")
    , @NamedQuery(name = "Stawki.findByEksploatacjaDzwigow", query = "SELECT s FROM Stawki s WHERE s.eksploatacjaDzwigow = :eksploatacjaDzwigow")
    , @NamedQuery(name = "Stawki.findByCo", query = "SELECT s FROM Stawki s WHERE s.co = :co")
    , @NamedQuery(name = "Stawki.findByCw", query = "SELECT s FROM Stawki s WHERE s.cw = :cw")
    , @NamedQuery(name = "Stawki.findByZwis", query = "SELECT s FROM Stawki s WHERE s.zwis = :zwis")
    , @NamedQuery(name = "Stawki.findByGaz", query = "SELECT s FROM Stawki s WHERE s.gaz = :gaz")
    , @NamedQuery(name = "Stawki.findByPradWPomWspolnych", query = "SELECT s FROM Stawki s WHERE s.pradWPomWspolnych = :pradWPomWspolnych")
    , @NamedQuery(name = "Stawki.findBySmieci", query = "SELECT s FROM Stawki s WHERE s.smieci = :smieci")
    , @NamedQuery(name = "Stawki.findByUbezpieczenie", query = "SELECT s FROM Stawki s WHERE s.ubezpieczenie = :ubezpieczenie")})
public class Stawki implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eksploatacja_podstawowa")
    private float eksploatacjaPodstawowa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fundusz_remontowy")
    private float funduszRemontowy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "legalizacja_wodomierza")
    private float legalizacjaWodomierza;
    @Basic(optional = false)
    @NotNull
    @Column(name = "konserwacja_domofonu")
    private float konserwacjaDomofonu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eksploatacja_dzwigow")
    private float eksploatacjaDzwigow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "co")
    private float co;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw")
    private float cw;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zwis")
    private float zwis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gaz")
    private float gaz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prad_w_pom_wspolnych")
    private float pradWPomWspolnych;
    @Basic(optional = false)
    @NotNull
    @Column(name = "smieci")
    private float smieci;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ubezpieczenie")
    private float ubezpieczenie;
    @JoinColumn(name = "id_budynku", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Budynek idBudynku;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idStawki")
    private Collection<Oplaty> oplatyCollection;

    public Stawki() {
    }

    public Stawki(Integer id) {
        this.id = id;
    }

    public Stawki(Integer id, float eksploatacjaPodstawowa, float funduszRemontowy, float legalizacjaWodomierza, float konserwacjaDomofonu, float eksploatacjaDzwigow, float co, float cw, float zwis, float gaz, float pradWPomWspolnych, float smieci, float ubezpieczenie) {
        this.id = id;
        this.eksploatacjaPodstawowa = eksploatacjaPodstawowa;
        this.funduszRemontowy = funduszRemontowy;
        this.legalizacjaWodomierza = legalizacjaWodomierza;
        this.konserwacjaDomofonu = konserwacjaDomofonu;
        this.eksploatacjaDzwigow = eksploatacjaDzwigow;
        this.co = co;
        this.cw = cw;
        this.zwis = zwis;
        this.gaz = gaz;
        this.pradWPomWspolnych = pradWPomWspolnych;
        this.smieci = smieci;
        this.ubezpieczenie = ubezpieczenie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getEksploatacjaPodstawowa() {
        return eksploatacjaPodstawowa;
    }

    public void setEksploatacjaPodstawowa(float eksploatacjaPodstawowa) {
        this.eksploatacjaPodstawowa = eksploatacjaPodstawowa;
    }

    public float getFunduszRemontowy() {
        return funduszRemontowy;
    }

    public void setFunduszRemontowy(float funduszRemontowy) {
        this.funduszRemontowy = funduszRemontowy;
    }

    public float getLegalizacjaWodomierza() {
        return legalizacjaWodomierza;
    }

    public void setLegalizacjaWodomierza(float legalizacjaWodomierza) {
        this.legalizacjaWodomierza = legalizacjaWodomierza;
    }

    public float getKonserwacjaDomofonu() {
        return konserwacjaDomofonu;
    }

    public void setKonserwacjaDomofonu(float konserwacjaDomofonu) {
        this.konserwacjaDomofonu = konserwacjaDomofonu;
    }

    public float getEksploatacjaDzwigow() {
        return eksploatacjaDzwigow;
    }

    public void setEksploatacjaDzwigow(float eksploatacjaDzwigow) {
        this.eksploatacjaDzwigow = eksploatacjaDzwigow;
    }

    public float getCo() {
        return co;
    }

    public void setCo(float co) {
        this.co = co;
    }

    public float getCw() {
        return cw;
    }

    public void setCw(float cw) {
        this.cw = cw;
    }

    public float getZwis() {
        return zwis;
    }

    public void setZwis(float zwis) {
        this.zwis = zwis;
    }

    public float getGaz() {
        return gaz;
    }

    public void setGaz(float gaz) {
        this.gaz = gaz;
    }

    public float getPradWPomWspolnych() {
        return pradWPomWspolnych;
    }

    public void setPradWPomWspolnych(float pradWPomWspolnych) {
        this.pradWPomWspolnych = pradWPomWspolnych;
    }

    public float getSmieci() {
        return smieci;
    }

    public void setSmieci(float smieci) {
        this.smieci = smieci;
    }

    public float getUbezpieczenie() {
        return ubezpieczenie;
    }

    public void setUbezpieczenie(float ubezpieczenie) {
        this.ubezpieczenie = ubezpieczenie;
    }

    public Budynek getIdBudynku() {
        return idBudynku;
    }

    public void setIdBudynku(Budynek idBudynku) {
        this.idBudynku = idBudynku;
    }

    @XmlTransient
    public Collection<Oplaty> getOplatyCollection() {
        return oplatyCollection;
    }

    public void setOplatyCollection(Collection<Oplaty> oplatyCollection) {
        this.oplatyCollection = oplatyCollection;
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
        if (!(object instanceof Stawki)) {
            return false;
        }
        Stawki other = (Stawki) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Stawki[ id=" + id + " ]";
    }
    
}
