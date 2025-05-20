package com.codesoftlution.petNova.user_microservice.respositories;

import com.codesoftlution.petNova.user_microservice.models.UserOfficeKey;
import com.codesoftlution.petNova.user_microservice.models.UserOfficeRelation;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserOfficeRelationRepository extends JpaRepository<UserOfficeRelation, UserOfficeKey> {

    List<UserOfficeRelation> findById_UserId(Long userId);

    List<UserOfficeRelation> findById_OfficeId(Long officeId);

    Optional<UserOfficeRelation> findById_UserIdAndId_OfficeId(Long userId, Long officeId);

    void deleteById_UserIdAndId_OfficeId(Long userId, Long officeId);
    void deleteById_UserId(Long userId);

    @Query("SELECT r FROM UserOfficeRelation r WHERE r.id.officeId = :officeId AND r.role = 'OFFICE_ADMIN'")
    Optional<UserOfficeRelation> findOfficeAdminByOfficeId(@Param("officeId") Long officeId);
}