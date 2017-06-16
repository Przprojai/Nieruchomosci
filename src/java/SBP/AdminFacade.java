/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Admin;
import Entity.Ksiegowosc;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import szyfrowanie.CryptWithSHA256;

/**
 *
 * @author Waldek
 */
@Stateless
public class AdminFacade extends AbstractFacade<Admin> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminFacade() {
        super(Admin.class);
    }
    public Admin login(String log, String pass) {
        pass = CryptWithSHA256.sha256(pass);
        Admin wynik= new Admin();
        try {
             TypedQuery<Admin> q2
                = em.createQuery("SELECT c FROM Admin c WHERE c.login = :login AND c.haslo=:haslo  ", Admin.class).setParameter("login", log).setParameter("haslo", pass);
            if (q2.getSingleResult() != null) {
                wynik = q2.getSingleResult();
            }
        } catch (NoResultException e) {

            wynik = null;
        }
        return wynik;
    }  
}
