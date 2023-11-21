package com.example.aspblindajes.converters;

import com.example.aspblindajes.dto.BrandModelDTO;
import com.example.aspblindajes.exception.ResourceNotFoundException;
import com.example.aspblindajes.model.BrandModel;
import com.example.aspblindajes.model.WorkGroup;
import com.example.aspblindajes.service.BrandService;
import com.example.aspblindajes.service.WorkGroupsService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class BrandModelDTOToBrandModel implements Converter<BrandModelDTO, BrandModel> {

    private final BrandService brandService;
    private final WorkGroupsService workGroupsService;
    @Override
    public BrandModel convert(BrandModelDTO source) {
        BrandModel brandModel = new BrandModel();
        try {
            brandModel.setBrand(brandService.findBrandByName(source.getBrandName()));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        brandModel.setName(source.getName());
        brandModel.setId(source.getId());
        if (source.getWorkGroupList().size() > 0){
            List<WorkGroup> workGroupList = new ArrayList<>();
            for (String name: source.getWorkGroupList()) {
                try {
                    workGroupList.add(workGroupsService.findWorkGroupsByName(name));
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                }
            }
            brandModel.setWorkGroupsList(workGroupList);
        }

        return brandModel;
    }
}
