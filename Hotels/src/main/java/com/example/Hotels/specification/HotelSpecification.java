package com.example.Hotels.specification;

import com.example.Hotels.entity.Hotel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class HotelSpecification {

    public static Specification<Hotel> filter(
            String name,
            String brand,
            String city,
            String country,
            String amenities) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null)
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));

            if (brand != null)
                predicates.add(cb.equal(root.get("brand"), brand));

            if (city != null)
                predicates.add(cb.equal(root.get("address").get("city"), city));

            if (country != null)
                predicates.add(cb.equal(root.get("address").get("country"), country));

            if (amenities != null)
                predicates.add(cb.isMember(amenities, root.get("amenities")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}