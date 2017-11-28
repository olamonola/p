/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.prz.dao.SearchInternship;
import org.prz.dao.Sprawozdanie;
import org.prz.dao.SprawozdanieSearch;
import org.prz.dao.Wykaz;
import org.prz.dao.WykazSearch;
import org.prz.entity.Internship;
import org.prz.entity.InternshipType;
import org.prz.entity.Specialization;
import org.prz.entity.UserProfile;
import org.prz.repository.FacultyRepository;
import org.prz.repository.InternshipRepository;
import org.prz.repository.InternshipTypeRepository;
import org.prz.repository.SchoolRepresentativeRepository;
import org.prz.repository.SpecializationRepository;
import org.prz.repository.SystemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ola
 */
@Service
public class InternshipServiceImpl implements InternshipService {
    
    private static final int PAGE_SIZE = 10;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private InternshipRepository repository;
    
    @Autowired
    private SpecializationRepository spacializationRepository;
    
    @Autowired
    private FacultyRepository facultyRepository;
    
    @Autowired
    private SystemRepository systemRepository;
    
    @Autowired
    private InternshipTypeRepository internshipTypeRepository;
    
    @Autowired
    private SchoolRepresentativeRepository schoolRepresentativeRepository;
    
    @Transactional
    @Override
    public Internship save(Internship entity) {
        entity.setCreated(new Date());
        return repository.save(entity);
    }
    
    @Transactional
    @Override
    public Internship update(Internship entity) {
        entity.setLastEdited(new Date());
        return repository.save(entity);
    }
    
    @Transactional
    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    /*
    Znajduje najstarszy rok szkolny wg. daty rozpoczęcia praktyk
     */
    @Override
    public int oldestYear() {
        Internship i = repository.findTopByOrderByStartDateAsc();
        if (i != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(i.getStartDate());
            int year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH);
            //jeśli jest październik lub później, to jest rok/rok+1 (np. 2015/2016)

            if (month >= 9) {
                return year;
            } else {
                return year - 1;
            }
        } else {
            return -1;
        }
    }
    
    @Override
    public ArrayList<Integer> allSchoolYears() {
        ArrayList<Integer> allSchoolYears = new ArrayList<Integer>() {
        };
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int nowYear = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        if (month < 9) {
            nowYear = nowYear - 1;
        }
        int oldest = this.oldestYear();
        for (nowYear = nowYear; nowYear >= oldest; nowYear--) {
            allSchoolYears.add((Integer) nowYear);
        }
        return allSchoolYears;
    }
    
    @Override
    public Internship findOne(int id) {
        return repository.findOne(id);
    }
    
    @Override
    public Page<Internship> wykaz(Integer pageNumber, WykazSearch wykazSearch) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE);
        Calendar cal = Calendar.getInstance();
        cal.set(wykazSearch.getSchoolYear(), 9, 1);
        Date beginning = cal.getTime();
        cal.set(wykazSearch.getSchoolYear() + 1, 9, 1);
        Date end = cal.getTime();
        return repository.wykaz(pageRequest, wykazSearch.getInternshipType(),
                wykazSearch.getSystem(), beginning, end);
    }
    
    @Override
    public List<Internship> wykazDaneDoPDF(WykazSearch wykazSearch) {
        Calendar cal = Calendar.getInstance();
        cal.set(wykazSearch.getSchoolYear(), 9, 1);
        Date beginning = cal.getTime();
        cal.set(wykazSearch.getSchoolYear() + 1, 9, 1);
        Date end = cal.getTime();
        return repository.wykazDaneDoPDF(wykazSearch.getInternshipType(), wykazSearch.getSystem(), beginning, end);
    }
    
    @Override
    public Page<Internship> findByUser(Integer pageNumber, UserProfile userProfile) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "startDate");
        return repository.findByAccountId(pageRequest, userProfile);
    }
    
    @Override
    public Page<Internship> findInternships(Integer pageNumber, String year) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "startDate");
        if (year != null) {
            Integer y = Integer.parseInt(year);
            return repository.findByYear(pageRequest, y);
        } else if (year == null) {
            return repository.findAll(pageRequest);
        } else {
            return null;
        }
    }

    /*Wyszukiwanie w jsp dla Sprawozdania - ilość studentów w firmie*/
    @Override
    public Page<Object[]> sprawozdanie(Integer pageNumber, SprawozdanieSearch s) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE);
        Calendar cal = Calendar.getInstance();
        cal.set(s.getSchoolYear(), 9, 1);
        Date beginning = cal.getTime();
        cal.set(s.getSchoolYear() + 1, 9, 1);
        Date end = cal.getTime();
        return repository.sprawozdanie(pageRequest, s.getInternshipTypeId(), s.getSystemId(),
                s.getSpecializationId(), s.getFacultyId(), s.getYear(), beginning, end);
    }
    
    public List<Object[]> sprawozdanieListaPDF(SprawozdanieSearch s) {
        Calendar cal = Calendar.getInstance();
        cal.set(s.getSchoolYear(), 9, 1);
        Date beginning = cal.getTime();
        cal.set(s.getSchoolYear() + 1, 9, 1);
        Date end = cal.getTime();
        return repository.sprawozdaniePDF(s.getInternshipTypeId(), s.getSystemId(),
                s.getSpecializationId(), s.getFacultyId(), s.getYear(), beginning, end);
    }
    
    @Override
    public Page<Internship> internshipsRegister(Integer pageNumber, Integer schoolYear, Integer year, Specialization specialization, SearchInternship searchInternship) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "accountId.lastName");

        /*pomiędzy jakimi datami ma szukać. (rok jest brany z public ArrayList<Integer> allSchoolYears(),
        np 2016 oznacza rok szkolny 2016/2017*/
        Calendar cal = Calendar.getInstance();
        cal.set(schoolYear, 9, 1);
        Date beginning = cal.getTime();
        cal.set(schoolYear + 1, 8, 30);
        Date end = cal.getTime();
        if (specialization == null && year == null && schoolYear == null) {
            logger.info("specialization == null && year== null && schoolYear == null");
        }

        /*jeśli jest specjalizacja*/
        if (specialization != null) {
            /*jeśli jest specjalizacja, jest nazwisko i jest imie, to...*/
            if (searchInternship.getLastName() != null && searchInternship.getFirstName() != null) {
                logger.info("jest specjalizacja, jest nazwisko i jest imie");
                return repository.findByStartDateBetweenAndYearAndSpecializationIdAndAccountId_LastNameContainingIgnoreCaseAndAccountId_FirstNameContainingIgnoreCaseOrderByAccountId_LastNameAsc(pageRequest, beginning, end, year, specialization, searchInternship.getLastName(), searchInternship.getFirstName());
            } else if (searchInternship.getLastName() == null && searchInternship.getFirstName() == null) {
                logger.info("jeśli jest specjalizacja, ale nie ma ani imienia, ani naziwsko to");
                return repository.findByStartDateBetweenAndYearAndSpecializationIdOrderByAccountId_LastNameAsc(pageRequest, beginning, end, year, specialization);
            }
            
        } else if (specialization == null && year != null && schoolYear != null) {/*jeśli nie ma specjalizacji, jest nazwisko i jest imie, to...*/
            if (searchInternship.getLastName() != null && searchInternship.getFirstName() != null) {
                logger.info("jest specjalizacja, jest nazwisko i jest imie");
                return repository.findByStartDateBetweenAndYearAndAccountId_LastNameContainingIgnoreCaseAndAccountId_FirstNameContainingIgnoreCaseOrderByAccountId_LastNameAsc(pageRequest, beginning, end, year, searchInternship.getLastName(), searchInternship.getFirstName());
            } /*jeśli nie ma specjalizacji, ale nie ma ani imienia, ani naziwsko to...*/ else if (searchInternship.getLastName() == null && searchInternship.getFirstName() == null) {
                logger.info("nie ma specjalizacji, ale nie ma ani imienia, ani naziwsko");
                return repository.findByStartDateBetweenAndYearOrderByAccountId_LastNameAsc(pageRequest, beginning, end, year);
            }
        }
        return null;
    }
    
    @Override
    public Page<Internship> internshipsSearchByStudent(Integer pageNumber, SearchInternship searchInternship) {
        PageRequest pageRequest
                = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.ASC, "accountId.lastName");
        /*jeśli jest imie i nazwisko...*/
        if (searchInternship.getLastName() != null && searchInternship.getFirstName() != null) {
            logger.info("nie ma specjalizacji, roku i roku szkolnego; jest nazwisko i jest imie");
            return repository.findByAccountId_LastNameContainingIgnoreCaseAndAccountId_FirstNameContainingIgnoreCaseOrderByAccountId_LastNameAsc(pageRequest, searchInternship.getLastName(), searchInternship.getFirstName());
        } else {
            return null;
        }
    }
    
    @Override
    public List<Specialization> specializationsInWykazResult(WykazSearch wykazSearch) {
        Calendar cal = Calendar.getInstance();
        cal.set(wykazSearch.getSchoolYear(), 9, 1);
        Date beginning = cal.getTime();
        cal.set(wykazSearch.getSchoolYear() + 1, 9, 1);
        Date end = cal.getTime();
        return spacializationRepository.specializationsInWykazResult(wykazSearch.getInternshipType(), beginning, end);
    }
    
    private String listaSpecjalizacji(WykazSearch wykazSearch) {
        String lista = "";
        if (wykazSearch.getSpecializations() != null) {
            for (int i = 0; i < wykazSearch.getSpecializations().size(); i++) {
                if (wykazSearch.getSpecializations().get(i) != null) {
                    logger.info("wykazSearch.getSpecializations().get(i) {}", wykazSearch.getSpecializations().get(i));
                    if (lista.isEmpty()) {
                        lista = wykazSearch.getSpecializations().get(i);
                    } else {
                        lista = lista + ", " + wykazSearch.getSpecializations().get(i);
                    }
                }
            }
        }
        return lista;
    }

    /*Główna metoda populacji pól pdf-u sprawozdanie*/
    @Override
    public ArrayList<Sprawozdanie> listaSprawozdaniePDF(SprawozdanieSearch s) {
        ArrayList<Sprawozdanie> lista = new ArrayList<>();
        Sprawozdanie element = new Sprawozdanie();
        element.setKierunek(facultyRepository.getOne(s.getFacultyId()).getName());
        element.setSpecjalnosc(spacializationRepository.getOne(s.getSpecializationId()).getName());
        element.setSystem(systemRepository.getOne(s.getSystemId()).getName());
        element.setRokStudiow(s.getYear().toString());
        element.setNazwaPraktyki(internshipTypeRepository.getOne(s.getInternshipTypeId()).getType());
        element.setDlugoscPraktyki(internshipTypeRepository.getOne(s.getInternshipTypeId()).getDuration());
        if (s.getTeacher() != null) {
            element.setOpiekun(s.getTeacher());
        } else {
            element.setOpiekun("");
        }
        if (s.getComments() != null) {
            element.setUwagi(s.getComments());
        } else {
            element.setUwagi("");
        }
        Calendar cal = Calendar.getInstance();
        cal.set(s.getSchoolYear(), 9, 1);
        Date beginning = cal.getTime();
        cal.set(s.getSchoolYear() + 1, 9, 1);
        Date end = cal.getTime();
        BigInteger studenciNaPraktykach = repository.studenciNaPraktykach(s.getInternshipTypeId(),
                s.getSpecializationId(), s.getSystemId(), s.getYear(), beginning, end);
        BigInteger studenciZwolnieni = repository.studenciZwolnieni(s.getInternshipTypeId(),
                s.getSpecializationId(), s.getSystemId(), s.getYear(), beginning, end);
        BigInteger studenciOdwolani = repository.studenciOdwolani(s.getInternshipTypeId(),
                s.getSpecializationId(), s.getSystemId(), s.getYear(), beginning, end);
        logger.info("Ilosc studentow odwolanyh z praktyki {}", repository.studenciOdwolani(s.getInternshipTypeId(),
                s.getSpecializationId(), s.getSystemId(), s.getYear(), beginning, end).toString());
        element.setStudenciNaPraktykach(studenciNaPraktykach.toString());
        element.setStudenciZwolnieni(studenciZwolnieni.toString());
        element.setStudenciOdwolani(studenciOdwolani.toString());
        lista.add(element);
        List<Object[]> instytucje = this.sprawozdanieListaPDF(s);
        for (int i = 0; i < instytucje.size(); i++) {
            Sprawozdanie elem = new Sprawozdanie();
            int lp = i + 1;
            elem.setLp("" + lp + ".");
            elem.setInstytucja(instytucje.get(i)[0].toString());
            elem.setStudenciWInstytucji(instytucje.get(i)[1].toString());
            lista.add(elem);
        }
        return lista;
    }

    /*Główna metoda wyszukiwania danych do wykazu pdf*/
    @Override
    public ArrayList<Wykaz> listaWykaz(List<Internship> praktyki, WykazSearch wykazSearch) {
        ArrayList<Wykaz> lista = new ArrayList<>();
        Wykaz w = new Wykaz();
        org.prz.entity.System system = systemRepository.findOne(wykazSearch.getSystem());
        w.setSystem(system.getName());
        w.setLp("1.");
        w.setSpecializations(this.listaSpecjalizacji(wykazSearch));
        String opiekun = "";
        if (wykazSearch.getTeacher() != null) {
            opiekun = wykazSearch.getTeacher();
        } else {
            opiekun = "";
        }
        w.setOpiekunUczelniany(opiekun);
        w.setKierunek("Informatyka");
        InternshipType type = internshipTypeRepository.findOne(wykazSearch.getInternshipType());
        w.setNazwaPraktyki(type.getType());
        w.setCzasPraktyki(type.getDuration());
        lista.add(w);
        for (int i = 0; i < praktyki.size(); i++) {
            Wykaz w2 = new Wykaz();
            int lp = i + 1;
            w2.setLp("" + lp + ".");
            w2.setStudent(praktyki.get(i).getAccountId().getFirstName() + " " + praktyki.get(i).getAccountId().getLastName());
            if (praktyki.get(i).getCompanyName() != null) {
                w2.setFirma(praktyki.get(i).getCompanyName());
                if (praktyki.get(i).getCompanyId().getPhone() != null) {
                    w2.setFirma(w2.getFirma() + ", " + praktyki.get(i).getCompanyId().getPhone());
                }
            } else {
                w2.setFirma("");
            }
            String opiekunFirmowy;
            if (praktyki.get(i).getCompanyInternshipAdministratorFirstName() != null) {
                opiekunFirmowy = praktyki.get(i).getCompanyInternshipAdministratorFirstName() + " ";
            } else {
                opiekunFirmowy = " ";
            }
            if (praktyki.get(i).getCompanyInternshipAdministratorLastName() != null) {
                opiekunFirmowy = opiekunFirmowy + praktyki.get(i).getCompanyInternshipAdministratorLastName();
            }
            w2.setOpiekunFirmowy(opiekunFirmowy);
            String termin;
            if (praktyki.get(i).getStartDate() != null) {
                termin = new SimpleDateFormat("dd.MM.YYYY").format(praktyki.get(i).getStartDate()) + " - ";
            } else {
                termin = " - ";
            }
            if (praktyki.get(i).getEndDate() != null) {
                termin = termin + new SimpleDateFormat("dd.MM.YYYY").format(praktyki.get(i).getEndDate());
            }
            w2.setTermin(termin);
            lista.add(w2);
        }
        
        return lista;
    }
    
}
