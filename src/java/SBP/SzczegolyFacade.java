/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Oplaty;
import Entity.Szczegoly;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Waldek
 */
@Stateless
public class SzczegolyFacade extends AbstractFacade<Szczegoly> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SzczegolyFacade() {
        super(Szczegoly.class);
    }
    public Szczegoly zwroc(Oplaty oplaty){
        Szczegoly wynik = null;
        try {
       // TypedQuery<Stawki> q2 = em.createNamedQuery("Stawki.findByBudynek",Stawki.class).setParameter("budynek", budynek);
       TypedQuery<Szczegoly> q2
                = em.createQuery("SELECT c FROM Szczegoly c WHERE c.idOplaty = :oplaty", Szczegoly.class).setParameter("oplaty", oplaty);
        if (q2.getSingleResult() != null) {
                wynik =  q2.getSingleResult();
            }
        } catch (NoResultException e) {

            wynik =null;
    }
        return wynik;
}
}
