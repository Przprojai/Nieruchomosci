package JCP;

import Entity.Liczniki;
import Entity.Mieszkanie;
import Entity.Oplaty;
import Entity.Stawki;
import Entity.Szczegoly;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.OplatyFacade;

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
    double sumaoplat=0.0;
    String sumaoplat2;
    Integer pomoc =0;
    Oplaty oplata2 = new Oplaty();
    Liczniki licznik1=new Liczniki();
    Liczniki licznik2=new Liczniki();
    Stawki stawki= new Stawki();
    List<Oplaty> lista = new ArrayList<Oplaty>();
    Mieszkanie mieszkanie = new Mieszkanie();
    List<Szczegoly> lista_szczegolow= new ArrayList<Szczegoly>();
    Szczegoly szczegoly = new Szczegoly();
    
    
        for (int i=0;i<mieszkania.size();i++){
            mieszkanie=mieszkania.get(i);
            stawki=getFacade().zwroc(mieszkanie.getIdBudynku());
            oplata2=getFacade().sprawdz(miesiac, rok,mieszkanie);
            sumaoplat=stawki.getEksploatacjaPodstawowa()*mieszkanie.getPowierzchnia();
            szczegoly.setEpf(stawki.getEksploatacjaPodstawowa()*mieszkanie.getPowierzchnia());
            szczegoly.setEksploatacjaPodstawowa("Eksploatacja podstawowa "+stawki.getEksploatacjaPodstawowa() + " zł/m2 * "+mieszkanie.getPowierzchnia()+ " m2  - " +(stawki.getEksploatacjaPodstawowa()*mieszkanie.getPowierzchnia())+ " zł");
            
            
            sumaoplat+= stawki.getFunduszRemontowy()*mieszkanie.getPowierzchnia()+stawki.getLegalizacjaWodomierza()*2;
            szczegoly.setFrf(stawki.getFunduszRemontowy()*mieszkanie.getPowierzchnia());
            szczegoly.setLwf(stawki.getLegalizacjaWodomierza()*2);
            szczegoly.setFunduszRemontowy("Fundusz Remontowy "+stawki.getFunduszRemontowy()+" zł/m2 * "+ mieszkanie.getPowierzchnia()+" m2  - " +stawki.getFunduszRemontowy()*mieszkanie.getPowierzchnia()+ " zł");
            szczegoly.setLegalizacjaWodomierza("Legalizacja Wodomierza "+stawki.getLegalizacjaWodomierza()+" zł/wod * 2  -"+stawki.getLegalizacjaWodomierza()*2+" zł ");
            
            sumaoplat+= stawki.getKonserwacjaDomofonu();
            szczegoly.setKdf(stawki.getKonserwacjaDomofonu());
            szczegoly.setKonserwacjaDomofonu("Konserwacja domofonu "+stawki.getKonserwacjaDomofonu()+" zł/lokal - "+stawki.getKonserwacjaDomofonu()+" zł");
            
            sumaoplat+= stawki.getEksploatacjaDzwigow()*mieszkanie.getLiczbaOsob()*(mieszkanie.getPietro());
            szczegoly.setEdf(stawki.getEksploatacjaDzwigow()*mieszkanie.getLiczbaOsob()*(mieszkanie.getPietro()));
            szczegoly.setEksploatacjaDzwigow("Eksploatacja Dzwigow " +stawki.getEksploatacjaDzwigow() +" zł/os * "+ mieszkanie.getLiczbaOsob()*mieszkanie.getPietro()+" os * piętro - "+stawki.getEksploatacjaDzwigow()*mieszkanie.getLiczbaOsob()*(mieszkanie.getPietro())+" zł");
            
            licznik1=getFacade().poprzedni(mieszkanie, miesiac, rok);
            licznik2=getFacade().liczniki(mieszkanie, miesiac, rok);
                if(licznik1==null){
                sumaoplat+= stawki.getCo()*licznik2.getLicznikCiepla();
                szczegoly.setCof(stawki.getCo()*licznik2.getLicznikCiepla());
                szczegoly.setCo("Centralne ogrzewanie "+stawki.getCo()+" zł*licznik ciepła * "+licznik2.getLicznikCiepla()+ " - "+stawki.getCo()*licznik2.getLicznikCiepla()+" zł");
                
                sumaoplat+= stawki.getCw()*licznik2.getLicznikWodyCieplej();
                szczegoly.setCwf(stawki.getCw()*licznik2.getLicznikWodyCieplej());
                szczegoly.setCw("Ciepła woda "+stawki.getCw()+" zł/m3 * "+licznik2.getLicznikWodyCieplej()+" m3 - " +stawki.getCw()*licznik2.getLicznikWodyCieplej()+" m3");
                
                sumaoplat+= stawki.getZwis()*licznik2.getLicznikWodyZimnej();
                szczegoly.setZwisf(stawki.getZwis()*licznik2.getLicznikWodyZimnej());
                szczegoly.setZwis("Zimna woda i ścieki "+stawki.getZwis()+" zł/m3 * "+licznik2.getLicznikWodyZimnej()+" m3 - "+stawki.getZwis()*licznik2.getLicznikWodyZimnej()+" zł");
                }else
                {
                sumaoplat+= stawki.getCo()*(licznik2.getLicznikCiepla()-licznik1.getLicznikCiepla());
                szczegoly.setCof(stawki.getCo()*(licznik2.getLicznikCiepla()-licznik1.getLicznikCiepla()));
                szczegoly.setCo("Centralne ogrzewanie "+stawki.getCo()+" zł*licznik ciepła * "+(licznik2.getLicznikCiepla()-licznik1.getLicznikCiepla()) +" - "+stawki.getCo()*(licznik2.getLicznikCiepla()-licznik1.getLicznikCiepla())+" zł");
                
                sumaoplat+= stawki.getCw()*(licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej());
                szczegoly.setCwf(stawki.getCw()*(licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej()));
                szczegoly.setCw("Ciepła woda "+stawki.getCw()+" zł/m3 * "+(licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej())+" m3 - " +stawki.getCw()*(licznik2.getLicznikWodyCieplej()-licznik1.getLicznikWodyCieplej())+" zł");
                
                sumaoplat+= stawki.getZwis()*(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej());
                szczegoly.setZwisf(stawki.getZwis()*(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej()));
                szczegoly.setZwis("Zimna woda i ścieki "+stawki.getZwis()+" zł*licznik ciepła * "+(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej())+" m3"+stawki.getZwis()*(licznik2.getLicznikWodyZimnej()-licznik1.getLicznikWodyZimnej())+" zł");
                }

            sumaoplat+= stawki.getGaz()*mieszkanie.getLiczbaOsob();
            szczegoly.setGazf(stawki.getGaz()*mieszkanie.getLiczbaOsob());
            szczegoly.setGaz("Gaz "+stawki.getGaz()+" zł/os * "+mieszkanie.getLiczbaOsob()+" os - "+stawki.getGaz()*mieszkanie.getLiczbaOsob()+" zł");
            
            sumaoplat+= stawki.getPradWPomWspolnych()*mieszkanie.getLiczbaOsob();
            szczegoly.setPwpwf(stawki.getPradWPomWspolnych()*mieszkanie.getLiczbaOsob());
            szczegoly.setPradWPomWspolnych("Prąd w pomieszczeniach wspólnych "+stawki.getPradWPomWspolnych()+" zł/os * "+mieszkanie.getLiczbaOsob()+" os -"+stawki.getPradWPomWspolnych()*mieszkanie.getLiczbaOsob()+" zł");
            
            sumaoplat+= stawki.getSmieci();
            szczegoly.setSmf(stawki.getSmieci());
            szczegoly.setSmieci("Smieci -"+stawki.getSmieci()+" zł");
            
            sumaoplat+= stawki.getUbezpieczenie();
            szczegoly.setUbf(stawki.getUbezpieczenie());
            szczegoly.setUbezpieczenie("Ubezpieczenie - "+stawki.getUbezpieczenie()+" zł");
            szczegoly.setDof(0.0f);
            szczegoly.setDodatkowe_oplaty("-");
            
            
            pomoc=(int)(sumaoplat*100);
            sumaoplat=(float)(pomoc*0.01);
            szczegoly.setSuma(""+sumaoplat);
            if(oplata2==null){
                Oplaty oplata = new Oplaty();
                oplata.setId(getFacade().id());
                oplata.setMiesiac(miesiac);
                oplata.setRok(rok);
                oplata.setIdStawki(stawki);
                oplata.setZaplacono(0);
                oplata.setPodsumowanie(0);
                oplata.setIdMieszkania(mieszkania.get(i));
                oplata.setSumaOplat((float) sumaoplat);
                oplata.setZaleglosci((float)getFacade().poprzedna_oplata(mieszkanie,miesiac,rok));
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
                  oplata2.setZaleglosci((float)getFacade().poprzedna_oplata(mieszkanie,miesiac,rok));
                  oplata2.setSumaOplat((float)sumaoplat);
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
        Float zmienna1=0.0f;
        Integer zmienna2=0;
        Oplaty oplaty = new Oplaty();
        lista=getFacade().oplatymiesieczne();
        for(int i=0;i<lista.size();i++){
        oplaty=lista.get(i);
        oplaty.setZaplacono((float)(oplaty.getIdMieszkania().getStanKonta()));
        zmienna1=(oplaty.getZaleglosci()-oplaty.getSumaOplat()+oplaty.getZaplacono());
        zmienna2=(int)(zmienna1*100);
        zmienna1=(float)(zmienna2*0.01);
        oplaty.setPodsumowanie(zmienna1);
        selected=oplaty;
        persist(PersistAction.UPDATE, "Pole Zapłacono dla mieszkania " + selected.getIdMieszkania().getId() + " zaktualizowano");
        }
        items=null;
       return true;
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
