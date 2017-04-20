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
@Table(name = "szczegoly")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Szczegoly.findAll", query = "SELECT s FROM Szczegoly s")
    , @NamedQuery(name = "Szczegoly.findById", query = "SELECT s FROM Szczegoly s WHERE s.id = :id")
    , @NamedQuery(name = "Szczegoly.findByEksploatacjaPodstawowa", query = "SELECT s FROM Szczegoly s WHERE s.eksploatacjaPodstawowa = :eksploatacjaPodstawowa")
    , @NamedQuery(name = "Szczegoly.findByFunduszRemontowy", query = "SELECT s FROM Szczegoly s WHERE s.funduszRemontowy = :funduszRemontowy")
    , @NamedQuery(name = "Szczegoly.findByLegalizacjaWodomierza", query = "SELECT s FROM Szczegoly s WHERE s.legalizacjaWodomierza = :legalizacjaWodomierza")
    , @NamedQuery(name = "Szczegoly.findByKonserwacjaDomofonu", query = "SELECT s FROM Szczegoly s WHERE s.konserwacjaDomofonu = :konserwacjaDomofonu")
    , @NamedQuery(name = "Szczegoly.findByEksploatacjaDzwigow", query = "SELECT s FROM Szczegoly s WHERE s.eksploatacjaDzwigow = :eksploatacjaDzwigow")
    , @NamedQuery(name = "Szczegoly.findByCo", query = "SELECT s FROM Szczegoly s WHERE s.co = :co")
    , @NamedQuery(name = "Szczegoly.findByGaz", query = "SELECT s FROM Szczegoly s WHERE s.gaz = :gaz")
    , @NamedQuery(name = "Szczegoly.findByCw", query = "SELECT s FROM Szczegoly s WHERE s.cw = :cw")
    , @NamedQuery(name = "Szczegoly.findByZwis", query = "SELECT s FROM Szczegoly s WHERE s.zwis = :zwis")
    , @NamedQuery(name = "Szczegoly.findByPradWPomWspolnych", query = "SELECT s FROM Szczegoly s WHERE s.pradWPomWspolnych = :pradWPomWspolnych")
    , @NamedQuery(name = "Szczegoly.findBySuma", query = "SELECT s FROM Szczegoly s WHERE s.suma = :suma")
    , @NamedQuery(name = "Szczegoly.findBySmieci", query = "SELECT s FROM Szczegoly s WHERE s.smieci = :smieci")
    , @NamedQuery(name = "Szczegoly.findByUbezpieczenie", query = "SELECT s FROM Szczegoly s WHERE s.ubezpieczenie = :ubezpieczenie")})
public class Szczegoly implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "eksploatacja_podstawowa")
    private String eksploatacjaPodstawowa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "fundusz_remontowy")
    private String funduszRemontowy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "legalizacja_wodomierza")
    private String legalizacjaWodomierza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "konserwacja_domofonu")
    private String konserwacjaDomofonu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "eksploatacja_dzwigow")
    private String eksploatacjaDzwigow;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "co")
    private String co;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cw")
    private String cw;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "zwis")
    private String zwis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "gaz")
    private String gaz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "prad_w_pom_wspolnych")
    private String pradWPomWspolnych;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "smieci")
    private String smieci;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ubezpieczenie")
    private String ubezpieczenie;
    @JoinColumn(name = "id_oplaty", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Oplaty idOplaty;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "suma")
    private String suma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "epf")
    private float epf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "frf")
    private float frf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lwf")
    private float lwf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kdf")
    private float kdf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "edf")
    private float edf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cof")
    private float cof;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cwf")
    private float cwf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zwisf")
    private float zwisf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pwpwf")
    private float pwpwf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "smf")
    private float smf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ubf")
    private float ubf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gazf")
    private float gazf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dof")
    private float dof;
    @Basic(optional = true)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "dodatkowe_oplaty")
    private String dodatkowe_oplaty;
    
    public Szczegoly() {
    }

    public Szczegoly(Integer id) {
        this.id = id;
    }

    public Szczegoly(Integer id, String eksploatacjaPodstawowa, String funduszRemontowy, String legalizacjaWodomierza, String konserwacjaDomofonu, String eksploatacjaDzwigow, String co, String cw, String zwis, String pradWPomWspolnych, String smieci, String ubezpieczenie, String gaz, String suma, float epf, float frf, float lwf, float kdf, float edf, float cof, float cwf, float zwisf, float pwpwf, float smf, float ubf, float gazf, float dof) {
        this.id = id;
        this.eksploatacjaPodstawowa = eksploatacjaPodstawowa;
        this.funduszRemontowy = funduszRemontowy;
        this.legalizacjaWodomierza = legalizacjaWodomierza;
        this.konserwacjaDomofonu = konserwacjaDomofonu;
        this.eksploatacjaDzwigow = eksploatacjaDzwigow;
        this.co = co;
        this.cw = cw;
        this.zwis = zwis;
        this.pradWPomWspolnych = pradWPomWspolnych;
        this.smieci = smieci;
        this.ubezpieczenie = ubezpieczenie;
        this.gaz = gaz;
        this.suma = suma;
        this.epf = epf;
        this.frf = frf;
        this.lwf = lwf;
        this.kdf = kdf;
        this.edf = edf;
        this.cof = cof;
        this.cwf = cwf;
        this.zwisf = zwisf;
        this.pwpwf = pwpwf;
        this.smf = smf;
        this.ubf = ubf;
        this.gazf = gazf;
        this.dof = dof;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEksploatacjaPodstawowa() {
        return eksploatacjaPodstawowa;
    }

    public void setEksploatacjaPodstawowa(String eksploatacjaPodstawowa) {
        this.eksploatacjaPodstawowa = eksploatacjaPodstawowa;
    }

    public String getFunduszRemontowy() {
        return funduszRemontowy;
    }

    public void setFunduszRemontowy(String funduszRemontowy) {
        this.funduszRemontowy = funduszRemontowy;
    }

    public String getLegalizacjaWodomierza() {
        return legalizacjaWodomierza;
    }

    public void setLegalizacjaWodomierza(String legalizacjaWodomierza) {
        this.legalizacjaWodomierza = legalizacjaWodomierza;
    }

    public String getKonserwacjaDomofonu() {
        return konserwacjaDomofonu;
    }

    public void setKonserwacjaDomofonu(String konserwacjaDomofonu) {
        this.konserwacjaDomofonu = konserwacjaDomofonu;
    }

    public String getEksploatacjaDzwigow() {
        return eksploatacjaDzwigow;
    }

    public void setEksploatacjaDzwigow(String eksploatacjaDzwigow) {
        this.eksploatacjaDzwigow = eksploatacjaDzwigow;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCw() {
        return cw;
    }

    public void setCw(String cw) {
        this.cw = cw;
    }

    public String getZwis() {
        return zwis;
    }

    public void setZwis(String zwis) {
        this.zwis = zwis;
    }
    
    public String getGaz() {
        return gaz;
    }

    public void setGaz(String gaz) {
        this.gaz = gaz;
    }

    public String getPradWPomWspolnych() {
        return pradWPomWspolnych;
    }

    public void setPradWPomWspolnych(String pradWPomWspolnych) {
        this.pradWPomWspolnych = pradWPomWspolnych;
    }

    public String getSmieci() {
        return smieci;
    }

    public void setSmieci(String smieci) {
        this.smieci = smieci;
    }

    public String getUbezpieczenie() {
        return ubezpieczenie;
    }

    public void setUbezpieczenie(String ubezpieczenie) {
        this.ubezpieczenie = ubezpieczenie;
    }

    public Oplaty getIdOplaty() {
        return idOplaty;
    }

    public void setIdOplaty(Oplaty idOplaty) {
        this.idOplaty = idOplaty;
    }

    public String getSuma() {
        return suma;
    }

    public void setSuma(String suma) {
        this.suma = suma;
    }
    
    public float getEpf() {
        return epf;
    }

    public void setEpf(float epf) {
        this.epf = epf;
    }

    public float getFrf() {
        return frf;
    }

    public void setFrf(float frf) {
        this.frf = frf;
    }

    public float getLwf() {
        return lwf;
    }

    public void setLwf(float lwf) {
        this.lwf = lwf;
    }

    public float getKdf() {
        return kdf;
    }

    public void setKdf(float kdf) {
        this.kdf = kdf;
    }

    public float getEdf() {
        return edf;
    }

    public void setEdf(float edf) {
        this.edf = edf;
    }

    public float getCof() {
        return cof;
    }

    public void setCof(float cof) {
        this.cof = cof;
    }

    public float getCwf() {
        return cwf;
    }

    public float getDof() {
        return dof;
    }

    public void setDof(float dof) {
        this.dof = dof;
    }
    public void setCwf(float cwf) {
        this.cwf = cwf;
    }

    public float getZwisf() {
        return zwisf;
    }

    public void setZwisf(float zwisf) {
        this.zwisf = zwisf;
    }

    public float getPwpwf() {
        return pwpwf;
    }

    public void setPwpwf(float pwpwf) {
        this.pwpwf = pwpwf;
    }

    public float getSmf() {
        return smf;
    }

    public void setSmf(float smf) {
        this.smf = smf;
    }

    public float getUbf() {
        return ubf;
    }

    public void setUbf(float ubf) {
        this.ubf = ubf;
    }

    public float getGazf() {
        return gazf;
    }

    public void setGazf(float gazf) {
        this.gazf = gazf;
    }
    public String getDodatkowe_oplaty() {
        return dodatkowe_oplaty;
    }

    public void setDodatkowe_oplaty(String dodatkowe_oplaty) {
        this.dodatkowe_oplaty = dodatkowe_oplaty;
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
        if (!(object instanceof Szczegoly)) {
            return false;
        }
        Szczegoly other = (Szczegoly) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Szczegoly[ id=" + id + " ]";
    }
    
}
