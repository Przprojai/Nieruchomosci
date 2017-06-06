package JCP;

import Entity.Budynek;
import Entity.LicznikiBudynku;
import Entity.Mieszkanie;
import Entity.Stawki;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.MieszkanieFacade;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Named("mieszkanieController")
@SessionScoped
public class MieszkanieController implements Serializable {

    @EJB
    private SBP.MieszkanieFacade ejbFacade;
    private List<Mieszkanie> items = null;
    private Mieszkanie selected;
    static String csvFile = null;
    BigDecimal prad2 = new BigDecimal(0);
    BigDecimal gaz2 = new BigDecimal(0);
    BigDecimal woda2 = new BigDecimal(0);
    BigDecimal co2 = new BigDecimal(0);

    public MieszkanieController() {
    }

    public Mieszkanie getSelected() {
        return selected;
    }

    public static String wyswietlsciezke() {
        return csvFile;
    }

    public BigDecimal getPrad2() {
        return prad2;
    }

    public BigDecimal getWoda2() {
        return woda2;
    }

    public void setWoda2(BigDecimal woda2) {
        this.woda2 = woda2;
    }

    public void setPrad2(BigDecimal prad2) {
        this.prad2 = prad2;
    }

    public BigDecimal getGaz2() {
        return gaz2;
    }

    public void setGaz2(BigDecimal gaz2) {
        this.gaz2 = gaz2;
    }

    public BigDecimal getCo2() {
        return co2;
    }

    public void setCo2(BigDecimal co2) {
        this.co2 = co2;
    }

    public static String pobierzplik(String plik) {
        MieszkanieController.csvFile = plik;
        return MieszkanieController.csvFile;
    }

    public static String asd() {
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
                for (int i = 0; i < lista_mieszkan.size(); i++) {
                    selected = lista_mieszkan.get(i);
                    numer[0] = numer[0].replace("nr ", "");
                    if (selected.getNrKonta().toString().equals(numer[0])) {
                        selected.setStanKonta(BigDecimal.valueOf(Double.parseDouble(numer[1])));
                        persist(PersistAction.UPDATE, "Stan konta dla mieszkania " + selected.getId() + " zaktualizowany");
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
        csvFile = null;
        items = null;
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

    public String utworz_lokatora(Mieszkanie mieszkanie) {
        mieszkanie.setZajetosc(true);
        selected = mieszkanie;
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MieszkanieUpdated"));
        return "/lokator/List_1.xhtml";
    }

    public List<Mieszkanie> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void rozlicz() {

        List<Budynek> lista = new ArrayList<Budynek>();
        BigDecimal pomoc = new BigDecimal(0);
        BigDecimal wynik = new BigDecimal(0);
        BigDecimal pradwpomwsp = new BigDecimal(0);
        BigDecimal gazcalk = new BigDecimal(0);
        BigDecimal cocalk = new BigDecimal(0);
        BigDecimal powierzchnia = new BigDecimal(0);
        lista = getFacade().budynki();
        Budynek budynek = new Budynek();
        for (int i = 0; i < lista.size(); i++) {

            budynek = lista.get(i);
            Date data = new Date();
            int rok=data.getYear()+1900;
            Stawki stawki = getFacade().stawki(budynek);
            List<LicznikiBudynku> liczniki_budynku = new ArrayList<LicznikiBudynku>();
            liczniki_budynku=getFacade().liczniki_budynku(budynek);
            List<Mieszkanie> list = getFacade().aktywne(lista.get(i));
            for (int j = 0; j < list.size(); j++) {
                selected = list.get(j);
                pradwpomwsp=getFacade().prad(selected, rok);
                for(int q=0;q<liczniki_budynku.size();q++){
                    pomoc=pomoc.add(new BigDecimal(liczniki_budynku.get(q).getPrad()).multiply(liczniki_budynku.get(q).getStawkaPradu()).divide(new BigDecimal(getFacade().aktywne(budynek).size()).multiply(new BigDecimal (selected.getLiczbaOsob())),2));
                }
               
                wynik = wynik.add(pomoc);
                wynik = wynik.subtract(pradwpomwsp);
                pomoc = new BigDecimal(0);
                for(int w=0;w<liczniki_budynku.size();w++){
                    pomoc=pomoc.add(new BigDecimal(liczniki_budynku.get(w).getGaz()).multiply(liczniki_budynku.get(w).getStawkaGazu()).divide(new BigDecimal(getFacade().aktywne(budynek).size()).multiply(new BigDecimal (selected.getLiczbaOsob())),2));
                }
                wynik = wynik.add(pomoc);
                if(budynek.getWspolnyLicznikGazu())gazcalk = getFacade().gaz(selected, rok);
                wynik = wynik.subtract(gazcalk);
                pomoc = new BigDecimal(0);
                for(int w=0;w<liczniki_budynku.size();w++){
                    pomoc=pomoc.add(new BigDecimal(liczniki_budynku.get(w).getWoda()).multiply(liczniki_budynku.get(w).getStawkaWody()).divide(new BigDecimal(getFacade().aktywne(budynek).size()).multiply(new BigDecimal (selected.getLiczbaOsob())),2));
                }
                wynik = wynik.add(pomoc);
                pomoc = new BigDecimal(0);
                if(budynek.getWspolnyLicznik())cocalk=getFacade().co(selected, rok);
                powierzchnia=getFacade().powierzchnia(budynek);
                for(int w=0;w<liczniki_budynku.size();w++){
                    pomoc=pomoc.add(new BigDecimal(liczniki_budynku.get(w).getCo()).multiply(liczniki_budynku.get(w).getStawkaCo()).divide(powierzchnia,2).multiply(selected.getPowierzchnia()));
                }
                //pomoc = pomoc.add(co);
                //pomoc = pomoc.multiply(new BigDecimal(budynek.getCo()));
                wynik = wynik.add(pomoc);
                wynik = wynik.subtract(cocalk);
                selected.setNadplata(wynik);
                update();
                wynik = new BigDecimal(0);
            }

        }
        //return "/mieszkanie/List_3.xhtml";
        items=null;
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
