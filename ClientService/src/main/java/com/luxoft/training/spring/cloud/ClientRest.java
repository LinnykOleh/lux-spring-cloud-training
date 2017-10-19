package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping("/create")
    public ClientEntity create(@RequestParam("name") String name) {
        return clientDAO.create(name);
    }

    @RequestMapping("/update/{id}")
    public ResponseEntity<Object> update(@RequestParam("name") String name, @PathVariable("id") Integer id) {
        boolean update = clientDAO.update(id, name);
        if (update) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        clientRepository.delete(id);
    }

    @RequestMapping(name = "/get")
    public List<ClientEntity> get() {
        return clientRepository.findAll();
    }

    @RequestMapping("/get/{id}")
    public void get(@PathVariable("id") Integer id) {
        clientRepository.findOne(id);
    }
}
