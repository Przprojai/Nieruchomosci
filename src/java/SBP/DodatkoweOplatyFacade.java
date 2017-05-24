/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.DodatkoweOplaty;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Waldek
 */
@Stateless
public class DodatkoweOplatyFacade extends AbstractFacade<DodatkoweOplaty> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DodatkoweOplatyFacade() {
        super(DodatkoweOplaty.class);
    }
                public Integer id(){
        Query q = em.createQuery ("SELECT MAX(X.id) FROM DodatkoweOplaty X");
Integer result = (Integer) q.getSingleResult ();
if(result==null)result=1;
else
      result+=1;
      
        return result;
    } 
}
