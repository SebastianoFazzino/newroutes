package com.newroutes.models.mappers.sendinblue;


import com.newroutes.entities.sendinblue.WebHookEntity;
import com.newroutes.enums.sendinblue.Template;
import com.newroutes.models.mappers.BaseMapper;
import com.newroutes.models.sendinblue.WebHook;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {SendinblueServiceMap.class})
public interface WebHookMapper extends BaseMapper<WebHookEntity, WebHook> {

    @Override
    @Mapping(source = "templateId", target = "templateId")
    @Mapping(source = "templateId", target = "template", qualifiedByName = "idToTemplate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WebHookEntity convertToEntity(WebHook dto);

    @Named("idToTemplate")
    static Template idToTemplate(long id) {
        return Template.getById(id);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "template", ignore = true)
    @Mapping(target = "templateId", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    void mergeInfo(WebHookEntity source, @MappingTarget WebHookEntity target);
}
