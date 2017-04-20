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
@Table(name = "dodatkowe_oplaty")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DodatkoweOplaty.findAll", query = "SELECT d FROM DodatkoweOplaty d")
    , @NamedQuery(name = "DodatkoweOplaty.findById", query = "SELECT d FROM DodatkoweOplaty d WHERE d.id = :id")
    , @NamedQuery(name = "DodatkoweOplaty.findByRodzaj", query = "SELECT d FROM DodatkoweOplaty d WHERE d.rodzaj = :rodzaj")
    , @NamedQuery(name = "DodatkoweOplaty.findByKoszt", query = "SELECT d FROM DodatkoweOplaty d WHERE d.koszt = :koszt")})
public class DodatkoweOplaty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "rodzaj")
    private String rodzaj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "koszt")
    private float koszt;
    @JoinColumn(name = "id_oplaty", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Oplaty idOplaty;

    public DodatkoweOplaty() {
    }

    public DodatkoweOplaty(Integer id) {
        this.id = id;
    }

    public DodatkoweOplaty(Integer id, String rodzaj, float koszt) {
        this.id = id;
        this.rodzaj = rodzaj;
        this.koszt = koszt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public float getKoszt() {
        return koszt;
    }

    public void setKoszt(float koszt) {
        this.koszt = koszt;
    }

    public Oplaty getIdOplaty() {
        return idOplaty;
    }

    public void setIdOplaty(Oplaty idOplaty) {
        this.idOplaty = idOplaty;
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
        if (!(object instanceof DodatkoweOplaty)) {
            return false;
        }
        DodatkoweOplaty other = (DodatkoweOplaty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.DodatkoweOplaty[ id=" + id + " ]";
    }
    
}
