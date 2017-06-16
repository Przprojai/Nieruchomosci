package JCP;

import Entity.Budynek;
import Entity.Liczniki;
import Entity.Mieszkanie;
import static JCP.LicznikiController.csvFileLicznik;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.BudynekFacade;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import java.io.Serializable;
import java.util.ArrayList;
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


@Named("budynekController")
@SessionScoped
public class BudynekController implements Serializable {


    @EJB private SBP.BudynekFacade ejbFacade;
    private List<Budynek> items = null;
    private Budynek selected;
    static String csvFileLicznik=null;


    public BudynekController() {
    }

    public Budynek getSelected() {
        return selected;
    }
    public static String setCsvFileLicznik(String csvFileLicznik) {
        BudynekController.csvFileLicznik = csvFileLicznik;
        return LicznikiController.csvFileLicznik;
    }
    public void setSelected(Budynek selected) {
        this.selected = selected;
    }


    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BudynekFacade getFacade() {
        return ejbFacade;
    }

    public Budynek prepareCreate() {
        selected = new Budynek();
        initializeEmbeddableKey();
        return selected;
    }
//    public void liczniki() {
//        BufferedReader br = null;
//        String line = "";
//        String cvsSplitBy = ";";
//        String[] numer = new String[]{};
//        List<Budynek> lista_budynkow = null;
//        lista_budynkow = getFacade().findAll();
//        //Mieszkanie mieszkanie = new Mieszkanie();
//        Budynek budynek = new Budynek();
//    //    selected = null;
//        try {
//
//            br = new BufferedReader(new FileReader(csvFileLicznik));
//            //br = csvFile;
//
//            while ((line = br.readLine()) != null) {
//
//                // use comma as separator
//                //String[] country = line.split(cvsSplitBy);
//                numer = line.split(cvsSplitBy);
//                for (int i = 0; i < lista_budynkow.size(); i++) {
//                    selected = lista_budynkow.get(i);
//                   // selected = mieszkanie;
//                  // selected = new Budynek();
//                    if (selected.getId().toString().equals(numer[0])) {
//                        {
//                        selected.setGaz(Integer.parseInt(numer[1]));
//                        selected.setPrad(Integer.parseInt(numer[2]));
//                        selected.setWoda(Integer.parseInt(numer[3]));
//                        selected.setCo(Integer.parseInt(numer[4]));
//                        persist(PersistAction.UPDATE, "Stan liczników dla budynku "+selected.getId()+" zaktualizowany");
//                        }
//                    }
//                }
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        csvFileLicznik = null;
//        items=null;
//    }
//    public void rozlicz(BigDecimal prad, BigDecimal gaz, BigDecimal woda, Integer co){
//
//        List<Budynek> lista = new ArrayList<Budynek>();
//        lista = getFacade().findAll();
//        for (int i=0;i<lista.size();i++){
//            
//        }
//        
//    }
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BudynekCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BudynekUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BudynekDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Budynek> getItems() {
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

    public Budynek getBudynek(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Budynek> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Budynek> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass=Budynek.class)
    public static class BudynekControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BudynekController controller = (BudynekController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "budynekController");
            return controller.getBudynek(getKey(value));
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
            if (object instanceof Budynek) {
                Budynek o = (Budynek) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Budynek.class.getName()});
                return null;
            }
        }

    }

}
