/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import org.prz.entity.SecretCode;
import org.prz.entity.Server;

/**
 *
 * @author Ola
 */
public interface ManagementService {
    
    public SecretCode changeSecretCode(SecretCode codeFromForm);
    
    public SecretCode getSecretCode();
    
    public Server getServer();
    
    public Server changeServerName(Server s);
      
}
