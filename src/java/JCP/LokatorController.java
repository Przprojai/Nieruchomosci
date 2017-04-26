package JCP;

import Entity.Lokator;
import Entity.Mieszkanie;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.LokatorFacade;

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
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import szyfrowanie.CryptWithSHA256;

@Named("lokatorController")
@SessionScoped
public class LokatorController implements Serializable {
    
    @EJB
    private SBP.LokatorFacade ejbFacade;
    private List<Lokator> items = null;
    private Lokator selected;
    List<Mieszkanie> lista_mieszkan = null;
    Mieszkanie mieszkanie = null;
    Boolean zalogowany = false;
    Boolean flaga = false;
    FlowEvent ivent = null;
    
    public LokatorController() {
    }
    
    public Lokator getSelected() {
        return selected;
    }
    
    public void setSelected(Lokator selected) {
        this.selected = selected;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    private LokatorFacade getFacade() {
        return ejbFacade;
    }

    public Boolean SprawdzHaslo(String log) {
        return getFacade().sprawdzHaslo(log);
    }

    public String wylogujLokator() {
        
        selected = null;
        items = null;
        zalogowany = false;
        return "/index.xhtml?faces-redirect=true";
    }

    public String logowanie(String login, String haslo) {
        
        if (getFacade().login(login, haslo) != null) {
            selected = getFacade().login(login, haslo);
            zalogowany = true;
            return "/lokator/zalogowany_lokator.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędny login lub hasło", "Błędny login lub hasło"));
            selected = getFacade().login(login, haslo);
            return "Login.xhtml";
        }
    }
    public String wiadomosci(Lokator lokator){
        
        return " Masz "+getFacade().wiadomosci(lokator)+" nowych wiadomości";
    }
    public List<Lokator> lista_hasel(List<Lokator> lista){
        return getFacade().sprawdzHaslo2(lista);
    }
    public Lokator przekaz() {
        return selected;
    }

    public Lokator prepareCreate() {
        selected = new Lokator();
        initializeEmbeddableKey();
        return selected;
    }
    
    public Lokator prepareCreate3() {
        selected = new Lokator();
        selected.setLogin("Lokator" + getFacade().id());
        initializeEmbeddableKey();
        return selected;
    }    
    
    public Mieszkanie getMieszkanie() {
        return mieszkanie;
    }
    
    public void setMieszkanie(Mieszkanie mieszkanie) {
        this.mieszkanie = mieszkanie;
    }

    public FlowEvent dupa() {
        return ivent;
    }

    public List<Mieszkanie> lista_mieszkan() {
        return getFacade().mieszkania();
    }

    public String onFlowProcess(FlowEvent event) {
        
        if (selected.getLogin().length() < 6) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login musi zawierać conajmniej 6 znaków", "Login musi zawierać conajmniej 6 znaków"));
            return "personal";
        } else {
            if (getFacade().sprawdzLogin(selected.getLogin())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login zajęty", "Login zajęty"));
                return "personal";
            } else {
                if (event.getOldStep().contains("mieszkanie")) {
                    if (selected.getIdMieszkania() == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wybierz mieszkanie", "Wybierz mieszkanie"));
                        return "mieszkanie";
                    } else {
                        return event.getNewStep();
                    }
                } else {
                    return event.getNewStep();
                }
            }
        }
        
    }
    
    public void prepareCreate2() {
        selected = new Lokator();
        initializeEmbeddableKey();
    }
    
    public Mieszkanie create3(Mieszkanie mieszkanie2) {
        selected.setHaslo(selected.getLogin());
        selected.setHaslo(CryptWithSHA256.sha256(selected.getHaslo()));
        selected.setId(getFacade().id());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LokatorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            
            items = null;    // Invalidate list of items to trigger re-query.
            return mieszkanie2;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Błąd"));
            return null;
        }
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LokatorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LokatorUpdated"));
    }

    public void zmiana_hasla(Lokator lokator, String haslo, String haslo2) {
        if (haslo.length() >= 6) {
            if (!lokator.getLogin().equals(haslo)) {
                if (haslo.equals(haslo2)) {
                    selected = lokator;
                    selected.setHaslo(CryptWithSHA256.sha256(haslo));
                    persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LokatorUpdated"));                    
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("PF('LokatorEditDialog').hide();");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła muszą się zgadzać", "Hasła muszą się zgadzać"));
                    
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasło musi być różne od loginu", "Hasło musi być różne od loginu"));
            }            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Długość hasła musi być nie mniejsza niż 6 znaków", "Długość hasła musi być nie mniejsza niż 6 znaków"));
        }
        
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LokatorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public List<Lokator> getItems() {
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
    
    public Lokator getLokator(java.lang.Integer id) {
        return getFacade().find(id);
    }
    
    public List<Lokator> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }
    
    public List<Lokator> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    @FacesConverter(forClass = Lokator.class)
    public static class LokatorControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LokatorController controller = (LokatorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lokatorController");
            return controller.getLokator(getKey(value));
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
            if (object instanceof Lokator) {
                Lokator o = (Lokator) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Lokator.class.getName()});
                return null;
            }
        }
        
    }
    
}
