/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prz.service;

import java.util.ArrayList;
import java.util.List;
import org.prz.dao.Porozumienie;
import org.prz.dao.SearchInternship;
import org.prz.dao.Sprawozdanie;
import org.prz.dao.SprawozdanieSearch;
import org.prz.dao.Wykaz;
import org.prz.dao.WykazSearch;
import org.prz.entity.Internship;
import org.prz.entity.Specialization;
import org.prz.entity.UserProfile;
import org.springframework.data.domain.Page;

/**
 *
 * @author Ola
 */
public interface InternshipService {
    
    public ArrayList<Sprawozdanie> listaSprawozdaniePDF(SprawozdanieSearch s);
    
    public Page<Object[]> sprawozdanie(Integer pageNumber, SprawozdanieSearch s);
    
    public ArrayList<Wykaz> listaWykaz(List<Internship> praktyki, WykazSearch wykazSearch);

    public Porozumienie setAgreement(Internship i);

    public Internship save(Internship internship);

    public Internship update(Internship internship);

    public void delete(Integer id);

    public Internship findOne(int id);

    public int oldestYear();

    public ArrayList<Integer> allSchoolYears();

    public Page<Internship> findByUser(Integer pageNumber, UserProfile userProfile);

    public Page<Internship> findInternships(Integer pageNumber, String year);

    /*funkcja znajduje praktyki dla danego roku szkolnego, dla danego rocznika stuentów, dla danej specjalizaci*/
    public Page<Internship> internshipsRegister(Integer pageNumber, Integer schoolYear, Integer year, Specialization specialization, SearchInternship searchInternship);

    /*funkcja znajduje praktyki dla danego studenta, dla każdego roku szkolnego,
    dla każdego rocznika stuentów, dla każdej specjalizaci*/
    public Page<Internship> internshipsSearchByStudent(Integer pageNumber, SearchInternship searchInternship);

    public Page<Internship> wykaz(Integer pageNumber, WykazSearch wykazSearch);
    
    public List<Internship> wykazDaneDoPDF(WykazSearch wykazSearch);
    
    public List<Specialization> specializationsInWykazResult(WykazSearch wykazSearch);
}
