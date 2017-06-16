/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SBP;

import Entity.Budynek;
import Entity.Informacja;
import Entity.Lokator;
import Entity.Mieszkanie;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Waldek
 */
@Stateless
public class InformacjaFacade extends AbstractFacade<Informacja> {

    @PersistenceContext(unitName = "NieruchomosciPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InformacjaFacade() {
        super(Informacja.class);
    }
public Integer id(){
        Query q = em.createQuery ("SELECT MAX(X.id) FROM Informacja X");
Integer result = (Integer) q.getSingleResult ();
if(result==null)result=1;
else
      result+=1;
      
        return result;
    }     
public Integer numer(){
        Query q = em.createQuery ("SELECT MAX(X.numer) FROM Informacja X");
Integer result = (Integer) q.getSingleResult ();
if(result==null)result=1;
else
      result+=1;
      
        return result;
    }
public Informacja sprawdzTytul(Informacja informacja){
Informacja wynik=null;
try{
    EntityManager em = getEntityManager();
            TypedQuery<Informacja> query = em.createQuery("SELECT X FROM Informacja X WHERE X.tytul=:tytul AND X.idMieszkania=:idMieszkania",Informacja.class).setParameter("tytul", informacja.getTytul()).setParameter("idMieszkania", informacja.getIdMieszkania());
            if(!query.getResultList().isEmpty()) wynik=query.getSingleResult();
}
catch(NullPointerException e){
    wynik=null;
}
return wynik;
}
public Boolean sprawdzOpis(Informacja informacja){
Boolean wynik=false;
try{
    EntityManager em = getEntityManager();
            TypedQuery<Informacja> query = em.createQuery("SELECT X FROM Informacja X WHERE X.opis=:opis",Informacja.class).setParameter("opis", informacja.getOpis());
            if(!query.getResultList().isEmpty()) wynik=true;
}
catch(NullPointerException e){
    wynik=false;
}
return wynik;
}
public List<Informacja> informacje_lokatora(Lokator lokator){
List<Informacja> wynik=new ArrayList<Informacja>();
try{
    EntityManager em = getEntityManager();
            TypedQuery<Informacja> query = em.createQuery("SELECT X FROM Informacja X WHERE X.idMieszkania=:idMieszkania",Informacja.class).setParameter("idMieszkania", lokator.getIdMieszkania());
            if(!query.getResultList().isEmpty()) wynik=query.getResultList();
}
catch(NullPointerException e){
    wynik=null;
}
return wynik;
}
public List<Mieszkanie> mieszkania(Budynek budynek){
   List<Mieszkanie> wynik=new ArrayList<Mieszkanie>(); 
   try{
    EntityManager em = getEntityManager();
            TypedQuery<Mieszkanie> query = em.createQuery("SELECT X FROM Mieszkanie X WHERE X.idBudynku=:budynek",Mieszkanie.class).setParameter("budynek", budynek);
            if(!query.getResultList().isEmpty()) wynik=query.getResultList();
}
catch(NullPointerException e){
    wynik=null;
}
return wynik;
}
}
