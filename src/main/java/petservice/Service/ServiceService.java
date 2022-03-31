package petservice.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import petservice.model.Entity.ServiceEntity;
import petservice.model.payload.request.ServiceResources.InfoServiceRequest;

import java.util.List;

@Component
public interface ServiceService {
    Page<ServiceEntity> getAllService(Pageable pageable);
    ServiceEntity saveService(ServiceEntity service);
    ServiceEntity updateServiceInfo(ServiceEntity service, InfoServiceRequest serviceInfo);
    ServiceEntity getService(String name);
    List<ServiceEntity> getServices();
    Boolean existsByName(String name);
    ServiceEntity findByName(String name);
    List<ServiceEntity> findByNameContainning(String name);
    ServiceEntity findById(String id);
    ServiceEntity setStatus(ServiceEntity service,Boolean status);
    Integer deleteService(String name);
    void deleteSevice(String id);
    void deleteSevices(String[] ids);
}
