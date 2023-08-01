package com.pamela.hh.heart.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class Node<K, V> {
    private K left;
    private V right;
}
