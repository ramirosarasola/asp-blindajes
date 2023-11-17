package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.DestinationDTO;
import com.example.aspblindajes.model.Destination;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DestinationDTOToDestination implements Converter<DestinationDTO, Destination> {
    @Override
    public Destination convert(DestinationDTO source) {
        Destination destination = new Destination();
        destination.setId(source.getId());
        destination.setName(source.getName());
        return destination;
    }
}
