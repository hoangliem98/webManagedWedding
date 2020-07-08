/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Liem Nemo
 */
@FacesValidator("UploadImageValidator")
public class UploadImageValidator implements Validator{
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException{
        Part p = (Part) value;
        if(p.getSize() > 5242880) {
            FacesMessage msg = new FacesMessage("Vui lòng chọn ảnh có dung lượng nhỏ hơn 5MB");
            throw new ValidatorException(msg);
        }
    }  
}
