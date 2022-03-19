package petservice.mapping;

import petservice.model.Entity.ServiceEntity;
import petservice.model.payload.request.ServiceResources.AddServiceRequest;
import petservice.model.payload.request.ServiceResources.InfoServiceRequest;


public class ServiceMapping {
    public static ServiceEntity ModelToEntity(AddServiceRequest registerRequest) {
        ServiceEntity newService = new ServiceEntity();
        newService.setName(registerRequest.getName());
        newService.setDescription(registerRequest.getDescription());
        newService.setPrice(registerRequest.getPrice());
        newService.setSlot(registerRequest.getSlot());
        if (registerRequest.getStatus() == null) {
            newService.setStatus(false);
        }
        else{
            newService.setStatus(registerRequest.getStatus());
        }
        return newService;
    }


    public static ServiceEntity UpdateServiceInfoByService(ServiceEntity service, InfoServiceRequest serviceInfo) {
        service.setName(serviceInfo.getName());
        service.setDescription(serviceInfo.getDescription());
        service.setPrice(serviceInfo.getPrice());
        service.setSlot(serviceInfo.getSlot());
        service.setStatus(serviceInfo.getStatus());
        return service;
    }
}
