package JCP;

import Entity.Mieszkanie;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.MieszkanieFacade;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.Serializable;
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

@Named("mieszkanieController")
@SessionScoped
public class MieszkanieController implements Serializable {

    @EJB
    private SBP.MieszkanieFacade ejbFacade;
    private List<Mieszkanie> items = null;
    private Mieszkanie selected;
    static String csvFile=null;
    
    
    public MieszkanieController() {
    }

    public Mieszkanie getSelected() {
        return selected;
    }
   
    public static String wyswietlsciezke() {
        return csvFile;
    }
    public static String pobierzplik(String plik){
       MieszkanieController.csvFile=plik;
       return MieszkanieController.csvFile;
    }
    public static String asd(){
        return MieszkanieController.csvFile;
    }
    public void setSelected(Mieszkanie selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MieszkanieFacade getFacade() {
        return ejbFacade;
    }

    public Mieszkanie prepareCreate() {
        selected = new Mieszkanie();
        initializeEmbeddableKey();
        return selected;
    }
    public List<Mieszkanie> lista_mieszkan() {
        
        return getFacade().lista_mieszkan();
    }
 public void wyswietl() {
       // String csvFile = "C:\\Users\\Waldek\\Desktop\\magisterka\\b.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        String[] numer = new String[]{};
        List<Mieszkanie> lista_mieszkan = null;
        lista_mieszkan = getFacade().findAll();
        Mieszkanie mieszkanie = new Mieszkanie();
        
        try {

            br = new BufferedReader(new FileReader(csvFile));
            //br = csvFile;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                //String[] country = line.split(cvsSplitBy);
                numer = line.split(cvsSplitBy);
                for (int i =0;i<lista_mieszkan.size();i++){
                selected = lista_mieszkan.get(i);
                numer[0]=numer[0].replace("nr ", "");
                if (selected.getNrKonta().toString().equals(numer[0])) {selected.setStanKonta(Float.parseFloat(numer[1]));persist(PersistAction.UPDATE, "Stan konta dla mieszkania "+selected.getId()+" zaktualizowany");}
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
                csvFile=null;
                items=null;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MieszkanieCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MieszkanieUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MieszkanieDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    public String utworz_lokatora(Mieszkanie mieszkanie){
    mieszkanie.setZajetosc(true);
    selected=mieszkanie;
    persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MieszkanieUpdated"));
    return "/lokator/List_1.xhtml";
    }
    public List<Mieszkanie> getItems() {
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

    public Mieszkanie getMieszkanie(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Mieszkanie> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Mieszkanie> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Mieszkanie.class)
    public static class MieszkanieControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MieszkanieController controller = (MieszkanieController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mieszkanieController");
            return controller.getMieszkanie(getKey(value));
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
            if (object instanceof Mieszkanie) {
                Mieszkanie o = (Mieszkanie) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Mieszkanie.class.getName()});
                return null;
            }
        }

    }

}
