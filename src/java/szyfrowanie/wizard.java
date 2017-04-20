/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szyfrowanie;

import Entity.Lokator;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Waldek
 */
@ManagedBean
@ViewScoped
public class wizard implements Serializable {
    private Lokator lokator = new Lokator();
    public void setLokator(Lokator lokator){
        this.lokator=lokator;
    }
    public Lokator getLokator(){
        return lokator;
    }
    boolean skip=false;
    boolean hasla;
    boolean login;
    public void koniec(){
        skip=true;
    }
    public void hasla(String haslo1, String haslo){
        if (haslo1==haslo)
            hasla= true;
        else
            hasla =false;
       
    }
   public void login(boolean pobierzlogin){
       login=pobierzlogin;
   }
  public String onFlowProcess(FlowEvent event) {
 // if(skip) {
       //     skip = false;   //reset in case user goes back
       //     return "confirm";
      //  }
      //  else {
      if(!login){
      if(hasla)
            return event.getNewStep();
      else
      {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hasła muszą się zgadzać", "Hasła muszą się zgadzać"));
          return "personal";
          
        }}
      else
      {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login zajęty", "Login zajęty"));
          return "personal";
      }
}
}
