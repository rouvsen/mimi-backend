package az.spring.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class HeadCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "headCollection",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<SubCollection> subCollections;
}
