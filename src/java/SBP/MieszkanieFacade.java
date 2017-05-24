/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Budynek;
import Entity.Mieszkanie;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Waldek
 */
@Stateless
public class MieszkanieFacade extends AbstractFacade<Mieszkanie> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    public List<Mieszkanie> lista_mieszkan(){
      return  em.createNamedQuery("Mieszkanie.findByZajetosc").setParameter("zajetosc", true).getResultList();
    }
    public MieszkanieFacade() {
        super(Mieszkanie.class);
    }
        public List<Mieszkanie> aktywne(Budynek budynek) {
        Integer wynik;
        List<Mieszkanie> lista = new ArrayList<Mieszkanie>();
        try {
            TypedQuery<Mieszkanie> q = em.createQuery("SELECT X FROM Mieszkanie X WHERE X.idBudynku=:budynek AND X.idBudynku=:budynek AND X.zajetosc=TRUE", Mieszkanie.class).setParameter("budynek", budynek);
            if (q.getResultList() != null) {
                lista = q.getResultList();
            }
        } catch (NoResultException e) {
            lista = null;
        }
        return lista;
    }
        public List<Budynek> budynki(){
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
}
