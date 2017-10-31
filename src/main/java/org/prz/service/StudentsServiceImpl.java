/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.ArrayList;
import java.util.List;
import org.prz.dao.SearchStudents;
import org.prz.dao.SearchStudentsAndPageInfo;
import org.prz.dao.StudentDataDao;
import org.prz.dao.StudentRoleDao;
import org.prz.entity.Account;
import org.prz.entity.AccountRole;
import org.prz.entity.Role;
import org.prz.entity.Internship;
import org.prz.entity.StudentView;
import org.prz.entity.StudentViewRoleDateUnimportant;
import org.prz.entity.StudentWithoutRoleView;
import org.prz.entity.UserProfile;
import org.prz.exception.IndexNoNotUnique;
import org.prz.repository.AccountRepository;
import org.prz.repository.AccountRoleRepository;
import org.prz.repository.InternshipRepository;
import org.prz.repository.StudentViewRepository;
import org.prz.repository.StudentViewRoleDateUnimportantRepository;
import org.prz.repository.StudentWithoutRoleViewRepository;
import org.prz.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ola
 */
@Service
public class StudentsServiceImpl implements StudentsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int PAGE_SIZE = 10;

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final StudentWithoutRoleViewRepository studentWithoutRoleViewRepository;

    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Autowired
    private final StudentViewRepository studentViewRepository;

    @Autowired
    private final InternshipRepository internshipRepository;

    @Autowired
    private final StudentViewRoleDateUnimportantRepository studentViewRoleDateUnimportantRepository;

    @Autowired
    private final AccountRoleRepository accountRoleRepository;

    public StudentsServiceImpl(AccountRepository accountRepository,
            StudentViewRepository studentViewRepository,
            StudentViewRoleDateUnimportantRepository studentViewRoleDateUnimportantRepository,
            InternshipRepository internshipRepository,
            UserProfileRepository userProfileRepository,
            AccountRoleRepository accountRoleRepository,
            StudentWithoutRoleViewRepository studentWithoutRoleViewRepository) {
        this.accountRepository = accountRepository;
        this.studentViewRepository = studentViewRepository;
        this.studentViewRoleDateUnimportantRepository = studentViewRoleDateUnimportantRepository;
        this.internshipRepository = internshipRepository;
        this.userProfileRepository = userProfileRepository;
        this.accountRoleRepository = accountRoleRepository;
        this.studentWithoutRoleViewRepository = studentWithoutRoleViewRepository;
    }

    @Override
    public Page<Account> findAllUsers(Integer pageNumber) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE);
        return accountRepository.findAllByOrderByUserProfile_LastNameAsc(pageRequest);
    }

    @Override
    public SearchStudentsAndPageInfo searchUsersQueryBeta(SearchStudents searchStudents, Integer pageNumber) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE);

        String studentRole = "ROLE_STUDENT";
        //Ustawienia wartości początkowych
        String lastName = searchStudents.getLastName();
        String firstName = searchStudents.getFirstName();
        String indexNo = searchStudents.getIndexNo();
        String login = searchStudents.getLogin();
        String email = searchStudents.getEmail();
        if (lastName == null) {
            lastName = "";
        }
        if (firstName == null) {
            firstName = "";
        }
        if (indexNo == null) {
            indexNo = "";
        }
        if (login == null) {
            login = "";
        }
        if (email == null) {
            email = "";
        }
        Boolean confirmed = false;
        Boolean notConfirmed = true;
        Boolean expiredAccount = false;
        Boolean studentsWithoutRole = false;
        if (searchStudents.getAccountConfirmed() != null) {
            confirmed = searchStudents.getAccountConfirmed();
        }
        if (searchStudents.getAccountNotConfirmed() != null) {
            notConfirmed = !searchStudents.getAccountNotConfirmed();
        }
        if (searchStudents.getStudentRoleExpired() != null) {
            expiredAccount = searchStudents.getStudentRoleExpired();
        }
        if (searchStudents.getStudentsWithoutRole() != null) {
            studentsWithoutRole = searchStudents.getStudentsWithoutRole();
        }
        //KONIEC ustawień wartości początkowych
        //Jeśli checkbox nie zaznaczony, to pokaż tylko tych studentów, którzy 
        //mają aktywną (nieprzeterminowaną) rolę oraz studentów bez roli 
        if (!expiredAccount) {
            logger.info("Studenci, którzy mają aktywną (nieprzeterminowaną) rolę oraz bez roli");
            if (studentsWithoutRole) {
                Page<StudentWithoutRoleView> studentciPage = studentWithoutRoleViewRepository
                        .searchStudentsWithoutRole(
                                pageRequest,
                                firstName.toLowerCase(),
                                lastName.toLowerCase(),
                                indexNo.toLowerCase(),
                                login.toLowerCase(),
                                email.toLowerCase(),
                                confirmed,
                                notConfirmed);
                List<StudentWithoutRoleView> studenci = studentciPage.getContent();
                //Dane potrzebne do paginacji
                int current = studentciPage.getNumber() + 1;
                int begin = Math.max(1, current - 5);
                int end = Math.min(begin + 10, studentciPage.getTotalPages());
                int totalPages = studentciPage.getTotalPages();
                //KONIEC danych potrzebnych do paginacji
                List<StudentView> studentViewList = new ArrayList<StudentView>();
                for (StudentWithoutRoleView a : studentciPage) {
                    StudentView studentView = new StudentView();
                    studentView.setAccountConfirmed(a.getAccountConfirmed());
                    studentView.setAccountId(a.getAccountId());
                    studentView.setActive(a.getActive());
                    studentView.setEmail(a.getEmail());
                    studentView.setFirstName(a.getFirstName());
                    studentView.setIndexNo(a.getIndexNo());
                    studentView.setLastName(a.getLastName());
                    studentView.setLogin(a.getLogin());
                    studentView.setRegistrationTime(a.getRegistrationTime());
                    studentView.setVerified(a.getVerified());
                    studentViewList.add(studentView);
                }
                logger.info("Tylko studenci bez roli");
                SearchStudentsAndPageInfo result = new SearchStudentsAndPageInfo(studentViewList, current, end, totalPages);
                return result;
            } else {
                Page<StudentView> studentciPage = studentViewRepository.searchConfirmedOrNotConfirmed(
                        pageRequest,
                        firstName.toLowerCase(),
                        lastName.toLowerCase(),
                        indexNo.toLowerCase(),
                        login.toLowerCase(),
                        email.toLowerCase(),
                        confirmed,
                        notConfirmed);
                List<StudentView> studenci = studentciPage.getContent();
                //Dane potrzebne do paginacji
                int current = studentciPage.getNumber() + 1;
                int begin = Math.max(1, current - 5);
                int end = Math.min(begin + 10, studentciPage.getTotalPages());
                int totalPages = studentciPage.getTotalPages();
                //KONIEC danych potrzebnych do paginacji
                SearchStudentsAndPageInfo result = new SearchStudentsAndPageInfo(studenci, current, end, totalPages);
                return result;
            }

        } //Jeśli checkbox zaznaczony, to pokaż  studentów zarówno z aktywną jak i nieaktywną rolą
        else {
            Page<StudentViewRoleDateUnimportant> studentsPage = studentViewRoleDateUnimportantRepository
                    .searchConfirmedOrNotConfirmed(
                            pageRequest,
                            firstName.toLowerCase(),
                            lastName.toLowerCase(),
                            indexNo.toLowerCase(),
                            login.toLowerCase(),
                            email.toLowerCase(),
                            confirmed,
                            notConfirmed);
            int current = studentsPage.getNumber() + 1;
            int begin = Math.max(1, current - 5);
            int end = Math.min(begin + 10, studentsPage.getTotalPages());
            int totalPages = studentsPage.getTotalPages();

            List<StudentView> studentViewList = new ArrayList<StudentView>();
            for (StudentViewRoleDateUnimportant a : studentsPage) {
                StudentView studentView = new StudentView();
                studentView.setAccountConfirmed(a.getAccountConfirmed());
                studentView.setAccountId(a.getAccountId());
                studentView.setActive(a.getActive());
                studentView.setEmail(a.getEmail());
                studentView.setFirstName(a.getFirstName());
                studentView.setIndexNo(a.getIndexNo());
                studentView.setLastName(a.getLastName());
                studentView.setLogin(a.getLogin());
                studentView.setRegistrationTime(a.getRegistrationTime());
                studentView.setRoleEndTime(a.getRoleEndTime());
                studentView.setRoleId(a.getAccountRoleId());
                studentView.setRoleStartTime(a.getRoleStartTime());
                studentView.setVerified(a.getVerified());
                studentViewList.add(studentView);
            }

            logger.info("I potwierdzone i niepotwierdzone konta z aktywną oraz nieaktywną rolą");
            SearchStudentsAndPageInfo result = new SearchStudentsAndPageInfo(studentViewList, current, end, totalPages);
            return result;
        }

    }

    @Override
    public Account findOneStudent(int id) {
        return accountRepository.findOne(id);
    }

    

    @Override
    public void editStudentData(Integer id, StudentDataDao newData)
    throws IndexNoNotUnique{
        UserProfile oldProfile = userProfileRepository.findOne(id);
        if (oldProfile != null) {
            if (!oldProfile.getIndexNo().equals(newData.getIndexNo())) {
                if (!this.isIndexNoUnique(newData.getIndexNo())) {
                    logger.info("IndexNoNotUnique Exception thrown");
                    throw new IndexNoNotUnique("indexNo", "indexNoNotUnique");
                }
            }
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setAccountId(id);
        userProfile.setFirstName(newData.getFirstName());
        userProfile.setIndexNo(newData.getIndexNo());
        userProfile.setLastName(newData.getLastName());
        userProfileRepository.save(userProfile);
    }

    @Override
    public AccountRole findStudentAccountRole(Integer accountId, Integer roleId) {
        return accountRoleRepository.findOneByAccountId_AccountIdAndRoleId_RoleId(accountId, roleId);
    }

    @Override
    public void editStudentRole(Integer id, StudentRoleDao newData) {
        AccountRole accountRole;
        if (findStudentAccountRole(id, 3) != null) {
            accountRole = findStudentAccountRole(id, 3);
            logger.info("Edycja roli studenta");
        } else {
            logger.info("Nadanie roli studentowi");
            accountRole = new AccountRole();
            Account a = new Account();
            a.setAccountId(id);
            accountRole.setAccountId(a);
        }
        Role r = new Role();
        r.setRoleId(3);
        accountRole.setRoleId(r);
        accountRole.setRoleEndTime(newData.getRoleEndTime());
        accountRole.setRoleStartTime(newData.getRoleStartTime());
        accountRoleRepository.save(accountRole);

    }
    
    //DO ZMIANY
    @Override
    public Page<Internship> findInternshipsByAccount(Integer id, Integer pageNumber) {
        UserProfile up = new UserProfile();
        up.setAccountId(id);
        PageRequest pageable
                = new PageRequest(pageNumber - 1, PAGE_SIZE);
        //return internshipRepository.findByAccountId(pageable, up);
        return null;
    }

    @Override
    public Account confirm(Integer id, Boolean confirmation) {
        Account account = accountRepository.findOne(id);
        if(account != null){
            account.setAccountConfirmed(confirmation);
            accountRepository.save(account);
        }
        return account;
    }
    
    /*true jeśli jest unikalny*/
    public Boolean isIndexNoUnique(String indexNo) {
        if (!accountRepository.findByUserProfile_IndexNo(indexNo).isEmpty()) {/*index istnieje, więc nie jest unikalny*/
            return false;
        } else {
            return true;
        }
    }

}
