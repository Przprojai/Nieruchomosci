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
    private BigDecimal eksploatacjaPodstawowa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fundusz_remontowy")
    private BigDecimal funduszRemontowy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "legalizacja_wodomierza")
    private BigDecimal legalizacjaWodomierza;
    @Basic(optional = false)
    @NotNull
    @Column(name = "konserwacja_domofonu")
    private BigDecimal konserwacjaDomofonu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eksploatacja_dzwigow")
    private BigDecimal eksploatacjaDzwigow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "co")
    private BigDecimal co;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cw")
    private BigDecimal cw;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zwis")
    private BigDecimal zwis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gaz")
    private BigDecimal gaz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prad_w_pom_wspolnych")
    private BigDecimal pradWPomWspolnych;
    @Basic(optional = false)
    @NotNull
    @Column(name = "smieci")
    private BigDecimal smieci;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ubezpieczenie")
    private BigDecimal ubezpieczenie;
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

    public Stawki(Integer id, BigDecimal eksploatacjaPodstawowa, BigDecimal funduszRemontowy, BigDecimal legalizacjaWodomierza, BigDecimal konserwacjaDomofonu, BigDecimal eksploatacjaDzwigow, BigDecimal co, BigDecimal cw, BigDecimal zwis, BigDecimal gaz, BigDecimal pradWPomWspolnych, BigDecimal smieci, BigDecimal ubezpieczenie) {
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

    public BigDecimal getEksploatacjaPodstawowa() {
        return eksploatacjaPodstawowa;
    }

    public void setEksploatacjaPodstawowa(BigDecimal eksploatacjaPodstawowa) {
        this.eksploatacjaPodstawowa = eksploatacjaPodstawowa;
    }

    public BigDecimal getFunduszRemontowy() {
        return funduszRemontowy;
    }

    public void setFunduszRemontowy(BigDecimal funduszRemontowy) {
        this.funduszRemontowy = funduszRemontowy;
    }

    public BigDecimal getLegalizacjaWodomierza() {
        return legalizacjaWodomierza;
    }

    public void setLegalizacjaWodomierza(BigDecimal legalizacjaWodomierza) {
        this.legalizacjaWodomierza = legalizacjaWodomierza;
    }

    public BigDecimal getKonserwacjaDomofonu() {
        return konserwacjaDomofonu;
    }

    public void setKonserwacjaDomofonu(BigDecimal konserwacjaDomofonu) {
        this.konserwacjaDomofonu = konserwacjaDomofonu;
    }

    public BigDecimal getEksploatacjaDzwigow() {
        return eksploatacjaDzwigow;
    }

    public void setEksploatacjaDzwigow(BigDecimal eksploatacjaDzwigow) {
        this.eksploatacjaDzwigow = eksploatacjaDzwigow;
    }

    public BigDecimal getCo() {
        return co;
    }

    public void setCo(BigDecimal co) {
        this.co = co;
    }

    public BigDecimal getCw() {
        return cw;
    }

    public void setCw(BigDecimal cw) {
        this.cw = cw;
    }

    public BigDecimal getZwis() {
        return zwis;
    }

    public void setZwis(BigDecimal zwis) {
        this.zwis = zwis;
    }

    public BigDecimal getGaz() {
        return gaz;
    }

    public void setGaz(BigDecimal gaz) {
        this.gaz = gaz;
    }

    public BigDecimal getPradWPomWspolnych() {
        return pradWPomWspolnych;
    }

    public void setPradWPomWspolnych(BigDecimal pradWPomWspolnych) {
        this.pradWPomWspolnych = pradWPomWspolnych;
    }

    public BigDecimal getSmieci() {
        return smieci;
    }

    public void setSmieci(BigDecimal smieci) {
        this.smieci = smieci;
    }

    public BigDecimal getUbezpieczenie() {
        return ubezpieczenie;
    }

    public void setUbezpieczenie(BigDecimal ubezpieczenie) {
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
