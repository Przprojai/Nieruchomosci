package JCP;

import Entity.Budynek;
import Entity.DodatkoweOplaty;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.DodatkoweOplatyFacade;

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

@Named("dodatkoweOplatyController")
@SessionScoped
public class DodatkoweOplatyController implements Serializable {

    @EJB
    private SBP.DodatkoweOplatyFacade ejbFacade;
    private List<DodatkoweOplaty> items = null;
    private DodatkoweOplaty selected;
    Budynek budynek = null;
    String miesiac="";



    public DodatkoweOplatyController() {
    }

    public DodatkoweOplaty getSelected() {
        return selected;
    }

    public void setSelected(DodatkoweOplaty selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DodatkoweOplatyFacade getFacade() {
        return ejbFacade;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }
    
    public List<String> klatka(){
        List<String> list = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        list.add("Wszystkie");
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("G");
        for(int i=0;i<budynek.getLiczbaKlatek()+1;i++)list2.add(list.get(i));
        return list2;
        
    }
    public DodatkoweOplaty prepareCreate() {
        selected = new DodatkoweOplaty();
        initializeEmbeddableKey();
        return selected;
    }
    public String onFlowProcess(FlowEvent event) {
        if(budynek!=null)
        return event.getNewStep();
        else
            return "budynek";
    }
    public Budynek getBudynek() {
        return budynek;
    }

    public void setBudynek(Budynek budynek) {
        this.budynek = budynek;
    }
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DodatkoweOplatyCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
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
        public String create2() {
        selected.setId(getFacade().id());
        selected.setIdBudynku(budynek);
        selected.setMiesiac(zamienMiesiac(miesiac));
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DodatkoweOplatyCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        return "/dodatkoweOplaty/List.xhtml";
    }
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DodatkoweOplatyUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DodatkoweOplatyDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DodatkoweOplaty> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public DodatkoweOplaty getDodatkoweOplaty(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<DodatkoweOplaty> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<DodatkoweOplaty> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = DodatkoweOplaty.class)
    public static class DodatkoweOplatyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DodatkoweOplatyController controller = (DodatkoweOplatyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dodatkoweOplatyController");
            return controller.getDodatkoweOplaty(getKey(value));
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
            if (object instanceof DodatkoweOplaty) {
                DodatkoweOplaty o = (DodatkoweOplaty) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DodatkoweOplaty.class.getName()});
                return null;
            }
        }

    }

}
