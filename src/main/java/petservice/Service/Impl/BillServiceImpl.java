package petservice.Service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import petservice.Service.BillService;
import petservice.Service.PetService;
import petservice.mapping.BillMapping;
import petservice.model.Entity.BillEntity;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BillResources.InfoBillRequest;
import petservice.repository.BillRepository;
import petservice.repository.PetRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
//@RequiredArgsConstructor sẽ sinh ra một constructor với các tham số bắt buộc phải có giá trị. Các thuộc tính final và các thuộc tính được đánh dấu @NonNull sẽ bị bắt buộc phải chứa giá trị trong constructor.
@RequiredArgsConstructor
//Quy dinh la 1 transactional
@Transactional
//đang sử dụng Simple Logging Facade for Java
@Slf4j
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepository billRepository;

    @Autowired
    BillMapping billMapping;

    @Autowired
    PetService petService;

    @Override
    public Page<BillEntity> getAllBills(Pageable pageable) {
        return billRepository.findAllByIdNotNull(pageable);
    }

    @Override
    public Page<BillEntity> getAllByUser(UserEntity user, Pageable pageable) {
        if (billRepository.findAllByUserBuyPet(user, pageable).isEmpty()) {
            return null;
        } else {
            return billRepository.findAllByUserBuyPet(user, pageable);
        }
    }

    @Override
    public BillEntity getByPet(PetEntity pet) {
        if (billRepository.findByPetSale(pet).isEmpty()) {
            return null;
        } else {
            return billRepository.findByPetSale(pet).get();
        }
    }

    @Override
    public Page<BillEntity> getAllByPaymentDate(LocalDateTime time, Pageable pageable) {
        if (billRepository.findAllByPaymentDate(time, pageable).isEmpty()) {
            return null;
        } else {
            return billRepository.findAllByPaymentDate(time, pageable);
        }
    }

    @Override
    public Page<BillEntity> getAllByUserBuyPetAndPaymentDate(UserEntity user, LocalDateTime time, Pageable pageable) {
        if (billRepository.findAllByUserBuyPetAndPaymentDate(user, time, pageable).isEmpty()) {
            return null;
        } else {
            return billRepository.findAllByUserBuyPetAndPaymentDate(user, time, pageable);
        }
    }

    @Override
    public Page<BillEntity> getAllByMethodPayment(String methodPayment, Pageable pageable) {
        if (billRepository.findAllByMethodPayment(methodPayment, pageable).isEmpty()) {
            return null;
        } else {
            return billRepository.findAllByMethodPayment(methodPayment, pageable);
        }
    }

    @Override
    public BillEntity getById(String id) {
        if (billRepository.findById(id).isEmpty()){
            return null;
        } else {
            return billRepository.findById(id).get();
        }
    }

    @Override
    public BillEntity saveBill(BillEntity bill) {
        return billRepository.save(bill);
    }

    @Override
    public BillEntity updateBillInfo(BillEntity bill, InfoBillRequest infoBillRequest) throws Exception {
        bill = billMapping.UpdateBilleByInfo(bill, infoBillRequest);
        return billRepository.save(bill);
    }

    @Override
    public Boolean existsByPetSale(PetEntity pet) {
        return billRepository.existsByPetSale(pet);
    }

    @Override
    public void deleteBills(String[] ids) {
        for (String id : ids) {
            billRepository.deleteById(id);
        }
    }

    @Override
    public void deleteByIdPet(String idPet) throws Exception {
        PetEntity petEntity = petService.getPet(idPet);
        if (petEntity != null) {
            billRepository.deleteByPetSale(petEntity);
        } else {
            throw new Exception("Pet not exist");
        }
    }
}
