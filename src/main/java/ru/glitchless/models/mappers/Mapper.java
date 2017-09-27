package ru.glitchless.models.mappers;

import java.util.ArrayList;
import java.util.List;

public interface Mapper<IN, OUT> {
    OUT map(IN in);

    default List<OUT> map(List<IN> in) {
        final ArrayList<OUT> list = new ArrayList<>(in.size());
        in.forEach((IN it) -> list.add(map(it)));
        return list;
    }
}
