/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Budynek;
import Entity.LicznikiBudynku;
import Entity.Mieszkanie;
import Entity.Stawki;
import Entity.Szczegoly;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public List<Mieszkanie> lista_mieszkan() {
        return em.createNamedQuery("Mieszkanie.findByZajetosc").setParameter("zajetosc", true).getResultList();
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
    public BigDecimal powierzchnia(Budynek budynek) {
        BigDecimal wynik= new BigDecimal(0);
        List<Mieszkanie> lista = new ArrayList<Mieszkanie>();
        try {
            TypedQuery<Mieszkanie> q = em.createQuery("SELECT X FROM Mieszkanie X WHERE X.idBudynku=:budynek AND X.idBudynku=:budynek AND X.zajetosc=TRUE", Mieszkanie.class).setParameter("budynek", budynek);
            if (q.getResultList() != null) {
                lista = q.getResultList();
                for(int i=0;i<lista.size();i++){
                    wynik=wynik.add(lista.get(i).getPowierzchnia());
                }
            }
        } catch (NoResultException e) {
            return wynik;
        }
        return wynik;
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

    public BigDecimal prad(Mieszkanie mieszkanie, Integer rok) {
        Szczegoly szczegoly = new Szczegoly();
        BigDecimal wynik = new BigDecimal(0);
        try {
            TypedQuery<Szczegoly> q = em.createQuery("SELECT X FROM Szczegoly X WHERE X.idOplaty.idMieszkania=:mieszkanie AND X.idOplaty.rok=:rok", Szczegoly.class).setParameter("mieszkanie", mieszkanie).setParameter("rok", rok);
            if (q.getResultList() != null) {
                for (int i = 0; i < q.getResultList().size(); i++) {
                    szczegoly = q.getResultList().get(i);
                    wynik = wynik.add(szczegoly.getPwpwf());
                }
            }
        } catch (NoResultException e) {
            wynik = new BigDecimal(0);
        }
        return wynik;
    }

    public BigDecimal gaz(Mieszkanie mieszkanie, Integer rok) {
        Szczegoly szczegoly = new Szczegoly();
        BigDecimal wynik = new BigDecimal(0);
        try {
            TypedQuery<Szczegoly> q = em.createQuery("SELECT X FROM Szczegoly X WHERE X.idOplaty.idMieszkania=:mieszkanie AND X.idOplaty.rok=:rok", Szczegoly.class).setParameter("mieszkanie", mieszkanie).setParameter("rok", rok);
            if (q.getResultList() != null) {
                for (int i = 0; i < q.getResultList().size(); i++) {
                    szczegoly = q.getResultList().get(i);
                    wynik = wynik.add(szczegoly.getGazf());
                }
            }
        } catch (NoResultException e) {
            wynik = new BigDecimal(0);
        }
        return wynik;
    }
        public BigDecimal co(Mieszkanie mieszkanie, Integer rok) {
        Szczegoly szczegoly = new Szczegoly();
        BigDecimal wynik = new BigDecimal(0);
        try {
            TypedQuery<Szczegoly> q = em.createQuery("SELECT X FROM Szczegoly X WHERE X.idOplaty.idMieszkania=:mieszkanie AND X.idOplaty.rok=:rok", Szczegoly.class).setParameter("mieszkanie", mieszkanie).setParameter("rok", rok);
            if (q.getResultList() != null) {
                for (int i = 0; i < q.getResultList().size(); i++) {
                    szczegoly = q.getResultList().get(i);
                    wynik = wynik.add(szczegoly.getCof());
                }
            }
        } catch (NoResultException e) {
            wynik = new BigDecimal(0);
        }
        return wynik;
    }
    public List<LicznikiBudynku> liczniki_budynku(Budynek budynek)
    {
        List<LicznikiBudynku> wynik= new ArrayList<LicznikiBudynku>();
        Date data = new Date();
        int rok=data.getYear()+1900;
        try {
            TypedQuery<LicznikiBudynku> q = em.createQuery("SELECT X FROM LicznikiBudynku X WHERE X.idBudynku=:budynek AND X.rok=:rok", LicznikiBudynku.class).setParameter("budynek", budynek).setParameter("rok", rok);
            if (!q.getResultList().isEmpty()) {
                wynik=q.getResultList();
            }
        } catch (NoResultException e) {
            wynik = null;
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
