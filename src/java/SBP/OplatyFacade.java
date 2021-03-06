/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Budynek;
import Entity.DodatkoweOplaty;
import Entity.Liczniki;
import Entity.Lokator;
import Entity.Mieszkanie;
import Entity.Oplaty;
import Entity.Stawki;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
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
public class OplatyFacade extends AbstractFacade<Oplaty> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OplatyFacade() {
        super(Oplaty.class);
    }

    public Integer id() {
        Query q = em.createQuery("SELECT MAX(X.id) FROM Oplaty X");
        Integer result = (Integer) q.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result += 1;
        }

        return result;
    }

    public Oplaty sprawdz(Integer miesiac, Integer rok, Mieszkanie mieszkanieid) {
        Oplaty oplaty = new Oplaty();
        try {
            TypedQuery<Oplaty> q = em.createQuery("SELECT x FROM Oplaty x WHERE X.miesiac=:miesiac AND X.rok=:rok AND X.idMieszkania=:mieszkanie ", Oplaty.class).setParameter("miesiac", miesiac).setParameter("rok", rok).setParameter("mieszkanie", mieszkanieid);
            if (q.getSingleResult() != null) {
                oplaty = q.getSingleResult();
            }
        } catch (NoResultException e) {
            oplaty = null;
        }
        return oplaty;
    }

    public List<Oplaty> oplatymiesieczne() {
        List<Oplaty> lista = new ArrayList<Oplaty>();
        Date data = new Date();
        Integer miesiac = data.getMonth() + 2;
        Integer rok = data.getYear() + 1900;
        try {
            TypedQuery<Oplaty> q = em.createQuery("SELECT X FROM Oplaty X WHERE X.miesiac=:miesiac AND X.rok=:rok", Oplaty.class).setParameter("miesiac", miesiac).setParameter("rok", rok);
            if (q.getResultList() != null) {
                lista = q.getResultList();
            }
        } catch (NoResultException e) {
            lista = null;
        }

        return lista;
    }

    public Stawki zwroc(Budynek budynek) {
        Stawki wynik = null;
        try {
            TypedQuery<Stawki> q2
                    = em.createQuery("SELECT c FROM Stawki c WHERE c.idBudynku = :budynek", Stawki.class).setParameter("budynek", budynek);
            if (q2.getSingleResult() != null) {
                wynik = q2.getSingleResult();
            }
        } catch (NoResultException e) {

            wynik = null;
        }
        return wynik;
    }

    public BigDecimal roczne_oplaty(Mieszkanie mieszkanie, Integer rok, Integer miesiac) {
        BigDecimal wynik = new BigDecimal(0);
        try {
            TypedQuery<Oplaty> q2
                    = em.createQuery("SELECT c FROM Oplaty c WHERE c.idMieszkania = :mieszkanie AND c.rok=:rok AND c.miesiac<:miesiac", Oplaty.class).setParameter("mieszkanie", mieszkanie).setParameter("rok", rok).setParameter("miesiac", miesiac);
            if (q2.getResultList() != null) {
                for (int i = 0; i < q2.getResultList().size(); i++) {
                    wynik.add(q2.getResultList().get(i).getSumaOplat());
                }
            }
        } catch (NoResultException e) {

            wynik = new BigDecimal(0);
        }
        return wynik;
    }

    public Liczniki liczniki(Mieszkanie mieszkanie, Integer miesiac, Integer rok) {
        Liczniki wynik = null;
        try {
            // TypedQuery<Stawki> q2 = em.createNamedQuery("Stawki.findByBudynek",Stawki.class).setParameter("budynek", budynek);
            TypedQuery<Liczniki> q2
                    = em.createQuery("SELECT c FROM Liczniki c WHERE c.idMieszkania=:mieszkanie AND c.miesiac=:miesiac AND c.rok=:rok", Liczniki.class).setParameter("mieszkanie", mieszkanie).setParameter("miesiac", miesiac).setParameter("rok", rok);
            if (!q2.getResultList().isEmpty()) {
                wynik = q2.getSingleResult();
            } 
        } catch (NoResultException e) {

            wynik=null;
        }
        return wynik;
    }

    public Liczniki poprzedni(Mieszkanie mieszkanie, Integer miesiac, Integer rok) {
        Liczniki wynik = null;
        try {
            // TypedQuery<Stawki> q2 = em.createNamedQuery("Stawki.findByBudynek",Stawki.class).setParameter("budynek", budynek);
            TypedQuery<Liczniki> q2
                    = em.createQuery("SELECT c FROM Liczniki c WHERE c.idMieszkania=:mieszkanie AND C.miesiac < :miesiac AND C.rok <= :rok ORDER BY C.id ", Liczniki.class).setParameter("mieszkanie", mieszkanie).setParameter("miesiac", miesiac).setParameter("rok", rok);
            if (!q2.getResultList().isEmpty()) {
                wynik = q2.getResultList().get(q2.getResultList().size() - 1);
            }
        } catch (NoResultException e) {

            wynik = null;
        }
        return wynik;
    }

    public BigDecimal poprzedna_oplata(Mieszkanie mieszkanie, Integer miesiac, Integer rok) {
        BigDecimal wynik = new BigDecimal(0);
        List<Oplaty> lista = new ArrayList<Oplaty>();
        try {
            TypedQuery<Oplaty> q = em.createQuery("SELECT C FROM Oplaty C WHERE C.idMieszkania=:mieszkanie AND C.miesiac < :miesiac AND C.rok <= :rok ORDER BY C.id", Oplaty.class).setParameter("mieszkanie", mieszkanie).setParameter("miesiac", miesiac).setParameter("rok", rok);
            if (!q.getResultList().isEmpty()) {
                lista = q.getResultList();
                wynik = lista.get(lista.size() - 1).getPodsumowanie();
            }
        } catch (NoResultException e) {
            wynik = new BigDecimal(0);
        }
        return wynik;
    }

    public List<Oplaty> upomnienie(Integer miesiac, Integer rok) {
        List<Oplaty> lista = new ArrayList<Oplaty>();
        try {
            TypedQuery<Oplaty> q = em.createQuery("SELECT C FROM Oplaty C WHERE C.podsumowanie < 0 AND  C.miesiac = :miesiac AND C.rok = :rok ORDER BY C.id", Oplaty.class).setParameter("miesiac", miesiac).setParameter("rok", rok);
            if (!q.getResultList().isEmpty()) {
                lista = q.getResultList();

            }
        } catch (NoResultException e) {
            new BigDecimal(0);
        }
        return lista;
    }

    public List<Oplaty> oplaty_lokatora(Lokator lokator) {
        List<Oplaty> lista = new ArrayList<Oplaty>();
        try {
            TypedQuery<Oplaty> q = em.createQuery("SELECT X FROM Oplaty X WHERE X.idMieszkania=:idMieszkania", Oplaty.class).setParameter("idMieszkania", lokator.getIdMieszkania());
            if (q.getResultList() != null) {
                lista = q.getResultList();
            }
        } catch (NoResultException e) {
            lista = null;
        }

        return lista;
    }

    public List<DodatkoweOplaty> dodatkowe_oplaty(Integer miesiac, Integer rok, Mieszkanie mieszkanie) {
        List<DodatkoweOplaty> lista = new ArrayList<DodatkoweOplaty>();
        try {
            TypedQuery<DodatkoweOplaty> q = em.createQuery("SELECT X FROM DodatkoweOplaty X WHERE X.idBudynku=:budynek AND X.miesiac=:miesiac AND X.rok=:rok AND (X.klatka=:klatka OR X.klatka='Wszystkie')", DodatkoweOplaty.class).setParameter("miesiac", miesiac).setParameter("rok", rok).setParameter("klatka", mieszkanie.getKlatka()).setParameter("budynek", mieszkanie.getIdBudynku());
            if (q.getResultList() != null) {
                lista = q.getResultList();
            }
        } catch (NoResultException e) {
            lista = null;
        }

        return lista;
    }

    public Integer aktywne(Mieszkanie mieszkanie) {
        Integer wynik;
        List<Mieszkanie> lista = new ArrayList<Mieszkanie>();
        try {
            TypedQuery<Mieszkanie> q = em.createQuery("SELECT X FROM Mieszkanie X WHERE X.idBudynku=:budynek AND X.idBudynku=:budynek AND X.zajetosc=TRUE", Mieszkanie.class).setParameter("budynek", mieszkanie.getIdBudynku());
            if (q.getResultList() != null) {
                lista = q.getResultList();
            }
        } catch (NoResultException e) {
            lista = null;
        }
        if (!lista.isEmpty()) {
            return lista.size();
        } else {
            return 1;
        }
    }
}
