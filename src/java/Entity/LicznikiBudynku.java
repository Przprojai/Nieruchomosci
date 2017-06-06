/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "liczniki_budynku")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LicznikiBudynku.findAll", query = "SELECT l FROM LicznikiBudynku l")
    , @NamedQuery(name = "LicznikiBudynku.findById", query = "SELECT l FROM LicznikiBudynku l WHERE l.id = :id")
    , @NamedQuery(name = "LicznikiBudynku.findByMiesiac", query = "SELECT l FROM LicznikiBudynku l WHERE l.miesiac = :miesiac")
    , @NamedQuery(name = "LicznikiBudynku.findByRok", query = "SELECT l FROM LicznikiBudynku l WHERE l.rok = :rok")
    , @NamedQuery(name = "LicznikiBudynku.findByPrad", query = "SELECT l FROM LicznikiBudynku l WHERE l.prad = :prad")
    , @NamedQuery(name = "LicznikiBudynku.findByStawkaPradu", query = "SELECT l FROM LicznikiBudynku l WHERE l.stawkaPradu = :stawkaPradu")
    , @NamedQuery(name = "LicznikiBudynku.findByGaz", query = "SELECT l FROM LicznikiBudynku l WHERE l.gaz = :gaz")
    , @NamedQuery(name = "LicznikiBudynku.findByStawkaGazu", query = "SELECT l FROM LicznikiBudynku l WHERE l.stawkaGazu = :stawkaGazu")
    , @NamedQuery(name = "LicznikiBudynku.findByWoda", query = "SELECT l FROM LicznikiBudynku l WHERE l.woda = :woda")
    , @NamedQuery(name = "LicznikiBudynku.findByStawkaWody", query = "SELECT l FROM LicznikiBudynku l WHERE l.stawkaWody = :stawkaWody")
    , @NamedQuery(name = "LicznikiBudynku.findByCo", query = "SELECT l FROM LicznikiBudynku l WHERE l.co = :co")
    , @NamedQuery(name = "LicznikiBudynku.findByStawkaCo", query = "SELECT l FROM LicznikiBudynku l WHERE l.stawkaCo = :stawkaCo")})
public class LicznikiBudynku implements Serializable {

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
    @Column(name = "prad")
    private int prad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stawka_pradu")
    private BigDecimal stawkaPradu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gaz")
    private int gaz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stawka_gazu")
    private BigDecimal stawkaGazu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "woda")
    private int woda;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stawka_wody")
    private BigDecimal stawkaWody;
    @Basic(optional = false)
    @NotNull
    @Column(name = "co")
    private int co;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stawka_co")
    private BigDecimal stawkaCo;
    @JoinColumn(name = "id_budynku", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Budynek idBudynku;

    public LicznikiBudynku() {
    }

    public LicznikiBudynku(Integer id) {
        this.id = id;
    }

    public LicznikiBudynku(Integer id, int miesiac, int rok, int prad, BigDecimal stawkaPradu, int gaz, BigDecimal stawkaGazu, int woda, BigDecimal stawkaWody, int co, BigDecimal stawkaCo) {
        this.id = id;
        this.miesiac = miesiac;
        this.rok = rok;
        this.prad = prad;
        this.stawkaPradu = stawkaPradu;
        this.gaz = gaz;
        this.stawkaGazu = stawkaGazu;
        this.woda = woda;
        this.stawkaWody = stawkaWody;
        this.co = co;
        this.stawkaCo = stawkaCo;
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

    public int getPrad() {
        return prad;
    }

    public void setPrad(int prad) {
        this.prad = prad;
    }

    public BigDecimal getStawkaPradu() {
        return stawkaPradu;
    }

    public void setStawkaPradu(BigDecimal stawkaPradu) {
        this.stawkaPradu = stawkaPradu;
    }

    public int getGaz() {
        return gaz;
    }

    public void setGaz(int gaz) {
        this.gaz = gaz;
    }

    public BigDecimal getStawkaGazu() {
        return stawkaGazu;
    }

    public void setStawkaGazu(BigDecimal stawkaGazu) {
        this.stawkaGazu = stawkaGazu;
    }

    public int getWoda() {
        return woda;
    }

    public void setWoda(int woda) {
        this.woda = woda;
    }

    public BigDecimal getStawkaWody() {
        return stawkaWody;
    }

    public void setStawkaWody(BigDecimal stawkaWody) {
        this.stawkaWody = stawkaWody;
    }

    public int getCo() {
        return co;
    }

    public void setCo(int co) {
        this.co = co;
    }

    public BigDecimal getStawkaCo() {
        return stawkaCo;
    }

    public void setStawkaCo(BigDecimal stawkaCo) {
        this.stawkaCo = stawkaCo;
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
        if (!(object instanceof LicznikiBudynku)) {
            return false;
        }
        LicznikiBudynku other = (LicznikiBudynku) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.LicznikiBudynku[ id=" + id + " ]";
    }
    
}
