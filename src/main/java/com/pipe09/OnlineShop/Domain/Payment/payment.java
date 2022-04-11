package com.pipe09.OnlineShop.Domain.Payment;


import com.pipe09.OnlineShop.Domain.Member.Member;
import com.pipe09.OnlineShop.Dto.Payment.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionId;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
public class payment {


    private String mId;
    private String version;
    @Id
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String currency;
    private String method;
    private String status; //READY,IN_PROGRESS,WAITING_FOR_DEPOSIT,DONE,CANCELED,PARTIAL_CANCELED,ABORTED,EXPIRED
    private String requestedAt;
    private String approvedAt;
    private boolean useEscrow;
    private boolean cultureExpense;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="card_id")
    private card card;
    @Embedded
    @Nullable
    private VirtualAccount virtualAccount;
    @Embedded
    @Nullable
    private Transfer transfer;
    @Embedded
    @Nullable
    private MobilePhone mobilePhone;
    @Embedded
    @Nullable
    private GiftCertificate giftCertificate;
    private String foreignEasyPay;
    @Embedded
    @Nullable
    private CashReceipt cashReceipt;
    @Embedded
    @Nullable
    private Discount discount;


    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    @Nullable
    private List<Cancels> cancels=new ArrayList<>(); //cancelAmount(취소금액),cancelReason(취소사유),taxFreeAmount(면세처리금액),taxAmount(과세금액),refundableAmount(취소후 환불가능금액),canceledAt(취소 날짜정보 YYYY-MM-dd'T'HH:mm:SS±hh:mm)

     /*
    @ElementCollection
    @CollectionTable(name="cancels")
    private List<Map<String,Object>> cancels;

      */
    //순서대로  NUM,STRING,NUM,NUM,STRING
    @Nullable
    private String secret; //가상계좌 콜백 시크릿키
    private String type;
    @Nullable
    private String easyPay; //간편결제 타입 정보
    private String country; //결제 국가
    @Nullable
    @Embedded
    private Failure failure; //결제 실패 내용
    private int totalAmount;
    private int balanceAmount;
    private int suppliedAmount;
    private int vat;
    private int taxFreeAmount;

    @Transient
    private boolean Notfound=false;

    @Enumerated(value = EnumType.STRING)
    private paymentType current_type;

    @OneToOne
    @JoinColumn(name = "user_ID")
    private Member member;

    public payment(approvePaymentDto dto){
        this.paymentKey=dto.getPaymentKey();
        this.mId=dto.getMId();
        this.version=dto.getVersion();
        this.orderId=dto.getOrderId();
        this.orderName=dto.getOrderName();
        this.currency=dto.getCurrency();
        this.method=dto.getMethod();
        this.status=dto.getStatus();
        this.requestedAt=dto.getRequestedAt();
        this.approvedAt=dto.getApprovedAt();
        this.useEscrow=dto.isUseEscrow();
        this.cultureExpense=dto.isCultureExpense();
        this.card= com.pipe09.OnlineShop.Domain.Payment.card.CardDtotoCard(dto.getCard(),this);
        this.virtualAccount=VirtualAccount.DtotoVritualAccount(dto.getVirtualAccount());
        this.transfer=Transfer.DtoToTransfer(dto.getTransfer());
        this.mobilePhone=MobilePhone.DtoToMobilePhone(dto.getMobilePhone());
        this.giftCertificate=GiftCertificate.DtoToGiftCertificate(dto.getGiftCertificate());
        this.foreignEasyPay=dto.getForeignEasyPay();
        this.cashReceipt=CashReceipt.DtoToCashReceipt(dto.getCashReceipt());
        this.discount=Discount.DtoToDiscount(dto.getDiscount());
        Cancels.processObjectArrayCancel(dto.getCancels(),this);
        this.setSecret(dto.getSecret());
        this.setType(dto.getType());
        this.setEasyPay(dto.getEasyPay());
        this.setCountry(dto.getCountry());
        this.setFailure(Failure.DtoToFailure(dto.getFailure()));
        this.totalAmount=dto.getTotalAmount();
        this.balanceAmount=dto.getBalanceAmount();
        this.suppliedAmount=dto.getSuppliedAmount();
        this.vat=dto.getVat();
        this.taxFreeAmount=dto.getTaxFreeAmount();
        this.setCurrent_type(paymentType.ACTIVATE);




    }

    public payment() {
    }

    public payment(boolean notfound){
        this.Notfound=notfound;
    }
}
