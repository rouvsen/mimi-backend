package az.spring.ecommerce.request;

import lombok.Data;

@Data
public class SearchRequest {

    private String column;
    private String value;

}
