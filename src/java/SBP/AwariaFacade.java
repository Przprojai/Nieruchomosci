/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Awaria;
import Entity.Budynek;
import Entity.Lokator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Waldek
 */
@Stateless
public class AwariaFacade extends AbstractFacade<Awaria> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AwariaFacade() {
        super(Awaria.class);
    }
    public List<Awaria> findbybudynekid(Budynek budynek){
      //  Budynek budynekId=lokator.getMieszkanieId().getBudynekId();
        TypedQuery<Awaria> query=
                em.createQuery("SELECT a FROM Awaria a WHERE  a.idBudynku=:budynek",Awaria.class).setParameter("budynek", budynek);
        List<Awaria> wynik = query.getResultList();
        return wynik;
    }
    public List<Awaria> findbylokatorid(Lokator lokator){
        Budynek budynek=lokator.getIdMieszkania().getIdBudynku();
        TypedQuery<Awaria> query=
                em.createQuery("SELECT a FROM Awaria a WHERE a.idBudynku=:budynek",Awaria.class).setParameter("budynek", budynek);
        List<Awaria> wynik = query.getResultList();
        return wynik;
    }
    public Integer id() {
        Query q = em.createQuery("SELECT MAX(x.id) FROM Awaria x");
        Integer result = (Integer) q.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
          result++;
        }
        return result;
    }
}
