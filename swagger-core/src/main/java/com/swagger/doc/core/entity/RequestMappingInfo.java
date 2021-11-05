package com.swagger.doc.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-03-30 下午4:59
 */
@Getter
@Setter
public class RequestMappingInfo {
    String name = "";

    String[] value = new String[]{};

    String[] path = new String[]{};

    RequestMethod[] method = new RequestMethod[]{};

    String[] params = new String[]{};

    String[] headers = new String[]{};

    String[] consumes = new String[]{};

    String[] produces = new String[]{};

}
