package az.spring.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Data
public class SubCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "head_collection_id")
    @JsonIgnore
    private HeadCollection headCollection;

    @OneToMany(mappedBy = "subCollection", cascade = CascadeType.ALL)
    private List<Category> categories;
}
