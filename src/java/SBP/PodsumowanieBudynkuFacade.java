/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Budynek;
import Entity.Oplaty;
import Entity.PodsumowanieBudynku;
import Entity.Szczegoly;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Waldek
 */
@Stateless
public class PodsumowanieBudynkuFacade extends AbstractFacade<PodsumowanieBudynku> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PodsumowanieBudynkuFacade() {
        super(PodsumowanieBudynku.class);
    }
    public Integer Budynki(){
        Integer wynik = 0;
        try {
       Query q2
                = (Query) em.createQuery("SELECT MAX(x.id) FROM Budynek x ");
        if (!q2.getResultList().isEmpty()) {
                wynik =(Integer) q2.getSingleResult();
            }
        } catch (NoResultException e) {

            wynik =0;
    }
        return wynik;
}
    public Budynek zwrocid(Integer id){
        Budynek budynek = new Budynek();
         try {
       TypedQuery<Budynek> q2
                = em.createQuery("SELECT C FROM Budynek C WHERE C.id=:id", Budynek.class).setParameter("id", id);
        if (q2.getSingleResult() != null) {
                budynek=   q2.getSingleResult();
            }
        } catch (NoResultException e) {

            budynek= null;
    }
         return budynek;
    }
    public List<Szczegoly> oplaty(Integer id){
        List<Szczegoly> wynik= new ArrayList<Szczegoly>();
        Date data = new Date();
        Integer miesiac = data.getMonth()+2;
        Integer rok = data.getYear()+1900;
        try {
       TypedQuery<Szczegoly> q2
                = em.createQuery("SELECT C FROM Szczegoly C WHERE C.idOplaty.idMieszkania.idBudynku.id=:id AND c.idOplaty.miesiac=:miesiac AND c.idOplaty.rok=:rok", Szczegoly.class).setParameter("id", id).setParameter("miesiac", miesiac).setParameter("rok", rok);
        if (q2.getResultList()!= null) {
                wynik =  q2.getResultList();
            }
        } catch (NoResultException e) {

            wynik =null;
    }
        return wynik;
        
    }
    public Integer id() {
        Query q = em.createQuery("SELECT MAX(x.id) FROM PodsumowanieBudynku x");
        Integer result = (Integer) q.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
          result++;
        }
        return result;
    }
    public PodsumowanieBudynku znajdz(Integer rok, Integer miesiac, Budynek budynek){
    PodsumowanieBudynku nowy = null;
        try {
       TypedQuery<PodsumowanieBudynku> q2
                = em.createQuery("SELECT C FROM PodsumowanieBudynku C WHERE  c.miesiac=:miesiac AND c.rok=:rok AND c.idBudynku=:budynek", PodsumowanieBudynku.class).setParameter("miesiac", miesiac).setParameter("rok", rok).setParameter("budynek", budynek);
        if (!q2.getResultList().isEmpty()) {
                nowy =  q2.getSingleResult();
            }
        } catch (NoResultException e) {

            nowy =null;
    }
        return nowy;
}
}
