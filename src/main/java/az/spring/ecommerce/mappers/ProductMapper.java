package az.spring.ecommerce.mappers;

import az.spring.ecommerce.model.Product;
import az.spring.ecommerce.request.ProductRequest;
import az.spring.ecommerce.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product fromRequestToModel(ProductRequest productRequest);

    ProductRequest fromModelToRequest(Product product);

    ProductResponse fromModelToResponse(Product product);

}