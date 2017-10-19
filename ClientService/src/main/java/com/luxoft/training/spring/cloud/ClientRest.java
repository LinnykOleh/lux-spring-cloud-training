package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientRest {
    private final ClientDAO clientDAO;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientRest(ClientDAO clientDAO, ClientRepository clientRepository) {
        this.clientDAO = clientDAO;
        this.clientRepository = clientRepository;
    }

    @RequestMapping(name = "/create")
    public ClientEntity create(@RequestParam("name") String name) {
        return clientDAO.create(name);
    }

    @RequestMapping(name = "/update/{id}")
    public HttpStatus update(@RequestParam("name") String name, @PathVariable("id") Integer id) {
        boolean update = clientDAO.update(id, name);
        if (update) {
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @RequestMapping(name = "/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        clientRepository.delete(id);
    }

    @RequestMapping(name = "/get")
    public List<ClientEntity> get() {
        return clientRepository.findAll();
    }

    @RequestMapping(name = "/get/{id}")
    public void get(@PathVariable("id") Integer id) {
        clientRepository.findOne(id);
    }
}
