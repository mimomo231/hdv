package com.example.userservice.repository;

import com.example.userdatamodel.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsername(String username);

    Optional<UserAccount> findByPhoneNum(String phoneNum);

    @Query(nativeQuery = true,
            value = "SELECT u.* " +
                    "FROM user_account u " +
                    "WHERE u.username IN :usernames " +
                    "   OR u.phone_number IN :phoneNums ")
    List<UserAccount> findAllByUsernameOrPhoneNum(Collection<String> usernames, Collection<String> phoneNums);
}
