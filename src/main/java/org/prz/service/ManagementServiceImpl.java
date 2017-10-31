/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import org.prz.entity.SecretCode;
import org.prz.entity.Server;
import org.prz.repository.SecretCodeRepository;
import org.prz.repository.ServerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ola
 */
@Service
public class ManagementServiceImpl implements ManagementService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final SecretCodeRepository secretCodeRepository;

    @Autowired
    private final ServerRepository serverRepository;
    

    public ManagementServiceImpl(SecretCodeRepository secretCodeRepository,
            ServerRepository serverRepository) {
        this.secretCodeRepository = secretCodeRepository;
        this.serverRepository = serverRepository;
    }

    @Transactional
    @Override
    public SecretCode changeSecretCode(SecretCode codeFromForm) {
        logger.info("Zmina tajnego kodu.");
        SecretCode code = new SecretCode();
        code.setSecretCodeId(1);
        code.setCode(codeFromForm.getCode());
        code = secretCodeRepository.save(code);
        return code;
    }

    @Override
    public SecretCode getSecretCode() {
        return secretCodeRepository.findOne(1);
    }
    
    @Override
    public Server getServer() {
        return serverRepository.findOne(1);
    }
    
    @Transactional
    @Override
    public Server changeServerName(Server s) {
        Server newS = new Server();
        newS.setServerId(1);
        newS.setName(s.getName());
        newS = serverRepository.save(newS);
        return newS;
    }

}
