package petservice.mapping;

import petservice.model.Entity.ServiceEntity;

import java.math.BigInteger;

public class ServiceMapping {
    public static ServiceEntity ModelToEntity(AddServiceRequest registerRequest) {
        ServiceEntity newService = new ServiceEntity();
        newService.setName(registerRequest.getName());
        newService.setDescription(registerRequest.getDescription());

        if (registerRequest.getPrice().trim().equals("")){
            newService.setPrice(new BigInteger("0"));
        }
        else{
            newService.setPrice(new BigInteger(registerRequest.getPrice()));
        }

        if (registerRequest.getSlot().trim().equals("")){
            newService.setSlot(new BigInteger("0"));
        }
        else{
            newService.setSlot(new BigInteger(registerRequest.getSlot()));
        }

        if (registerRequest.getStatus().toString().trim().equals("")){
            newService.setStatus(true);
        }
        else {
            newService.setStatus(Boolean.parseBoolean(registerRequest.getStatus()));
        }
        return newService;
    }


    public static ServiceEntity UpdateServiceInfoByService(ServiceEntity service, InfoServiceRequest serviceInfo) {
        service.setName(serviceInfo.getName());
        service.setDescription(serviceInfo.getDescription());
        if (serviceInfo.getPrice().trim().equals("")){
            service.setPrice(new BigInteger("0"));
        }
        else{
            service.setPrice(new BigInteger(serviceInfo.getPrice()));
        }

        if (serviceInfo.getSlot().trim().equals("")){
            service.setSlot(new BigInteger("0"));
        }
        else{
            service.setSlot(new BigInteger(serviceInfo.getSlot()));
        }

        if (serviceInfo.getStatus().toString().trim().equals("")){
            service.setStatus(true);
        }
        else {
            service.setStatus(Boolean.parseBoolean(serviceInfo.getStatus()));
        }

        return service;
    }
}
