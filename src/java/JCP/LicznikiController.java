package JCP;

import Entity.Liczniki;
import Entity.Mieszkanie;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.LicznikiFacade;
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

@Named("licznikiController")
@SessionScoped
public class LicznikiController implements Serializable {

    @EJB
    private SBP.LicznikiFacade ejbFacade;
    private List<Liczniki> items = null;
    private Liczniki selected;
    static String csvFileLicznik=null;
    
    public LicznikiController() {
    }

    public Liczniki getSelected() {
        return selected;
    }
    public static String setCsvFileLicznik(String csvFileLicznik) {
        LicznikiController.csvFileLicznik = csvFileLicznik;
        return LicznikiController.csvFileLicznik;
    }
    public static String wyswietlsciezke2() {
        return csvFileLicznik;
    }
    public void setSelected(Liczniki selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LicznikiFacade getFacade() {
        return ejbFacade;
    }

    public Liczniki prepareCreate() {
        selected = new Liczniki();
        initializeEmbeddableKey();
        return selected;
    }
    public void liczniki() {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        String[] numer = new String[]{};
        List<Mieszkanie> lista_mieszkan = null;
        lista_mieszkan = getFacade().lista_mieszkan();
        Mieszkanie mieszkanie = new Mieszkanie();
    //    selected = null;
        try {

            br = new BufferedReader(new FileReader(csvFileLicznik));
            //br = csvFile;

            while ((line = br.readLine()) != null) {

                // use comma as separator
                //String[] country = line.split(cvsSplitBy);
                numer = line.split(cvsSplitBy);
                for (int i = 0; i < lista_mieszkan.size(); i++) {
                    mieszkanie = lista_mieszkan.get(i);
                   // selected = mieszkanie;
                   selected = new Liczniki();
                    if (mieszkanie.getId().toString().equals(numer[0])) {
                        if(getFacade().update(mieszkanie, Integer.parseInt(numer[4]), Integer.parseInt(numer[5]))==null){
                        selected.setId(getFacade().id());
                        selected.setIdMieszkania(mieszkanie);
                        selected.setLicznikWodyCieplej(Integer.parseInt(numer[1]));
                        selected.setLicznikWodyZimnej(Integer.parseInt(numer[2]));
                        selected.setLicznikCiepla(Integer.parseInt(numer[3]));
                        selected.setMiesiac(Integer.parseInt(numer[4]));
                        selected.setRok(Integer.parseInt(numer[5]));
                        persist(PersistAction.CREATE, "Dodano nowy stan licznika dla  "+selected.getIdMieszkania()+" Mieszkania");
                        }
                        else{
                        selected = getFacade().update(mieszkanie, Integer.parseInt(numer[4]), Integer.parseInt(numer[5]));
                        selected.setLicznikWodyCieplej(Integer.parseInt(numer[1]));
                        selected.setLicznikWodyZimnej(Integer.parseInt(numer[2]));
                        selected.setLicznikCiepla(Integer.parseInt(numer[3]));
                        persist(PersistAction.UPDATE, "Stan liczników dla mieszkania "+selected.getIdMieszkania()+" zaktualizowany");
                        }
                    }
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

        csvFileLicznik = null;
    }
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LicznikiCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LicznikiUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LicznikiDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Liczniki> getItems() {
       // if (items == null) {
            items = getFacade().findAll();
      //  }
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

    public Liczniki getLiczniki(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Liczniki> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Liczniki> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Liczniki.class)
    public static class LicznikiControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LicznikiController controller = (LicznikiController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "licznikiController");
            return controller.getLiczniki(getKey(value));
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
            if (object instanceof Liczniki) {
                Liczniki o = (Liczniki) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Liczniki.class.getName()});
                return null;
            }
        }

    }

}
