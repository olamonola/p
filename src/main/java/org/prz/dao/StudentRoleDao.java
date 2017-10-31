/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.dao;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.prz.entity.AccountRole;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Ola
 */
public class StudentRoleDao {

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date roleStartTime;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date roleEndTime;
    private Integer roleId;

    public StudentRoleDao() {
    }

    public StudentRoleDao(AccountRole ar) {
        if (ar != null) {
            this.roleEndTime = ar.getRoleEndTime();
            this.roleStartTime = ar.getRoleStartTime();
            this.roleId = ar.getRoleId().getRoleId();
        } else {
            this.roleStartTime = new Date();
            Calendar cal = Calendar.getInstance();
            if (cal.get(Calendar.MONTH) >= 0 && cal.get(Calendar.MONTH) < 9) {
                cal.set(cal.get(Calendar.YEAR) + 1, 1, 28);
            } else if (cal.get(Calendar.MONTH) >= 9 && cal.get(Calendar.MONTH) <= 11) {
                cal.set(cal.get(Calendar.YEAR) + 2, 1, 28);
            }

            this.roleEndTime = cal.getTime();
        }
    }

    public Date getRoleStartTime() {
        return roleStartTime;
    }

    public void setRoleStartTime(Date roleStartTime) {
        this.roleStartTime = roleStartTime;
    }

    public Date getRoleEndTime() {
        return roleEndTime;
    }

    public void setRoleEndTime(Date roleEndTime) {
        this.roleEndTime = roleEndTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
