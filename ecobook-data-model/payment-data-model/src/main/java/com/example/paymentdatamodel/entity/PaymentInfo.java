package com.example.paymentdatamodel.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment_info")
public class PaymentInfo extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "status")
    private String status;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "payer_id")
    private String payerId;

    @Column(name = "paypal_id")
    private String paypalId;
}