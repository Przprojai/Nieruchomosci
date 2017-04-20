package JCP;

import Entity.Ksiegowosc;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.KsiegowoscFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("ksiegowoscController")
@SessionScoped
public class KsiegowoscController implements Serializable {

    @EJB
    private SBP.KsiegowoscFacade ejbFacade;
    private List<Ksiegowosc> items = null;
    private Ksiegowosc selected;
    private Boolean zalogowany=false;

    public KsiegowoscController() {
    }

    public Ksiegowosc getSelected() {
        return selected;
    }

    public void setSelected(Ksiegowosc selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private KsiegowoscFacade getFacade() {
        return ejbFacade;
    }

    public Ksiegowosc prepareCreate() {
        selected = new Ksiegowosc();
        initializeEmbeddableKey();
        return selected;
    }
    public String logowanie(String login, String haslo) {
        
        if (getFacade().login(login, haslo)!=null) {
            selected=getFacade().login(login, haslo);
            zalogowany = true;
            return "/ksiegowosc/zalogowany_ksiegowy.xhtml";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędny login lub hasło", "Błędny login lub hasło"));
           selected=getFacade().login(login, haslo);
           return "Login.xhtml";
        }}
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("KsiegowoscCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("KsiegowoscUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("KsiegowoscDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Ksiegowosc> getItems() {
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

    public Ksiegowosc getKsiegowosc(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Ksiegowosc> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Ksiegowosc> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Ksiegowosc.class)
    public static class KsiegowoscControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            KsiegowoscController controller = (KsiegowoscController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ksiegowoscController");
            return controller.getKsiegowosc(getKey(value));
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
            if (object instanceof Ksiegowosc) {
                Ksiegowosc o = (Ksiegowosc) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Ksiegowosc.class.getName()});
                return null;
            }
        }

    }

}
