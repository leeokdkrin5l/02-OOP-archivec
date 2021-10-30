package com.swagger.doc.core.entity;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-05-02 下午4:53
 */
public class WrapSwagger extends Swagger {
    private Logger                        logger  = LoggerFactory.getLogger(WrapSwagger.class);
    //tag存储的所有的path
    private Map<Tag, List<PathOperation>> tagPath = new HashMap<>();

    public Map<Tag, List<PathOperation>> getTagPath() {
        return tagPath;
    }

    public void setTagPath(Map<Tag, List<PathOperation>> tagPath) {
        this.tagPath = tagPath;
    }

    public void process() {
        try {
            List<Tag> tagString = getTags();
            Map<String, Tag> tagMap = tagString.stream()
                .collect(Collectors.toMap(Tag::getName, o -> o, (existingValue, newValue) -> existingValue));
            Map<String, Path> pathMap = getPaths();
            if (pathMap != null)
                pathMap.forEach((k, v) -> processPath(k, v, tagMap));
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    public void processPath(String k, Path v, Map<String, Tag> tagMap) {
        for (Operation operation : v.getOperations()) {
            List<String> tagsList = operation.getTags();
            for (String s : tagsList) {
                Tag tag = tagMap.get(s);
                if (tag != null) {
                    List<PathOperation> mapList = tagPath.get(tag);
                    if (mapList == null)
                        mapList = new ArrayList<>();
                    PathOperation pathOperation = new PathOperation();
                    pathOperation.setHrefPath(k.replace("/", ""));
                    pathOperation.setRealPath(k);
                    pathOperation.setOperation(operation);
                    mapList.add(pathOperation);
                    tagPath.put(tag, mapList);
                }
            }
        }
    }

}
