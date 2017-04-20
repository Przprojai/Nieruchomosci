/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Waldek
 */
@Entity
@Table(name = "awaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Awaria.findAll", query = "SELECT a FROM Awaria a")
    , @NamedQuery(name = "Awaria.findById", query = "SELECT a FROM Awaria a WHERE a.id = :id")
    , @NamedQuery(name = "Awaria.findByData", query = "SELECT a FROM Awaria a WHERE a.data = :data")
    , @NamedQuery(name = "Awaria.findByGodzina", query = "SELECT a FROM Awaria a WHERE a.godzina = :godzina")
    , @NamedQuery(name = "Awaria.findByStatus", query = "SELECT a FROM Awaria a WHERE a.status = :status")
    , @NamedQuery(name = "Awaria.findByKoszt", query = "SELECT a FROM Awaria a WHERE a.koszt = :koszt")})
public class Awaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @NotNull
    @Column(name = "godzina")
    @Temporal(TemporalType.TIME)
    private Date godzina;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "koszt")
    private float koszt;
    @JoinColumn(name = "id_budynku", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Budynek idBudynku;
    @JoinColumn(name = "id_lokatora", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lokator idLokatora;

    public Awaria() {
    }

    public Awaria(Integer id) {
        this.id = id;
    }

    public Awaria(Integer id, Date data, Date godzina, String status, float koszt) {
        this.id = id;
        this.data = data;
        this.godzina = godzina;
        this.status = status;
        this.koszt = koszt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getGodzina() {
        return godzina;
    }

    public void setGodzina(Date godzina) {
        this.godzina = godzina;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getKoszt() {
        return koszt;
    }

    public void setKoszt(float koszt) {
        this.koszt = koszt;
    }

    public Budynek getIdBudynku() {
        return idBudynku;
    }

    public void setIdBudynku(Budynek idBudynku) {
        this.idBudynku = idBudynku;
    }

    public Lokator getIdLokatora() {
        return idLokatora;
    }

    public void setIdLokatora(Lokator idLokatora) {
        this.idLokatora = idLokatora;
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
        if (!(object instanceof Awaria)) {
            return false;
        }
        Awaria other = (Awaria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Awaria[ id=" + id + " ]";
    }
    
}
