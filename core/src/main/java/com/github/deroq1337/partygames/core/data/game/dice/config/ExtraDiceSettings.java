package com.github.deroq1337.partygames.core.data.game.dice.config;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ExtraDiceSettings {

    private int min = 1;
    private int max = 3;
}
