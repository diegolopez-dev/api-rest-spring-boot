package com.diegodev.api_rest.controller;

import com.diegodev.api_rest.model.dto.ClienteDto;
import com.diegodev.api_rest.model.entity.Cliente;
import com.diegodev.api_rest.model.payload.MensajeResponse;
import com.diegodev.api_rest.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private IClienteService clienteServivice;

    @GetMapping("clientes")
    public ResponseEntity<?> showAll() {
        List<Cliente> getList = clienteServivice.listAll();
        if (getList == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros")
                            .object(null)
                            .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(getList)
                        .build(), HttpStatus.OK);
    }

    @PostMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto) {
        Cliente clienteSave = null;
        try {
            clienteSave = clienteServivice.save(clienteDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(clienteDto.builder()
                        .idCliente(clienteSave.getIdCliente())
                        .nombre(clienteSave.getNombre())
                        .apellido(clienteSave.getApellido())
                        .correo(clienteSave.getCorreo())
                        .fechaRegistro(clienteSave.getFechaRegistro())
                        .build())
                    .build(), HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ClienteDto clienteDto) {
        Cliente clienteUpdate = null;
        try {
            if (clienteServivice.existsById(id)) {
                clienteDto.setIdCliente(id);
                clienteUpdate = clienteServivice.save(clienteDto);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Guardado correctamente")
                        .object(ClienteDto.builder()
                                .idCliente(clienteUpdate.getIdCliente())
                                .nombre(clienteUpdate.getNombre())
                                .apellido(clienteUpdate.getApellido())
                                .correo(clienteUpdate.getCorreo())
                                .fechaRegistro(clienteUpdate.getFechaRegistro())
                                .build())
                        .build(), HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El registro no se encuentra en la BBDD")
                                .object(null)
                                .build(), HttpStatus.NOT_FOUND);
            }

        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Cliente clienteDelete = clienteServivice.findById(id);
            clienteServivice.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                        .mensaje(exDt.getMessage())
                        .object(null)
                        .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Cliente cliente = clienteServivice.findById(id);
        if (cliente == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro no existe")
                            .object(null)
                            .build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(ClienteDto.builder()
                                .idCliente(cliente.getIdCliente())
                                .nombre(cliente.getNombre())
                                .apellido(cliente.getApellido())
                                .correo(cliente.getCorreo())
                                .fechaRegistro(cliente.getFechaRegistro())
                                .build())
                        .build(), HttpStatus.OK);
    }

}
