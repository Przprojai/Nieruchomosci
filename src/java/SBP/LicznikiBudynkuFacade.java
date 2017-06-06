/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Budynek;
import Entity.LicznikiBudynku;
import Entity.Oplaty;
import Entity.Stawki;
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
public class LicznikiBudynkuFacade extends AbstractFacade<LicznikiBudynku> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LicznikiBudynkuFacade() {
        super(LicznikiBudynku.class);
    }
    public List<Budynek> budynki() {
        List<Budynek> lista = new ArrayList<Budynek>();

        try {
            TypedQuery<Budynek> q = em.createQuery("SELECT X FROM Budynek X ", Budynek.class);
            if (q.getResultList() != null) {
                lista = q.getResultList();
            }
        } catch (NoResultException e) {
            lista = null;
        }

        return lista;
    }
    public Integer id(){
        Query q = em.createQuery ("SELECT MAX(X.id) FROM LicznikiBudynku X");
Integer result = (Integer) q.getSingleResult ();
if(result==null)result=1;
else
      result+=1;
      
        return result;
    } 
    public LicznikiBudynku update (Budynek budynek, Integer miesiac, Integer rok){
    LicznikiBudynku wynik = null;
    try {
       TypedQuery<LicznikiBudynku> q2
                = em.createQuery("SELECT c FROM LicznikiBudynku c WHERE C.idBudynku =:budynek AND C.miesiac=:miesiac AND C.rok=:rok", LicznikiBudynku.class).setParameter("budynek", budynek).setParameter("miesiac", miesiac).setParameter("rok", rok);
        if (q2.getSingleResult()!= null) {
                wynik =  q2.getSingleResult();
            }
        } catch (NoResultException e) {

            wynik =null;
    }
    
    return wynik;
}
        public Stawki stawki(Budynek budynek){
    Stawki wynik = new Stawki();
    try {
            TypedQuery<Stawki> q = em.createQuery("SELECT X FROM Stawki X WHERE X.idBudynku=:budynek", Stawki.class).setParameter("budynek", budynek);
            if (!q.getResultList().isEmpty()) {
                wynik=q.getSingleResult();
            }
        } catch (NoResultException e) {
            wynik = null;
        }
        return wynik;
    }
}
