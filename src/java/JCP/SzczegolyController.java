package JCP;

import Entity.Lokator;
import Entity.Oplaty;
import Entity.Szczegoly;
import JCP.util.JsfUtil;
import JCP.util.JsfUtil.PersistAction;
import SBP.SzczegolyFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.HashMap;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named("szczegolyController")
@SessionScoped
public class SzczegolyController implements Serializable {

    private static Szczegoly szczegoly;
    ;
    @EJB
    private SBP.SzczegolyFacade ejbFacade;
    private List<Szczegoly> items = null;
    private Szczegoly selected;
    JasperPrint jasperPrint;
    static List<Szczegoly> szczegoly_lista = null;
            
    public SzczegolyController() {
    }

    public Szczegoly getSelected() {
        return selected;
    }

    public void setSelected(Szczegoly selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SzczegolyFacade getFacade() {
        return ejbFacade;
    }

    public Szczegoly prepareCreate() {
        selected = new Szczegoly();
        initializeEmbeddableKey();
        return selected;
    }
    public void pokaz_szczegoly(Oplaty oplaty){
       // selected= new Szczegoly();
       
        selected=getFacade().zwroc(oplaty);
        //return selected;
    }
    public void utworz(List<Szczegoly> lista){
        for(int i=0;i<lista.size();i++){
            selected = lista.get(i);
            
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SzczegolyUpdated"));
        }
    }

    public static List<Szczegoly> getSzczegoly_lista() {
        return szczegoly_lista;
    }

    public static void setSzczegoly_lista(List<Szczegoly> szczegoly_lista) {
        SzczegolyController.szczegoly_lista = szczegoly_lista;
    }

        public void init(Szczegoly szczegoly) throws JRException{

        HashMap hm = new HashMap();
        selected=getItems().get(0);
        hm.put("przekaz_id", 1);
        hm.put("miesiac",szczegoly.getIdOplaty().getMiesiac());
        hm.put("rok",szczegoly.getIdOplaty().getRok());
        hm.put("konto",szczegoly.getIdOplaty().getIdMieszkania().getNrKonta());
        Lokator lokator=getFacade().lokator(szczegoly.getIdOplaty().getIdMieszkania());
        hm.put("imie",lokator.getImie()+" "+lokator.getNazwisko());
        //hm.put("nazwisko",);
        hm.put("mieszkanie",szczegoly.getIdOplaty().getIdMieszkania().getIdBudynku().getAdres()+"/"+szczegoly.getIdOplaty().getIdMieszkania().getNrMieszkania());
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(getItems());
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/raport/report1.jasper");
        jasperPrint = JasperFillManager.fillReport(reportPath, hm, new JRBeanArrayDataSource(new Szczegoly [] {szczegoly}));
}
    public void PDF(Szczegoly szczegoly) throws JRException, IOException{
        init(szczegoly);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("ASD", "attachment; filename=report.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public static List<Szczegoly> setSzczegoly(List<Szczegoly> szczegoly) {
        SzczegolyController.szczegoly_lista = szczegoly;
        return SzczegolyController.szczegoly_lista;
        
    }
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SzczegolyCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SzczegolyUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SzczegolyDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Szczegoly> getItems() {
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

    public Szczegoly getSzczegoly(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Szczegoly> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Szczegoly> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Szczegoly.class)
    public static class SzczegolyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SzczegolyController controller = (SzczegolyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "szczegolyController");
            return controller.getSzczegoly(getKey(value));
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
            if (object instanceof Szczegoly) {
                Szczegoly o = (Szczegoly) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Szczegoly.class.getName()});
                return null;
            }
        }

    }

}
