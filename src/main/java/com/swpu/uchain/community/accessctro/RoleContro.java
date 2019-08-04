package com.swpu.uchain.community.accessctro;

import com.swpu.uchain.community.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName RoleContro
 * @Author hobo
 * @Date 19-7-12 下午7:45
 * @Description
 **/

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleContro {
    RoleEnum role();
}
