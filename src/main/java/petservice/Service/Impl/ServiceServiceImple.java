package petservice.Service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import petservice.Service.ServiceService;
import petservice.mapping.ServiceMapping;
import petservice.model.Entity.ServiceEntity;
import petservice.model.payload.request.ServiceResources.InfoServiceRequest;
import petservice.repository.ServiceRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
//@RequiredArgsConstructor sẽ sinh ra một constructor với các tham số bắt buộc phải có giá trị. Các thuộc tính final và các thuộc tính được đánh dấu @NonNull sẽ bị bắt buộc phải chứa giá trị trong constructor.
@RequiredArgsConstructor
//Quy dinh la 1 transactional
@Transactional
//đang sử dụng Simple Logging Facade for Java
@Slf4j
public class ServiceServiceImple implements ServiceService {
    final ServiceRepository serviceRepository;

    @Override
    public List<ServiceEntity> getAllService(Pageable pageable) {
        return serviceRepository.findAllByIdNotNull(pageable);
    }

    @Override
    public ServiceEntity saveService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    @Override
    public ServiceEntity updateServiceInfo(ServiceEntity service, InfoServiceRequest serviceInfo) {
        service = ServiceMapping.UpdateServiceInfoByService(service, serviceInfo);
        return serviceRepository.save(service);
    }

    @Override
    public ServiceEntity getService(String name) {
        log.info("Fetching service {}",name);
        return serviceRepository.findByName(name).get();
    }

    @Override
    public List<ServiceEntity> getServices() {
        log.info("Fetching all services ");
        return serviceRepository.findAll();
    }

    @Override
    public Boolean existsByName(String name) {
        return serviceRepository.existsByName(name);
    }

    @Override
    public ServiceEntity findByName(String name) {
        return serviceRepository.findByName(name).get();
    }

    @Override
    public ServiceEntity findById(String id) {
        return serviceRepository.findById(id).get();
    }


    @Override
    public ServiceEntity setStatus(ServiceEntity service, Boolean status) {
        service.setStatus(status);
        return serviceRepository.save(service);
    }

    @Override
    public Integer deleteService(String name) {
        ServiceEntity service = serviceRepository.findByName(name).get();
        serviceRepository.deleteById(service.getId());
        return null;
    }
    @Override
    public void deleteSevice(String id) {
            serviceRepository.deleteById(id);
    }
    @Override
    public void deleteSevices(String[] ids) {
        for(String id: ids){
            deleteSevice(id);
        }
    }
}
