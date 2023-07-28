package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.ClientDTO;
import com.example.aspblindajes.model.Client;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ClientDTOToClient implements Converter<ClientDTO, Client> {

    @Override
    public Client convert(ClientDTO source) {
        Client client = new Client();
        client.setId(source.getId());
        client.setName(source.getName());
        return client;
    }
}
