package org.paurus.taxation.v1.db;

import org.springframework.stereotype.Repository;

@Repository
public class TraderRepository {
    public String getCountryById(long traderId) {
        if (traderId == 1L) {
            return "SLO";
        } else if (traderId == 2L) {
            return "AUT";
        } else {
            throw new IllegalArgumentException();
        }
    }
}
