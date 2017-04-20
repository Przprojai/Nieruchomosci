/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Mieszkanie;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

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
    
}
