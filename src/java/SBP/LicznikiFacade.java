/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Liczniki;
import Entity.Mieszkanie;
import java.util.ArrayList;
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
public class LicznikiFacade extends AbstractFacade<Liczniki> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LicznikiFacade() {
        super(Liczniki.class);
    }
            public Integer id(){
        Query q = em.createQuery ("SELECT MAX(X.id) FROM Liczniki X");
Integer result = (Integer) q.getSingleResult ();
if(result==null)result=1;
else
      result+=1;
      
        return result;
    } 
 public List<Mieszkanie> lista_mieszkan(){
        List<Mieszkanie> lista = new ArrayList<Mieszkanie>();
        try {
       // TypedQuery<Stawki> q2 = em.createNamedQuery("Stawki.findByBudynek",Stawki.class).setParameter("budynek", budynek);
       TypedQuery<Mieszkanie> q2
                = em.createQuery("SELECT c FROM Mieszkanie c ", Mieszkanie.class);
        if (q2.getResultList()!= null) {
                lista =  q2.getResultList();
            }
        } catch (NoResultException e) {

            lista =null;
    }
        return lista;
}
public Liczniki update (Mieszkanie mieszkanie, Integer miesiac, Integer rok){
    Liczniki wynik = null;
    try {
       TypedQuery<Liczniki> q2
                = em.createQuery("SELECT c FROM Liczniki c WHERE C.idMieszkania =:mieszkanie AND C.miesiac=:miesiac AND C.rok=:rok", Liczniki.class).setParameter("mieszkanie", mieszkanie).setParameter("miesiac", miesiac).setParameter("rok", rok);
        if (q2.getSingleResult()!= null) {
                wynik =  q2.getSingleResult();
            }
        } catch (NoResultException e) {

            wynik =null;
    }
    
    return wynik;
}
}
