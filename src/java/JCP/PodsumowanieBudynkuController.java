package JCP;

import Entity.Oplaty;
import Entity.PodsumowanieBudynku;
import Entity.Szczegoly;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.PodsumowanieBudynkuFacade;

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

@Named("podsumowanieBudynkuController")
@SessionScoped
public class PodsumowanieBudynkuController implements Serializable {

    @EJB
    private SBP.PodsumowanieBudynkuFacade ejbFacade;
    private List<PodsumowanieBudynku> items = null;
    private PodsumowanieBudynku selected;

    public PodsumowanieBudynkuController() {
    }

    public PodsumowanieBudynku getSelected() {
        return selected;
    }

    public void setSelected(PodsumowanieBudynku selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PodsumowanieBudynkuFacade getFacade() {
        return ejbFacade;
    }

    public PodsumowanieBudynku prepareCreate() {
        selected = new PodsumowanieBudynku();
        initializeEmbeddableKey();
        return selected;
    }
    public void podsumowanie(Boolean a){
      List<Szczegoly> lista = new ArrayList<Szczegoly>();
      Integer budynki=getFacade().Budynki();
        Date data = new Date();
        Integer miesiac = data.getMonth()+2;
        Integer rok = data.getYear()+1900;
      for(int i=1;i<=budynki;i++){
       lista=getFacade().oplaty(i);
       PodsumowanieBudynku nowy = new PodsumowanieBudynku();
       for(int j=0;j<lista.size();j++){
       nowy.setEksploatacjaPodstawowa(nowy.getEksploatacjaPodstawowa()+lista.get(j).getEpf());
       nowy.setFunduszRemontowy(nowy.getFunduszRemontowy()+lista.get(j).getFrf());
       nowy.setLegalizacjaWodomierza(nowy.getLegalizacjaWodomierza()+lista.get(j).getLwf());
       nowy.setEksploatacjaDzwigow(nowy.getEksploatacjaDzwigow()+lista.get(j).getEdf());
       nowy.setKonserwacjaDomofonu(nowy.getKonserwacjaDomofonu()+lista.get(j).getKdf());
       nowy.setCo(nowy.getCo()+lista.get(j).getCof());
       nowy.setCw(nowy.getCw()+lista.get(j).getCwf());
       nowy.setZwis(nowy.getZwis()+lista.get(j).getZwisf());
       nowy.setPradWPomWspolnych(nowy.getPradWPomWspolnych()+lista.get(j).getPwpwf());
       nowy.setSmieci(nowy.getSmieci()+lista.get(j).getSmf());
       nowy.setUbezpieczenie(nowy.getUbezpieczenie()+lista.get(j).getUbf());
       nowy.setGaz(nowy.getGaz()+lista.get(j).getGazf());
       nowy.setDodatkowe_oplaty(nowy.getDodatkowe_oplaty()+lista.get(j).getDof()); 
       nowy.setSuma(nowy.getSuma()+lista.get(j).getIdOplaty().getSumaOplat());
       nowy.setZaplacono(nowy.getZaplacono()+lista.get(j).getIdOplaty().getZaplacono());
       nowy.setWynik(nowy.getZaplacono()-nowy.getSuma());
       nowy.setZajetychMieszkan(lista.size());
       }
       

       nowy.setIdBudynku(getFacade().zwrocid(i));
       selected=getFacade().znajdz(rok, miesiac,getFacade().zwrocid(i));
       if(selected!=null){
       selected.setEksploatacjaPodstawowa(nowy.getEksploatacjaPodstawowa());
       selected.setFunduszRemontowy(nowy.getFunduszRemontowy());
       selected.setLegalizacjaWodomierza(nowy.getLegalizacjaWodomierza());
       selected.setEksploatacjaDzwigow(nowy.getEksploatacjaDzwigow());
       selected.setKonserwacjaDomofonu(nowy.getKonserwacjaDomofonu());
       selected.setCo(nowy.getCo());
       selected.setCw(nowy.getCw());
       selected.setZwis(nowy.getZwis());
       selected.setPradWPomWspolnych(nowy.getPradWPomWspolnych());
       selected.setSmieci(nowy.getSmieci());
       selected.setUbezpieczenie(nowy.getUbezpieczenie());
       selected.setGaz(nowy.getGaz());
       selected.setDodatkowe_oplaty(nowy.getDodatkowe_oplaty());
       selected.setSuma(nowy.getSuma());
       selected.setZaplacono(nowy.getZaplacono());
       selected.setWynik(nowy.getZaplacono()-nowy.getSuma());
       persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle2").getString("PodsumowanieBudynkuUpdated"));
       }
       else
       {
       nowy.setId(getFacade().id());
       nowy.setMiesiac(miesiac);
       nowy.setRok(rok);
       selected=nowy;
       persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle2").getString("PodsumowanieBudynkuCreated"));
       }
       
      }
        
    }
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle2").getString("PodsumowanieBudynkuCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle2").getString("PodsumowanieBudynkuUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle2").getString("PodsumowanieBudynkuDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PodsumowanieBudynku> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle2").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle2").getString("PersistenceErrorOccured"));
            }
        }
    }

    public PodsumowanieBudynku getPodsumowanieBudynku(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PodsumowanieBudynku> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PodsumowanieBudynku> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PodsumowanieBudynku.class)
    public static class PodsumowanieBudynkuControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PodsumowanieBudynkuController controller = (PodsumowanieBudynkuController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "podsumowanieBudynkuController");
            return controller.getPodsumowanieBudynku(getKey(value));
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
            if (object instanceof PodsumowanieBudynku) {
                PodsumowanieBudynku o = (PodsumowanieBudynku) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PodsumowanieBudynku.class.getName()});
                return null;
            }
        }

    }

}
