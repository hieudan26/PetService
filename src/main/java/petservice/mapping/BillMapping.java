package petservice.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import petservice.Service.PetService;
import petservice.Service.UserService;
import petservice.model.Entity.BillEntity;
import petservice.model.Entity.PetEntity;
import petservice.model.Entity.UserEntity;
import petservice.model.payload.request.BillResources.AddBillRequest;
import petservice.model.payload.request.BillResources.InfoBillRequest;
import petservice.model.payload.request.PetResources.InfoPetRequest;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class BillMapping {

    @Autowired
    UserService userService;

    @Autowired
    PetService petService;

    public BillEntity ModelToEntity(AddBillRequest addBillRequest) throws Exception {
        BillEntity newBill = new BillEntity();

        UserEntity user = userService.findById(addBillRequest.getIdUser());
        if (user == null) {
            throw new Exception("User of Add Bill Request is null!!");
        } else {
            newBill.setUserBuyPet(user);
        }

        PetEntity pet = petService.getPet(addBillRequest.getIdPet());
        if (pet == null) {
            throw new Exception("Pet of Add Bill Request is null!!");
        } else {
            newBill.setPetSale(pet);
        }

        if (addBillRequest.getPrice().equals("")) {
            newBill.setPrice(new BigInteger("0"));
        } else {
            newBill.setPrice(addBillRequest.getPrice());
        }

        newBill.setMethodPayment(addBillRequest.getMethodPayment());

        try{
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime dateTime = LocalDateTime.parse(addBillRequest.getPaymentDate(), formatter);

            newBill.setPaymentDate(addBillRequest.getPaymentDate());
        }catch (Exception e) {
            throw new Exception("Incorrect date format");
        }

        return newBill;
    }

    public BillEntity UpdateBilleByInfo(BillEntity bill, InfoBillRequest infoBillRequest) throws Exception {
        UserEntity user = userService.findById(infoBillRequest.getIdUser());
        if (user == null) {
            throw new Exception("User of Add Bill Request is null!!");
        } else {
            bill.setUserBuyPet(user);
        }

        PetEntity pet = petService.getPet(infoBillRequest.getIdPet());
        if (pet == null) {
            throw new Exception("Pet of Add Bill Request is null!!");
        } else {
            bill.setPetSale(pet);
        }

        if (infoBillRequest.getPrice().equals("")) {
            bill.setPrice(new BigInteger("0"));
        } else {
            bill.setPrice(infoBillRequest.getPrice());
        }

        bill.setMethodPayment(infoBillRequest.getMethodPayment());

        try{
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime dateTime = LocalDateTime.parse(infoBillRequest.getPaymentDate(), formatter);
//            bill.setPaymentDate(dateTime);
            bill.setPaymentDate(infoBillRequest.getPaymentDate());
        }catch (Exception e) {
            throw new Exception("Incorrect date format");
        }
        return bill;
    }
}
