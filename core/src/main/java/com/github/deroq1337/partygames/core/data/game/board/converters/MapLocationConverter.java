package com.github.deroq1337.partygames.core.data.game.board.converters;

import com.github.deroq1337.partygames.core.data.game.board.serialization.MapLocation;
import lombok.RequiredArgsConstructor;
import net.cubespace.Yamler.Config.ConfigSection;
import net.cubespace.Yamler.Config.Converter.Converter;
import net.cubespace.Yamler.Config.InternalConverter;

import java.lang.reflect.ParameterizedType;

@RequiredArgsConstructor
public class MapLocationConverter implements Converter {

    private final InternalConverter internalConverter;

    @Override
    public Object toConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) {
        return ((MapLocation) o).toMap();
    }

    @Override
    public Object fromConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) {
        if (!(o instanceof ConfigSection configSection)) {
            return null;
        }

        if (configSection.getRawMap() == null) {
            return null;
        }

        return new MapLocation(configSection.getRawMap());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(MapLocation.class);
    }
}
