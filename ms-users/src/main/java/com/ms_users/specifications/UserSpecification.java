package com.ms_users.specifications;

import com.ms_users.dto.FilterDTO;
import com.ms_users.models.entity.User;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filter(FilterDTO filterDTO) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtros WHERE
            String sex = filterDTO.getPreferenceDTO() != null ? filterDTO.getPreferenceDTO().getSexPreference() : null;
            Long ageFrom = filterDTO.getPreferenceDTO() != null ? filterDTO.getPreferenceDTO().getAgeFrom() : null;
            Long ageTo = filterDTO.getPreferenceDTO() != null ? filterDTO.getPreferenceDTO().getAgeTo() : null;
            String country = filterDTO.getCountryDTO() != null ? filterDTO.getCountryDTO().getCountry() : null;
            Boolean isEnabled = filterDTO.getIsEnabled();
            String city = filterDTO.getCityDTO() != null ? filterDTO.getCityDTO().getCity() : null;
            String state = filterDTO.getStateDTO() != null ? filterDTO.getStateDTO().getState() : null;

            if (sex != null) {
                predicates.add(cb.equal(root.get("sex"), sex));
            }
            if (ageFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("age"), ageFrom));
            }
            if (ageTo != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("age"), ageTo));
            }
            if (country != null) {
                predicates.add(cb.equal(root.join("country").get("country"), country));
            }
            if (isEnabled != null) {
                predicates.add(cb.equal(root.get("isEnabled"), isEnabled));
            }

            // ORDER BY con CASE WHEN (solo para la query de datos, no para el count)
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                Expression<Integer> orderExpr;

                if (city != null && state != null) {
                    orderExpr = cb.<Integer>selectCase()
                            .when(cb.and(
                                    cb.equal(root.join("city", JoinType.LEFT).get("city"), city),
                                    cb.equal(root.join("state", JoinType.LEFT).get("state"), state)
                            ), 1)
                            .when(cb.equal(root.join("state", JoinType.LEFT).get("state"), state), 2)
                            .otherwise(3);
                } else if (state != null) {
                    orderExpr = cb.<Integer>selectCase()
                            .when(cb.equal(root.join("state", JoinType.LEFT).get("state"), state), 2)
                            .otherwise(3);
                } else {
                    orderExpr = cb.literal(3);
                }

                query.orderBy(
                        cb.asc(orderExpr),
                        cb.desc(root.get("id"))
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}