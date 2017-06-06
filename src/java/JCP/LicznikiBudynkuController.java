package JCP;

import Entity.Budynek;
import Entity.LicznikiBudynku;
import Entity.Stawki;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.LicznikiBudynkuFacade;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Named("licznikiBudynkuController")
@SessionScoped
public class LicznikiBudynkuController implements Serializable {

    @EJB
    private SBP.LicznikiBudynkuFacade ejbFacade;
    private List<LicznikiBudynku> items = null;
    private LicznikiBudynku selected;
    static String csvFileLicznik=null;
    static String csvFileLicznik2=null;

    public LicznikiBudynkuController() {
    }

    public LicznikiBudynku getSelected() {
        return selected;
    }
    public static String setCsvFileLicznik(String csvFileLicznik) {
        LicznikiBudynkuController.csvFileLicznik = csvFileLicznik;
        return LicznikiBudynkuController.csvFileLicznik;
    }
    public void setSelected(LicznikiBudynku selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LicznikiBudynkuFacade getFacade() {
        return ejbFacade;
    }

    public LicznikiBudynku prepareCreate() {
        selected = new LicznikiBudynku();
        initializeEmbeddableKey();
        return selected;
    }
    public void liczniki(){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        String[] numer = new String[]{};
        List<Budynek> lista_budynkow = getFacade().budynki();
        //lista_budynkow = getFacade().findAll();
        //Mieszkanie mieszkanie = new Mieszkanie();
        LicznikiBudynku liczniki = new LicznikiBudynku();
    //    selected = null;
        try {

            br = new BufferedReader(new FileReader(csvFileLicznik));
            //br = csvFile;

            while ((line = br.readLine()) != null) {

                // use comma as separator
                //String[] country = line.split(cvsSplitBy);
                numer = line.split(cvsSplitBy);
                for (int i = 0; i < lista_budynkow.size(); i++) {
                    selected = new LicznikiBudynku();
                   // selected = mieszkanie;
                  // selected = new Budynek();
                  Budynek budynek = new Budynek();
                  budynek=lista_budynkow.get(i);
                    if (budynek.getId().toString().equals(numer[0])) {
                        if(getFacade().update(budynek, Integer.parseInt(numer[5]), Integer.parseInt(numer[6]))==null)
                        {
                        selected.setId(getFacade().id());
                        selected.setGaz(Integer.parseInt(numer[1]));
                        selected.setPrad(Integer.parseInt(numer[2]));
                        selected.setWoda(Integer.parseInt(numer[3]));
                        if(lista_budynkow.get(i).getWspolnyLicznik())
                        selected.setCo(Integer.parseInt(numer[4]));
                        else
                        selected.setCo(0);    
                        selected.setMiesiac(Integer.parseInt(numer[5]));
                        selected.setRok(Integer.parseInt(numer[6]));
                        Stawki stawki = getFacade().stawki(budynek);
                        selected.setStawkaCo(stawki.getCo());
                        selected.setStawkaGazu(stawki.getGaz());
                        selected.setStawkaPradu(stawki.getPradWPomWspolnych());
                        selected.setStawkaWody(new BigDecimal(3));
                        selected.setIdBudynku(budynek);
                        persist(PersistAction.UPDATE, "Stan liczników dla budynku "+selected.getId()+" zaktualizowany");
                        }
                        else
                        {
                            selected=getFacade().update(budynek, Integer.parseInt(numer[5]), Integer.parseInt(numer[6]));
                             selected.setGaz(Integer.parseInt(numer[1]));
                        selected.setPrad(Integer.parseInt(numer[2]));
                        selected.setWoda(Integer.parseInt(numer[3]));
                        if(lista_budynkow.get(i).getWspolnyLicznik())
                        selected.setCo(Integer.parseInt(numer[4]));
                        else
                        selected.setCo(0);    
                        Stawki stawki = getFacade().stawki(budynek);
                        selected.setStawkaCo(stawki.getCo());
                        selected.setStawkaGazu(stawki.getGaz());
                        selected.setStawkaPradu(stawki.getPradWPomWspolnych());
                        selected.setStawkaWody(new BigDecimal(3));
                        persist(PersistAction.UPDATE, "Stan liczników dla budynku "+selected.getId()+" zaktualizowany");
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
        items=null;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LicznikiBudynkuCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LicznikiBudynkuUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LicznikiBudynkuDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<LicznikiBudynku> getItems() {
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

    public LicznikiBudynku getLicznikiBudynku(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<LicznikiBudynku> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<LicznikiBudynku> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = LicznikiBudynku.class)
    public static class LicznikiBudynkuControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LicznikiBudynkuController controller = (LicznikiBudynkuController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "licznikiBudynkuController");
            return controller.getLicznikiBudynku(getKey(value));
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
            if (object instanceof LicznikiBudynku) {
                LicznikiBudynku o = (LicznikiBudynku) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), LicznikiBudynku.class.getName()});
                return null;
            }
        }

    }

}
