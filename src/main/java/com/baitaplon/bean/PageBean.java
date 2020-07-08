/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.bean;

import com.baitaplon.pojo.Hall;
import com.baitaplon.service.PageService;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Liem Nemo
 */
@ManagedBean
@Named(value = "pageBean")
@Dependent
public class PageBean {
    private final static PageService pageService = new PageService();
    /**
     * Creates a new instance of PageBean
     */
    public PageBean() {
    }
    public List<Hall> getHalls() {
        return pageService.getHalls();
    }
}
