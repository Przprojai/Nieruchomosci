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
@Table(name = "podsumowanie_budynku")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PodsumowanieBudynku.findAll", query = "SELECT p FROM PodsumowanieBudynku p")
    , @NamedQuery(name = "PodsumowanieBudynku.findById", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.id = :id")
    , @NamedQuery(name = "PodsumowanieBudynku.findByMiesiac", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.miesiac = :miesiac")
    , @NamedQuery(name = "PodsumowanieBudynku.findByRok", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.rok = :rok")
    , @NamedQuery(name = "PodsumowanieBudynku.findByEksploatacjaPodstawowa", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.eksploatacjaPodstawowa = :eksploatacjaPodstawowa")
    , @NamedQuery(name = "PodsumowanieBudynku.findByFunduszRemontowy", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.funduszRemontowy = :funduszRemontowy")
    , @NamedQuery(name = "PodsumowanieBudynku.findByLegalizacjaWodomierza", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.legalizacjaWodomierza = :legalizacjaWodomierza")
    , @NamedQuery(name = "PodsumowanieBudynku.findByKonserwacjaDomofonu", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.konserwacjaDomofonu = :konserwacjaDomofonu")
    , @NamedQuery(name = "PodsumowanieBudynku.findByEksploatacjaDzwigow", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.eksploatacjaDzwigow = :eksploatacjaDzwigow")
    , @NamedQuery(name = "PodsumowanieBudynku.findByCo", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.co = :co")
    , @NamedQuery(name = "PodsumowanieBudynku.findByCw", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.cw = :cw")
    , @NamedQuery(name = "PodsumowanieBudynku.findByZwis", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.zwis = :zwis")
    , @NamedQuery(name = "PodsumowanieBudynku.findByGaz", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.gaz = :gaz")
    , @NamedQuery(name = "PodsumowanieBudynku.findByPradWPomWspolnych", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.pradWPomWspolnych = :pradWPomWspolnych")
    , @NamedQuery(name = "PodsumowanieBudynku.findBySmieci", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.smieci = :smieci")
    , @NamedQuery(name = "PodsumowanieBudynku.findByUbezpieczenie", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.ubezpieczenie = :ubezpieczenie")
    , @NamedQuery(name = "PodsumowanieBudynku.findBySuma", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.suma = :suma")
    , @NamedQuery(name = "PodsumowanieBudynku.findByZaplacono", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.zaplacono = :zaplacono")
    , @NamedQuery(name = "PodsumowanieBudynku.findByWynik", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.wynik = :wynik")
    , @NamedQuery(name = "PodsumowanieBudynku.findByZajetychMieszkan", query = "SELECT p FROM PodsumowanieBudynku p WHERE p.zajetychMieszkan = :zajetychMieszkan")})
public class PodsumowanieBudynku implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "dodatkowe_oplaty")
    private float dodatkoweoplaty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "suma")
    private float suma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zaplacono")
    private float zaplacono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wynik")
    private float wynik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zajetych_mieszkan")
    private int zajetychMieszkan;
    @JoinColumn(name = "id_budynku", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Budynek idBudynku;

    public PodsumowanieBudynku() {
    }

    public PodsumowanieBudynku(Integer id) {
        this.id = id;
    }

    public PodsumowanieBudynku(Integer id, int miesiac, int rok, float eksploatacjaPodstawowa, float funduszRemontowy, float legalizacjaWodomierza, float konserwacjaDomofonu, float eksploatacjaDzwigow, float co, float cw, float zwis, float gaz, float pradWPomWspolnych, float smieci,float dodatkoweoplaty, float ubezpieczenie, float suma, float zaplacono, float wynik, int zajetychMieszkan) {
        this.id = id;
        this.miesiac = miesiac;
        this.rok = rok;
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
        this.suma = suma;
        this.zaplacono = zaplacono;
        this.wynik = wynik;
        this.zajetychMieszkan = zajetychMieszkan;
        this.dodatkoweoplaty = dodatkoweoplaty;
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

    public float getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
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

    public float getDodatkowe_oplaty() {
        return dodatkoweoplaty;
    }

    public void setDodatkowe_oplaty(float dodatkoweoplaty) {
        this.dodatkoweoplaty = dodatkoweoplaty;
    }
    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }

    public float getZaplacono() {
        return zaplacono;
    }

    public void setZaplacono(float zaplacono) {
        this.zaplacono = zaplacono;
    }

    public float getWynik() {
        return wynik;
    }

    public void setWynik(float wynik) {
        this.wynik = wynik;
    }

    public int getZajetychMieszkan() {
        return zajetychMieszkan;
    }

    public void setZajetychMieszkan(int zajetychMieszkan) {
        this.zajetychMieszkan = zajetychMieszkan;
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
        if (!(object instanceof PodsumowanieBudynku)) {
            return false;
        }
        PodsumowanieBudynku other = (PodsumowanieBudynku) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.PodsumowanieBudynku[ id=" + id + " ]";
    }
    
}
