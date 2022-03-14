package com.assigment.suretime.competitor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CompetitorService {

    private final CompetitorRepository competitorRepository;

    public Competitor getCompetitorByEmail(String email){
        return competitorRepository.findCompetitorByEmail(email).orElseThrow(
                () -> new CompetitorNotFoundException(email));
    }

    public List<Competitor> getAllCompetitors() {
        return competitorRepository.findAll();
    }
}
