package com.example.userdatamodel.entity;

import com.example.userdatamodel.entity.enumtype.AccountRoleEnum;
import com.example.userdatamodel.entity.enumtype.AuthProviderEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_account")
public class UserAccount extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AccountRoleEnum role;

    @Column(name = "first_name")
    private String fName;

    @Column(name = "last_name")
    private String lName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNum;

    @Column(name = "auth_provider")
    @Enumerated(EnumType.STRING)
    private AuthProviderEnum authProvider;
}
