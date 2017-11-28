/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import org.prz.entity.Internship;
import org.prz.entity.Specialization;
import org.prz.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Ola
 */
public interface InternshipRepository extends JpaRepository<Internship, Integer> {
    
    @Query(value = "SELECT c.name, COUNT(c.name) from Internship i"
            + " join i.companyId c"
            + " join i.internshipTypeId it"
            + " join i.systemSystemId s"
            + " join i.accountId a"
            + " join i.specializationId spec"
            + " join i.facultyId f"
            + " where it.internshipTypeId = :internshipTypeId"
            + " and s.systemId = :systemId"
            + " and spec.specializationId = :specializationId"
            + " and f.facultyId = :facultyId"
            + " and i.year = :year"
            + " and i.confirmation = TRUE"
            + " and i.archived = FALSE"
            + " and i.startDate >= :date1"
            + " and i.startDate < :date2"
            + " group by c.name"
            + " order by c.name")
    public Page<Object[]> sprawozdanie(
            Pageable pageable,
            @Param("internshipTypeId") Integer internshipTypeId,
            @Param("systemId") Integer systemId,
            @Param("specializationId") Integer specializationId,
            @Param("facultyId") Integer facultyId,
            @Param("year") Integer year,
            @Param("date1") Date date1,
            @Param("date2") Date date2);

    @Query(value = "SELECT c.name, COUNT(c.name) from Internship i"
            + " join i.companyId c"
            + " join i.internshipTypeId it"
            + " join i.systemSystemId s"
            + " join i.accountId a"
            + " join i.specializationId spec"
            + " join i.facultyId f"
            + " where it.internshipTypeId = :internshipTypeId"
            + " and s.systemId = :systemId"
            + " and spec.specializationId = :specializationId"
            + " and f.facultyId = :facultyId"
            + " and i.year = :year"
            + " and i.confirmation = TRUE"
            + " and i.archived = FALSE"
            + " and i.startDate >= :date1"
            + " and i.startDate < :date2"
            + " group by c.name"
            + " order by c.name")
    public List<Object[]> sprawozdaniePDF(
            @Param("internshipTypeId") Integer internshipTypeId,
            @Param("systemId") Integer systemId,
            @Param("specializationId") Integer specializationId,
            @Param("facultyId") Integer facultyId,
            @Param("year") Integer year,
            @Param("date1") Date date1,
            @Param("date2") Date date2);

    @Query(value = "SELECT count(distinct account_id) from internship i"
            + " join account a using(account_id)"
            + " where i.internship_type_id = ?1"
            + " and i.specialization_id = ?2"
            + " and i.system_system_id = ?3"
            + " and i.year = ?4"
            + " and i.confirmation = TRUE"
            + " and i.archived = FALSE"
            + " and i.start_date >= ?5"
            + " and i.start_date < ?6"
            , nativeQuery = true)
    public BigInteger studenciNaPraktykach(
            Integer internship_type_id,
            Integer spcialization_id,
            Integer system_id,
            Integer year, 
            Date beginning,
            Date end);
    
    @Query(value = "SELECT count(distinct account_id) from internship i"
            + " join account a using(account_id)"
            + " where i.internship_type_id = ?1"
            + " and i.specialization_id = ?2"
            + " and i.system_system_id = ?3"
            + " and i.year = ?4"
            + " and i.confirmation = TRUE"
            + " and i.archived = FALSE"
            + " and i.exemption = TRUE"
            + " and i.start_date >= ?5"
            + " and i.start_date < ?6"
            , nativeQuery = true)
    public BigInteger studenciZwolnieni(
            Integer internship_type_id,
            Integer spcialization_id,
            Integer system_id,
            Integer year, 
            Date beginning,
            Date end);
    
    @Query(value = "SELECT count(distinct account_id) from internship i"
            + " join account a using(account_id)"
            + " where i.internship_type_id = ?1"
            + " and i.specialization_id = ?2"
            + " and i.system_system_id = ?3"
            + " and i.year = ?4"
            + " and i.confirmation = TRUE"
            + " and i.archived = FALSE"
            + " and i.dismissed = TRUE"
            + " and i.start_date >= ?5"
            + " and i.start_date < ?6"
            , nativeQuery = true)
    public BigInteger studenciOdwolani(
            Integer internship_type_id,
            Integer spcialization_id,
            Integer system_id,
            Integer year, 
            Date beginning,
            Date end);

    @Query(value = "SELECT i from Internship i"
            + " join i.internshipTypeId it"
            + " join i.systemSystemId s"
            + " join i.accountId a"
            + " join i.companyId c"
            + " where it.internshipTypeId = :internshipTypeId"
            + " and s.systemId = :systemId"
            + " and i.confirmation = TRUE"
            + " and i.archived = FALSE"
            + " and i.startDate >= :date1"
            + " and i.startDate < :date2"
            + " order by a.lastName")
    public Page<Internship> wykaz(
            Pageable pageable,
            @Param("internshipTypeId") Integer internshipTypeId,
            @Param("systemId") Integer systemId,
            @Param("date1") Date date1,
            @Param("date2") Date date2);

    @Query(value = "SELECT i from Internship i"
            + " join i.internshipTypeId it"
            + " join i.systemSystemId s"
            + " join i.accountId a"
            + " join i.companyId c"
            + " where it.internshipTypeId = :internshipTypeId"
            + " and s.systemId = :systemId"
            + " and i.confirmation = TRUE"
            + " and i.archived = FALSE"
            + " and i.startDate >= :date1"
            + " and i.startDate < :date2"
            + " order by a.lastName")
    public List<Internship> wykazDaneDoPDF(
            @Param("internshipTypeId") Integer internshipTypeId,
            @Param("systemId") Integer systemId,
            @Param("date1") Date date1,
            @Param("date2") Date date2);

    public Page<Internship> findByAccountId(Pageable pageable, UserProfile accountId);

    public Page<Internship> findByYear(Pageable pageable, Integer year);

    /*szuka po roku, roku szkolnym, specjalności*/
    public Page<Internship> findByStartDateBetweenAndYearAndSpecializationIdOrderByAccountId_LastNameAsc(Pageable pageable, Date beginSchoolYear, Date endSchoolYear, Integer year, Specialization specialization);

    Internship findTopByOrderByStartDateAsc();

    /*szuka po roku, roku szkolnym*/
    public Page<Internship> findByStartDateBetweenAndYearOrderByAccountId_LastNameAsc(Pageable pageable, Date beginSchoolYear, Date endSchoolYear, Integer year);

    /*szuka po roku, roku szkolnym, nazwisku i imieniu*/
    public Page<Internship> findByStartDateBetweenAndYearAndAccountId_LastNameContainingIgnoreCaseAndAccountId_FirstNameContainingIgnoreCaseOrderByAccountId_LastNameAsc(Pageable pageable, Date beginSchoolYear, Date endSchoolYear, Integer year, String lastName, String firstName);

    /*szuka po roku, roku szkolnym, specjalności, nazwisku i imieniu*/
    public Page<Internship> findByStartDateBetweenAndYearAndSpecializationIdAndAccountId_LastNameContainingIgnoreCaseAndAccountId_FirstNameContainingIgnoreCaseOrderByAccountId_LastNameAsc(Pageable pageable, Date beginSchoolYear, Date endSchoolYear, Integer year, Specialization specialization, String lastName, String firstName);

    /*szuka po nazwisku i imieniu*/
    public Page<Internship> findByAccountId_LastNameContainingIgnoreCaseAndAccountId_FirstNameContainingIgnoreCaseOrderByAccountId_LastNameAsc(Pageable pageable, String lastName, String firstName);

}
