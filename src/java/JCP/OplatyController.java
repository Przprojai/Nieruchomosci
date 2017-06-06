package JCP;

import Entity.DodatkoweOplaty;
import Entity.Liczniki;
import Entity.Lokator;
import Entity.Mieszkanie;
import Entity.Oplaty;
import Entity.Stawki;
import Entity.Szczegoly;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.OplatyFacade;
import java.math.BigDecimal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("oplatyController")
@SessionScoped
public class OplatyController implements Serializable {

    @EJB
    private SBP.OplatyFacade ejbFacade;
    private List<Oplaty> items = null;
    private Oplaty selected;

    public OplatyController() {
    }

    public Oplaty getSelected() {
        return selected;
    }

    public void setSelected(Oplaty selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private OplatyFacade getFacade() {
        return ejbFacade;
    }

    public Oplaty prepareCreate() {
        selected = new Oplaty();
        initializeEmbeddableKey();
        return selected;
    }
public List<String> miesiac(){
        List<String> lista = new ArrayList<String>();
        List<String> lista2 = new ArrayList<String>();
        Date data = new Date();
        
        lista.add("Styczeń");
        lista.add("Luty");
        lista.add("Marzec");
        lista.add("Kwiecień");
        lista.add("Maj");
        lista.add("Czerwiec");
        lista.add("Lipiec");
        lista.add("Sierpień");
        lista.add("Wrzesień");
        lista.add("Październik");
        lista.add("Listopad");
        lista.add("Grudzień");
        for (int i=data.getMonth()+1;i<12;i++)lista2.add(lista.get(i));
        return lista2;
    }
    public Integer zamienMiesiac(String miesiac){
        Integer wynik=0;
        switch(miesiac){
            case "Styczeń": {wynik=1;break;}
            case "Luty": {wynik=2;break;}
            case "Marzec": {wynik=3;break;}
            case "Kwiecień": {wynik=4;break;}
            case "Maj": {wynik=5;break;}
            case "Czerwiec": {wynik=6;break;}
            case "Lipiec": {wynik=7;break;}
            case "Sierpień": {wynik=8;break;}
            case "Wrzesień": {wynik=9;break;}
            case "Październik": {wynik=10;break;}
            case "Listopad": {wynik=11;break;}
            case "Grudzień": {wynik=12;break;}
        }
        return wynik;
    }
    public List<Szczegoly> automat(List<Mieszkanie> mieszkania,Integer miesiac,Integer rok){
    //double sumaoplat= 0.0;
    BigDecimal sumaoplat;
    Double sumaoplat2;
    Integer pomoc =0;
    BigDecimal temp = new BigDecimal(0);
    Oplaty oplata2 = new Oplaty();
    Liczniki licznik1=new Liczniki();
    Liczniki licznik2=null;
    Stawki stawki= new Stawki();
    List<Oplaty> lista = new ArrayList<Oplaty>();
    Mieszkanie mieszkanie = new Mieszkanie();
    List<Szczegoly> lista_szczegolow= new ArrayList<Szczegoly>();
    Szczegoly szczegoly = new Szczegoly();
    
    
        for (int i=0;i<mieszkania.size();i++){
            mieszkanie=mieszkania.get(i);
            stawki=getFacade().zwroc(mieszkanie.getIdBudynku());
            oplata2=getFacade().sprawdz(miesiac, rok,mieszkanie);
            
            
            sumaoplat=stawki.getEksploatacjaPodstawowa().multiply(mieszkanie.getPowierzchnia());
            szczegoly.setEpf(stawki.getEksploatacjaPodstawowa().multiply(mieszkanie.getPowierzchnia()));
            szczegoly.setEksploatacjaPodstawowa("Eksploatacja podstawowa "+stawki.getEksploatacjaPodstawowa() + " zł/m2 * "+mieszkanie.getPowierzchnia()+ " m2  - " +(stawki.getEksploatacjaPodstawowa().multiply(mieszkanie.getPowierzchnia()))+ " zł");
           
            if(miesiac==1){
                sumaoplat=sumaoplat.add(mieszkanie.getNadplata());
            }
            
            sumaoplat= sumaoplat.add(stawki.getFunduszRemontowy().multiply(mieszkanie.getPowierzchnia()));
            sumaoplat= sumaoplat.add(stawki.getLegalizacjaWodomierza().multiply(new BigDecimal (2)));
            szczegoly.setFrf(stawki.getFunduszRemontowy().multiply(mieszkanie.getPowierzchnia()));
            szczegoly.setLwf(stawki.getLegalizacjaWodomierza().multiply(new BigDecimal (2)));
            szczegoly.setFunduszRemontowy("Fundusz remontowy "+stawki.getFunduszRemontowy()+" zł/m2 * "+ mieszkanie.getPowierzchnia()+" m2  - " +stawki.getFunduszRemontowy().multiply(mieszkanie.getPowierzchnia())+ " zł");
            szczegoly.setLegalizacjaWodomierza("Legalizacja wodomierza "+stawki.getLegalizacjaWodomierza()+" zł/wod * 2  -"+stawki.getLegalizacjaWodomierza().multiply(new BigDecimal (2))+" zł ");
            
            sumaoplat= sumaoplat.add(stawki.getKonserwacjaDomofonu());
            szczegoly.setKdf(stawki.getKonserwacjaDomofonu());
            szczegoly.setKonserwacjaDomofonu("Konserwacja domofonu "+stawki.getKonserwacjaDomofonu()+" zł/lokal - "+stawki.getKonserwacjaDomofonu()+" zł");
            
            sumaoplat=sumaoplat.add(stawki.getEksploatacjaDzwigow().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()).multiply(((new BigDecimal (mieszkanie.getPietro()))))));
            szczegoly.setEdf(stawki.getEksploatacjaDzwigow().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()).multiply(((new BigDecimal (mieszkanie.getPietro()))))));
            szczegoly.setEksploatacjaDzwigow("Eksploatacja dzwigow " +stawki.getEksploatacjaDzwigow() +" zł/os * "+ mieszkanie.getLiczbaOsob()*mieszkanie.getPietro()+" os * piętro - "+stawki.getEksploatacjaDzwigow().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()).multiply(((new BigDecimal (mieszkanie.getPietro())))))+" zł");
            
            if(!mieszkanie.getIdBudynku().getWspolnyLicznik()){
            licznik1=getFacade().poprzedni(mieszkanie, miesiac, rok);
            licznik2=getFacade().liczniki(mieszkanie, miesiac, rok);
            if (licznik2 == null && licznik1 !=null){
                licznik2=new Liczniki();
                licznik2.setLicznikCiepla(licznik1.getLicznikCiepla()+5);
                licznik2.setLicznikWodyCieplej(licznik1.getLicznikWodyCieplej()+5);
                licznik2.setLicznikWodyZimnej(licznik1.getLicznikWodyZimnej()+5);
                licznik2.setGaz(licznik1.getGaz()+5);
            }
            else
            {
                licznik2.setLicznikCiepla(5);
                licznik2.setLicznikWodyCieplej(5);
                licznik2.setLicznikWodyZimnej(5);
                licznik2.setGaz(5);
            }
                if(licznik1==null){
                sumaoplat=sumaoplat.add(stawki.getCo().multiply(new BigDecimal(licznik2.getLicznikCiepla())));
                szczegoly.setCof(stawki.getCo().multiply(new BigDecimal(licznik2.getLicznikCiepla())));
                szczegoly.setCo("Centralne ogrzewanie "+stawki.getCo()+" zł*licznik ciepła * "+licznik2.getLicznikCiepla()+ " - "+stawki.getCo().multiply(new BigDecimal(licznik2.getLicznikCiepla()))+" zł");
                
                sumaoplat=sumaoplat.add(stawki.getCw().multiply(new BigDecimal(licznik2.getLicznikWodyCieplej())));
                szczegoly.setCwf(stawki.getCw().multiply(new BigDecimal(licznik2.getLicznikWodyCieplej())));
                szczegoly.setCw("Ciepła woda "+stawki.getCw()+" zł/m3 * "+licznik2.getLicznikWodyCieplej()+" m3 - " +stawki.getCw().multiply(new BigDecimal(licznik2.getLicznikWodyCieplej()))+" m3");
                
                sumaoplat=sumaoplat.add(stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej())));
                szczegoly.setZwisf(stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej())));
                szczegoly.setZwis("Zimna woda i ścieki "+stawki.getZwis()+" zł/m3 * "+licznik2.getLicznikWodyZimnej()+" m3 - "+stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej()))+" zł");
                
//                sumaoplat=sumaoplat.add(stawki.getGaz().multiply(new BigDecimal(licznik2.getGaz())));
//                szczegoly.setGazf(stawki.getGaz().multiply(new BigDecimal(licznik2.getGaz())));
//                szczegoly.setZwis("Gaz "+stawki.getGaz()+" zł/m3 * "+licznik2.getGaz()+" m3 - "+stawki.getGaz().multiply(new BigDecimal(licznik2.getGaz()))+" zł");
                
                }else
                {
                sumaoplat=sumaoplat.add(stawki.getCo().multiply(new BigDecimal((licznik2.getLicznikCiepla()-licznik1.getLicznikCiepla()))));
                szczegoly.setCof(stawki.getCo().multiply(new BigDecimal((licznik2.getLicznikCiepla()-licznik1.getLicznikCiepla()))));
                szczegoly.setCo("Centralne ogrzewanie "+stawki.getCo()+" zł*licznik ciepła * "+(licznik2.getLicznikCiepla()-licznik1.getLicznikCiepla()) +" - "+stawki.getCo().multiply(new BigDecimal((licznik2.getLicznikCiepla()-licznik1.getLicznikCiepla())))+" zł");
                
                sumaoplat=sumaoplat.add(stawki.getCw().multiply(new BigDecimal((licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej()))));
                szczegoly.setCwf(stawki.getCw().multiply(new BigDecimal((licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej()))));
                szczegoly.setCw("Ciepła woda "+stawki.getCw()+" zł/m3 * "+(licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej())+" m3 - " +stawki.getCw().multiply(new BigDecimal((licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej())))+" zł");
                
                sumaoplat=sumaoplat.add(stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej())));
                szczegoly.setZwisf(stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej())));
                szczegoly.setZwis("Zimna woda i ścieki "+stawki.getZwis()+" zł*licznik ciepła * "+(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej())+" m3 "+stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej()))+" zł");
                
//                sumaoplat=sumaoplat.add(stawki.getGaz().multiply(new BigDecimal(licznik2.getGaz()-licznik1.getGaz())));
//                szczegoly.setGazf(stawki.getGaz().multiply(new BigDecimal(licznik2.getGaz()-licznik1.getGaz())));
//                szczegoly.setZwis("Gaz "+stawki.getGaz()+" zł/m3 * "+(licznik2.getGaz()-licznik1.getGaz())+" m3 - "+stawki.getGaz().multiply(new BigDecimal(licznik2.getGaz()-licznik1.getGaz()))+" zł");
                }
            }else
            {
                 licznik1=getFacade().poprzedni(mieszkanie, miesiac, rok);
            licznik2=getFacade().liczniki(mieszkanie, miesiac, rok);
            if (licznik2 == null && licznik1 !=null){
                licznik2=new Liczniki();
                //licznik2.setLicznikCiepla(licznik1.getLicznikCiepla()+5);
                licznik2.setLicznikWodyCieplej(licznik1.getLicznikWodyCieplej()+5);
                licznik2.setLicznikWodyZimnej(licznik1.getLicznikWodyZimnej()+5);
            }
            else
            {
              //  licznik2.setLicznikCiepla(5);
                licznik2=new Liczniki();
                licznik2.setLicznikWodyCieplej(5);
                licznik2.setLicznikWodyZimnej(5);
            }
                if(licznik1==null){
                sumaoplat=sumaoplat.add(new BigDecimal(9));
                szczegoly.setCof(new BigDecimal(9));
                szczegoly.setCo("Centralne ogrzewanie: "+new BigDecimal(9)+" zł");
                
                sumaoplat=sumaoplat.add(stawki.getCw().multiply(new BigDecimal(licznik2.getLicznikWodyCieplej())));
                szczegoly.setCwf(stawki.getCw().multiply(new BigDecimal(licznik2.getLicznikWodyCieplej())));
                szczegoly.setCw("Ciepła woda "+stawki.getCw()+" zł/m3 * "+licznik2.getLicznikWodyCieplej()+" m3 - " +stawki.getCw().multiply(new BigDecimal(licznik2.getLicznikWodyCieplej()))+" m3");
                
                sumaoplat=sumaoplat.add(stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej())));
                szczegoly.setZwisf(stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej())));
                szczegoly.setZwis("Zimna woda i ścieki "+stawki.getZwis()+" zł/m3 * "+licznik2.getLicznikWodyZimnej()+" m3 - "+stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej()))+" zł");
                }else
                {
                sumaoplat=sumaoplat.add(new BigDecimal(9));
                szczegoly.setCof(new BigDecimal(9));
                szczegoly.setCo("Centralne ogrzewanie: "+new BigDecimal(9)+" zł");
                
                sumaoplat=sumaoplat.add(stawki.getCw().multiply(new BigDecimal((licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej()))));
                szczegoly.setCwf(stawki.getCw().multiply(new BigDecimal((licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej()))));
                szczegoly.setCw("Ciepła woda "+stawki.getCw()+" zł/m3 * "+(licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej())+" m3 - " +stawki.getCw().multiply(new BigDecimal((licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej())))+" zł");
                
                sumaoplat=sumaoplat.add(stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej())));
                szczegoly.setZwisf(stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej())));
                szczegoly.setZwis("Zimna woda i ścieki "+stawki.getZwis()+" zł*licznik ciepła * "+(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej())+" m3 "+stawki.getZwis().multiply(new BigDecimal(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej()))+" zł");
                }
            }
            
           // sumaoplat=sumaoplat.add(stawki.getGaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob())));
           // szczegoly.setGazf(stawki.getGaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob())));
           // szczegoly.setGaz("Gaz "+stawki.getGaz()+" zł/os * "+mieszkanie.getLiczbaOsob()+" os - "+stawki.getGaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()))+" zł");
            
            sumaoplat=sumaoplat.add(stawki.getPradWPomWspolnych().multiply(new BigDecimal(mieszkanie.getLiczbaOsob())));
            szczegoly.setPwpwf(stawki.getPradWPomWspolnych().multiply(new BigDecimal(mieszkanie.getLiczbaOsob())));
            szczegoly.setPradWPomWspolnych("Prąd w pomieszczeniach wspólnych "+stawki.getPradWPomWspolnych()+" zł/os * "+mieszkanie.getLiczbaOsob()+" os -"+stawki.getPradWPomWspolnych().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()))+" zł");
            
            sumaoplat=sumaoplat.add(stawki.getSmieci());
            szczegoly.setSmf(stawki.getSmieci());
            szczegoly.setSmieci("Smieci -"+stawki.getSmieci()+" zł");
            
            sumaoplat=sumaoplat.add(stawki.getUbezpieczenie());
            szczegoly.setUbf(stawki.getUbezpieczenie());
            szczegoly.setUbezpieczenie("Ubezpieczenie - "+stawki.getUbezpieczenie()+" zł");
            if(mieszkanie.getIdBudynku().getWspolnyLicznikGazu())
                {
                    sumaoplat=sumaoplat.add((stawki.getRyczalt_gaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()))));
                    szczegoly.setGazf(stawki.getRyczalt_gaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob())));
                    szczegoly.setGaz("Gaz "+stawki.getRyczalt_gaz()+" zł/os * "+mieszkanie.getLiczbaOsob()+" os - "+stawki.getRyczalt_gaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()))+" zł");

                }
                else
                {
                    licznik1=getFacade().poprzedni(mieszkanie, miesiac, rok);
                    licznik2=getFacade().liczniki(mieszkanie, miesiac, rok);
                   if(licznik1==null && licznik2==null) 
                   {
                        sumaoplat=sumaoplat.add((stawki.getRyczalt_gaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()))));
                        szczegoly.setGazf(stawki.getRyczalt_gaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob())));
                        szczegoly.setGaz("Gaz "+stawki.getRyczalt_gaz()+" zł/os * "+mieszkanie.getLiczbaOsob()+" os - "+stawki.getRyczalt_gaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()))+" zł");

                   }
                   else{
                       if(licznik2!=null && licznik1==null){
                        sumaoplat=sumaoplat.add((stawki.getGaz().multiply(new BigDecimal (licznik2.getGaz()).multiply(new BigDecimal(mieszkanie.getLiczbaOsob())))));
                        szczegoly.setGazf(stawki.getGaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()).multiply(new BigDecimal(licznik2.getGaz()))));
                        szczegoly.setGaz("Gaz "+stawki.getGaz()+" zł/m3* "+licznik2.getGaz()+" m3 -"+stawki.getGaz().multiply(new BigDecimal(licznik2.getGaz()))+" zł");

                       }
                       if(licznik2==null && licznik1!=null){
                        sumaoplat=sumaoplat.add((stawki.getGaz().multiply(new BigDecimal (licznik1.getGaz()+5).multiply(new BigDecimal(mieszkanie.getLiczbaOsob())))));
                        szczegoly.setGazf(stawki.getGaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()).multiply(new BigDecimal(licznik1.getGaz()+5))));
                        szczegoly.setGaz("Gaz "+stawki.getGaz()+" zł/m3* "+(licznik1.getGaz()+5)+" m3 -"+stawki.getGaz().multiply(new BigDecimal(licznik1.getGaz()+5))+" zł");

                       }
                        if(licznik2!=null && licznik1!=null){
                        sumaoplat=sumaoplat.add((stawki.getGaz().multiply(new BigDecimal (licznik2.getGaz()-licznik1.getGaz()).multiply(new BigDecimal(mieszkanie.getLiczbaOsob())))));
                        szczegoly.setGazf(stawki.getGaz().multiply(new BigDecimal(mieszkanie.getLiczbaOsob()).multiply(new BigDecimal(licznik2.getGaz()-licznik1.getGaz()))));
                        szczegoly.setGaz("Gaz "+stawki.getGaz()+" zł/m3 * "+(licznik2.getGaz()-licznik1.getGaz()+" m3 -")+stawki.getGaz().multiply(new BigDecimal(licznik2.getGaz()-licznik1.getGaz()))+" zł");

                       }
                               }
                }
            szczegoly.setDof(new BigDecimal(0));
            if(getFacade().poprzedna_oplata(mieszkanie,miesiac,rok).doubleValue()<=0){
            szczegoly.setDodatkowe_oplaty("Zaległości z poprzednich miesięcy: "+ getFacade().poprzedna_oplata(mieszkanie,miesiac,rok)+" zł" );
            szczegoly.setDof(getFacade().poprzedna_oplata(mieszkanie,miesiac,rok));
                    }
            else
            {
            szczegoly.setDodatkowe_oplaty("Nadpłata z poprzednich miesięcy: "+ getFacade().poprzedna_oplata(mieszkanie,miesiac,rok)+" zł" );
            szczegoly.setDof(getFacade().poprzedna_oplata(mieszkanie,miesiac,rok));
            }
            List<DodatkoweOplaty> listaOplat = new ArrayList<DodatkoweOplaty>();
            listaOplat=getFacade().dodatkowe_oplaty(miesiac, rok, mieszkanie);
            if(!listaOplat.isEmpty()){
            for (int j=0;j<listaOplat.size();j++){
            
            BigDecimal pom = new BigDecimal(0);
            pom = pom.add(listaOplat.get(j).getKoszt());
            pom = pom.divide(new BigDecimal(getFacade().aktywne(mieszkanie)),2);
            sumaoplat=sumaoplat.add(pom);
            szczegoly.setDodatkowe_oplaty(szczegoly.getDodatkowe_oplaty()+"  "+listaOplat.get(j).getRodzaj() + " " + pom + " zł");
            szczegoly.setDof(szczegoly.getDof().subtract(pom));
            }
            }
            //sumaoplat=(float)(sumaoplat*0.01);
            szczegoly.setSuf(sumaoplat);
            szczegoly.setPods(szczegoly.getDof().subtract(sumaoplat));
            if(szczegoly.getPods().compareTo(BigDecimal.ZERO)>0){szczegoly.setPods(new BigDecimal(0));}else {szczegoly.setPods(szczegoly.getPods().abs());}
            szczegoly.setSuma("Suma opłat: "+sumaoplat);
            if(oplata2==null){
                Oplaty oplata = new Oplaty();
                oplata.setId(getFacade().id());
                oplata.setMiesiac(miesiac);
                oplata.setRok(rok);
                oplata.setIdStawki(stawki);
                oplata.setZaplacono(new BigDecimal(0));
                oplata.setPodsumowanie(new BigDecimal(0));
                oplata.setIdMieszkania(mieszkania.get(i));
                oplata.setSumaOplat(sumaoplat);
                oplata.setZaleglosci(getFacade().poprzedna_oplata(mieszkanie,miesiac,rok));
                selected=oplata;
                szczegoly.setId(oplata.getId());
                
                persist(PersistAction.CREATE, "Oplaty dla mieszkania " + mieszkanie.getId() +" dodane");
                szczegoly.setIdOplaty(oplata);
                lista_szczegolow.add(szczegoly);
                szczegoly = new Szczegoly();
            }
            else
            {
                  oplata2.setMiesiac(miesiac);
                  oplata2.setRok(rok);
                  oplata2.setZaleglosci(getFacade().poprzedna_oplata(mieszkanie,miesiac,rok));
                  oplata2.setSumaOplat(sumaoplat);
                  selected=oplata2;
                  
                  persist(PersistAction.UPDATE, "Oplaty dla mieszkania " + mieszkanie.getId() +" zaktualizowane");
                  szczegoly.setIdOplaty(selected);
                  szczegoly.setId(selected.getId());
                  lista_szczegolow.add(szczegoly);
                  szczegoly = new Szczegoly();
            }
        }
        items=null;
        return lista_szczegolow;
    }
    public Boolean zaplacono(){
        List<Oplaty> lista = new ArrayList<Oplaty>();
        Date data = new Date();
        Integer miesiac = data.getMonth()+2;
        Integer rok = data.getYear()+1900;
        BigDecimal zmienna1;
        Integer zmienna2=0;
        Oplaty oplaty = new Oplaty();
        lista=getFacade().oplatymiesieczne();
        for(int i=0;i<lista.size();i++){
        oplaty=lista.get(i);
        oplaty.setZaplacono((oplaty.getIdMieszkania().getStanKonta()));
        zmienna1=(oplaty.getZaleglosci().add(oplaty.getZaplacono()));
        zmienna1=(zmienna1.subtract(oplaty.getSumaOplat()));
        oplaty.setPodsumowanie(zmienna1);
        selected=oplaty;
        persist(PersistAction.UPDATE, "Pole Zapłacono dla mieszkania " + selected.getIdMieszkania().getId() + " zaktualizowano");
        }
        items=null;
       return true;
    }
    public List<Oplaty> upomnienie(){
        Date data = new Date();
        Integer miesiac = data.getMonth()+2;
        Integer rok = data.getYear()+1900;
        return getFacade().upomnienie(miesiac, rok);
    }
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("OplatyCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("OplatyUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("OplatyDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    public List<Oplaty> oplaty_lokatora(Lokator lokator){
       return getFacade().oplaty_lokatora(lokator);
    }
    public List<Oplaty> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    public List<Oplaty> tablicadanych(){
        List<Oplaty> lista = new ArrayList<Oplaty>();
        lista = getFacade().findAll();
        return lista;
    }
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Oplaty getOplaty(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Oplaty> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Oplaty> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Oplaty.class)
    public static class OplatyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OplatyController controller = (OplatyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oplatyController");
            return controller.getOplaty(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Oplaty) {
                Oplaty o = (Oplaty) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Oplaty.class.getName()});
                return null;
            }
        }

    }

}
