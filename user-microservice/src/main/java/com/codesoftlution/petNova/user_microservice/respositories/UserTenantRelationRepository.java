package com.codesoftlution.petNova.user_microservice.respositories;

import com.codesoftlution.petNova.user_microservice.models.UserTenantKey;
import com.codesoftlution.petNova.user_microservice.models.UserTenantRelation;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserTenantRelationRepository extends JpaRepository<UserTenantRelation, UserTenantKey> {

    List<UserTenantRelation> findById_UserId(Long userId);

    List<UserTenantRelation> findById_TenantId(Long tenantId);

    Optional<UserTenantRelation> findById_UserIdAndId_TenantId(Long userId, Long tenantId);

    void deleteById_UserIdAndId_TenantId(Long userId, Long tenantId);
    void deleteById_UserId(Long userId);

    @Query("SELECT DISTINCT r.id.userId FROM UserTenantRelation r")
    Set<Long> findAllUserIdsWithTenants();

    @Query("SELECT r FROM UserTenantRelation r WHERE r.id.tenantId = :tenantId AND r.role = 'TENANT_ADMIN'")
    Optional<UserTenantRelation> findTenantAdminByTenantId(@Param("tenantId") Long tenantId);

    @Query("SELECT r.id.userId FROM UserTenantRelation r WHERE r.id.tenantId = :tenantId AND r.role = 'TENANT_ADMIN'")
    List<Long> findAdminUserIdsByTenantId(@Param("tenantId") Long tenantId);
}
