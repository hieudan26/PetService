package petservice.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import petservice.model.Entity.BillEntity;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BillResources.InfoBillRequest;

import java.time.LocalDateTime;
import java.util.List;

@Component
public interface BillService {
    Page<BillEntity> getAllBills(Pageable pageable);
    Page<BillEntity> getAllByUser(UserEntity user, Pageable pageable);
    BillEntity getByPet(PetEntity pet);
    Page<BillEntity> getAllByPaymentDate(LocalDateTime time, Pageable pageable);
    Page<BillEntity> getAllByUserBuyPetAndPaymentDate(UserEntity user, LocalDateTime time, Pageable pageable);
    Page<BillEntity> getAllByMethodPayment(String methodPayment, Pageable pageable);
    BillEntity getById(String id);
    BillEntity saveBill(BillEntity bill);
    BillEntity updateBillInfo(BillEntity bill, InfoBillRequest infoBillRequest) throws Exception;
    Boolean existsByPetSale(PetEntity pet);
    void deleteBills(String[] ids);
    void deleteByIdPet(String idPet) throws Exception;

}
