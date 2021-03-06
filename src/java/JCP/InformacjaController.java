package JCP;

import Entity.Budynek;
import Entity.Informacja;
import Entity.Lokator;
import Entity.Mieszkanie;
import Entity.Oplaty;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.InformacjaFacade;
import java.awt.event.ActionEvent;

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
import org.primefaces.event.FlowEvent;

@Named("informacjaController")
@SessionScoped
public class InformacjaController implements Serializable {

    @EJB
    private SBP.InformacjaFacade ejbFacade;
    private List<Informacja> items = null;
    private Informacja selected;
    Budynek budynek = null;

    public InformacjaController() {
    }

    public Informacja getSelected() {
        return selected;
    }

    public void setSelected(Informacja selected) {
        this.selected = selected;
    }

    public Budynek getBudynek() {
        return budynek;
    }

    public void setBudynek(Budynek budynek) {
        this.budynek = budynek;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InformacjaFacade getFacade() {
        return ejbFacade;
    }

    public Informacja prepareCreate() {
        selected = new Informacja();
        initializeEmbeddableKey();
        return selected;
    }
    public void upomnienie(List<Oplaty> lista){
        Date data = new Date();
        Integer numer= getFacade().numer();
        for(int i=0;i<lista.size();i++){
            Informacja info = new Informacja();
            info.setNumer(numer);
            info.setId(getFacade().id());
            info.setData(data);
            info.setPotwierdzenie(false);
            info.setIdMieszkania(lista.get(i).getIdMieszkania());
            info.setTytul("Zaległości opłat za miesiąc "+lista.get(i).getMiesiac()+"/"+lista.get(i).getRok()+"r.");
            info.setOpis("Zaległości opłat w wysokości "+ (lista.get(i).getPodsumowanie().abs())+ "zł, za miesiąc "+lista.get(i).getMiesiac()+"/"+lista.get(i).getRok()+"r.");
            selected=getFacade().sprawdzTytul(info);
            if(selected!=null){
               // if(!getFacade().sprawdzOpis(info)){
               if(!selected.getOpis().equals(info.getOpis())){
                selected.setOpis(info.getOpis());
                selected.setPotwierdzenie(false);
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InformacjaUpdated"));
                }
            }
            else
            {
            selected=info;
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InformacjaUpdated"));
            }
        }
        items=null;
    }
        public String onFlowProcess(FlowEvent event) {
        if(budynek!=null)
        return event.getNewStep();
        else
            return "budynek";
    }
    public void create() {
        selected.setId(getFacade().id());
        selected.setNumer(getFacade().numer());
        Date data = new Date();
        selected.setData(data);
        selected.setPotwierdzenie(false);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InformacjaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    public String create2() {
        List<Mieszkanie> wynik=getFacade().mieszkania(budynek);
        int id=getFacade().id();
        int numer = getFacade().numer();
        Date data = new Date();
        for(int i=0;i<wynik.size();i++){
            Informacja pomoc = new Informacja();
            pomoc.setId(id);
            pomoc.setOpis(selected.getOpis());
            pomoc.setNumer(numer);
            pomoc.setPotwierdzenie(false);
            pomoc.setTytul(selected.getTytul());
            pomoc.setData(data);
            pomoc.setIdMieszkania(wynik.get(i));
            selected=pomoc;
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InformacjaCreated"));
            id+=1;
        }
            items = null;    // Invalidate list of items to trigger re-query.
        return "/informacja/List_2.xhtml";
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InformacjaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InformacjaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Informacja> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    public List<Informacja> informacje_lokatora(Lokator lokator){
        return getFacade().informacje_lokatora(lokator);
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

    public Informacja getInformacja(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Informacja> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Informacja> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Informacja.class)
    public static class InformacjaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InformacjaController controller = (InformacjaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "informacjaController");
            return controller.getInformacja(getKey(value));
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
            if (object instanceof Informacja) {
                Informacja o = (Informacja) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Informacja.class.getName()});
                return null;
            }
        }

    }

}
