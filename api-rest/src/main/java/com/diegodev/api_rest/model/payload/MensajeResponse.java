package com.diegodev.api_rest.model.payload;

import com.diegodev.api_rest.model.dto.ClienteDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@ToString
@Builder
public class MensajeResponse implements Serializable {

    private String mensaje;
    private Object object;

}
