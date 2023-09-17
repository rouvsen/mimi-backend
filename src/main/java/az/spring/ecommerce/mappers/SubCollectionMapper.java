package az.spring.ecommerce.mappers;

import az.spring.ecommerce.model.SubCollection;
import az.spring.ecommerce.request.SubCollectionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubCollectionMapper {
        SubCollection fromSubCollectionRequestToModel(SubCollectionRequest subCollectionRequest);
        SubCollectionRequest fromModelToSubCollectionRequest(SubCollection entity);
}
