package com.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gg
 * @create 2020-12-13 下午5:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    private String name;
    private String author;
}
